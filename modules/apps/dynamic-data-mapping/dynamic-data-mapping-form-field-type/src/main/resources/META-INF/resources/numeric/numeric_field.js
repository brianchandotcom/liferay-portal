AUI.add(
	'liferay-ddm-form-field-numeric',
	function(A) {
		var CSS_SETTINGS_SIDEBAR = A.getClassName('liferay', 'ddm', 'form', 'builder', 'field', 'settings', 'sidebar', 'content');

		new A.TooltipDelegate(
			{
				cssClass: 'clay-tooltip',
				opacity: 1,
				position: 'right',
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

					symbols: {
						value: {
							decimalSymbol: '.',
							thousandsSeparator: ','
						}
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

					getDecimalMaskConfig: function() {
						var instance = this;

						var symbols = instance.get('symbols');

						return {
							allowDecimal: true,
							allowLeadingZeroes: true,
							decimalLimit: null,
							decimalSymbol: symbols.decimalSymbol,
							includeThousandsSeparator: false,
							prefix: ''
						};
					},

					getEvaluationContext: function(context) {
						return {
							dataType: context.dataType
						};
					},

					getIntegerMaskConfig: function() {
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

					showErrorMessage: function() {
						var instance = this;

						NumericField.superclass.showErrorMessage.apply(instance, arguments);
					},

					_afterNumericFieldRender: function() {
						var instance = this;

						var fieldNode = instance.getInputNode();
						var fieldSidebar = A.one('.' + CSS_SETTINGS_SIDEBAR + ' .liferay-ddm-form-field-numeric');

						if (instance.maskedInputController && fieldSidebar) {
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