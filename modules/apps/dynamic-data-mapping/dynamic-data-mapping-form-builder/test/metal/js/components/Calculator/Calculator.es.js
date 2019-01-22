import '../../__fixtures__/MockField.es';
import mockPages from 'mock/mockPages.es';
import dom from 'metal-dom';
import Calculator from 'source/components/Calculator/Calculator.es';

let component;

const pages = [...mockPages];

const calculatorOptions = [
	{
		label: 'sum',
		tooltip: '',
		value: 'sum'
	}
];

const options = [
	{
		dataType: 'number',
		fieldName: 'option1repeatablefieldName',
		name: 'option1 repeatable',
		options: [],
		repeatable: true,
		title: 'option1repeatablefieldName',
		type: 'checkbox',
		value: 'option1repeatablefieldName'
	},
	{
		dataType: 'number',
		fieldName: 'option1nonrepeatablefieldName',
		name: 'option1',
		options: [],
		repeatable: false,
		title: 'option1nonrepeatablefieldName',
		type: 'checkbox',
		value: 'option1nonrepeatablefieldName'
	}
];

const spritemap = 'icons.svg';

const getBaseConfig = () => ({
	calculatorOptions,
	expression: '',
	expressionArray: [],
	options,
	pages,
	spritemap
});

describe(
	'Calculator',
	() => {
		describe(
			'Acceptance Criteria',
			() => {

				afterEach(
					() => {
						component.dispose();
					}
				);

				beforeEach(
					() => {
						jest.useFakeTimers();
					}
				);

				describe(
					'when the user selects Calculate in the Actions options',
					() => {
						it(
							'should show the calculator when a Result is selected',
							() => {
								component = new Calculator(
									{
										...getBaseConfig()
									}
								);

								component.setState(
									{
										resultSelected: 'numero'
									}
								);

								jest.runAllTimers();

								expect(component).toMatchSnapshot();
							}
						);

						it(
							'should only show the valid fields when a Calculate action is selected',
							() => {
								component = new Calculator(
									{
										...getBaseConfig()
									}
								);

								jest.runAllTimers();

								const dropdownMenu = component.refs.calculatorOptions0.refs.dropdown.refs.portal.refs.menu;

								dropdownMenu.querySelector('button[aria-label=\'sum\']').click();

								expect(component.optionsRepeatable[0].repeatable).toEqual(true);
							}
						);

						it(
							'should disable the Add Field button when there\'s no numeric field options',
							() => {
								component = new Calculator(
									{
										...getBaseConfig(),
										options: []
									}
								);

								jest.runAllTimers();

								expect(component.refs.calculatorAddField0.disabled).toEqual(true);
							}
						);

						it(
							'should not allow type two repeated signs',
							() => {
								component = new Calculator(
									{
										...getBaseConfig()
									}
								);

								const button1 = component.element.querySelector('[data-calculator-key=\'1\']');

								const buttonPlus = component.element.querySelector('[data-calculator-key=\'+\']');

								const button2 = component.element.querySelector('[data-calculator-key=\'2\']');

								const buttonMinus = component.element.querySelector('[data-calculator-key=\'-\']');

								dom.triggerEvent(button1, 'click', {});
								dom.triggerEvent(buttonPlus, 'click', {});
								dom.triggerEvent(button2, 'click', {});
								dom.triggerEvent(buttonPlus, 'click', {});
								dom.triggerEvent(buttonMinus, 'click', {});

								jest.runAllTimers();

								expect(component.expressionArray).toEqual(['1', '+', '2', '-']);
							}
						);

						it(
							'should clear the expression when changing the result field',
							() => {
								component = new Calculator(
									{
										...getBaseConfig()
									}
								);

								component.expressionArray = ['a', '+', 'b'];

								component.expression = 'a+b';

								component.resultSelected = 'date';

								component.setState(
									{
										resultSelected: 'numero'
									}
								);

								jest.runAllTimers();

								expect(component.expressionArray).toEqual([]);

								expect(component.expression).toEqual('');

							}
						);

						it(
							'should compose an expression by interacting with the calculator fields',
							() => {
								component = new Calculator(
									{
										...getBaseConfig()
									}
								);

								const button1 = component.element.querySelector('[data-calculator-key=\'1\']');

								const buttonPlus = component.element.querySelector('[data-calculator-key=\'+\']');

								const button2 = component.element.querySelector('[data-calculator-key=\'2\']');

								dom.triggerEvent(button1, 'click', {});
								dom.triggerEvent(buttonPlus, 'click', {});
								dom.triggerEvent(button2, 'click', {});

								jest.runAllTimers();

								expect(component.expressionArray).toEqual(['1', '+', '2']);
							}
						);

						it(
							'should compose an expression by interacting with the calculator Add Field button',
							() => {
								component = new Calculator(
									{
										...getBaseConfig(),
										disableCalculatorField: false
									}
								);

								jest.runAllTimers();

								const addFieldMenu = component.refs.calculatorAddField0.refs.portal.refs.menu;

								addFieldMenu.querySelector('li').click();

								jest.runAllTimers();

								expect(component.expressionArray).toEqual([options[0].fieldName]);
							}
						);
					}
				);
			}
		);
	}
);