import Checkbox from '../Checkbox.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
let spritemap = 'icons.svg';

describe('Field Checkbox', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new Checkbox({
			editable: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new Checkbox({
			helpText: 'Type something',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Checkbox({
			id: 'ID',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Checkbox({
			label: 'label',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefined Value', () => {
		component = new Checkbox({
			placeholder: 'Option 1',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Checkbox({
			required: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should be shown as a switcher', () => {
		component = new Checkbox({
			showAsSwitcher: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should be shown as checkbox', () => {
		component = new Checkbox({
			showAsSwitcher: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Checkbox({
			label: 'text',
			showLabel: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Checkbox({
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Checkbox({
			value: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new Checkbox({
			key: 'key',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should emit field edit event on field change', () => {
		const handleFieldChange = jest.fn();
		const events = {fieldEdit: handleFieldChange};

		component = new Checkbox({
			spritemap: spritemap,
			events,
		});

		MetalTestUtil.triggerEvent(
			component.element.querySelector('input'),
			'change',
			{}
		);

		expect(handleFieldChange).toHaveBeenCalled();
	});

	it('should propagate the field edit event on field change', () => {
		component = new Checkbox({
			spritemap: spritemap,
		});

		const spy = jest.spyOn(component, 'emit');

		MetalTestUtil.triggerEvent(
			component.element.querySelector('input'),
			'change',
			{}
		);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdit', expect.any(Object));
	});
});
