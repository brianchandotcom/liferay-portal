import Numeric from 'source/Numeric/Numeric.es';
import {dom} from 'metal-dom';

let component;
const spritemap = 'icons.svg';

const defaultNumericConfig = {
	name: 'numericField',
	spritemap
};

describe('Field Numeric', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should render the default markup', () => {
		component = new Numeric({
			...defaultNumericConfig,
			readOnly: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be readOnly', () => {
		component = new Numeric({
			...defaultNumericConfig,
			readOnly: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new Numeric({
			...defaultNumericConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Numeric({
			...defaultNumericConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Numeric({
			...defaultNumericConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a placeholder', () => {
		component = new Numeric({
			...defaultNumericConfig,
			placeholder: 'Placeholder'
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Numeric({
			...defaultNumericConfig,
			required: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Numeric({
			...defaultNumericConfig,
			label: 'Numeric Field',
			showLabel: true
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Numeric(defaultNumericConfig);

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Numeric({
			...defaultNumericConfig,
			value: '123'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new Numeric({
			...defaultNumericConfig,
			key: 'key'
		});

		expect(component).toMatchSnapshot();
	});

	it('should emit a field edit event on field value change', () => {
		jest.useFakeTimers();

		const handleFieldEdited = jest.fn();

		const events = {fieldEdited: handleFieldEdited};

		component = new Numeric({
			...defaultNumericConfig,
			events
		});

		dom.triggerEvent(component.element.querySelector('input'), 'input', {});

		jest.runAllTimers();

		expect(handleFieldEdited).toHaveBeenCalled();
	});

	it('should propagate the field edit event', () => {
		jest.useFakeTimers();

		component = new Numeric({
			...defaultNumericConfig,
			key: 'input'
		});

		const spy = jest.spyOn(component, 'emit');

		dom.triggerEvent(component.element.querySelector('input'), 'input', {});

		jest.runAllTimers();

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.any(Object));
	});

	it('should change the mask type', () => {
		component = new Numeric({
			...defaultNumericConfig
		});

		jest.runAllTimers();

		component.setState({
			dataType: 'double'
		});

		jest.runAllTimers();

		expect(component.dataType).toBe('double');
	});
});
