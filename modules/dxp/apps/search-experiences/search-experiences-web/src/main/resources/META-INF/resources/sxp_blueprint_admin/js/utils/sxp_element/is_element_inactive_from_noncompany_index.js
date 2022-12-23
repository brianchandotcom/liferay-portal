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

/**
 * When a non-company index is selected, this conditional is to check which
 * elements should be disabled.
 * - Disables elements in the sidebar
 * - Disables elements in the query builder
 * @param {boolean} isIndexCompany
 * @param {object} sxpElement
 * @returns {boolean}
 */
export default function isElementInactiveFromNonCompanyIndex(
	isIndexCompany = true,
	sxpElement = {}
) {
	if (isIndexCompany) {
		return false;
	}

	// Allow specific elements to be used across all indexes.

	if (
		sxpElement.externalReferenceCode === 'BOOST_ALL_KEYWORDS_MATCH' ||
		sxpElement.externalReferenceCode === 'FILTER_BY_EXACT_TERMS_MATCH' ||
		sxpElement.externalReferenceCode === 'HIDE_BY_EXACT_TERM_MATCH' ||
		sxpElement.externalReferenceCode ===
			'SEARCH_WITH_QUERY_STRING_SYNTAX' ||
		sxpElement.externalReferenceCode === 'TEXT_MATCH_OVER_MULTIPLE_FIELDS'
	) {
		return false;
	}

	// Allow custom out-of-the-box elements (Custom JSON, Paste Elasticsearch Query).

	if (
		sxpElement.elementDefinition?.category === 'custom' &&
		sxpElement.readOnly
	) {
		return false;
	}

	// Disable other system elements.

	if (sxpElement.readOnly) {
		return true;
	}

	return false;
}
