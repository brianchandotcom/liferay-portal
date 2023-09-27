/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.persistence.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;
import com.liferay.portal.tools.service.builder.test.model.CacheDisabledEntry;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheDisabledEntryPersistence;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class CacheDisabledEntryPersistenceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.portal.tools.service.builder.test.service"));

	@Test
	public void testEntityCacheDisabled() {
		CacheDisabledEntry cacheDisabledEntry =
			_cacheDisabledEntryPersistence.create(RandomTestUtil.nextLong());

		_cacheDisabledEntry = _cacheDisabledEntryPersistence.update(
			cacheDisabledEntry);

		CacheDisabledEntry existingCacheDisabledEntry =
			_cacheDisabledEntryPersistence.fetchByPrimaryKey(
				_cacheDisabledEntry.getPrimaryKey());

		Assert.assertNotNull(existingCacheDisabledEntry);
		Assert.assertFalse(existingCacheDisabledEntry.isCachedModel());

		PortalCache<?, ?> portalCache = _entityCache.getPortalCache(
			_cacheDisabledEntry.getClass());

		List<?> keys = portalCache.getKeys();

		Assert.assertTrue(keys.isEmpty());
	}

	@Test
	public void testFinderCacheDisabled() {
		CacheDisabledEntry cacheDisabledEntry =
			_cacheDisabledEntryPersistence.create(RandomTestUtil.nextLong());

		cacheDisabledEntry.setName("Test");

		_cacheDisabledEntry = _cacheDisabledEntryPersistence.update(
			cacheDisabledEntry);

		// Test 1, unique cache

		CacheDisabledEntry existingCacheDisabledEntry =
			_cacheDisabledEntryPersistence.fetchByName(
				_cacheDisabledEntry.getName());

		Assert.assertEquals(_cacheDisabledEntry, existingCacheDisabledEntry);

		_assertFinderCache(_FINDER_CLASS_NAME_ENTITY);

		// Test 2, without pagination

		Assert.assertEquals(1, _cacheDisabledEntryPersistence.countAll());

		_assertFinderCache(_FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		// Test 3, with pagination

		List<CacheDisabledEntry> cacheDisabledEntryList =
			_cacheDisabledEntryPersistence.findAll(0, 10);

		Assert.assertEquals(
			cacheDisabledEntryList.toString(), 1,
			cacheDisabledEntryList.size());

		_assertFinderCache(_FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
	}

	private void _assertFinderCache(String cacheName) {
		Map<String, PortalCache<Serializable, Serializable>> portalCaches =
			ReflectionTestUtil.getFieldValue(_finderCache, "_portalCaches");

		Assert.assertFalse(portalCaches.containsKey(cacheName));
	}

	private static final String _FINDER_CLASS_NAME_ENTITY =
		"com.liferay.portal.tools.service.builder.test.model.impl." +
			"CacheDisabledEntryImpl";

	private static final String _FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		_FINDER_CLASS_NAME_ENTITY + ".List1";

	private static final String _FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		_FINDER_CLASS_NAME_ENTITY + ".List2";

	@DeleteAfterTestRun
	private CacheDisabledEntry _cacheDisabledEntry;

	@Inject
	private CacheDisabledEntryPersistence _cacheDisabledEntryPersistence;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private FinderCache _finderCache;

}