/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function updatePreviewImage({
	contents,
	fileEntryId,
	previewURL,
}: {
	contents: Array<{
		content: string;
		fragmentEntryLinkId: string;
	}>;
	fileEntryId: string;
	previewURL: string;
}): {
	readonly contents: {
		content: string;
		fragmentEntryLinkId: string;
	}[];
	readonly fileEntryId: string;
	readonly previewURL: string;
	readonly type: 'UPDATE_PREVIEW_IMAGE';
};
