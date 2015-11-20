AUI.add(
	'liferay-admin',
	function(A) {
		var Lang = A.Lang;

		var STR_CLICK = 'click';

		var STR_FORM = 'form';

		var STR_URL = 'url';

		var Admin = A.Component.create(
			{
				ATTRS: {
					form: {
						setter: A.one,
						value: null
					},

					redirectUrl: {
						validator: Lang.isString,
						value: null
					},

					submitButton: {
						validator: Lang.isString,
						value: null
					},

					url: {
						value: null
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'admin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._errorCount = 0;

						instance._eventHandles = [];

						var submitButton = instance.one(instance.get('submitButton'));

						if (submitButton) {
							instance._eventHandles.push(
								submitButton.on(STR_CLICK, instance._submitForm, instance)
							);
						}

						instance._bindXuggler();
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_addInputsFromData: function(node) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var data = node.getData();

						for (var key in data) {
							if (data.hasOwnProperty(key)) {
								var namespaceKey = instance.ns(key);

								form.append('<input id="' + namespaceKey + '" name="' + namespaceKey + '" type="hidden" value="' + data[key] + '" />');
							}
						}
					},

					_bindXuggler: function() {
						var instance = this;

						var installXugglerButton = instance.one('#installXugglerButton');

						if (installXugglerButton) {
							instance._eventHandles.push(
								installXugglerButton.on(STR_CLICK, instance._installXuggler, instance)
							);

							instance._installXugglerButton = installXugglerButton;

							instance._xugglerProgressInfo = instance.one('#xugglerProgressInfo');
						}
					},

					_installXuggler: function(event) {
						var instance = this;

						Liferay.Util.toggleDisabled(instance._installXugglerButton, true);

						var form = instance.get(STR_FORM);

						instance._addInputsFromData(event.currentTarget);

						var loadingMask = new A.LoadingMask(
							{
								'strings.loading': Liferay.Language.get('xuggler-library-is-installed'),
								target: A.one('#adminXugglerPanel')
							}
						);

						loadingMask.show();

						A.io.request(
							instance.get(STR_URL),
							{
								after: {
									complete: function() {
										loadingMask.hide();

										instance._bindXuggler();
									},
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										var adminXugglerPanel = AUI.$(responseData).find('#adminXugglerPanel');

										var adminXugglerPanelHTML = adminXugglerPanel.html();

										AUI.$('#adminXugglerPanel').html(adminXugglerPanelHTML);
									}
								},
								dataType: 'HTML',
								form: form.getDOM()
							}
						);
					},

					_submitForm: function(event) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var redirect = instance.one('#redirect', form);

						if (redirect) {
							redirect.val(instance.get('redirectURL'));
						}

						instance._addInputsFromData(event.currentTarget);

						submitForm(
							form,
							instance.get(STR_URL)
						);
					}
				}
			}
		);

		Liferay.Portlet.Admin = Admin;
	},
	'',
	{
		requires: ['aui-loading-mask-deprecated', 'liferay-portlet-base']
	}
);