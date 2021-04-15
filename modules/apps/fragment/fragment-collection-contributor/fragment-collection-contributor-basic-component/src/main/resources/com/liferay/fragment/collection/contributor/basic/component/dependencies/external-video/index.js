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
	content.style.height = '';
	content.style.width = '';

	requestAnimationFrame(function () {
		try {
			const boundingClientRect = content.getBoundingClientRect();

			const contentWidth = width || boundingClientRect.width;

			const contentHeight = height || contentWidth * 0.5625;

			content.style.height = contentHeight + 'px';
			content.style.width = contentWidth + 'px';
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
		if (configuration.video) {
			const videoConfiguration = JSON.parse(configuration.video);

			if (videoConfiguration.html) {
				videoContainer.innerHTML = videoConfiguration.html;
				requestAnimationFrame(showVideo);
			}
			else {
				showError();
			}
		}
		else {
			showError();
		}
	}
	catch (error) {
		showError();
	}
}

main();
