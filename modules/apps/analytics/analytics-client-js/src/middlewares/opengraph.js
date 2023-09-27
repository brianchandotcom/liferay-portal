/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
 * @param {Object} element
 * @returns {boolean}
 */
function isOpenGraphElement(element) {
	let openGraphMetaTag = false;

	if (element && element.getAttribute) {
		const property = element.getAttribute('property');

		if (property) {
			openGraphMetaTag = openGraphTagPatterns.some((regExp) =>
				property.match(regExp)
			);
		}
	}

	return openGraphMetaTag;
}

/**
 * Updates context with OpenGraph information
 * @param {Object} request Request object to alter
 * @param {Object} analytics Analytics instance
 * @returns {Object} The updated request object
 */
function openGraph(request) {
	const elements = [].slice.call(document.querySelectorAll('meta'));
	const openGraphElements = elements.filter(isOpenGraphElement);

	const openGraphData = openGraphElements.reduce(
		(data, meta) =>
			Object.assign(data, {
				[meta.getAttribute('property')]: meta.getAttribute('content'),
			}),
		{}
	);

	Object.assign(request.context, openGraphData);

	return request;
}

export {openGraph};
export default openGraph;
