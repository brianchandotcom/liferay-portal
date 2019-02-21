import debounce from 'metal-debounce';
import {closest, getClosestAssetElement} from '../utils/assets';
import {onReady} from '../utils/events.js';
import {ScrollTracker} from '../utils/scroll';

const applicationId = 'Custom';

/**
 * Returns analytics payload with Custom Asset information.
 * @param {object} custom The custom asset DOM element
 * @return {object} The payload with custom information
 */
function getCustomAssetPayload({dataset}) {
	const {
		analyticsAssetCategory: category,
		analyticsAssetId: assetId,
		analyticsAssetTitle: title,
	} = dataset;

	return {
		assetId,
		category,
		title,
	};
}

/**
 * Wether a Custom Asset is trackable or not.
 * @param {object} element The Custom DOM element
 * @return {boolean} True if the element is trackable.
 */
function isTrackableCustomAsset(element) {
	return element && 'analyticsAssetId' in element.dataset;
}

/**
 * Sends information when user clicks on a Custom Asset.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetDownloaded(analytics) {
	const onClick = ({target}) => {
		const actionElement = closest(
			target,
			'[data-analytics-asset-action="download"]'
		);

		const customAssetElement = getClosestAssetElement(target, 'custom');

		if (actionElement && isTrackableCustomAsset(customAssetElement)) {
			analytics.send(
				'assetDownloaded',
				applicationId,
				getCustomAssetPayload(customAssetElement)
			);
		}
	};

	document.addEventListener('click', onClick);

	return () => document.removeEventListener('click', onClick);
}

/**
 * Sends information about Custom Asset scroll actions.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetScroll(analytics, customAssetElements) {
	const scrollSessionId = new Date().toISOString();
	const scrollTracker = new ScrollTracker();

	const onScroll = debounce(() => {
		customAssetElements.forEach(element => {
			scrollTracker.onDepthReached(depth => {
				analytics.send('assetDepthReached', applicationId, {
					...getCustomAssetPayload(element),
					depth,
					sessionId: scrollSessionId,
				});
			}, element);
		});
	}, 1500);

	document.addEventListener('scroll', onScroll);

	return () => {
		document.removeEventListener('scroll', onScroll);
	};
}

/**
 * Adds an event listener for a Custom Asset submission and sends information when that
 * event happens.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetSubmitted(analytics) {
	const onSubmit = ({target}) => {
		const customAssetElement = getClosestAssetElement(target, 'custom');

		if (
			!isTrackableCustomAsset(customAssetElement) ||
			(isTrackableCustomAsset(customAssetElement) &&
				target.tagName !== 'FORM')
		) {
			return;
		}

		analytics.send(
			'assetSubmitted',
			applicationId,
			getCustomAssetPayload(customAssetElement)
		);
	};

	document.addEventListener('submit', onSubmit, true);

	return () => document.removeEventListener('submit', onSubmit, true);
}

/**
 * Sends information when user scrolls on a Custom asset.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetViewed(analytics) {
	const customAssetElements = [];
	const stopTrackingOnReady = onReady(() => {
		Array.prototype.slice
			.call(
				document.querySelectorAll(
					'[data-analytics-asset-type="custom"]'
				)
			)
			.filter(element => isTrackableCustomAsset(element))
			.forEach(element => {
				const payload = getCustomAssetPayload(element);

				customAssetElements.push(element);

				analytics.send('assetViewed', applicationId, payload);
			});
	});

	const stopTrackingCustomAssetScroll = trackCustomAssetScroll(
		analytics,
		customAssetElements
	);

	return () => {
		stopTrackingCustomAssetScroll();
		stopTrackingOnReady();
	};
}

/**
 * Sends information when user clicks on a Custom Asset.
 * @param {object} analytics The Analytics client instance
 */
function trackCustomAssetClick(analytics) {
	const onClick = ({target}) => {
		const customAssetElement = getClosestAssetElement(target, 'custom');

		if (!isTrackableCustomAsset(customAssetElement)) {
			return;
		}

		const tagName = target.tagName.toLowerCase();

		const payload = {
			...getCustomAssetPayload(customAssetElement),
			tagName,
		};

		if (tagName === 'a') {
			payload.href = target.href;
			payload.text = target.innerText;
		} else if (tagName === 'img') {
			payload.src = target.src;
		}

		analytics.send('assetClicked', applicationId, payload);
	};

	document.addEventListener('click', onClick);

	return () => document.removeEventListener('click', onClick);
}

/**
 * Plugin function that registers listeners for Custom Asset events
 * @param {object} analytics The Analytics client
 */
function custom(analytics) {
	const stopTrackingClicked = trackCustomAssetClick(analytics);
	const stopTrackingDownloaded = trackCustomAssetDownloaded(analytics);
	const stopTrackingSubmitted = trackCustomAssetSubmitted(analytics);
	const stopTrackingViewed = trackCustomAssetViewed(analytics);

	return () => {
		stopTrackingClicked();
		stopTrackingDownloaded();
		stopTrackingSubmitted();
		stopTrackingViewed();
	};
}

export {custom};
export default custom;