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

'use strict';

import Cacheable from '../../../src/main/resources/META-INF/resources/senna/cacheable/Cacheable';

describe('Cacheable', () => {
	it('should not be cacheable by default', () => {
		assert.ok(!new Cacheable().isCacheable());
	});

	it('should be cacheable', () => {
		var cacheable = new Cacheable();
		cacheable.setCacheable(true);
		assert.ok(cacheable.isCacheable());
	});

	it('should clear cache when toggle cacheable state', () => {
		var cacheable = new Cacheable();
		cacheable.setCacheable(true);
		cacheable.addCache('data');
		assert.strictEqual('data', cacheable.getCache());
		cacheable.setCacheable(false);
		assert.strictEqual(null, cacheable.getCache());
	});

	it('should clear cache on dispose', () => {
		var cacheable = new Cacheable();
		cacheable.setCacheable(true);
		cacheable.addCache('data');
		cacheable.dispose();
		assert.strictEqual(null, cacheable.getCache());
	});
});
