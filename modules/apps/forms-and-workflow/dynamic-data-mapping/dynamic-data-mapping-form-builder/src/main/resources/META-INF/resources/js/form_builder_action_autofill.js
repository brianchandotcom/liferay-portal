AUI.add(
	'liferay-ddm-form-builder-action-autofill',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var TPL_CONTAINER_INPUT_OUTPUT_COMPONENT = '<div class="col-md-9 container-input-field container-input-field-{index}"></div>';

		var TPL_CONTAINER_INPUT_OUTPUT_FIELD = '<div class="col-md-3 container-input-label">{field}&nbsp;{required}</div>';

		var TPL_LABEL_ACTION = '<h4>{message}</h4>';

		var TPL_REQUIRED_ACTION = '<label>' +
				'<svg aria-hidden="true" class="lexicon-icon lexicon-icon-asterisk reference-mark">' +
					'<use xlink:href="' + themeDisplay.getPathThemeImages() + '/lexicon/icons.svg#asterisk" />' +
				'</svg>' +
			'</label>';

		var FormBuilderActionAutofill = A.Component.create(
			{
				ATTRS: {
					action: {
						value: ''
					},

					fields: {
						value: []
					},

					getDataProviders: {
						value: []
					},

					index: {
						value: ''
					},

					options: {
						value: []
					},

					strings: {
						value: {
							dataProviderParameterInput: Liferay.Language.get('data-provider-parameter-input'),
							dataProviderParameterInputDescription: Liferay.Language.get('data-provider-parameter-input-description'),
							dataProviderParameterOutput: Liferay.Language.get('data-provider-parameter-output'),
							dataProviderParameterOutputDescription: Liferay.Language.get('data-provider-parameter-output-description'),
							requiredField: Liferay.Language.get('required-field')
						}
					}
				},

				AUGMENTS: [],

				EXTENDS: Liferay.DDM.FormBuilderAction,

				NAME: 'liferay-ddm-form-builder-action-autofill',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._inputParameters = [];

						instance._outputParameters = [];
					},

					getValue: function() {
						var instance = this;

						var selectedDataProviderInstance = instance._dataProvidersList.getValue();

						return {
							action: 'auto-fill',
							ddmDataProviderInstanceUUID: instance._getUUId(selectedDataProviderInstance[0]),
							inputs: instance._getInputValue(),
							outputs: instance._getOutputValue(),
							requiredInputs: instance._getRequiredInputs()
						};
					},

					render: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var index = instance.get('index');

						var fieldsListContainer = boundingBox.one('.target-' + index);

						fieldsListContainer.append(
							Lang.sub(
								TPL_LABEL_ACTION,
								{
									message: Liferay.Language.get('from-data-provider')
								}
							)
						);

						instance._createDataProviderList().render(fieldsListContainer);

						instance._fillDataProvidersSelectField();
					},

					_afterDataProviderChange: function(event) {
						if (!event.newVal || !event.newVal[0]) {
							return;
						}

						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var index = instance.get('index');

						boundingBox.one('.additional-info-' + index).empty();

						A.io.request(
							Liferay.DDM.Settings.getDataProviderParametersSettingsURL,
							{
								data: instance._getDataProviderPayload(event.newVal[0]),
								method: 'GET',
								on: {
									success: function(event, id, xhr) {
										var result = xhr.responseText;

										if (result) {
											instance._createDataProviderParametersSettings(JSON.parse(result));
										}
									}
								}
							}
						);
					},

					_createDataProviderInputParametersSettings: function(inputParameters) {
						var instance = this;

						var index = instance.get('index');

						var boundingBox = instance.get('boundingBox');

						var inputParametersContainer = boundingBox.one('.additional-info-' + index).one('.data-provider-parameter-input-list');

						var inputParameterField;

						var value;

						var action = instance.get('action');

						for (var i = 0; i < inputParameters.length; i++) {
							var label = inputParameters[i].label;
							var name = inputParameters[i].name;
							var requiredField = inputParameters[i].required;

							value = [];

							inputParametersContainer.append(
								Lang.sub(
									TPL_CONTAINER_INPUT_OUTPUT_FIELD,
									{
										field: label,
										required: requiredField ? TPL_REQUIRED_ACTION : ''
									}
								)
							);

							inputParametersContainer.append(
								Lang.sub(
									TPL_CONTAINER_INPUT_OUTPUT_COMPONENT,
									{
										index: i
									}
								)
							);

							if (action && action.inputs && action.inputs[name]) {
								value = [action.inputs[name]];
							}

							inputParameterField = instance.createSelectField(
								{
									fieldName: instance.get('index') + '-action',
									options: instance.getFieldsByType(inputParameters[i].type),
									showLabel: false,
									value: value,
									visible: true
								}
							).render(inputParametersContainer.one('.container-input-field-' + i));

							instance._inputParameters.push(
								{
									field: inputParameterField,
									parameter: name,
									required: requiredField
								}
							);
						}
					},

					_createDataProviderList: function() {
						var instance = this;

						instance._dataProvidersList = instance.createSelectField(
							{
								fieldName: instance.get('index') + '-action',
								options: [],
								showLabel: false,
								visible: true
							}
						);

						instance._dataProvidersList.get('container').addClass('lfr-ddm-form-field-container-inline');

						instance._dataProvidersList.after('valueChange', A.bind(instance._afterDataProviderChange, instance));

						return instance._dataProvidersList;
					},

					_createDataProviderOutputParametersSettings: function(outputParameters) {
						var instance = this;

						var index = instance.get('index');

						var boundingBox = instance.get('boundingBox');

						var outputParametersContainer = boundingBox.one('.additional-info-' + index).one('.data-provider-parameter-output-list');

						var outputParameterField;

						var action = instance.get('action');

						var value;

						for (var i = 0; i < outputParameters.length; i++) {
							var name = outputParameters[i].name;

							value = [];

							outputParametersContainer.append(
								Lang.sub(
									TPL_CONTAINER_INPUT_OUTPUT_FIELD,
									{
										field: name,
										required: ''
									}
								)
							);

							outputParametersContainer.append(
								Lang.sub(
									TPL_CONTAINER_INPUT_OUTPUT_COMPONENT,
									{
										index: i
									}
								)
							);

							if (action && action.outputs && action.outputs[name]) {
								value = [action.outputs[name]];
							}

							outputParameterField = instance.createSelectField(
								{
									fieldName: instance.get('index') + '-action',
									label: outputParameters[i].name,
									options: instance.getFieldsByType(outputParameters[i].type),
									showLabel: false,
									visible: true
								}
							).render(outputParametersContainer.one('.container-input-field-' + i));

							instance._outputParameters.push(
								{
									field: outputParameterField,
									parameter: name
								}
							);

							outputParameterField.setValue(value);
						}
					},

					_createDataProviderParametersSettings: function(dataProviderParametersSettings) {
						var instance = this;

						var index = instance.get('index');

						var dataProviderParametersContainer = instance.get('boundingBox').one('.additional-info-' + index);

						instance._retriveRequiredInputs(dataProviderParametersSettings.inputs);

						dataProviderParametersContainer.setHTML(instance._getRuleContainerTemplate(dataProviderParametersSettings.inputs));

						instance._createDataProviderInputParametersSettings(dataProviderParametersSettings.inputs);

						instance._createDataProviderOutputParametersSettings(dataProviderParametersSettings.outputs);
					},

					_fillDataProvidersSelectField: function() {
						var instance = this;

						instance._renderDataProvidersList(instance.get('getDataProviders'));
					},

					_getDataProviderPayload: function(ddmDataProviderInstanceId) {
						var instance = this;

						var payload = {
							ddmDataProviderInstanceId: ddmDataProviderInstanceId
						};

						return payload;
					},

					_getInputValue: function() {
						var instance = this;

						var inputParameters = instance._inputParameters;

						var inputParameterValues = {};

						for (var i = 0; i < inputParameters.length; i++) {
							var value = inputParameters[i].field.getValue();

							if (inputParameters[i].parameter && value) {
								inputParameterValues[inputParameters[i].parameter] = value[0];
							}
						}

						return inputParameterValues;
					},

					_getOutputValue: function() {
						var instance = this;

						var outputParameters = instance._outputParameters;

						var outputParameterValues = {};

						for (var i = 0; i < outputParameters.length; i++) {
							var value = outputParameters[i].field.getValue();

							if (outputParameters[i].parameter && value) {
								outputParameterValues[outputParameters[i].parameter] = value[0];
							}
						}

						return outputParameterValues;
					},

					_getRequiredInputs: function() {
						var instance = this;

						return instance._requiredInputs;
					},

					_getRuleContainerTemplate: function(inputs) {
						var instance = this;

						var strings = instance.get('strings');

						var dataProviderParametersTemplateRenderer = Liferay.DDM.SoyTemplateUtil.getTemplateRenderer('DDMDataProviderParameter.render');

						var container = document.createDocumentFragment();

						new dataProviderParametersTemplateRenderer(
							{
								hasInputs: inputs.length > 0,
								hasRequiredInputs: !AObject.isEmpty(instance._getRequiredInputs()),
								pathThemeImages: themeDisplay.getPathThemeImages(),
								strings: strings
							},
							container
						);

						return container.firstChild.outerHTML;
					},

					_getUUId: function(id) {
						var instance = this;

						var dataProviderList = instance._dataProvidersList.get('options');

						var uuid;

						for (var i = 0; i < dataProviderList.length; i++) {
							if (dataProviderList[i].value === id) {
								uuid = dataProviderList[i].uuid;

								break;
							}
						}

						return uuid;
					},

					_renderDataProvidersList: function(result) {
						var instance = this;

						var dataProvidersList = [];

						var uuid;

						var value;

						var action = instance.get('action');

						if (action && action.ddmDataProviderInstanceUUID) {
							uuid = action.ddmDataProviderInstanceUUID;
						}

						for (var i = 0; i < result.length; i++) {
							if (result[i].uuid === uuid) {
								value = result[i].id;
							}

							dataProvidersList.push(
								{
									label: result[i].name,
									uuid: result[i].uuid,
									value: result[i].id
								}
							);
						}

						instance._dataProvidersList.set('options', dataProvidersList);

						if (value) {
							instance._dataProvidersList.setValue([value]);
						}
					},

					_retriveRequiredInputs: function(inputs) {
						var instance = this;

						instance._requiredInputs = {};

						for (var i = 0; i < inputs.length; i++) {
							if (inputs[i].required) {
								instance._requiredInputs[inputs[i].name] = true;
							}
						}
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderActionAutofill = FormBuilderActionAutofill;
	},
	'',
	{
		requires: ['liferay-ddm-form-builder-action']
	}
);