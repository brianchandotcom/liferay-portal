/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
		(data, meta) => ({
			[meta.getAttribute('property')]: meta.getAttribute('content'),
			...data,
		}),
		{}
	);

	request.context = {
		...openGraphData,
		...request.context,
	};

	return request;
}

export {openGraph};
export default openGraph;
