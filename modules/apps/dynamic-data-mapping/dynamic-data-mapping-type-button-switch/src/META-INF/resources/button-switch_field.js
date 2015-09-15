AUI.add(
	'liferay-ddm-form-field-button-switch',
	function(A) {
		var ButtonSwitchField = A.Component.create(
			{
				ATTRS: {
					type: {
						value: 'button-switch'
					},

					value: {
						setter: '_setValue'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-button-switch',

				prototype: {
					_syncState: function() {
						this.get('container').one('input').val(this._buttonSwitch.get('activated'));
						console.log('sync: ' + this.get('container').one('input').val());
					},

					render: function() {
						var field = this;

						ButtonSwitchField.superclass.render.apply(this, arguments);

						if (this._buttonSwitch) {
							this._buttonSwitch.destroy();
						}

						this._buttonSwitch = new A.ButtonSwitch().render(this.get('container').one('.field')._node);

						field._syncState();

						this._buttonSwitch.after('activatedChange', function() {
							field._syncState();
						});
					},

					getTemplateContext: function() {
						var instance = this;

						var value = instance.get('value');

						if (instance.get('localizable')) {
							value = value[instance.get('locale')];
						}

						return A.merge(
							ButtonSwitchField.superclass.getTemplateContext.apply(instance, arguments), {}
						);
					},

					getValue: function() {
						return this._buttonSwitch.get('activated');
					},

					_setValue: function(value) {
						var instance = this;

						if (instance.get('localizable')) {
							for (var locale in value) {
								value[locale] = A.DataType.Boolean.parse(value[locale]);
							}
						}
						else {
							value = A.DataType.Boolean.parse(value);
						}

						return value;
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').ButtonSwitch = ButtonSwitchField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field', 'aui-button-switch']
	}
);