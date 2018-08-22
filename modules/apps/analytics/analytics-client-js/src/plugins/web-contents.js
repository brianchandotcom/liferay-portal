import {onReady} from '../utils/events.js';
import {getClosestAssetElement, getNumberOfWords} from '../utils/assets';

const applicationId = 'WebContent';

/**
 * Returns analytics payload with WebContent information.
 * @param {object} webContent The webContent DOM element
 * @return {object} The payload with webContent information
 */
function getWebContentPayload(webContent) {
	return {articleId: webContent.dataset.analyticsAssetId};
}

/**
 * Wether a WebContent is trackable or not.
 * @param {object} element The WebContent DOM element
 * @return {boolean} True if the element is trackable.
 */
function isTrackableWebContent(element) {
	return element && 'analyticsAssetId' in element.dataset;
}

/**
 * Sends information when user clicks on a Web Content.
 * @param {object} The Analytics client instance
 */
function trackWebContentClicked(analytics) {
	const onClick = ({target}) => {
		const webContentElement = getClosestAssetElement(target, 'web-content');

		if (!isTrackableWebContent(webContentElement)) {
			return;
		}

		const tagName = target.tagName.toLowerCase();

		const payload = {
			...getWebContentPayload(webContentElement),
			tagName,
		};

		if (tagName === 'a') {
			payload.href = target.href;
			payload.text = target.innerText;
		} else if (tagName === 'img') {
			payload.src = target.src;
		}

		analytics.send('webContentClicked', applicationId, payload);
	};

	document.addEventListener('click', onClick);

	return () => document.removeEventListener('click', onClick);
}

/**
 * Sends information when user scrolls on a WebContent.
 * @param {object} The Analytics client instance
 */
function trackWebContentViewed(analytics) {
	const stopTrackingOnReady = onReady(() => {
		Array.prototype.slice
			.call(
				document.querySelectorAll(
					'[data-analytics-asset-type="web-content"]'
				)
			)
			.filter(element => isTrackableWebContent(element))
			.forEach(element => {
				let payload = getWebContentPayload(element);
				const title = element.dataset.analyticsAssetTitle;
				const numberOfWords = getNumberOfWords(element);

				payload = {numberOfWords, ...payload};

				if (title) {
					payload = {title, ...payload};
				}

				analytics.send('webContentViewed', applicationId, payload);
			});
	});
	return () => stopTrackingOnReady();
}

/**
 * Plugin function that registers listeners for Web Content events
 * @param {object} analytics The Analytics client
 */
function webContent(analytics) {
	const stopTrackingWebContentClicked = trackWebContentClicked(analytics);
	const stopTrackingWebContentViewed = trackWebContentViewed(analytics);

	return () => {
		stopTrackingWebContentClicked();
		stopTrackingWebContentViewed();
	};
}

export {webContent};
export default webContent;