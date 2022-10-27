/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
