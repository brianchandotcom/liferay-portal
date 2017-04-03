AUI.add(
	'liferay-ddl-form-builder-calculator',
	function(A) {
		var CSS_CALCULATOR_BUTTON = A.getClassName('calculator', 'button');

		var CSS_CALCULATOR_ADD_FIELD = A.getClassName('calculator', 'add', 'field', 'button');

		var CSS_CALCULATOR_ADD_FIELD_CONTANIER = A.getClassName('calculator', 'add', 'field', 'button', 'container');

		var FormBuilderCalculator = A.Component.create(
			{
				ATTRS: {
					advancedOperators: {
						value: ''
					},
					options: {
						value: []
					},
					strings: {
						value: {
							addField: Liferay.Language.get('add-field')
						}
					}
				},

				EXTENDS: A.Widget,

				NAME: 'liferay-ddl-form-builder-calculator',

				prototype: {
					renderUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.setHTML(instance._getTemplate());
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.delegate('click', A.bind(instance._handleButtonClick, instance), '.' + CSS_CALCULATOR_BUTTON);

						boundingBox.one('.calculator-add-field-button').after('click', A.bind(instance._onAddFieldClick, instance));
					},

					_afterSelectFieldChange: function(event) {
						var instance = this;

						instance.fire(
							'clickedKey',
							{
								key: event.newVal.join()
							}
						);
					},

					_createSelectField: function() {
						var instance = this;

						var buttonContainer = instance.get('boundingBox').one('.' + CSS_CALCULATOR_ADD_FIELD_CONTANIER);

						var select = new Liferay.DDM.Field.Select(
							{
								after: {
									valueChange: A.bind(instance._afterSelectFieldChange, instance)
								},
								options: instance.get('options'),
								triggers: [
									instance.get('boundingBox').one('.' + CSS_CALCULATOR_ADD_FIELD)
								]
							}
						);

						select.render(buttonContainer);

						return select;
					},

					_getSelectField: function() {
						var instance = this;

						if (!instance._select) {
							instance._select = instance._createSelectField();
						}

						return instance._select;
					},

					_getTemplate: function() {
						var instance = this;

						var strings = instance.get('strings');

						var calculatorTemplateRenderer = Liferay.DDM.SoyTemplateUtil.getTemplateRenderer('ddl.calculator.settings');

						return calculatorTemplateRenderer(
							{
								advancedOperators: instance.get('advancedOperators'),
								calculatorAngleLeft: Liferay.Util.getLexiconIconTpl('angle-left', 'icon-monospaced'),
								calculatorEllipsis: Liferay.Util.getLexiconIconTpl('ellipsis-h', 'icon-monospaced'),
								strings: strings
							}
						);
					},

					_handleButtonClick: function(event) {
						var instance = this;

						instance.fire(
							'clickedKey',
							{
								key: event.currentTarget.getData('calculator-key')
							}
						);
					},

					_onAddFieldClick: function() {
						var instance = this;

						instance._getSelectField().toggleList();
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderCalculator = FormBuilderCalculator;
	},
	'',
	{
		requires: ['aui-component', 'liferay-ddm-form-field-select', 'liferay-ddm-soy-template-util']
	}
);