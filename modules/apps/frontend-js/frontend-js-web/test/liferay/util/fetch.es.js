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

/* eslint no-undef: "warn" */

'use strict';

import fetch from '../../../src/main/resources/META-INF/resources/liferay/util/fetch.es';

describe('Liferay.Util.fetch', () => {
	const sampleUrl = 'http://sampleurl.com';

	it('should apply default settings if none are given', () => {
		global.fetch = jest.fn((resource, init) => {
			expect(init).toEqual({
				credentials: 'include'
			});
		});

		fetch(sampleUrl);

		global.fetch.mockRestore();
	});

	it('should override a default setting with given setting', () => {
		global.fetch = jest.fn((resource, init) => {
			expect(init).toEqual({
				credentials: 'omit'
			});
		});

		fetch(sampleUrl, {
			credentials: 'omit'
		});

		global.fetch.mockRestore();
	});

	it('should merge default settings with given different settings', () => {
		global.fetch = jest.fn((resource, init) => {
			expect(init).toEqual({
				credentials: 'include',
				method: 'get'
			});
		});

		fetch(sampleUrl, {
			method: 'get'
		});

		global.fetch.mockRestore();
	});
});
