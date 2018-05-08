/**
 * Generates a local helper function to fetch information from DOM elements
 * @param {string} selector A CSS selector query string
 * @param {string} attribute The element attribute to get
 * @return {string} Value of the specified attribute
 */
function getAttribute(selector, attribute) {
	const tag = document.querySelector(selector) || {};

	return tag[attribute] || '';
}

/**
 * Updates context with general page information
 * @param {object} request Request object to alter
 * @return {object} The updated request object
 */
function meta(request) {
	request.context = {
		canonicalUrl: getAttribute('link[rel=canonical]', 'href'),
		contentLanguageId: getAttribute('html', 'lang'),
		description: getAttribute('meta[name="description"]', 'content'),
		keywords: getAttribute('meta[name="keywords"]', 'content'),
		languageId: navigator.language,
		referrer: document.referrer,
		title: getAttribute('title', 'innerHTML'),
		url: location.href,
		userAgent: navigator.userAgent,
		...request.context,
	};

	return request;
}

export {meta};
export default meta;