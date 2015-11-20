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

						instance._eventHandles = [];

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						instance._eventHandles.push(
							instance.get(STR_FORM).delegate(
								STR_CLICK,
								A.bind('_onSubmit', instance),
								instance.get('submitButton')
							)
						);
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						instance._eventHandles = null;
					},

					_addInputsFromData: function(data) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var inputsArray = A.Object.map(
							data,
							function(value, key) {
								var nsKey = instance.ns(key);

								return '<input id="' + nsKey + '" name="' + nsKey + '" type="hidden" value="' + value + '" />';
							}
						);

						form.append(inputsArray.join(''));
					},

					_installXuggler: function(event) {
						var instance = this;

						var form = instance.get(STR_FORM);

						A.one('#adminXugglerPanelContent').load(
							instance.get(STR_URL),
							{
								form: form.getDOM(),
								loadingMask: {
									'strings.loading': Liferay.Language.get('xuggler-library-is-installed')
								},
								selector: '#adminXugglerPanelContent',
								where: 'outer'
							}
						);
					},

					_onSubmit: function(event) {
						var instance = this;

						var data = event.currentTarget.getData();
						var form = instance.get(STR_FORM);

						var cmd = data.cmd;
						var redirect = instance.one('#redirect', form);

						if (redirect) {
							redirect.val(instance.get('redirectURL'));
						}

						instance._addInputsFromData(data);

						if (!!cmd && cmd === 'installXuggler') {
							var cmdNode = instance.one('#cmd');

							instance._installXuggler();

							if (cmdNode) {
								cmdNode.remove();
							}

							instance._installXuggler();
						}
						else {
							submitForm(
								form,
								instance.get(STR_URL)
							);
						}
					}
				}
			}
		);

		Liferay.Portlet.Admin = Admin;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'liferay-portlet-base']
	}
);