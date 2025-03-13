/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from '../types';

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
 */
function isOpenGraphElement(element: Element) {
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
 */
function openGraph(request: {context: Analytics.Context}) {
	const elements = [].slice.call(document.querySelectorAll('meta'));
	const openGraphElements = elements.filter(isOpenGraphElement) as Element[];

	const openGraphData = openGraphElements.reduce(
		(data, meta) =>
			Object.assign(data, {
				[meta.getAttribute('property') as string]:
					meta.getAttribute('content'),
			}),
		{}
	);

	Object.assign(request.context, openGraphData);

	return request;
}

export {openGraph};
export default openGraph;
