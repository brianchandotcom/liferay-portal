/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface FileEntry {
	fileEntryURL: string;
}
export interface ImageSize {
	label: string;
	mediaQuery?: string;
	size: string;
	url?: string;
	value: string;
	width: number;
}
declare const _default: {
	getAvailableImageConfigurations({
		fileEntryId,
	}: {
		fileEntryId: string;
	}): Promise<ImageSize[]>;
	getFileEntry({fileEntryId}: {fileEntryId: string}): Promise<FileEntry>;
};
export default _default;
