import Validation from 'source/Validation/Validation.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
const spritemap = 'icons.svg';

describe(
	'Validation',
	() => {
		afterEach(
			() => {
				if (component) {
					component.dispose();
				}
			}
		);

		it(
			'should render checkbox to enable Validation',
			() => {
				component = new Validation(
					{
						dataType: 'string',
						label: 'Validator',
						name: 'validation',
						spritemap
					}
				);

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should enable validation after click on toogle',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'string',
						label: 'Validator',
						name: 'validation',
						spritemap
					}
				);

				MetalTestUtil.triggerEvent(
					component.element.querySelector('input[type=checkbox]'),
					'change',
					{}
				);

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render parameter field with TextField element',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'string',
						enableValidation: true,
						label: 'Validator',
						name: 'validation',
						spritemap
					}
				);

				component.handleValidationValue({value: ['notContains']});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render parameter field with Numeric element',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'numeric',
						enableValidation: true,
						label: 'Validator',
						name: 'validation',
						spritemap
					}
				);

				component.handleValidationValue({value: ['lt']});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render parameter field with Text element and then with Numeric after update dataType',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'string',
						enableValidation: true,
						label: 'Validator',
						name: 'validation',
						spritemap,
						validation: {
							dataType: undefined
						}
					}
				);

				component.handleValidationValue({value: ['notContains']});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();

				component.dataType = 'numeric';

				jest.runAllTimers();

				component.handleValidationValue({value: ['lt']});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not render parameter field if validation is isUrl',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'string',
						enableValidation: true,
						label: 'Validator',
						name: 'validation',
						spritemap
					}
				);

				component.handleValidationValue({value: ['url']});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should not render parameter field if validation is isEmailAddress',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'string',
						enableValidation: true,
						label: 'Validator',
						name: 'validation',
						spritemap
					}
				);

				component.handleValidationValue({value: ['email']});

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should render filled fields when value is filled',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'string',
						label: 'Validator',
						name: 'validation',
						spritemap,
						value: {
							errorMessage: 'Error message',
							expression: 'NOT(contains(textfield, "forbiddenWord"))'
						}
					}
				);

				jest.runAllTimers();

				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should update parameter message and emit a field edited event',
			() => {
				jest.useFakeTimers();

				const handleFieldEdited = jest.fn();

				const events = {fieldEdited: handleFieldEdited};

				component = new Validation(
					{
						dataType: 'string',
						enableValidation: true,
						events,
						label: 'Validator',
						name: 'validation',
						spritemap,
						validation: {
							fieldName: 'textfield'
						},
						value: {
							errorMessage: 'Error message',
							expression: 'NOT(contains(textfield, "forbiddenWord"))'
						}
					}
				);

				jest.runAllTimers();

				const parameterComponent = component.element.querySelector(
					'input[name="validation_parameterMessage"]'
				);

				parameterComponent.value = 'new Parameter Message';

				MetalTestUtil.triggerEvent(parameterComponent, 'input', {});

				jest.runAllTimers();

				expect(handleFieldEdited).toHaveBeenCalled();
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should update error message and emit a field edited event',
			() => {
				jest.useFakeTimers();

				const handleFieldEdited = jest.fn();

				const events = {fieldEdited: handleFieldEdited};

				component = new Validation(
					{
						dataType: 'string',
						enableValidation: true,
						events,
						label: 'Validator',
						name: 'validation',
						spritemap,
						validation: {
							fieldName: 'textfield'
						},
						value: {
							errorMessage: 'Error message',
							expression: 'NOT(contains(textfield, "forbiddenWord"))'
						}
					}
				);

				jest.runAllTimers();

				const errorComponent = component.element.querySelector(
					'input[name="validation_errorMessage"]'
				);

				errorComponent.value = 'new Error Message';

				MetalTestUtil.triggerEvent(errorComponent, 'input', {});

				jest.runAllTimers();

				expect(handleFieldEdited).toHaveBeenCalled();
				expect(component).toMatchSnapshot();
			}
		);

		it(
			'should propagate the field edit event',
			() => {
				jest.useFakeTimers();

				component = new Validation(
					{
						dataType: 'string',
						enableValidation: true,
						name: 'validation',
						spritemap,
						validation: {
							fieldName: 'textfield'
						},
						value: {
							errorMessage: 'Error message',
							expression: 'NOT(contains(textfield, "forbiddenWord"))'
						}
					}
				);

				const spy = jest.spyOn(component, 'emit');

				jest.runAllTimers();

				const errorComponent = component.element.querySelector(
					'input[name="validation_errorMessage"]'
				);

				errorComponent.value = 'new Error Message';

				MetalTestUtil.triggerEvent(errorComponent, 'input', {});

				jest.runAllTimers();

				expect(spy).toHaveBeenCalled();
				expect(spy).toHaveBeenCalledWith('fieldEdited', expect.any(Object));
			}
		);
	}
);