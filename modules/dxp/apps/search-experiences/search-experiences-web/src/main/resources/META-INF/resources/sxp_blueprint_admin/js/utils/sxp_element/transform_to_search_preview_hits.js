/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {removeBrackets} from '../functions/remove_brackets';

/**
 * Converts the results from search preview into the format expected
 * for `hits` property inside PreviewSidebar.
 *
 * @param {object} results Contains search hits
 * @returns {Array}
 */
export default function transformToSearchPreviewHits(results) {
	const searchHits = results.searchHits?.hits || [];

	const finalHits = [];

	searchHits.forEach((hit) => {
		const documentFields = {};

		Object.entries(hit.documentFields).forEach(([key, value]) => {
			documentFields[key] = removeBrackets(
				JSON.stringify(value.values || [])
			);
		});

		finalHits.push({
			...hit,
			documentFields,
		});
	});

	return finalHits;
}
