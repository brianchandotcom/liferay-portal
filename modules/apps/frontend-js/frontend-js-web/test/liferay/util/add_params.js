/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addParams from '../../../src/main/resources/META-INF/resources/liferay/util/add_params';

describe('Liferay.Util.addParams', () => {
	const sampleUrl = 'http://example.com';
	const sampleUrlWithParams = 'http://example.com?param';

	describe('when using an object as the first argument', () => {
		it('adds parameters to a URL', () => {
			expect(
				addParams({id: 'sampleId', order: 'asc'}, sampleUrl)
			).toEqual('http://example.com/?id=sampleId&order=asc');
		});

		it('adds additional parameters to a URL that already contains parameters', () => {
			expect(
				addParams({id: 'sampleId', size: '10'}, sampleUrlWithParams)
			).toEqual('http://example.com/?param=&id=sampleId&size=10');
		});
	});

	describe('when using a string as the first argument', () => {
		it('adds a parameter to a URL', () => {
			expect(addParams('something=other', sampleUrl)).toEqual(
				'http://example.com/?something=other'
			);
		});

		it('adds a parameter with no value to a URL', () => {
			expect(addParams('something', sampleUrl)).toEqual(
				'http://example.com/?something='
			);
		});

		it('trims parameters before adding them to a URL', () => {
			expect(addParams('   sampleId', sampleUrl)).toEqual(
				'http://example.com/?sampleId='
			);
		});

		it('adds multiple parameters to a URL', () => {
			expect(addParams('something=other&sampleId=2', sampleUrl)).toEqual(
				'http://example.com/?something=other&sampleId=2'
			);
		});

		it('adds parameters when the base URL already has parameters', () => {
			expect(addParams('extra=thing', sampleUrlWithParams)).toEqual(
				'http://example.com/?param=&extra=thing'
			);
		});
	});

	it('throws an error if not passed params as an object or a string', () => {
		expect(() => addParams(1)).toThrow(TypeError);

		expect(() => addParams(['one', 'two'])).toThrow(TypeError);
	});

	it('throws an error when passed an invalid URL', () => {
		expect(() =>
			addParams('something=other', 'not a relative or absolute URL')
		).toThrow(/Invalid URL: not a relative or absolute URL/);
	});

	it('gracefully handles a relative path as the second argument', () => {

		// Invariant: Jest environment sets up location like so:

		expect(location.href).toBe('http://localhost/');

		expect(addParams('something=other', '/hello-there')).toEqual(
			`http://localhost/hello-there?something=other`
		);
	});

	it('changes the base URL if the second argument is an absolute URL', () => {
		const url = addParams('something=other', 'https://liferay.com');

		expect(url).toEqual('https://liferay.com/?something=other');
	});
});
