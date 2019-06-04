import Checkbox from 'source/Checkbox/Checkbox.es';

let component;
const spritemap = 'icons.svg';

describe('Field Checkbox', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new Checkbox({
			readOnly: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new Checkbox({
			spritemap,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Checkbox({
			id: 'ID',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Checkbox({
			label: 'label',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefined Value', () => {
		component = new Checkbox({
			placeholder: 'Option 1',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Checkbox({
			required: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should be shown as a switcher', () => {
		component = new Checkbox({
			showAsSwitcher: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should be shown as checkbox', () => {
		component = new Checkbox({
			showAsSwitcher: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Checkbox({
			label: 'text',
			showLabel: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Checkbox({
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Checkbox({
			spritemap,
			value: true
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new Checkbox({
			key: 'key',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should emit field edit event on field change', done => {
		const handleFieldEdited = jest.fn();

		const events = {fieldEdited: handleFieldEdited};

		component = new Checkbox({
			events,
			spritemap
		});

		component.on('fieldEdited', () => {
			expect(handleFieldEdited).toHaveBeenCalled();

			done();
		});

		component.handleInputChangeEvent({
			delegateTarget: {
				checked: true
			}
		});

		jest.runAllTimers();
	});

	it('should propagate the field edit event on field change', () => {
		component = new Checkbox({
			spritemap
		});

		const spy = jest.spyOn(component, 'emit');

		component.handleInputChangeEvent({
			delegateTarget: {
				checked: true
			}
		});

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdited', expect.any(Object));
	});
});
