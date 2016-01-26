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

						if (instance._dialog) {
							instance._dialog.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindFullscreenButton: function() {
						var instance = this;

						instance._eventHandles.push(
							instance.get('fullscreenButton').on('click', instance._openDialog, instance)
						);
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.on('fullscreenButtonChange', instance._bindFullscreenButton, instance),
							instance.on('urlChange', instance._updateDialog, instance)
						];

						instance._bindFullscreenButton();
					},

					_getDialog: function() {
						var instance = this;

						var dialog = instance._dialog;

						if (!dialog) {
							dialog = new Liferay.UrlPreview(
								{
									title: 'Pages',
									url: instance.get('url')
								}
							);
						}

						return dialog;
					},

					_openDialog: function() {
						var instance = this;

						var dialog = instance._getDialog();

						dialog.open();
					},

					_updateDialog: function(event) {
						var instance = this;

						var dialog = instance._getDialog();

						dialog.set('url', instance.get('url'));
					}
				}
			}
		);

		Liferay.LayoutTreeFullscreen = LayoutTreeFullscreen;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base', 'liferay-url-preview']
	}
);