/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FILE_MIME_TYPE_CSS_CLASSES,
	FILE_MIME_TYPE_ICONS,
} from '../../../../src/main/resources/META-INF/resources/main/utils/mime_type/constants';
import {default as mimeTypeUtils} from '../../../../src/main/resources/META-INF/resources/main/utils/mime_type/index';

describe('mimeTypeUtils', () => {
	describe('getClassNameFromMimeType', () => {
		it('It should return the specific CSS class for an exact mimeType match', () => {
			const mimeType = 'application/x-7z-compressed';
			const result = mimeTypeUtils.getClassNameFromMimeType(mimeType);

			expect(result).toBe(FILE_MIME_TYPE_CSS_CLASSES[mimeType]);
		});

		it('It should return the prefix class when the specific subtype is missing', () => {
			const mimeType = 'video/quicktime';
			const result = mimeTypeUtils.getClassNameFromMimeType(mimeType);

			expect(result).toBe(FILE_MIME_TYPE_CSS_CLASSES['video']);
		});

		it('It should return the default class for unknown types', () => {
			const result = mimeTypeUtils.getClassNameFromMimeType(
				'application/x-unknown'
			);

			expect(result).toBe(FILE_MIME_TYPE_CSS_CLASSES['default']);
		});
	});

	describe('getIconFromMimeType', () => {
		it('It should return the correct icon for a known mimeType', () => {
			const mimeType = 'application/pdf';
			const result = mimeTypeUtils.getIconFromMimeType(mimeType);

			expect(result).toBe(
				FILE_MIME_TYPE_ICONS[mimeType] ||
					FILE_MIME_TYPE_ICONS['default']
			);
		});

		it('It should fallback to the generic icon for partial matches', () => {
			const mimeType = 'image/bmp';
			const result = mimeTypeUtils.getIconFromMimeType(mimeType);

			// If image/bmp doesn't exist, it should use the 'image' icon

			expect(result).toBe(FILE_MIME_TYPE_ICONS['image']);
		});
	});
});
