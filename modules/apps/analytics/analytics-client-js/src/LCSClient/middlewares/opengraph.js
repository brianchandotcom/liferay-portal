const openGraphTagPatterns = [
	/^og:.*/,
	/^music:/,
	/^video:/,
	/^article:/,
	/^book:/,
	/^profile:/,
	/^fb:/,
];

/**
 * Determines whether the given element is a valid OpenGraph meta tag
 * @param {object} element
 * @return {boolean}
 */
function isOpenGraphElement(element) {
	let isOpenGraphMetaTag = false;

	if (element && element.getAttribute) {
		const property = element.getAttribute('property');

		if (property) {
			isOpenGraphMetaTag = openGraphTagPatterns.some(regExp =>
				property.match(regExp)
			);
		}
	}

	return isOpenGraphMetaTag;
}

/**
 * Updates context with OpenGraph information
 * @param {object} request Request object to alter
 * @param {object} analytics Analytics instance
 * @return {object} The updated request object
 */
function openGraph(request) {
	const elements = [].slice.call(document.querySelectorAll('meta'));
	const openGraphElements = elements.filter(isOpenGraphElement);

	const openGraphData = openGraphElements.reduce((data, meta) => {
		return {
			[meta.getAttribute('property')]: meta.getAttribute('content'),
			...data,
		};
	}, {});

	request.context = {
		...openGraphData,
		...request.context,
	};

	return request;
}

export {openGraph};
export default openGraph;