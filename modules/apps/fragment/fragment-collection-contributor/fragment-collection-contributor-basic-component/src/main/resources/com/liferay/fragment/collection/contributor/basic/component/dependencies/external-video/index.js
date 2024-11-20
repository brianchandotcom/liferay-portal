let content = null;
let errorMessage = null;
let loadingIndicator = null;
let videoContainer = null;
let videoMask = null;

const editMode = layoutMode === 'edit';

const height = configuration.videoHeight
	? configuration.videoHeight.replace('px', '')
	: configuration.videoHeight;

const width = configuration.videoWidth
	? configuration.videoWidth.replace('px', '')
	: configuration.videoWidth;

function main() {
	if (!document.body.contains(fragmentElement)) {
		return;
	}

	content = fragmentElement.querySelector('.video');

	if (!content) {
		return requestAnimationFrame(main);
	}

	errorMessage = content.querySelector('.error-message');
	loadingIndicator = content.querySelector('.loading-animation');
	videoContainer = content.querySelector('.video-container');
	videoMask = content.querySelector('.video-mask');

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

function resize() {
	content.style.height = '';
	content.style.width = '';

	const contentWidth = width;
	const contentHeight = height || contentWidth * 0.5625;

	content.style.height = contentHeight + 'px';
	content.style.width = contentWidth + 'px';
}

function showError() {
	if (editMode) {
		errorMessage.removeAttribute('hidden');
		loadingIndicator.parentElement.removeChild(loadingIndicator);
		videoContainer.parentElement.removeChild(videoContainer);
	}
	else {
		fragmentElement.parentElement.removeChild(fragmentElement);
	}
}

function showVideo() {
	errorMessage.parentElement.removeChild(errorMessage);
	loadingIndicator.parentElement.removeChild(loadingIndicator);
	videoContainer.removeAttribute('aria-hidden');

	if (!editMode) {
		videoMask.parentElement.removeChild(videoMask);
	}

	if (width || height) {
		content.classList.remove('aspect-ratio', 'aspect-ratio-16-to-9');

		resize();
	}
}

main();
