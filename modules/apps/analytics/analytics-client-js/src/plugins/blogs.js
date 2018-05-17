import debounce from 'metal-debounce';
import {onReady} from '../utils/events.js';
import {ScrollTracker} from '../utils/scroll';

const applicationId = 'blogs';

/**
 * Returns analytics payload with Blog information.
 * @param {object} blog The blog DOM element
 * @return {object} The payload with blog information
 */
function getBlogPayload(blog) {
	return {entryId: blog.dataset.analyticsAssetId};
}

/**
 * Returns first blog element ancestor of given element.
 * @param {object} element The DOM element
 * @return {object} The blog element
 */
function getClosestBlogElement(element) {
	if (element.closest) {
		return element.closest('[data-analytics-asset-type="blog"]');
	}
	while (
		(element = element.parentElement) &&
		element.dataset.analyticsAssetType !== 'blog'
	);
	return element;
}

/**
 * Wether a Blog is trackable or not.
 * @param {object} element The Blog DOM element
 * @return {boolean} True if the element is trackable.
 */
function isTrackableBlog(element) {
	return element && 'analyticsAssetId' in element.dataset;
}

/**
 * Sends information about Blogs scroll actions.
 * @param {object} The Analytics client instance
 */
function trackBlogsScroll(analytics, blogElements) {
	const scrollSessionId = new Date().toISOString();
	const scrollTracker = new ScrollTracker();

	const onScroll = debounce(() => {
		blogElements.forEach(element => {
			scrollTracker.onDepthReached(depth => {
				analytics.send('depthReached', applicationId, {
					...getBlogPayload(element),
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
 * Sends information when user scrolls on a Blog.
 * @param {object} The Analytics client instance
 */
function trackBlogViewed(analytics) {
	const blogElements = [];
	const stopTrackingOnReady = onReady(() => {
		Array.prototype.slice
			.call(
				document.querySelectorAll('[data-analytics-asset-type="blog"]')
			)
			.filter(element => isTrackableBlog(element))
			.forEach(element => {
				let payload = getBlogPayload(element);
				const title = element.dataset.analyticsAssetTitle;

				if (title) {
					payload = {title, ...payload};
				}

				blogElements.push(element);

				analytics.send('blogViewed', applicationId, payload);
			});
	});
	const stopTrackingBlogsScroll = trackBlogsScroll(analytics, blogElements);
	return () => {
		stopTrackingBlogsScroll();
		stopTrackingOnReady();
	};
}

/**
 * Sends information when user clicks on a Blog.
 * @param {object} The Analytics client instance
 */
function trackBlogClicked(analytics) {
	const onClick = ({target}) => {
		const blogElement = getClosestBlogElement(target);

		if (!isTrackableBlog(blogElement)) {
			return;
		}

		const tagName = target.tagName.toLowerCase();

		const payload = {
			...getBlogPayload(blogElement),
			tagName,
		};

		if (tagName === 'a') {
			payload.href = target.href;
			payload.text = target.innerText;
		} else if (tagName === 'img') {
			payload.src = target.src;
		}

		analytics.send('blogClicked', applicationId, payload);
	};

	document.addEventListener('click', onClick);

	return () => document.removeEventListener('click', onClick);
}

/**
 * Plugin function that registers listeners for Blog events
 * @param {object} analytics The Analytics client
 */
function blogs(analytics) {
	const stopTrackingBlogClicked = trackBlogClicked(analytics);
	const stopTrackingBlogViewed = trackBlogViewed(analytics);

	return () => {
		stopTrackingBlogClicked();
		stopTrackingBlogViewed();
	};
}

export {blogs};
export default blogs;