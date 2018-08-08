import Grid from '../Grid.es';

let component;
let spritemap = 'icons.svg';

describe('Grid', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should render columns', () => {
		component = new Grid({
			columns: [
				{
					label: 'col1',
					value: 'fieldId',
				},
				{
					label: 'col2',
					value: 'fieldId',
				},
			],
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render no columns when columns comes empty', () => {
		component = new Grid({
			columns: [],
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should be not edidable', () => {
		component = new Grid({
			editable: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helpText', () => {
		component = new Grid({
			helpText: 'Type something',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Grid({
			id: 'ID',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Grid({
			label: 'label',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Grid({
			required: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render rows', () => {
		component = new Grid({
			rows: [
				{
					label: 'row1',
					value: 'fieldId',
				},
				{
					label: 'row2',
					value: 'fieldId',
				},
			],
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render no rows when row comes empty', () => {
		component = new Grid({
			rows: [],
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Grid({
			label: 'text',
			showLabel: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Grid({
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});
});
