AUI.add(
	'liferay-ddm-form-field-numeric',
	function(A) {
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

						instance.bindInputEvent('keypress', A.bind('_onNumericFieldKeyPress', instance));
						instance.bindInputEvent('keyup', A.bind('_onNumericFieldKeyUp', instance));

						instance.evaluate = A.debounce(
							function() {
								NumericField.superclass.evaluate.apply(instance, arguments);
							},
							300
						);
					},

					getChangeEventName: function() {
						return 'input';
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

					_onNumericFieldKeyPress: function(event) {
						event = event || window.event;

						var charCode = (typeof event.which == 'number') ? event.which : event.keyCode;

						if (charCode < 32 || (charCode >= 48 && charCode <= 57) || charCode === 46) {
							return true;
						}

						event.preventDefault();

						return false;
					},

					_onNumericFieldKeyUp: function() {
						var instance = this;

						var value = String(instance.get('value'));

						var inputNode = instance.getInputNode();

						inputNode.val(value.replace(/[^0-9.]/g, ''));
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