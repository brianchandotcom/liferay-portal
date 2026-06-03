/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getPathError,
	isValidPath,
	isValidPathList,
} from '../../../js/health_scan/validation';

describe('isValidPath', () => {
	it('accepts a single exact relative page', () => {
		expect(isValidPath('/about-us')).toBe(true);
	});

	it('accepts a directory wildcard', () => {
		expect(isValidPath('/blog/*')).toBe(true);
	});

	it('accepts nested sub-paths with a wildcard', () => {
		expect(isValidPath('/help/articles/*')).toBe(true);
	});

	it('rejects a path missing the leading slash', () => {
		expect(isValidPath('blog/*')).toBe(false);
	});

	it('rejects a bare wildcard in favor of the "All Pages" scope', () => {
		expect(isValidPath('*')).toBe(false);
	});

	it('rejects an absolute URL', () => {
		expect(isValidPath('https://www.example.com/blog/')).toBe(false);
	});

	it('rejects a path containing a space', () => {
		expect(isValidPath('/blog posts')).toBe(false);
	});

	it('rejects paths containing angle brackets or braces', () => {
		expect(isValidPath('/blog<1>')).toBe(false);
		expect(isValidPath('/blog{1}')).toBe(false);
	});

	it('rejects an empty string', () => {
		expect(isValidPath('')).toBe(false);
	});
});

describe('isValidPathList', () => {
	it('accepts a single valid entry', () => {
		expect(isValidPathList('/about-us')).toBe(true);
	});

	it('accepts multiple comma-and-space separated entries', () => {
		expect(isValidPathList('/products/*, /services/*')).toBe(true);
	});

	it('tolerates inconsistent spacing around the comma', () => {
		expect(isValidPathList('/products/*,/services/*')).toBe(true);
		expect(isValidPathList('/products/*  ,  /services/*')).toBe(true);
	});

	it('rejects the list when any entry is invalid', () => {
		expect(isValidPathList('/products/*, services/*')).toBe(false);
		expect(isValidPathList('/products/*, https://x.com/a')).toBe(false);
	});

	it('rejects an empty or whitespace-only value', () => {
		expect(isValidPathList('')).toBe(false);
		expect(isValidPathList('   ')).toBe(false);
	});

	it('rejects a value made up only of separators', () => {
		expect(isValidPathList(', ,')).toBe(false);
	});
});

describe('getPathError', () => {
	it('returns the required message for an empty or blank value', () => {
		expect(getPathError('')).toBe('this-field-is-required');
		expect(getPathError('   ')).toBe('this-field-is-required');
	});

	it('returns the format message for a malformed value', () => {
		expect(getPathError('blog/*')).toBe('invalid-path-format');
		expect(getPathError('/products/*, services/*')).toBe(
			'invalid-path-format'
		);
	});

	it('returns undefined for a valid value', () => {
		expect(getPathError('/blog/*')).toBeUndefined();
		expect(getPathError('/products/*, /services/*')).toBeUndefined();
	});
});
