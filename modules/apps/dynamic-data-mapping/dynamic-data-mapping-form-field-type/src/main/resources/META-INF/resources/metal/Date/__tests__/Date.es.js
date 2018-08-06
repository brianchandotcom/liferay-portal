import Date from '../Date.es';

let component;
let spritemap = 'icons.svg';

describe('Field Date', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be not edidable', () => {
		component = new Date({
			editable: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helpText', () => {
		component = new Date({
			helpText: 'Type something',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Date({
			id: 'ID',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Date({
			label: 'label',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a placeholder', () => {
		component = new Date({
			placeholder: '__/__/____',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Date({
			required: false,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Date({
			label: 'text',
			showLabel: true,
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Date({
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Date({
			value: 'value',
			spritemap: spritemap,
		});

		expect(component).toMatchSnapshot();
	});
});
