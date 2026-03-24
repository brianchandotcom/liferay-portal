/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Extracts folderId and groupIds from an OData filter string.
 *
 * @param {string} filter
 * @returns {{folderId?: string, groupIds?: string}}
 */
export function getFolderIdAndGroupIdsFromFilter(filter: string): {
	folderId?: string;
	groupIds?: string;
} {
	const folderIdMatch = filter.match(/folderId eq (\d+)/);
	const groupIdsMatch = filter.match(/groupIds\/any\(g:g in \([\d, ]+\)\)/);

	return {
		folderId: folderIdMatch ? folderIdMatch[1] : undefined,
		groupIds: groupIdsMatch ? groupIdsMatch[0] : undefined,
	};
}
