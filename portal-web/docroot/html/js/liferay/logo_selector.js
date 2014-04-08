AUI.add(
	'liferay-logo-selector',
	function(A) {
		var Lang = A.Lang;

		var DELETE_LOGO = 'DELETE_LOGO';

		var MAP_DELETE_LOGO = {
			src: DELETE_LOGO
		};

		var LogoSelector = A.Component.create(
			{
				ATTRS: {
					defaultLogoURL: {
						value: ''
					},

					editLogoFn: {
						setter: function(value) {
							var fn = function() {};

							if (Lang.isFunction(window[value])) {
								fn = window[value] || fn;
							}

							return fn;
						},
						validator: A.Lang.isString,
						value: ''
					},

					editLogoURL: {
						value: ''
					},

					logoDisplaySelector: {
						value: ''
					},

					logoURL: {
						value: ''
					},

					portletNamespace: {
						value: ''
					},

					randomNamespace: {
						value: ''
					}
				},

				BIND_UI_ATTRS: ['logoURL'],

				NAME: 'logoselector',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._portletNamespace = instance.get('portletNamespace');
						instance._randomNamespace = instance.get('randomNamespace');

						window[instance._randomNamespace + 'changeLogo'] = A.bind('_changeLogo', instance);
					},

					renderUI: function() {
						var instance = this;

						var portletNamespace = instance._portletNamespace;
						var randomNamespace = instance._randomNamespace;

						var contentBox = instance.get('contentBox');

						instance._avatar = contentBox.one('#' + randomNamespace + 'avatar');
						instance._deleteLogoButton = contentBox.one('.delete-logo');
						instance._deleteLogoInput = contentBox.one('#' + portletNamespace + 'deleteLogo');
						instance._fileEntryIdInput = contentBox.one('#' + portletNamespace + 'fileEntryId');
					},

					bindUI: function() {
						var instance = this;

						instance.get('contentBox').delegate('click', instance._openEditLogoWindow, '.edit-logo', instance);
						instance.get('contentBox').delegate('click', instance._onDeleteLogoClick, '.delete-logo', instance);
					},

					_changeLogo: function(url, fileEntryId) {
						var instance = this;

						instance.set('logoURL', url);

						if (fileEntryId) {
							instance._fileEntryIdInput.val(fileEntryId);
						}
					},

					_onDeleteLogoClick: function(event) {
						var instance = this;

						instance.set('logoURL', instance.get('defaultLogoURL'), MAP_DELETE_LOGO);
					},

					_openEditLogoWindow: function(event) {
						var instance = this;

						var editLogoURL = instance.get('editLogoURL');

						Liferay.Util.openWindow(
							{
								cache: false,
								dialog: {
									destroyOnHide: true,
									width: 600
								},
								id: instance._portletNamespace + 'changeLogo',
								title: Liferay.Language.get('upload-image'),
								uri: editLogoURL
							}
						);

						event.preventDefault();
					},

					_uiSetLogoURL: function(value, src) {
						var instance = this;

						var logoURL = value;

						var logoDisplaySelector = instance.get('logoDisplaySelector');

						var deleteLogo = src == DELETE_LOGO;

						instance._avatar.attr('src', logoURL);

						if (logoDisplaySelector) {
							var logoDisplay = A.one(logoDisplaySelector);

							if (logoDisplay) {
								logoDisplay.attr('src', logoURL);
							}
						}

						instance.get('editLogoFn').apply(instance, [logoURL, deleteLogo]);

						instance._deleteLogoInput.val(deleteLogo);
						instance._deleteLogoButton.attr('disabled', deleteLogo ? 'disabled' : '');
						instance._deleteLogoButton.toggleClass('disabled', deleteLogo);
					}
				}
			}
		);

		Liferay.LogoSelector = LogoSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-util-window']
	}
);