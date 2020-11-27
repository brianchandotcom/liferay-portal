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

import Cacheable from '../../src/main/resources/META-INF/resources/cacheable/Cacheable';

describe('Cacheable', () => {
	it('is not cacheable by default', () => {
		expect(!new Cacheable().isCacheable()).toBeTruthy();
	});

	it('is set to cacheable', () => {
		const cacheable = new Cacheable();
		cacheable.setCacheable(true);
		expect(cacheable.isCacheable()).toBeTruthy();
	});

	it('is cache when toggle cacheable state', () => {
		const cacheable = new Cacheable();
		cacheable.setCacheable(true);
		cacheable.addCache('data');
		expect(cacheable.getCache()).toBe('data');
		cacheable.setCacheable(false);
		expect(cacheable.getCache()).toBeNull();
	});

	it('clears cache on dispose', () => {
		const cacheable = new Cacheable();
		cacheable.setCacheable(true);
		cacheable.addCache('data');
		cacheable.dispose();
		expect(cacheable.getCache()).toBeNull();
	});
});
