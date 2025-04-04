/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Iterates through the search parameters of a given URL object.
 * If a key contains ALL of the strings passed in`subStringsToMatch`,
 * its value is updated to `newSearchParamValue` and iteration stops.
 *
 * @param {string} newSearchParamValue - The new value to set for the matching search parameter.
 * @param {string[]} subStringsToMatch - An array of substrings. A search parameter's key
 *                                       must include ALL of these substrings to be considered a match.
 * @param {URL} urlObject - The URL object whose search parameters will be modified.
 *
 * @throws {TypeError} If urlObject is not an instance of URL.
 */
export function updateURLObjectSearchParam(
	newSearchParamValue,
	subStringsToMatch,
	urlObject
) {
	for (const searchParam of urlObject.searchParams.keys()) {
		const searchParamMatch = subStringsToMatch.every((subString) =>
			searchParam.includes(subString)
		);

		if (searchParamMatch) {
			urlObject.searchParams.set(searchParam, newSearchParamValue);

			break;
		}
	}
}

/**
 * Takes an instance of URL and replaces a specific segment of its
 * pathname with a new segment, but only if the segment includes certain
 * substrings.
 *
 * @param {string} newPathSegment - The new path segment to use as a replacement.
 *                                   It will be URL-encoded before
 *                                   being inserted into the pathname.
 * @param {string[]} subStringsToMatch - An array of substrings. A path segment
 *                                       must contain ALL of these substrings to
 *                                       be considered a match and be replaced.
 * @param {URL} urlObject - The URL object whose pathname will be modified.
 *
 * @throws {TypeError} If urlObject is not an instance of URL.
 */
export function replaceURLObjectPathnameSegment(
	newPathSegment,
	subStringsToMatch,
	urlObject
) {
	const pathSegments = urlObject.pathname.split('/');

	const updatedPathSegments = pathSegments.map((path) => {
		const pathSegmentMatch = subStringsToMatch.every((subString) =>
			path.includes(subString)
		);

		return pathSegmentMatch ? encodeURIComponent(newPathSegment) : path;
	});

	urlObject.pathname = updatedPathSegments.join('/');
}
