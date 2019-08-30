let configuration = {};
let handleProviderResize = () => {};

let content = null;
let videoContainer = null;
let errorMessage = null;
let loadingIndicator = null;

const resize = () => {
	content.style.width = '';
	content.style.height = '';

	requestAnimationFrame(() => {
		try {
			const boundingClientRect = content.getBoundingClientRect();

			const width = configuration.width || boundingClientRect.width;
			const height = configuration.height || width * 0.5625;

			content.style.height = `${height}px`;
			content.style.width = `${width}px`;
		} catch (error) {
			window.removeEventListener('resize', resize);
		}
	});
};

const showVideo = () => {
	videoContainer.removeAttribute('aria-hidden');
	errorMessage.parentElement.removeChild(errorMessage);
	loadingIndicator.parentElement.removeChild(loadingIndicator);
	resize();
};

const showError = () => {
	errorMessage.removeAttribute('hidden');
	videoContainer.parentElement.removeChild(videoContainer);
	loadingIndicator.parentElement.removeChild(loadingIndicator);
};

const rawProvider = {
	getParameters: url => {
		return {url};
	},

	showVideo: parameters => {
		const video = document.createElement('video');
		const source = document.createElement('source');

		source.src = parameters.url;

		video.autoplay = configuration.autoPlay;
		video.controls = !configuration.hideControls;
		video.loop = configuration.loop;
		video.muted = configuration.mute;

		video.style.height = '100%';
		video.style.width = '100%';

		video.appendChild(source);
		videoContainer.appendChild(video);
		showVideo();
	}
};

const youtubeProvider = {
	getParameters: url => {
		if (['www.youtube.com', 'youtube.com'].includes(url.hostname)) {
			const videoId = url.searchParams.get('v');

			if (videoId) {
				return {videoId};
			}
		} else if (['www.youtu.be', 'youtu.be'].includes(url.hostname)) {
			const videoId = url.pathname.substr(1);

			if (videoId) {
				return {videoId};
			}
		}
	},

	showVideo: parameters => {
		const handleAPIReady = () => {
			const player = new YT.Player(videoContainer, {
				height: configuration.height,
				width: configuration.width,
				videoId: parameters.videoId,
				playerVars: {
					autoplay: configuration.autoPlay,
					controls: configuration.hideControls ? 0 : 1,
					loop: configuration.loop ? 0 : 1
				},
				events: {
					onReady: () => {
						if (configuration.mute) {
							player.mute();
						}

						showVideo();
					}
				}
			});
		};

		if ('YT' in window) {
			handleAPIReady();
		} else {
			const oldCallback = window.onYouTubeIframeAPIReady || (() => {});

			window.onYouTubeIframeAPIReady = () => {
				oldCallback();
				handleAPIReady();
			};

			const apiSrc = '//www.youtube.com/iframe_api';

			let script = Array.from(document.querySelectorAll('script')).find(
				script => script.src === apiSrc
			);

			if (!script) {
				script = document.createElement('script');
				script.src = apiSrc;
				document.body.appendChild(script);
			}
		}
	}
};

const main = () => {
	content = fragmentElement.querySelector('.video');

	if (!content) {
		return requestAnimationFrame(main);
	}

	videoContainer = content.querySelector('.video-container');
	errorMessage = content.querySelector('.error-message');
	loadingIndicator = content.querySelector('.loading-animation');

	configuration = {
		autoPlay: content.dataset.autoPlay === 'true',
		hideControls: content.dataset.hideControls === 'true',
		loop: content.dataset.loop === 'true',
		mute: content.dataset.mute === 'true',
		url: content.dataset.url,
		width: parseInt(content.dataset.width || 0, 10),
		height: parseInt(content.dataset.height || 0, 10)
	};

	window.removeEventListener('resize', resize);
	window.addEventListener('resize', resize);

	try {
		let matched = false;
		const url = new URL(configuration.url);
		const providers = [youtubeProvider, rawProvider];

		for (let i = 0; i < providers.length && !matched; i++) {
			const provider = providers[i];
			const parameters = provider.getParameters(url);

			if (parameters) {
				provider.showVideo(parameters);
				matched = true;
			}
		}

		if (!matched) {
			showError();
		}
	} catch (error) {
		showError();
	}
};

main();
