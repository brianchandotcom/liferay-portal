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

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.LoadFinderCacheEntry;
import com.liferay.portal.tools.service.builder.test.service.LoadFinderCacheEntryLocalService;
import com.liferay.portal.tools.service.builder.test.service.persistence.LoadFinderCacheEntryPersistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class LoadFinderCacheEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testLoadFinderCache() {
		_loadFinderCacheEntries = new ArrayList<>();

		_loadFinderCacheEntries.add(_addLoadFinderCacheEntry(1L, 10L, "test1"));
		_loadFinderCacheEntries.add(_addLoadFinderCacheEntry(1L, 10L, "test2"));
		_loadFinderCacheEntries.add(_addLoadFinderCacheEntry(1L, 10L, "test3"));
		_loadFinderCacheEntries.add(_addLoadFinderCacheEntry(1L, 11L, "test4"));
		_loadFinderCacheEntries.add(_addLoadFinderCacheEntry(1L, 11L, "test5"));

		BasePersistence<?> basePersistence =
			_loadFinderCacheEntryLocalService.getBasePersistence();

		basePersistence.clearCache();

		FinderPath finderPathFetchByUniqueName =
			_loadFinderCacheEntryPersistence.getFinderPathFetchByUniqueName();
		FinderPath finderPathWithoutPaginationFindByGroupId =
			_loadFinderCacheEntryPersistence.
				getFinderPathWithoutPaginationFindByGroupId();
		FinderPath finderPathWithoutPaginationFindByC_G =
			_loadFinderCacheEntryPersistence.
				getFinderPathWithoutPaginationFindByC_G();

		Assert.assertNull(
			_fetchFromFinderCache(
				finderPathFetchByUniqueName, new Object[] {"test1"}));

		Assert.assertNull(
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByGroupId, new Object[] {10L}));

		Assert.assertNull(
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByGroupId, new Object[] {11L}));

		Assert.assertNull(
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByC_G, new Object[] {1L, 10L}));

		Assert.assertNull(
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByC_G, new Object[] {1L, 11L}));

		_loadFinderCacheEntryLocalService.loadFinderCache(
			new FinderPath[] {
				finderPathFetchByUniqueName,
				finderPathWithoutPaginationFindByGroupId,
				finderPathWithoutPaginationFindByC_G
			});

		Assert.assertArrayEquals(
			new String[] {"test1"},
			_fetchFromFinderCache(
				finderPathFetchByUniqueName, new Object[] {"test1"}));

		Assert.assertArrayEquals(
			new String[] {"test1", "test2", "test3"},
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByGroupId, new Object[] {10L}));

		Assert.assertArrayEquals(
			new String[] {"test4", "test5"},
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByGroupId, new Object[] {11L}));

		Assert.assertArrayEquals(
			new String[] {"test1", "test2", "test3"},
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByC_G, new Object[] {1L, 10L}));

		Assert.assertArrayEquals(
			new String[] {"test4", "test5"},
			_fetchFromFinderCache(
				finderPathWithoutPaginationFindByC_G, new Object[] {1L, 11L}));
	}

	private LoadFinderCacheEntry _addLoadFinderCacheEntry(
		long companyId, long groupId, String uniqueName) {

		LoadFinderCacheEntry loadFinderCacheEntry =
			_loadFinderCacheEntryLocalService.createLoadFinderCacheEntry(
				RandomTestUtil.nextLong());

		loadFinderCacheEntry.setCompanyId(companyId);
		loadFinderCacheEntry.setGroupId(groupId);
		loadFinderCacheEntry.setUniqueName(uniqueName);

		return _loadFinderCacheEntryLocalService.updateLoadFinderCacheEntry(
			loadFinderCacheEntry);
	}

	private String[] _fetchFromFinderCache(
		FinderPath finderPath, Object[] arguments) {

		Object result = _finderCache.getResult(
			finderPath, arguments, _loadFinderCacheEntryPersistence);

		if (result == null) {
			return null;
		}

		List<LoadFinderCacheEntry> loadFinderCacheEntries;

		if (result instanceof List) {
			loadFinderCacheEntries = (List<LoadFinderCacheEntry>)result;
		}
		else {
			loadFinderCacheEntries = Collections.singletonList(
				(LoadFinderCacheEntry)result);
		}

		List<String> uniqueNames = new ArrayList<>();

		for (LoadFinderCacheEntry loadFinderCacheEntry :
				loadFinderCacheEntries) {

			uniqueNames.add(loadFinderCacheEntry.getUniqueName());
		}

		Collections.sort(uniqueNames);

		return uniqueNames.toArray(new String[0]);
	}

	@Inject
	private FinderCache _finderCache;

	@DeleteAfterTestRun
	private List<LoadFinderCacheEntry> _loadFinderCacheEntries;

	@Inject
	private LoadFinderCacheEntryLocalService _loadFinderCacheEntryLocalService;

	@Inject
	private LoadFinderCacheEntryPersistence _loadFinderCacheEntryPersistence;

}