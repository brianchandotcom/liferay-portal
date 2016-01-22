AUI.add(
	'liferay-layout-tree-fullscreen',
	function(A) {
		var Lang = A.Lang;

		var LayoutTreeFullscreen = A.Component.create(
			{
				ATTRS: {
					fullscreenButton: {
						setter: A.one
					},

					url: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'layouttreefullscreen',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.get('fullscreenButton').on('click', instance._openDialog, instance)
						];
					},

					_openDialog: function() {
						var instance = this;

						var dialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									centered: true,
									destroyOnHide: true,
									draggable: false
								},
								title: 'Pages',
								uri: instance.get('url')
							}
						);
					}
				}
			}
		);

		Liferay.LayoutTreeFullscreen = LayoutTreeFullscreen;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base']
	}
);