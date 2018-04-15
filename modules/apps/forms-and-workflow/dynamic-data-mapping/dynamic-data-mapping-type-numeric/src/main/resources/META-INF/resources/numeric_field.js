AUI.add(
	'liferay-ddm-form-field-numeric',
	function(A) {
		var CSS_SETTINGS_SIDEBAR = A.getClassName('liferay', 'ddm', 'form', 'builder', 'field', 'settings', 'sidebar', 'content');

		new A.TooltipDelegate(
			{
				position: 'left',
				trigger: '.liferay-ddm-form-field-numeric .trigger-tooltip',
				triggerHideEvent: ['blur', 'mouseleave'],
				triggerShowEvent: ['focus', 'mouseover'],
				visible: false
			}
		);

		var NumericField = A.Component.create(
			{
				ATTRS: {
					dataType: {
						value: 'integer'
					},

					placeholder: {
						state: true,
						value: ''
					},

					predefinedValue: {
						value: ''
					},

					type: {
						value: 'numeric'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-numeric',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('render', instance._afterNumericFieldRender, instance)
						);

						instance.evaluate = A.debounce(
							function() {
								NumericField.superclass.evaluate.apply(instance, arguments);
							},
							300
						);
					},

					applyMask: function(fieldNode) {
						var instance = this;

						var dataType = instance.get('dataType');
						var maskController = fieldNode.getData('mask-controller');

						if (maskController) {
							maskController.destroy();
						}

						var numberMaskOptions = instance.getIntegerMaskConfig();

						if (dataType == 'double') {
							numberMaskOptions = instance.getDecimalMaskConfig();
						}

						var numberMask = DDMNumeric.createNumberMask(numberMaskOptions);

						instance.lastDataType = dataType;
						instance.maskedInputController = DDMNumeric.vanillaTextMask(
							{
								inputElement: fieldNode.getDOM(),
								mask: numberMask
							}
						);

						fieldNode.setData('mask-controller', instance.maskedInputController);
					},

					getChangeEventName: function() {
						return 'input';
					},

					getEvaluationContext: function(context) {
						return {
							dataType: context.dataType
						}
					},

					getDecimalMaskConfig: function() {
						var instance = this;

						return {
							allowDecimal: true,
							decimalLimit: 6,
							decimalSymbol: ',',
							includeThousandsSeparator: true,
							prefix: '',
							thousandsSeparatorSymbol: '.'
						};
					},

					getIntegerMaskConfig: function() {
						var instance = this;

						return {
							allowLeadingZeroes: true,
							includeThousandsSeparator: false,
							prefix: ''
						};
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							NumericField.superclass.getTemplateContext.apply(instance, arguments),
							{
								predefinedValue: instance.get('predefinedValue')
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						var value = inputNode.val();

						if (value === '') {
							return value;
						}

						var dataType = instance.get('dataType');

						if (dataType === 'integer') {
							return parseInt(value, 10);
						}

						return parseFloat(value);
					},

					showErrorMessage: function() {
						var instance = this;

						NumericField.superclass.showErrorMessage.apply(instance, arguments);
					},

					_afterNumericFieldRender: function() {
						var instance = this;

						var fieldNode = instance.getInputNode();
						var fieldSidebar = A.one('.' + CSS_SETTINGS_SIDEBAR + ' .liferay-ddm-form-field-numeric');

						if ((instance.maskedInputController) && (instance.get('dataType') == instance.lastDataType)) {
							return;
						}

						if ((instance.maskedInputController) && (fieldSidebar)) {
							fieldNode = fieldSidebar.one('input');
						}

						instance.applyMask(fieldNode);
					}

				}
			}
		);

		Liferay.namespace('DDM.Field').Numeric = NumericField;
	},
	'',
	{
		requires: ['aui-autosize-deprecated', 'aui-tooltip', 'liferay-ddm-form-renderer-field']
	}
);