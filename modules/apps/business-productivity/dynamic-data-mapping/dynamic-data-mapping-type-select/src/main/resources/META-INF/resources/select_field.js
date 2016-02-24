AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var Lang = A.Lang;

		var TPL_OPTION = '<option>{label}</option>';

		var SelectField = A.Component.create(
			{
				ATTRS: {
					dataSourceOptions: {
						value: []
					},

					dataSourceType: {
						value: 'manual'
					},

					dataSourceURL: {
						value: '/o/dynamic-data-mapping-data-provider/'
					},

					ddmDataProviderInstanceId: {
						value: 0
					},

					multiple: {
						value: false
					},

					options: {
						validator: Array.isArray,
						value: []
					},

					strings: {
						value: {
							chooseAnOption: Liferay.Language.get('choose-an-option'),
							dynamicallyLoadedData: Liferay.Language.get('dynamically-loaded-data')
						}
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
					getContextValue: function() {
						var instance = this;

						var value = SelectField.superclass.getContextValue.apply(instance, arguments);

						if (!Array.isArray(value)) {
							try {
								value = JSON.parse(value);
							}
							catch (e) {
								value = [value];
							}
						}

						return value[0] || '';
					},

					getOptions: function() {
						var instance = this;

						var options = instance.get('options');

						if (instance.get('dataSourceType') !== 'manual') {
							options = instance.get('dataSourceOptions');
						}

						return A.map(
							options,
							function(item) {
								var label = item.label;

								if (Lang.isObject(label)) {
									label = label[instance.get('locale')];
								}

								return {
									label: label,
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
								multiple: instance.get('multiple') ? 'multiple' : '',
								options: instance.getOptions(),
								strings: instance.get('strings'),
								value: instance.getContextValue()
							}
						);
					},

					render: function() {
						var instance = this;

						var dataSourceType = instance.get('dataSourceType');

						SelectField.superclass.render.apply(instance, arguments);

						if (dataSourceType !== 'manual') {
							if (instance.get('builder')) {
								var inputNode = instance.getInputNode();

								var strings = instance.get('strings');

								inputNode.attr('disabled', true);

								inputNode.html(
									Lang.sub(
										TPL_OPTION,
										{
											label: strings.dynamicallyLoadedData
										}
									)
								);
							}
							else {
								var container = instance.get('container');

								instance._getDataSourceData(
									function(options) {
										instance.set('dataSourceOptions', options);

										container.html(instance.getTemplate());
									}
								);
							}
						}

						return instance;
					},

					_getDataSourceData: function(callback) {
						var instance = this;

						var form = instance.getRoot();

						A.io.request(
							instance.get('dataSourceURL'),
							{
								data: {
									fieldName: instance.get('name'),
									serializedDDMForm: JSON.stringify(form.get('definition'))
								},
								dataType: 'JSON',
								method: 'GET',
								on: {
									failure: function() {
										callback.call(instance, null);
									},
									success: function() {
										var result = this.get('responseData');

										callback.call(instance, result);
									}
								}
							}
						);
					},

					_getOptionStatus: function(option) {
						var instance = this;

						var status = '';

						if (instance.getContextValue() === option.value) {
							status = 'selected';
						}

						return status;
					},

					_renderErrorMessage: function() {
						var instance = this;

						SelectField.superclass._renderErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-select-wrapper');

						inputGroup.insert(container.one('.help-block'), 'after');
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