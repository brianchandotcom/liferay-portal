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
	});

	it('throws an error when passing something else than an object or a string as the first argument', () => {
		const fn1 = () => {
			return addParams(1);
		};
		expect(fn1).toThrow(TypeError);

		const fn2 = () => {
			return addParams(['one', 'two']);
		};
		expect(fn2).toThrow(TypeError);
	});

	it('throws an error when passing an invalid URL', () => {
		const fn = () => {
			return addParams('something=other', 'invalid url');
		};
		expect(fn).toThrow();
	});
});
