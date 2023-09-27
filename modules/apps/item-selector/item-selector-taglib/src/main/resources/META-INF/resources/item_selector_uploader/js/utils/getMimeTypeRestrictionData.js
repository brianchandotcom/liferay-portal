/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const MIME_TYPE_RESTRICTIONS = {
	DEFAULT: '*',
	IMAGE: 'image',
	VIDEO: 'video',
};

export default function getMimeTypeRestrictionData(mimeTypeRestriction) {
	if (mimeTypeRestriction === MIME_TYPE_RESTRICTIONS.IMAGE) {
		return {
			icon: 'document-image',
			text: Liferay.Language.get(
				'drag-and-drop-your-images-or-browse-to-upload'
			),
		};
	}

	if (mimeTypeRestriction === MIME_TYPE_RESTRICTIONS.VIDEO) {
		return {
			icon: 'document-multimedia',
			text: Liferay.Language.get(
				'drag-and-drop-your-videos-or-browse-to-upload'
			),
		};
	}

	return {
		icon: 'document-text',
		text: Liferay.Language.get(
			'drag-and-drop-your-files-or-browse-to-upload'
		),
	};
}
