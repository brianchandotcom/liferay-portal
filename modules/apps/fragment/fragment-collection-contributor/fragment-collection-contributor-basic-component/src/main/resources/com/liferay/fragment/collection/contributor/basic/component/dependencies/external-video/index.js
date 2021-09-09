let content = null;
let errorMessage = null;
let loadingIndicator = null;
let resizeIntervalId = null;
let videoContainer = null;
let videoMask = null;

const editMode = document.body.classList.contains('has-edit-mode-menu');

const height = configuration.videoHeight
	? configuration.videoHeight.replace('px', '')
	: configuration.videoHeight;

const width = configuration.videoWidth
	? configuration.videoWidth.replace('px', '')
	: configuration.videoWidth;

function debounce(fn, timeout) {
	let timeoutId = null;

	return function () {
		clearTimeout(timeoutId);

		timeoutId = setTimeout(fn, timeout);
	};
}

function main() {
	clearInterval(resizeIntervalId);
	window.removeEventListener('resize', resize);

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

const resize = debounce(function () {
	if (!document.body.contains(fragmentElement)) {
		clearInterval(resizeIntervalId);
		window.removeEventListener('resize', resize);

		return;
	}

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
			clearInterval(resizeIntervalId);
			window.removeEventListener('resize', resize);
		}
	});
}, 300);

function showError() {
	if (document.body.classList.contains('has-edit-mode-menu')) {
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

	if (!document.body.classList.contains('has-edit-mode-menu')) {
		videoMask.parentElement.removeChild(videoMask);
	}

	window.addEventListener('resize', resize);

	if (editMode) {
		resizeIntervalId = setInterval(resize, 2000);
	}

	resize();
}

main();
