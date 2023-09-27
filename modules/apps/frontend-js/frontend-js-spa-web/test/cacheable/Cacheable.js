/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
