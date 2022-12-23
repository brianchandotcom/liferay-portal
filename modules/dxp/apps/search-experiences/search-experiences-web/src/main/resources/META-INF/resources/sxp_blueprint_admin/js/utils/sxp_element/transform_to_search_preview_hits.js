/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
