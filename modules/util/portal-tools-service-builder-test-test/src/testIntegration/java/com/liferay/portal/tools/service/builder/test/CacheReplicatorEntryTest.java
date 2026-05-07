/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntryTable;
import com.liferay.portal.tools.service.builder.test.service.CacheReplicatorEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheReplicatorEntryUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class CacheReplicatorEntryTest implements Serializable {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	public static final TomcatClusterTestRule tomcatClusterTestRule =
		new TomcatClusterTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		TomcatCluster.Builder builder1 =
			tomcatClusterTestRule.buildTomcatNode();

		_tomcatNode1 = builder1.build();

		_tomcatNode1.start(true);

		TomcatCluster.Builder builder2 =
			tomcatClusterTestRule.buildTomcatNode();

		_tomcatNode2 = builder2.build();

		_tomcatNode2.start(true);
	}

	@Test
	public void testClearCacheReplicatorMessageCount() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		String name =
			"CacheReplicatorEntryTest_" + RandomTestUtil.randomString();

		long entryId = _tomcatNode1.syncExecute(
			() -> {
				CacheReplicatorEntry cacheReplicatorEntry =
					CacheReplicatorEntryLocalServiceUtil.
						addCacheReplicatorEntry(companyId, name);

				return cacheReplicatorEntry.getCacheReplicatorEntryId();
			});

		try {
			_tomcatNode1.syncExecute(
				() -> {
					_populateCachesAndAssert("Node 1", companyId, name);

					return null;
				});

			_tomcatNode2.syncExecute(
				() -> {
					_populateCachesAndAssert("Node 2", companyId, name);

					return null;
				});

			_tomcatNode1.syncExecute(
				() -> {
					_registerListeners();

					TestPortalCacheListener.setCountDownLatch(
						new CountDownLatch(11));

					return null;
				});

			_tomcatNode2.syncExecute(
				() -> {
					_registerListeners();

					TestPortalCacheListener.setCountDownLatch(
						new CountDownLatch(11));

					return null;
				});

			_tomcatNode1.syncExecute(
				() -> {
					CacheReplicatorEntry cacheReplicatorEntry =
						CacheReplicatorEntryLocalServiceUtil.
							fetchCacheReplicatorEntry(entryId);

					CacheReplicatorEntryUtil.clearCache(cacheReplicatorEntry);

					CountDownLatch countDownLatch =
						TestPortalCacheListener.getCountDownLatch();

					Assert.assertTrue(
						"Node 1 did not receive all listener events within " +
							"60 seconds (still missing " +
								countDownLatch.getCount() + ")",
						countDownLatch.await(60, TimeUnit.SECONDS));

					_assertAllCachesEmpty("Node 1");

					_assertEvents(
						"Node 1 listener events mismatch",
						TestPortalCacheListener.INSTANCE.getEvents(),
						Arrays.asList("remove"),
						Arrays.asList("remove", "remove", "remove", "remove"),
						Arrays.asList("removeAll", "removeAll"),
						Arrays.asList("removeAll", "removeAll"),
						Arrays.asList("removeAll", "removeAll"));

					_assertEvents(
						"Node 1 replicator events mismatch",
						TestPortalCacheReplicator.INSTANCE.getEvents(),
						Arrays.asList("remove"),
						Arrays.asList("remove", "remove"),
						Arrays.asList("removeAll"), Arrays.asList("removeAll"),
						Arrays.asList("removeAll"));

					return null;
				});

			_tomcatNode2.syncExecute(
				() -> {
					CountDownLatch countDownLatch =
						TestPortalCacheListener.getCountDownLatch();

					Assert.assertTrue(
						"Node 2 did not receive all listener events within " +
							"60 seconds (still missing " +
								countDownLatch.getCount() + ")",
						countDownLatch.await(60, TimeUnit.SECONDS));

					_assertAllCachesEmpty("Node 2");

					_assertEvents(
						"Node 2 listener events mismatch",
						TestPortalCacheListener.INSTANCE.getEvents(),
						Arrays.asList("remove"),
						Arrays.asList("remove", "remove", "remove", "remove"),
						Arrays.asList("removeAll", "removeAll"),
						Arrays.asList("removeAll", "removeAll"),
						Arrays.asList("removeAll", "removeAll"));

					_assertEvents(
						"Node 2 replicator events mismatch",
						TestPortalCacheReplicator.INSTANCE.getEvents(), null,
						Arrays.asList("remove", "remove"),
						Arrays.asList("removeAll"), Arrays.asList("removeAll"),
						Arrays.asList("removeAll"));

					return null;
				});
		}
		finally {
			_tomcatNode1.syncExecute(
				() -> {
					_unregisterListeners();

					CacheReplicatorEntryLocalServiceUtil.
						deleteCacheReplicatorEntry(entryId);

					return null;
				});

			_tomcatNode2.syncExecute(
				() -> {
					_unregisterListeners();

					return null;
				});
		}
	}

	public abstract static class BaseTestListener
		implements PortalCacheListener<Serializable, Serializable>,
				   Serializable {

		@Override
		public void dispose() {
		}

		public Map<String, List<String>> getEvents() {
			return _events;
		}

		@Override
		public void notifyEntryEvicted(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {

			record(portalCache, "evicted");
		}

		@Override
		public void notifyEntryExpired(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {

			record(portalCache, "expired");
		}

		@Override
		public void notifyEntryPut(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {

			record(portalCache, "put");
		}

		@Override
		public void notifyEntryRemoved(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {

			record(portalCache, "remove");
		}

		@Override
		public void notifyEntryUpdated(
				PortalCache<Serializable, Serializable> portalCache,
				Serializable key, Serializable value, int timeToLive)
			throws PortalCacheException {

			record(portalCache, "updated");
		}

		@Override
		public void notifyRemoveAll(
				PortalCache<Serializable, Serializable> portalCache)
			throws PortalCacheException {

			record(portalCache, "removeAll");
		}

		public void reset() {
			_events.clear();
		}

		protected void record(
			PortalCache<Serializable, Serializable> portalCache,
			String action) {

			List<String> events = _events.computeIfAbsent(
				portalCache.getPortalCacheName(),
				key -> Collections.synchronizedList(new ArrayList<>()));

			events.add(action);
		}

		private final Map<String, List<String>> _events =
			new ConcurrentHashMap<>();

	}

	public static class TestPortalCacheListener extends BaseTestListener {

		public static final TestPortalCacheListener INSTANCE =
			new TestPortalCacheListener();

		public static CountDownLatch getCountDownLatch() {
			return _countDownLatch;
		}

		public static void setCountDownLatch(CountDownLatch countDownLatch) {
			_countDownLatch = countDownLatch;
		}

		@Override
		public void reset() {
			super.reset();

			_countDownLatch = null;
		}

		@Override
		protected void record(
			PortalCache<Serializable, Serializable> portalCache,
			String action) {

			super.record(portalCache, action);

			if (_countDownLatch != null) {
				_countDownLatch.countDown();
			}
		}

		private static CountDownLatch _countDownLatch;

	}

	public static class TestPortalCacheReplicator
		extends BaseTestListener
		implements PortalCacheReplicator<Serializable, Serializable> {

		public static final TestPortalCacheReplicator INSTANCE =
			new TestPortalCacheReplicator();

	}

	private void _assertAllCachesEmpty(String nodeName) {
		for (PortalCache<Serializable, Serializable> portalCache :
				_getAllRelatedPortalCaches(false)) {

			List<Serializable> keys = portalCache.getKeys();

			Assert.assertTrue(
				nodeName + " portal cache " + portalCache.getPortalCacheName() +
					" should be empty but contains keys " + keys,
				keys.isEmpty());
		}
	}

	private void _assertEvents(
		String message, Map<String, List<String>> actualEvents,
		List<String> entityActions, List<String> entityFinderActions,
		List<String> list1Actions, List<String> list2Actions,
		List<String> dslActions) {

		Assert.assertEquals(
			message,
			HashMapBuilder.<String, List<String>>put(
				_getPortalCacheName(
					EntityCacheUtil.getEntityCache(), _ENTITY_CLASS_NAME),
				() -> entityActions
			).put(
				_getPortalCacheName(
					FinderCacheUtil.getFinderCache(), _DSL_QUERY_CACHE_NAME),
				() -> dslActions
			).put(
				_getPortalCacheName(
					FinderCacheUtil.getFinderCache(), _ENTITY_CLASS_NAME),
				() -> entityFinderActions
			).put(
				_getPortalCacheName(
					FinderCacheUtil.getFinderCache(), _LIST1_CACHE_NAME),
				() -> list1Actions
			).put(
				_getPortalCacheName(
					FinderCacheUtil.getFinderCache(), _LIST2_CACHE_NAME),
				() -> list2Actions
			).build(),
			actualEvents);
	}

	private List<PortalCache<Serializable, Serializable>>
		_getAllRelatedPortalCaches(boolean allowNotExisted) {

		return ListUtil.fromArray(
			_getPortalCache(
				EntityCacheUtil.getEntityCache(), _ENTITY_CLASS_NAME,
				allowNotExisted),
			_getPortalCache(
				FinderCacheUtil.getFinderCache(), _ENTITY_CLASS_NAME,
				allowNotExisted),
			_getPortalCache(
				FinderCacheUtil.getFinderCache(), _LIST1_CACHE_NAME,
				allowNotExisted),
			_getPortalCache(
				FinderCacheUtil.getFinderCache(), _LIST2_CACHE_NAME,
				allowNotExisted),
			_getPortalCache(
				FinderCacheUtil.getFinderCache(), _DSL_QUERY_CACHE_NAME,
				allowNotExisted));
	}

	private PortalCache<Serializable, Serializable> _getPortalCache(
		Object cache, String key, boolean allowNotExisted) {

		ConcurrentMap<String, PortalCache<Serializable, Serializable>>
			portalCaches = ReflectionTestUtil.getFieldValue(
				cache, "_portalCaches");

		PortalCache<Serializable, Serializable> portalCache = portalCaches.get(
			key);

		if ((portalCache == null) && !allowNotExisted) {
			throw new IllegalStateException(
				"No portal cache for \"" + key + "\"");
		}

		return portalCache;
	}

	private String _getPortalCacheName(Object cache, String key) {
		PortalCache<Serializable, Serializable> portalCache = _getPortalCache(
			cache, key, false);

		return portalCache.getPortalCacheName();
	}

	private void _populateCachesAndAssert(
		String nodeName, long companyId, String name) {

		CacheReplicatorEntryLocalServiceUtil.fetchCacheReplicatorEntryByName(
			name);
		CacheReplicatorEntryLocalServiceUtil.
			getCacheReplicatorEntriesByCompanyId(companyId);
		CacheReplicatorEntryLocalServiceUtil.
			getCacheReplicatorEntriesByCompanyId(companyId, 0, 10);
		CacheReplicatorEntryLocalServiceUtil.
			getCacheReplicatorEntriesCountByCompanyId(companyId);

		CacheReplicatorEntryLocalServiceUtil.dslQueryCount(
			DSLQueryFactoryUtil.count(
			).from(
				CacheReplicatorEntryTable.INSTANCE
			).where(
				CacheReplicatorEntryTable.INSTANCE.companyId.eq(companyId)
			));

		Map<String, Map<String, FinderPath>> finderPathsMap =
			ReflectionTestUtil.getFieldValue(
				FinderCacheUtil.getFinderCache(), "_finderPathsMap");

		Map<String, FinderPath> entityFinderPaths = finderPathsMap.get(
			_ENTITY_CLASS_NAME);

		Assert.assertEquals(
			nodeName +
				" entity-keyed finder cache should have one finder path " +
					"(fetchByName) but has " + entityFinderPaths.keySet(),
			1, entityFinderPaths.size());

		Map<String, FinderPath> list1FinderPaths = finderPathsMap.get(
			_LIST1_CACHE_NAME);

		Assert.assertEquals(
			nodeName +
				" .List1 cache should have one finder path (findByCompanyId " +
					"paginated) but has " + list1FinderPaths.keySet(),
			1, list1FinderPaths.size());

		Map<String, FinderPath> list2FinderPaths = finderPathsMap.get(
			_LIST2_CACHE_NAME);

		Assert.assertEquals(
			nodeName +
				" .List2 cache should have two finder paths (findByCompanyId " +
					"unpaginated and countByCompanyId) but has " +
						list2FinderPaths.keySet(),
			2, list2FinderPaths.size());

		for (PortalCache<Serializable, Serializable> portalCache :
				_getAllRelatedPortalCaches(false)) {

			List<Serializable> keys = portalCache.getKeys();

			Assert.assertFalse(
				nodeName + " portal cache " + portalCache.getPortalCacheName() +
					" should be populated but is empty",
				keys.isEmpty());
		}
	}

	private void _registerListeners() {
		TestPortalCacheListener.INSTANCE.reset();
		TestPortalCacheReplicator.INSTANCE.reset();

		for (PortalCache<Serializable, Serializable> portalCache :
				_getAllRelatedPortalCaches(false)) {

			portalCache.registerPortalCacheListener(
				TestPortalCacheListener.INSTANCE);
			portalCache.registerPortalCacheListener(
				TestPortalCacheReplicator.INSTANCE);
		}
	}

	private void _unregisterListeners() {
		for (PortalCache<Serializable, Serializable> portalCache :
				_getAllRelatedPortalCaches(true)) {

			if (portalCache == null) {
				continue;
			}

			portalCache.unregisterPortalCacheListener(
				TestPortalCacheListener.INSTANCE);
			portalCache.unregisterPortalCacheListener(
				TestPortalCacheReplicator.INSTANCE);
		}
	}

	private static final String _DSL_QUERY_CACHE_NAME = "CacheReplicatorEntry";

	private static final String _ENTITY_CLASS_NAME =
		"com.liferay.portal.tools.service.builder.test.model.impl." +
			"CacheReplicatorEntryImpl";

	private static final String _LIST1_CACHE_NAME =
		_ENTITY_CLASS_NAME + ".List1";

	private static final String _LIST2_CACHE_NAME =
		_ENTITY_CLASS_NAME + ".List2";

	private static transient TomcatNode _tomcatNode1;
	private static transient TomcatNode _tomcatNode2;

}