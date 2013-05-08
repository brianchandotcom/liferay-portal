AUI.add(
	'liferay-layout-freeform',
	function(A) {
		var DDM = A.DD.DDM;

		var Layout = Liferay.Layout;

		Layout.register = function() {
			var freeformLayoutDefaults = A.merge(
				Layout.DEFAULT_LAYOUT_OPTIONS,
				{
					after: {
						'drag:start': function(event) {
							var instance = this;

							var node = DDM.activeDrag.get('node');
							var nodeId = node.get('id');

							var proxyNode = instance.get('proxyNode');

							proxyNode.one('.portlet-topper').html(Layout._getPortletTitle(nodeId));
						}
					},
					lazyStart: false,
					on: {
						'drag:end': function(event) {
							var instance = this;

							var node = event.target.get('node');

							node.removeClass('yui3-dd-dragging');
						}
					},
					proxy: {
						positionProxy: true
					}
				}
			);

			var dragConfig = A.namespace.call(freeformLayoutDefaults, 'delegateConfig.dragConfig');

			dragConfig.startCentered = false;

			Layout.layoutHandler = new Layout.FreeFormLayout(freeformLayoutDefaults);
		};

		var FreeFormLayout = A.Component.create(
			{
				ATTRS: {
					proxyNode: {
						value: Liferay.Template.PORTLET
					}
				},

				EXTENDS: Layout.ColumnLayout,

				NAME: 'FreeFormLayout',

				prototype: {
					portletZIndex: 100,

					initializer: function() {
						var instance = this;

						var placeholder = instance.get('placeholder');

						if (placeholder) {
							placeholder.addClass(Layout.options.freeformPlaceholderClass);
						}

						Layout.getPortlets().each(
							function(item, index, collection) {
								instance._setupNodeResize(item);
								instance._setupNodeStack(item);
							}
						);

						Liferay.on('addPortlet', instance._onAddPortlet, instance);
					},

					alignPortlet: function(portletNode, referenceNode) {
						var instance = this;

						portletNode.setXY(referenceNode.getXY());

						instance.savePosition(portletNode);
					},

					savePosition: function(portletNode) {
						var portletId = Liferay.Util.getPortletId(portletNode.get('id'));

						var heightNode = portletNode.one('.portlet-content-container') || portletNode;

						Layout.saveLayout(
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

					_onAddPortlet: function(event) {
						var instance = this;

						var portlet = event.portlet;

						instance._setupNodeResize(portlet);
						instance._setupNodeStack(portlet);
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

						var resizable = node.hasClass('resize');

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

						node.on('mousedown', A.bind('_onPortletMouseDown', instance));
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

		Layout.FreeFormLayout = FreeFormLayout;
	},
	'',
	{
		requires: ['aui-resize-deprecated', 'liferay-layout-column']
	}
);