/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
