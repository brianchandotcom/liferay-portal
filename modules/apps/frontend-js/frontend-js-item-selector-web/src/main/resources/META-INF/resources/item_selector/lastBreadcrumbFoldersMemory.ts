/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type FolderCrumb = {
	id: number | null;
	label: string;
	scopeId?: number | null;
};

/**
 * The folder path the user last browsed to when confirming a selection, kept
 * per opener key for the lifetime of the page so the CMS files item selector
 * reopens where the previously selected file lives instead of always falling
 * back to the root. Each field passes its own key so one field's last folder
 * does not leak into another.
 */
const lastBreadcrumbFoldersMap = new Map<string, FolderCrumb[]>();

export function getLastBreadcrumbFolders(key: string): FolderCrumb[] | null {
	return lastBreadcrumbFoldersMap.get(key) ?? null;
}

export function setLastBreadcrumbFolders(
	key: string,
	folders: FolderCrumb[] | null
): void {
	if (folders) {
		lastBreadcrumbFoldersMap.set(key, folders);
	}
	else {
		lastBreadcrumbFoldersMap.delete(key);
	}
}
