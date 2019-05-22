import Paragraph from 'source/Paragraph/Paragraph.es';

let component;
const spritemap = 'icons.svg';

const defaultParagraphConfig = {
	name: 'textField',
	spritemap
};

describe('Field Paragraph', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should be readOnly', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			readOnly: true
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a placeholder', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			placeholder: 'Placeholder'
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			required: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			label: 'text',
			showLabel: true
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new Paragraph(defaultParagraphConfig);

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			value: 'value'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a key', () => {
		component = new Paragraph({
			...defaultParagraphConfig,
			key: 'key'
		});

		expect(component).toMatchSnapshot();
	});
});
