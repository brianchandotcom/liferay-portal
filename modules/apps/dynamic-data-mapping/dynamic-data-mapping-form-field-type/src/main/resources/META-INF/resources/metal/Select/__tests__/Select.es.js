import Select from '../Select.es';
import {dom as MetalTestUtil} from 'metal-dom';

let component;
let spritemap = 'icons.svg';

describe('Select', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new Select({
			editable: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new Select({
			helpText: 'Type something',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Select({
			id: 'ID',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render items', () => {
		component = new Select({
			items: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item',
				},
			],
		});

		expect(component).toMatchSnapshot();
	});

	it('should render no items when items come empty', () => {
		component = new Select({
			items: [],
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Select({
			label: 'label',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should be closed by default', () => {
		component = new Select({
			open: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have class dropdown-opened when it\'s opened', () => {
		component = new Select({
			open: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a placeholder', () => {
		component = new Select({
			placeholder: 'Placeholder',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefinedValue', () => {
		component = new Select({
			predefinedValue: 'Select',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Select({
			required: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Select({
			label: 'text',
			showLabel: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Select({
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Select({
			value: 'value',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new Select({
			key: 'key',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should emit a field edit event when an item is selected', () => {
		const handleFieldEdit = jest.fn();
		const events = {fieldEdit: handleFieldEdit};

		component = new Select({
			items: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item',
				},
			],
			spritemap: spritemap,
			events,
		});

		MetalTestUtil.triggerEvent(
			component.element.querySelector('.dropdown-menu'),
			'click',
			{}
		);

		expect(handleFieldEdit).toHaveBeenCalled();
	});

	it('should open dropdown when select is clicked', () => {
		component = new Select({
			items: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item',
				},
			],
			spritemap: spritemap,
		});

		MetalTestUtil.triggerEvent(
			component.element.querySelector('.select-field-trigger'),
			'click',
			{}
		);
		expect(component.getState().open).toBe(true);
	});

	it('should propagate the field edit event', () => {
		component = new Select({
			items: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item',
				},
			],
			spritemap: spritemap,
		});

		const spy = jest.spyOn(component, 'emit');

		MetalTestUtil.triggerEvent(
			component.element.querySelector('.dropdown-menu'),
			'click',
			{}
		);

		expect(spy).toHaveBeenCalled();
		expect(spy).toHaveBeenCalledWith('fieldEdit', expect.any(Object));
	});
});
