import ImagePreviewer from '../src/main/resources/META-INF/resources/preview/js/ImagePreviewer.es';

let component;

describe('document-library-preview-image', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should render an image previewer', () => {
		component = new ImagePreviewer({
			imageURL: 'image.jpg',
			spritemap: 'icons.svg'
		});

		expect(component).toMatchSnapshot();
	});
});
