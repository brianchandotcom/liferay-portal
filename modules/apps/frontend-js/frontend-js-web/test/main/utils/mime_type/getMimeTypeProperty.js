/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getMimeTypeProperty from '../../../../src/main/resources/META-INF/resources/main/utils/mime_type/getMimeTypeProperty';

describe('getMimeTypeProperty', () => {
	const mockMap = {
		'default': 'DEFAULT_VALUE',
		'image': 'GENERIC_IMAGE_VALUE',
		'image/jpeg': 'JPEG_VALUE',
		'video': 'GENERIC_VIDEO_VALUE',
	};

	it('It should return the exact value when the mimeType exists on the map.', () => {
		const result = getMimeTypeProperty({
			map: mockMap,
			mimeType: 'image/jpeg',
		});
		expect(result).toBe('JPEG_VALUE');
	});

	it('It should return the prefix value when the specified mimeType does not exist.', () => {
		const result = getMimeTypeProperty({
			map: mockMap,
			mimeType: 'image/png',
		});
		expect(result).toBe('GENERIC_IMAGE_VALUE');
	});

	it('It should return the default value when the mimeType and prefix are not found.', () => {
		const result = getMimeTypeProperty({
			map: mockMap,
			mimeType: 'application/pdf',
		});
		expect(result).toBe('DEFAULT_VALUE');
	});

	it('It should return the default value when the mimeType is an empty string.', () => {
		const result = getMimeTypeProperty({
			map: mockMap,
			mimeType: '',
		});
		expect(result).toBe('DEFAULT_VALUE');
	});

	it('Must handle mimetypes without a backslash correctly (using the self as a prefix)', () => {
		const result = getMimeTypeProperty({
			map: mockMap,
			mimeType: 'video',
		});
		expect(result).toBe('GENERIC_VIDEO_VALUE');
	});
});
