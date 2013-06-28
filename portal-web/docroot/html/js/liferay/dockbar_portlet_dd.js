AUI.add(
	'liferay-dockbar-portlet-dd',
	function(A) {
		var Dockbar = Liferay.Dockbar;
		var Layout = Liferay.Layout;

		var NAME = 'portletdd';

		var SELECTOR_ADD_CONTENT_ITEM = '.add-content-item';

		var STR_NODE = 'node';

		var PortletDragDrop = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				ATTRS: {
					srcNode: {
						setter: A.one
					}
				},

				prototype: {
					initializer: function() {
						var instance = this;

						instance._bindUIDragDrop();
					},

					_bindUIDragDrop: function() {
						var instance = this;

						var portletItemOptions = {
							delegateConfig: {
								container: instance.get('srcNode'),
								dragConfig: {
									clickPixelThresh: 0,
									clickTimeThresh: 0
								},
								invalid: '.lfr-portlet-used',
								target: false
							},
							dragNodes: '[data-draggable]',
							dropContainer: function(dropNode) {
								return dropNode.one(Layout.options.dropContainer);
							}
						};

						var defaultLayoutOptions = Layout.DEFAULT_LAYOUT_OPTIONS;

						if (defaultLayoutOptions) {
							portletItemOptions.on = defaultLayoutOptions.on;
						}

						var portletItemClass = 'PortletItem';

						if (themeDisplay.isFreeformLayout()) {
							portletItemClass = 'FreeFormPortletItem';
						}

						var portletItem = new Dockbar[portletItemClass](portletItemOptions);

						portletItem.on('drag:end', instance._onDragEnd, instance);

						portletItem.delegate.dd.addInvalid(SELECTOR_ADD_CONTENT_ITEM);

						instance._portletItem = portletItem;

						Liferay.fire('initLayout');
					},

					_onDragEnd: function(event) {
						var instance = this;

						var portletItem = event.currentTarget;

						var appendNode = portletItem.appendNode;

						if (appendNode && appendNode.inDoc()) {
							var portletNode = event.target.get(STR_NODE);

							instance.fire(
								'dragEnd',
								{
									appendNode: appendNode,
									portletNode: portletNode
								}
							);
						}
					}
				}
			}
		);

		Dockbar.PortletDragDrop = PortletDragDrop;
	},
	'',
	{
		requires: [ 'aui-base',	'dd', 'liferay-dockbar', 'liferay-layout', 'liferay-layout-column',	'liferay-layout-freeform', 'liferay-portlet-base' ]
	}
);