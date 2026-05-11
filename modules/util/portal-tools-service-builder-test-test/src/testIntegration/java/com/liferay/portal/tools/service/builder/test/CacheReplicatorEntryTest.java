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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntry;
import com.liferay.portal.tools.service.builder.test.model.CacheReplicatorEntryTable;
import com.liferay.portal.tools.service.builder.test.service.CacheReplicatorEntryLocalServiceUtil;
import com.liferay.portal.tools.service.builder.test.service.persistence.CacheReplicatorEntryUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
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
		_tomcatNode1 = tomcatClusterTestRule.buildTomcatNode(
		).build();

		_tomcatNode1.start(true);

		_tomcatNode2 = tomcatClusterTestRule.buildTomcatNode(
		).build();

		_tomcatNode2.start(true);
	}

	@Test
	public void testClearCacheReplicatorMessageCount() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		String name =
			"CacheReplicatorEntryTest_" + RandomTestUtil.randomString();

		CacheReplicatorEntry cacheReplicatorEntry =
			CacheReplicatorEntryLocalServiceUtil.addCacheReplicatorEntry(
				companyId, name);

		long entryId = cacheReplicatorEntry.getCacheReplicatorEntryId();

		try {
			TomcatNode.ClusterExecutable<Serializable> clusterExecutable =
				() -> {
					_populateCaches(companyId, name);

					_assertFinderPaths();

					for (PortalCache<Serializable, Serializable> portalCache :
							_getAllRelatedPortalCaches(false)) {

						List<Serializable> keys = portalCache.getKeys();

						Assert.assertFalse(keys.isEmpty());
					}

					return null;
				};

			_tomcatNode1.syncExecute(clusterExecutable);

			_tomcatNode2.syncExecute(clusterExecutable);

			_populateCaches(companyId, name);

			List<PortalCache<Serializable, Serializable>> portalCaches =
				_getAllRelatedPortalCaches(false);

			String[] cacheNames = new String[portalCaches.size()];

			for (int i = 0; i < portalCaches.size(); i++) {
				cacheNames[i] = portalCaches.get(
					i
				).getPortalCacheName();
			}

			String[][] events = {
				{cacheNames[0], "removeAll"}, {cacheNames[1], "remove"},
				{cacheNames[2], "remove", "remove"},
				{cacheNames[3], "removeAll"}, {cacheNames[4], "removeAll"}
			};

			_tomcatNode1.syncExecute(
				() -> {
					_registerListeners(cacheNames, events);

					return null;
				});

			_tomcatNode2.syncExecute(
				() -> {
					_registerListeners(cacheNames, events);

					return null;
				});

			_tomcatNode1.syncExecute(
				() -> {
					CacheReplicatorEntryUtil.clearCache(
						CacheReplicatorEntryLocalServiceUtil.
							fetchCacheReplicatorEntry(entryId));

					Assert.assertTrue(TestPortalCacheListener.await());

					_assertAllCachesEmpty();

					Assert.assertArrayEquals(
						"Node 1 listener events mismatch", events,
						TestPortalCacheListener.getEvents());

					Assert.assertArrayEquals(
						"Node 1 replicator events mismatch",
						new String[][] {
							{cacheNames[0]}, {cacheNames[1], "remove"},
							{cacheNames[2]}, {cacheNames[3]}, {cacheNames[4]}
						},
						TestPortalCacheReplicator.getEvents());

					return null;
				});

			_tomcatNode2.syncExecute(
				() -> {
					Assert.assertTrue(TestPortalCacheListener.await());

					_assertAllCachesEmpty();

					Assert.assertArrayEquals(
						"Node 2 listener events mismatch", events,
						TestPortalCacheListener.getEvents());

					Assert.assertArrayEquals(
						"Node 2 replicator events mismatch",
						new String[][] {
							{cacheNames[0]}, {cacheNames[1]}, {cacheNames[2]},
							{cacheNames[3]}, {cacheNames[4]}
						},
						TestPortalCacheReplicator.getEvents());

					return null;
				});
		}
		finally {
			_tomcatNode1.syncExecute(
				() -> {
					_unregisterListeners();

					return null;
				});

			_tomcatNode2.syncExecute(
				() -> {
					_unregisterListeners();

					return null;
				});

			CacheReplicatorEntryLocalServiceUtil.deleteCacheReplicatorEntry(
				entryId);
		}
	}

	public abstract static class BaseTestListener
		implements PortalCacheListener<Serializable, Serializable>,
				   Serializable {

		@Override
		public void dispose() {
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

		protected synchronized void record(
			PortalCache<Serializable, Serializable> portalCache,
			String action) {

			String cacheName = portalCache.getPortalCacheName();

			for (int i = 0; i < events.length; i++) {
				if (events[i][0].equals(cacheName)) {
					events[i] = ArrayUtil.append(events[i], action);

					return;
				}
			}
		}

		protected void resetEvents(String[] cacheNames) {
			events = new String[cacheNames.length][];

			for (int i = 0; i < cacheNames.length; i++) {
				events[i] = new String[] {cacheNames[i]};
			}
		}

		protected String[][] events;

	}

	public static class TestPortalCacheListener extends BaseTestListener {

		public static final TestPortalCacheListener INSTANCE =
			new TestPortalCacheListener();

		public static boolean await() throws InterruptedException {
			return INSTANCE._countDownLatch.await(60, TimeUnit.SECONDS);
		}

		public static String[][] getEvents() {
			return INSTANCE.events;
		}

		public static void reset(String[] cacheNames, String[][] events) {
			INSTANCE.resetEvents(cacheNames);

			int totalEvents = 0;

			for (String[] event : events) {
				totalEvents += event.length - 1;
			}

			INSTANCE._countDownLatch = new CountDownLatch(totalEvents);
		}

		@Override
		protected synchronized void record(
			PortalCache<Serializable, Serializable> portalCache,
			String action) {

			super.record(portalCache, action);

			if (_countDownLatch != null) {
				_countDownLatch.countDown();
			}
		}

		private CountDownLatch _countDownLatch;

	}

	public static class TestPortalCacheReplicator
		extends BaseTestListener
		implements PortalCacheReplicator<Serializable, Serializable> {

		public static final TestPortalCacheReplicator INSTANCE =
			new TestPortalCacheReplicator();

		public static String[][] getEvents() {
			return INSTANCE.events;
		}

		public static void reset(String[] cacheNames) {
			INSTANCE.resetEvents(cacheNames);
		}

	}

	private void _assertAllCachesEmpty() {
		for (PortalCache<Serializable, Serializable> portalCache :
				_getAllRelatedPortalCaches(false)) {

			List<Serializable> keys = portalCache.getKeys();

			Assert.assertTrue(keys.isEmpty());
		}
	}

	private void _assertFinderPaths() {
		Map<String, Map<String, FinderPath>> finderPathsMap =
			ReflectionTestUtil.getFieldValue(
				FinderCacheUtil.getFinderCache(), "_finderPathsMap");

		Map<String, FinderPath> entityFinderPaths = finderPathsMap.get(
			_ENTITY_CLASS_NAME);

		Assert.assertEquals(1, entityFinderPaths.size());

		Map<String, FinderPath> list1FinderPaths = finderPathsMap.get(
			_LIST1_CACHE_NAME);

		Assert.assertEquals(1, list1FinderPaths.size());

		Map<String, FinderPath> list2FinderPaths = finderPathsMap.get(
			_LIST2_CACHE_NAME);

		Assert.assertEquals(2, list2FinderPaths.size());
	}

	private List<PortalCache<Serializable, Serializable>>
		_getAllRelatedPortalCaches(boolean allowNotExisted) {

		return ListUtil.fromArray(
			_getPortalCache(
				FinderCacheUtil.getFinderCache(), _DSL_QUERY_CACHE_NAME,
				allowNotExisted),
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

	private void _populateCaches(long companyId, String name) {
		CacheReplicatorEntryLocalServiceUtil.fetchCacheReplicatorEntry(
			name);
		CacheReplicatorEntryLocalServiceUtil.
			getCacheReplicatorEntries(companyId);
		CacheReplicatorEntryLocalServiceUtil.
			getCacheReplicatorEntries(companyId, 0, 10);
		CacheReplicatorEntryLocalServiceUtil.
			getCacheReplicatorEntriesCountByCompanyId(companyId);

		CacheReplicatorEntryLocalServiceUtil.dslQueryCount(
			DSLQueryFactoryUtil.count(
			).from(
				CacheReplicatorEntryTable.INSTANCE
			).where(
				CacheReplicatorEntryTable.INSTANCE.companyId.eq(companyId)
			));
	}

	private void _registerListeners(String[] cacheNames, String[][] events) {
		TestPortalCacheListener.reset(cacheNames, events);
		TestPortalCacheReplicator.reset(cacheNames);

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