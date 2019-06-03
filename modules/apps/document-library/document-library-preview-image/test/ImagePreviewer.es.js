import ImagePreviewer from '../src/main/resources/META-INF/resources/preview/js/ImagePreviewer.es';

describe('document-library-preview-image', () => {
	it('should render an image previewer', () => {
		const imagePreviewer = new ImagePreviewer({
			element: document.body,
			imageURL: 'image.jpg',
			spritemap: 'icons.svg'
		});

		expect(imagePreviewer).toMatchSnapshot();
	});
});
