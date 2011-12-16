AUI().add(
	'liferay-layout-freeform',
	function(A) {
		var DDM = A.DD.DDM;

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

		Liferay.Layout.register = function(id) {
			var freeformLayoutDefaults = A.merge(
				Liferay.Layout.DEFAULT_LAYOUT_OPTIONS,
				{
					after: {
						'drag:start': function(event) {
							var instance = this;

							var proxyNode = instance.get('proxyNode');
							var node = DDM.activeDrag.get('node');
							var nodeId = node.get('id');

							proxyNode.one('.portlet-topper').html(getTitle(nodeId));
						}
					},
					lazyStart: false,
					on: {}
				}
			);

			var dragConfig = A.namespace.call(freeformLayoutDefaults, 'delegateConfig.dragConfig');

			dragConfig.startCentered = false;

			Liferay.Layout.layoutHandler = new Liferay.Layout.FreeFormLayout(freeformLayoutDefaults);
		};

		var FreeFormLayout = A.Component.create(
			{
				ATTRS: {
					proxyNode: {
						value: Liferay.Template.PORTLET
					}
				},

				EXTENDS: Liferay.Layout.ColumnLayout,

				NAME: 'FreeFormLayout',

				prototype: {
					portletZIndex: 100,

					initializer: function() {
						var instance = this;

						var placeholder = instance.get('placeholder');

						if (placeholder) {
							placeholder.addClass(Liferay.Layout.options.freeformPlaceholderClass);
						}

						Liferay.Layout.getPortlets().each(
							function(item, index, collection) {
								instance._setupNodeResize(item);
								instance._setupNodeStack(item);
							}
						);
					},

					alignPortlet: function(portletNode, referenceNode) {
						var instance = this;

						portletNode.setXY(referenceNode.getXY());

						instance.savePosition(portletNode);
					},

					savePosition: function(portletNode) {
						var portletId = Liferay.Util.getPortletId(portletNode.get('id'));

						var heightNode = portletNode.one('.portlet-content-container') || portletNode;

						Liferay.Layout.saveLayout(
							{
								cmd: 'drag',
								height: heightNode.getStyle('height'),
								left: portletNode.getStyle('left'),
								p_p_id: portletId,
								top: portletNode.getStyle('top'),
								width: portletNode.getStyle('width')
							}
						);
					},

					_onPortletMouseDown: function(event) {
						var instance = this;

						var portlet = event.currentTarget;

						portlet.setStyle('zIndex', instance.portletZIndex++);
					},

					_positionNode: function(event) {
						var instance = this;

						var activeDrag = DDM.activeDrag;
						var dragNode = activeDrag.get('dragNode');
						var portletNode = activeDrag.get('node');

						var activeDrop = instance.activeDrop;

						if (activeDrop) {
							FreeFormLayout.superclass._positionNode.apply(this, arguments);
						}

						dragNode.setStyle('display', 'block');

						instance.alignPortlet(portletNode, dragNode);

						dragNode.setStyle('display', 'none');
					},

					_setupNodeResize: function(node) {
						var instance = this;

						var resizable = node.hasClass('aui-resize');

						if (!resizable) {
							var resize = new A.Resize(
								{
									after: {
										end: function(event) {
											var info = event.info;

											var portletNode = this.get('node');

											var containerNode = portletNode.one('.portlet-content-container');

											if (containerNode) {
												var containerHeight = info.offsetHeight;

												var topperNode = portletNode.one('.portlet-topper');

												if (topperNode) {
													containerHeight -= topperNode.get('offsetHeight');
												}

												var contentNode = portletNode.one('.portlet-content');

												if (contentNode) {
													containerHeight -= contentNode.getPadding('tb');
												}

												containerNode.setStyle('height', containerHeight);

												portletNode.setStyle('height', 'auto');
											}

											instance.savePosition(portletNode);
										}
									},
									handles: 'r,br,b',
									node: node,
									proxy: true
								}
							);
						}
					},

					_setupNodeStack: function(node) {
						var instance = this;

						node.on('mousedown', A.bind(instance._onPortletMouseDown, instance));
					},

					_syncProxyNodeSize: function() {
						var instance = this;

						var node = DDM.activeDrag.get('node');
						var proxyNode = instance.get('proxyNode');

						if (proxyNode) {
							var offsetHeight = node.get('offsetHeight');
							var offsetWidth = node.get('offsetWidth');

							proxyNode.set('offsetHeight', offsetHeight);
							proxyNode.set('offsetWidth', offsetWidth);
						}
					}
				}
			}
		);

		Liferay.Layout.FreeFormLayout = FreeFormLayout;
	},
	'',
	{
		requires: ['aui-resize', 'dd', 'liferay-layout-column']
	}
);