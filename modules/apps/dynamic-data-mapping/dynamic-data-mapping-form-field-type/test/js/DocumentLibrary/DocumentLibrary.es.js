import DocumentLibrary from 'source/DocumentLibrary/DocumentLibrary.es';

let component;
const spritemap = 'icons.svg';

const defaultDocumentLibraryConfig = {
	name: 'textField',
	spritemap
};

describe('Field DocumentLibrary', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should not be readOnly', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			readOnly: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a helptext', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			tip: 'Type something'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have an id', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			id: 'ID'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a label', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			label: 'label'
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a placeholder', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			placeholder: 'Placeholder'
		});

		expect(component).toMatchSnapshot();
	});

	it('should not be required', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			required: false
		});

		expect(component).toMatchSnapshot();
	});

	it('should render Label if showLabel is true', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			label: 'text',
			showLabel: true
		});

		expect(component).toMatchSnapshot();
	});

	it('should have a spritemap', () => {
		component = new DocumentLibrary(defaultDocumentLibraryConfig);

		expect(component).toMatchSnapshot();
	});

	it('should have a value', () => {
		component = new DocumentLibrary({
			...defaultDocumentLibraryConfig,
			value: {
				id: '123'
			}
		});

		expect(component).toMatchSnapshot();
	});
});
