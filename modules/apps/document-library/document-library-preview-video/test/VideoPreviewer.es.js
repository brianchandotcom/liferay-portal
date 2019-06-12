import VideoPreviewer from '../src/main/resources/META-INF/resources/preview/js/VideoPreviewer.es';

let component;

describe('document-library-preview-video', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should render an video player', () => {
		component = new VideoPreviewer({
			videoPosterURL: 'poster.jpg',
			videoSources: [
				{
					type: 'video/mp4',
					url: '//video.mp4'
				},
				{
					type: 'video/ogv',
					url: '//video.ogv'
				}
			]
		});

		expect(component).toMatchSnapshot();
	});
});
