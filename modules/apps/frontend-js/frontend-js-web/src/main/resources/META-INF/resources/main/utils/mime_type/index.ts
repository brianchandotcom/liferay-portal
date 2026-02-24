/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FILE_MIME_TYPE_CSS_CLASSES, FILE_MIME_TYPE_ICONS} from './constants';
import getMimeTypeProperty from './getMimeTypeProperty';

function getClassNameFromMimeType(mimeType: string) {
	return getMimeTypeProperty({
		map: FILE_MIME_TYPE_CSS_CLASSES,
		mimeType,
	});
}

function getIconFromMimeType(mimeType: string) {
	return getMimeTypeProperty({
		map: FILE_MIME_TYPE_ICONS,
		mimeType,
	});
}

export default {
	getClassNameFromMimeType,
	getIconFromMimeType,
};
