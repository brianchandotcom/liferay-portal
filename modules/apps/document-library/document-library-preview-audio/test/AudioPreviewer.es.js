import AudioPreviewer from '../src/main/resources/META-INF/resources/preview/js/AudioPreviewer.es';

let component;

describe('document-library-preview-audio', () => {
	afterEach(() => {
		if (component) {
			component.dispose();
		}
	});

	it('should render an audio player', () => {
		component = new AudioPreviewer({
			audioMaxWidth: 520,
			audioSources: [
				{
					type: 'audio/ogg',
					url: '//audio.ogg'
				},
				{
					type: 'audio/mp3',
					url: '//audio.mp3'
				}
			]
		});

		expect(component).toMatchSnapshot();
	});
});
