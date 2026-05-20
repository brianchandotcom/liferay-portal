/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {validateFileExtension} from '../../js/Attachment/util/attachment';

jest.mock('frontend-js-web', () => ({
	sub: (key: string, value: string) => `${key}: ${value}`,
}));

(globalThis as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

describe('validateFileExtension', () => {
	describe('wildcard (*)', () => {
		it('accepts an extension-less file when accepted extensions is *', () => {
			expect(validateFileExtension('*', '')).toBeUndefined();
		});

		it('accepts a file with extension when accepted extensions is *', () => {
			expect(validateFileExtension('*', 'pdf')).toBeUndefined();
		});

		it('accepts any file when * appears among other extensions', () => {
			expect(validateFileExtension('pdf, *, jpg', 'txt')).toBeUndefined();
		});
	});

	describe('explicit extension list', () => {
		it('returns undefined when the file extension matches', () => {
			expect(validateFileExtension('pdf', 'pdf')).toBeUndefined();
		});

		it('returns an error when an extension-less file is submitted', () => {
			expect(validateFileExtension('pdf', '')).toMatchObject({
				displayErrors: true,
				valid: false,
			});
		});

		it('returns an error when the extension does not match', () => {
			expect(validateFileExtension('pdf, jpg', 'txt')).toMatchObject({
				displayErrors: true,
				valid: false,
			});
		});

		it('performs a case-insensitive comparison', () => {
			expect(validateFileExtension('PDF', 'pdf')).toBeUndefined();
			expect(validateFileExtension('pdf', 'PDF')).toBeUndefined();
		});
	});
});
