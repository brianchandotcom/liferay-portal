AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var SelectField = A.Component.create(
			{
				ATTRS: {
					options: {
						validator: Array.isArray,
						value: []
					},

					type: {
						value: 'select'
					},

					value: {
						setter: '_setValue',
						value: []
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-select',

				prototype: {
					getOptions: function() {
						var instance = this;

						return A.map(
							instance.get('options'),
							function(item) {
								return {
									label: item.label[instance.get('locale')],
									status: instance._getOptionStatus(item),
									value: item.value
								};
							}
						);
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
								SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								options: instance.getOptions()
							}
						);
					},

					_getOptionStatus: function(option) {
						var instance = this;

						var status = '';

						var value = instance.get('value');

						if (instance.get('localizable')) {
							value = value[instance.get('locale')] || [];
						}

						if (value.indexOf(option.value) > -1) {
							status = 'selected';
						}

						return status;
					},

					_setValue: function(val) {
						return val || [];
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);