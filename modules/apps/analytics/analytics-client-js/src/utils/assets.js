/**
 * Returns first webContent element ancestor of given element.
 * @param {object} element The DOM element
 * @return {object} The webContent element
 */
function getClosestAssetElement(element, assetType) {
	return closest(element, `[data-analytics-asset-type="${assetType}"]`);
}

function closest(element, selector) {
	if (element.closest) {
		return element.closest(selector);
	}
	if (!document.documentElement.contains(element)) {
		return null;
	}
	do {
		if (element.matches(selector)) {
			return element;
		}
		element = element.parentElement || element.parentNode;
	} while (element !== null && element.nodeType === 1);
	return null;
}

/**
 * Return all words from an element
 * @param {object} element
 * @return {number} the total of words
 */
function getNumberOfWords({innerText}) {
	const words = innerText.split(/\s+/).filter(Boolean);

	return innerText !== '' ? words.length : 0;
}

export {closest, getClosestAssetElement, getNumberOfWords};