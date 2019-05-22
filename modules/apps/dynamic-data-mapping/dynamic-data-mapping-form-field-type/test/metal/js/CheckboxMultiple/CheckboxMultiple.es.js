import CheckboxMultiple from 'source/CheckboxMultiple/CheckboxMultiple.es';

let component;
const spritemap = 'icons.svg';

describe('Field Checkbox Multiple', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new CheckboxMultiple({
			readOnly: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new CheckboxMultiple({
			spritemap,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new CheckboxMultiple({
			id: 'ID',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new CheckboxMultiple({
			label: 'label',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefined Value', () => {
		component = new CheckboxMultiple({
			placeholder: 'Option 1',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new CheckboxMultiple({
			required: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should be shown as a switcher', () => {
		component = new CheckboxMultiple({
			showAsSwitcher: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should be shown as checkbox', () => {
		component = new CheckboxMultiple({
			showAsSwitcher: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new CheckboxMultiple({
			label: 'text',
			showLabel: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new CheckboxMultiple({
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new CheckboxMultiple({
			spritemap,
			value: true
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new CheckboxMultiple({
			key: 'key',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should emit field edit event on field change', done => {
		const handleFieldEdited = jest.fn();

		const events = {fieldEdited: handleFieldEdited};

		component = new CheckboxMultiple({
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
		component = new CheckboxMultiple({
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
