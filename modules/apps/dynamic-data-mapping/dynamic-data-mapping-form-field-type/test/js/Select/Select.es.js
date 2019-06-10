import Select from 'source/Select/Select.es';

let component;
const spritemap = 'icons.svg';

describe('Select', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new Select({
			readOnly: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new Select({
			spritemap,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Select({
			id: 'ID',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should render options', () => {
		component = new Select({
			options: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item'
				}
			],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should render no options when options come empty', () => {
		component = new Select({
			options: [],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Select({
			label: 'label',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should be closed by default', () => {
		component = new Select({
			open: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it("should have class dropdown-opened when it's opened", () => {
		component = new Select({
			open: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a placeholder', () => {
		component = new Select({
			placeholder: 'Placeholder',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a predefinedValue', () => {
		component = new Select({
			predefinedValue: ['Select'],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Select({
			required: false,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should put an asterisk when field is required', () => {
		component = new Select({
			label: 'This is the label',
			required: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Select({
			label: 'text',
			showLabel: true,
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Select({
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Select({
			spritemap,
			value: ['value']
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new Select({
			key: 'key',
			spritemap
		});

		expect(component).toMatchSnapshot();
	});

	it('should emit a field edit event when an item is selected', () => {
		const handleFieldEdited = jest.fn();

		const events = {fieldEdited: handleFieldEdited};

		jest.useFakeTimers();

		component = new Select({
			dataSourceType: 'manual',
			events,
			options: [
				{
					checked: false,
					disabled: false,
					id: 'id',
					inline: false,
					label: 'label',
					name: 'name',
					showLabel: true,
					value: 'item'
				}
			],
			spritemap
		});

		const spy = jest.spyOn(component, 'emit');

		jest.runAllTimers();

		component._handleItemClicked({
			data: {
				item: {
					value: 'Liferay'
				}
			},
			preventDefault: () => 0
		});

		expect(spy).toHaveBeenCalled();
	});

	it('should render the dropdown with search when there are more than six options', () => {
		component = new Select({
			dataSourceType: 'manual',
			options: [
				{
					label: 'label',
					name: 'name',
					value: 'item'
				},
				{
					label: 'label',
					name: 'name',
					value: 'item'
				},
				{
					label: 'label',
					name: 'name',
					value: 'item'
				},
				{
					label: 'label',
					name: 'name',
					value: 'item'
				},
				{
					label: 'label',
					name: 'name',
					value: 'item'
				},
				{
					label: 'label',
					name: 'name',
					value: 'item'
				},
				{
					label: 'label',
					name: 'name',
					value: 'item'
				}
			],
			spritemap
		});

		expect(component).toMatchSnapshot();
	});
});
