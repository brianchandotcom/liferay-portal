let content = null;
let videoContainer = null;
let errorMessage = null;
let loadingIndicator = null;
let videoMask = null;

const height = configuration.videoHeight
	? configuration.videoHeight.replace('px', '')
	: configuration.videoHeight;
const width = configuration.videoWidth
	? configuration.videoWidth.replace('px', '')
	: configuration.videoWidth;

function resize() {
	const scrollPosition = {
		left: window.scrollX,
		top: window.scrollY,
	};

	content.style.height = '';
	content.style.width = '';

	requestAnimationFrame(function () {
		try {
			const boundingClientRect = content.getBoundingClientRect();

			const contentWidth = width || boundingClientRect.width;

			const contentHeight = height || contentWidth * 0.5625;

			content.style.height = contentHeight + 'px';
			content.style.width = contentWidth + 'px';

			window.scrollTo(scrollPosition);
		}
		catch (error) {
			window.removeEventListener('resize', resize);
		}
	});
}

function showVideo() {
	videoContainer.removeAttribute('aria-hidden');
	errorMessage.parentElement.removeChild(errorMessage);
	loadingIndicator.parentElement.removeChild(loadingIndicator);

	if (!document.body.classList.contains('has-edit-mode-menu')) {
		videoMask.parentElement.removeChild(videoMask);
	}

	window.addEventListener('resize', resize);

	resize();
}

function showError() {
	if (document.body.classList.contains('has-edit-mode-menu')) {
		errorMessage.removeAttribute('hidden');
		videoContainer.parentElement.removeChild(videoContainer);
		loadingIndicator.parentElement.removeChild(loadingIndicator);
	}
	else {
		fragmentElement.parentElement.removeChild(fragmentElement);
	}
}

const rawProvider = {
	getParameters: function (url) {
		return {url: url};
	},

	showVideo: function (parameters) {
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
	},
};

const youtubeProvider = {
	getParameters: function (url) {
		const start = url.searchParams.get('start');

		if (['www.youtube.com', 'youtube.com'].includes(url.hostname)) {
			const videoId = url.searchParams.get('v');

			if (videoId) {
				return {
					start: start,
					videoId: videoId,
				};
			}
		}
		else if (['www.youtu.be', 'youtu.be'].includes(url.hostname)) {
			const videoId = url.pathname.substr(1);

			if (videoId) {
				return {
					start: start,
					videoId: videoId,
				};
			}
		}
	},

	showVideo: function (parameters) {
		const handleAPIReady = function () {
			const player = new YT.Player(videoContainer, {
				events: {
					onReady: function () {
						if (configuration.mute) {
							player.mute();
						}

						showVideo();
					},
				},
				height: height,
				playerVars: {
					autoplay: configuration.autoPlay,
					controls: configuration.hideControls ? 0 : 1,
					loop: configuration.loop ? 1 : 0,
					playlist: configuration.loop
						? parameters.videoId
						: undefined,
					start: !parameters.start ? 0 : parameters.start,
				},
				videoId: parameters.videoId,
				width: width,
			});
		};

		if ('YT' in window) {
			handleAPIReady();
		}
		else {
			const oldCallback =
				window.onYouTubeIframeAPIReady || function () {};

			window.onYouTubeIframeAPIReady = function () {
				oldCallback();
				handleAPIReady();
			};

			const apiSrc = '//www.youtube.com/iframe_api';

			let script = Array.from(document.querySelectorAll('script')).find(
				function (script) {
					return script.src === apiSrc;
				}
			);

			if (!script) {
				script = document.createElement('script');
				script.src = apiSrc;
				document.body.appendChild(script);
			}
		}
	},
};

function main() {
	content = fragmentElement.querySelector('.video');

	if (!content) {
		return requestAnimationFrame(main);
	}

	videoContainer = content.querySelector('.video-container');
	errorMessage = content.querySelector('.error-message');
	loadingIndicator = content.querySelector('.loading-animation');
	videoMask = content.querySelector('.video-mask');

	window.removeEventListener('resize', resize);

	try {
		let matched = false;
		const url = new URL(configuration.url, window.location.origin);
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
	}
	catch (error) {
		showError();
	}
}

main();
