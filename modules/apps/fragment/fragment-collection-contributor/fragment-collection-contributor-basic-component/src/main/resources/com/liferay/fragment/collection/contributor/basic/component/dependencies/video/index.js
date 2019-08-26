const content = fragmentElement.querySelector('.video');
const errorMessage = content.querySelector('.error-message');
const loadingIndicator = content.querySelector('.loading-animation');
const videoContainer = content.querySelector('.video-container');
const videoElement = content.querySelector('.video-source');
const widthOption = parseInt(content.dataset.width || 0, 10);
const heightOption = parseInt(content.dataset.height || 0, 10);

const configuration = {
	autoPlay: content.dataset.autoPlay === 'true',
	hideControls: content.dataset.hideControls === 'true',
	loop: content.dataset.loop === 'true',
	width: widthOption,
	height: heightOption
};

const showVideo = () => {
	videoContainer.removeAttribute('aria-hidden');
	errorMessage.parentElement.removeChild(errorMessage);
	loadingIndicator.parentElement.removeChild(loadingIndicator);
};

const showError = () => {
	errorMessage.removeAttribute('hidden');
	videoContainer.parentElement.removeChild(videoContainer);
	loadingIndicator.parentElement.removeChild(loadingIndicator);
};

const resize = () => {
	const boundingClientRect = content.getBoundingClientRect();
	configuration.width = widthOption || boundingClientRect.width;
	configuration.height = heightOption || configuration.width * 0.5625;
	content.style.height = `${configuration.height}px`;
};

let handleProviderResize = () => {};

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
					onReady: showVideo
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
	const providers = [youtubeProvider];

	const handleResize = () => {
		if (document.body.contains(content)) {
			resize();
			handleProviderResize();
		} else {
			window.removeEventListener('resize', handleResize);
		}
	};

	window.addEventListener('resize', handleResize);
	resize();

	try {
		let matched = false;

		const urls = Array.from(videoElement.querySelectorAll('source')).map(
			source => source.src
		);

		for (let i = 0; i < urls.length && !matched; i++) {
			const url = new URL(urls[i]);
			for (let j = 0; j < providers.length && !matched; j++) {
				const provider = providers[j];
				const parameters = provider.getParameters(url);

				if (parameters) {
					provider.showVideo(parameters);
					matched = true;
				}
			}
		}

		videoElement.innerHTML = '';

		if (!matched) {
			showError();
		}
	} catch (error) {
		showError();
	}
};

main();
