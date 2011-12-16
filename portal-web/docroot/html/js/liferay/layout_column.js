AUI().add(
	'liferay-layout-column',
	function(A) {
		var DDM = A.DD.DDM;

		var CSS_DRAGGING = 'aui-dragging';

		var getTitle = A.cached(
			function(id) {
				var portletBoundary = A.one('#' + id);

				var portletTitle = portletBoundary.one('.portlet-title');

				if (!portletTitle) {
					portletTitle = Layout.PROXY_NODE_ITEM.one('.portlet-title');

					var title = portletBoundary.one('.portlet-title-default');

					var titleText = '';

					if (title) {
						titleText = title.html();
					}

					portletTitle.html(titleText);
				}

				return portletTitle.outerHTML();
			}
		);

		Liferay.Layout.register = function() {
			var columnLayoutDefaults = A.merge(
				Liferay.Layout.DEFAULT_LAYOUT_OPTIONS,
				{
					after: {
						'drag:start': function(event) {
							var node = DDM.activeDrag.get('node');
							var nodeId = node.get('id');

							Liferay.Layout.PORTLET_TOPPER.html(getTitle(nodeId));

							Liferay.Layout._columnContainer.addClass(CSS_DRAGGING);
						},

						'drag:end': function(event) {
							Liferay.Layout._columnContainer.removeClass(CSS_DRAGGING);
						}
					}
				}
			);

			Liferay.Layout._columnContainer = A.all(columnLayoutDefaults.columnContainer);

			Liferay.Layout.layoutHandler = new Liferay.Layout.ColumnLayout(columnLayoutDefaults);

			Liferay.Layout.syncDraggableClassUI();
		};

		var ColumnLayout = A.Component.create(
			{
				ATTRS: {
					proxyNode: {
						value: Liferay.Layout.PROXY_NODE
					}
				},

				NAME: 'ColumnLayout',

				EXTENDS: A.PortalLayout,

				prototype: {
					dragItem: 0,

					_positionNode: function(event) {
						var instance = this;

						var portalLayout = event.currentTarget;
						var activeDrop = portalLayout.lastAlignDrop || portalLayout.activeDrop;

						if (activeDrop) {
							var dropNode = activeDrop.get('node');
							var isStatic = dropNode.isStatic;

							if (isStatic) {
								var start = (isStatic == 'start');

								portalLayout.quadrant = (start ? 4 : 1);
							}

							ColumnLayout.superclass._positionNode.apply(this, arguments);
						}
					},

					_syncProxyNodeSize: function() {
						var instance = this;

						var dragNode = DDM.activeDrag.get('dragNode');
						var proxyNode = instance.get('proxyNode');

						if (proxyNode && dragNode) {
							dragNode.set('offsetHeight', 30);
							dragNode.set('offsetWidth', 200);

							proxyNode.set('offsetHeight', 30);
							proxyNode.set('offsetWidth', 200);
						}
					}
				}
			}
		);

		Liferay.Layout.ColumnLayout = ColumnLayout;
	},
	'',
	{
		requires: ['aui-portal-layout', 'dd']
	}
);