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

	it('adds the parameters to the portlet URL', () => {
		expect(addParams({id: 'sampleId', order: 'asc'}, sampleUrl)).toEqual(
			'http://example.com/?id=sampleId&order=asc'
		);
	});

	it('trims parameter if it has space before and adds it to base url', () => {
		expect(addParams('   sampleId', sampleUrl)).toEqual(
			'http://example.com/?sampleId='
		);
	});

	it('sets portlet url when base url has parameters', () => {
		expect(
			addParams({id: 'sampleId', size: '10'}, sampleUrlWithParams)
		).toEqual('http://example.com/?param=&id=sampleId&size=10');
	});
});
