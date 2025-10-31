/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.PortalCacheException;
import com.liferay.portal.kernel.cache.PortalCacheHelperUtil;
import com.liferay.portal.kernel.cache.PortalCacheListener;
import com.liferay.portal.kernel.cache.PortalCacheListenerScope;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.TomcatClusterTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.test.cluster.tomcat.TomcatCluster;
import com.liferay.portal.test.cluster.tomcat.TomcatNode;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ClusterCacheReplicationTest {

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

	@After
	public void tearDown() throws Exception {
		String testCacheName = ClusterCacheReplicationTest.class.getName();

		_tomcatNode1.syncExecute(
			() -> {
				PortalCacheHelperUtil.removePortalCache(
					PortalCacheManagerNames.MULTI_VM, testCacheName);

				return null;
			});

		_tomcatNode2.syncExecute(
			() -> {
				PortalCacheHelperUtil.removePortalCache(
					PortalCacheManagerNames.MULTI_VM, testCacheName);

				return null;
			});
	}

	@Test
	public void testDoNotReplicatePut() throws Exception {
		String testCacheName = ClusterCacheReplicationTest.class.getName();

		String testKey = "testKey";
		String testValue = "testValue";
		String updateValue = "test.value.update";

		// Assert empty on node 1, set up property

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePuts", false);

					return portalCache.get(testKey);
				}));

		// Assert empty on node 2, set up property and listener

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePuts", false);

					portalCache.registerPortalCacheListener(
						new TestPortalCacheListener());

					return portalCache.get(testKey);
				}));

		// Assert node1 can see the value it just put

		Assert.assertEquals(
			testValue,
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.put(testKey, testValue);

					return portalCache.get(testKey);
				}));

		// Assert empty on node 2, because put is not replicate

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					return portalCache.get(testKey);
				}));

		// Assert node2 can see the value it just put

		Assert.assertEquals(
			updateValue,
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.put(testKey, updateValue);

					return portalCache.get(testKey);
				}));

		// Assert node1 has the old value, because put is not replicate

		Assert.assertEquals(
			testValue,
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					return portalCache.get(testKey);
				}));

		// Assert node1 is empty after removal

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.remove(testKey);

					return portalCache.get(testKey);
				}));

		// Assert node2 is also empty now, because remove is still replicate

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener.await(
						portalCache,
						TestPortalCacheListener::getRemoveCountDownLatch);

					return portalCache.get(testKey);
				}));
	}

	@Test
	public void testEntityCacheFinderCacheSynchronization() throws Exception {
		String userGroupNamePrefix =
			ClusterCacheReplicationTest.class.getSimpleName();

		TomcatNode.ClusterExecutable<ArrayList<UserGroup>>
			getUserGroupsClusterExecutable = () -> new ArrayList<>(
				UserGroupLocalServiceUtil.getUserGroups(
					TestPropsValues.getCompanyId(), userGroupNamePrefix + "%",
					QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		// Assert node 1 does not see any UserGroup with testing prefix

		List<UserGroup> userGroups = _tomcatNode1.syncExecute(
			getUserGroupsClusterExecutable);

		Assert.assertTrue(userGroups.toString(), userGroups.isEmpty());

		// Add UserGroup1 on node 1

		String userGroupName1 = userGroupNamePrefix + "_userGroup1";

		UserGroup userGroup1 = _tomcatNode1.syncExecute(
			() -> {
				UserGroup userGroup = UserGroupTestUtil.addUserGroup();

				userGroup.setName(userGroupName1);

				return UserGroupLocalServiceUtil.updateUserGroup(userGroup);
			});

		Assert.assertEquals(userGroupName1, userGroup1.getName());

		// Assert node 2 can see UserGroup1

		userGroups = _tomcatNode2.syncExecute(getUserGroupsClusterExecutable);

		Assert.assertEquals(userGroups.toString(), 1, userGroups.size());
		Assert.assertEquals(userGroup1, userGroups.get(0));

		// Add UserGroup2 on node 1

		String userGroupName2 = userGroupNamePrefix + "_userGroup2";

		UserGroup userGroup2 = _tomcatNode1.syncExecute(
			() -> {
				UserGroup userGroup = UserGroupTestUtil.addUserGroup();

				userGroup.setName(userGroupName2);

				return UserGroupLocalServiceUtil.updateUserGroup(userGroup);
			});

		Assert.assertEquals(userGroupName2, userGroup2.getName());

		// Assert node 2 can see UserGroup1 and UserGroup2

		userGroups = _tomcatNode2.syncExecute(getUserGroupsClusterExecutable);

		Assert.assertEquals(userGroups.toString(), 2, userGroups.size());
		Assert.assertEquals(userGroup1, userGroups.get(0));
		Assert.assertEquals(userGroup2, userGroups.get(1));

		// Remove UserGroup1 and UserGroup2 on node 2

		userGroups = _tomcatNode2.syncExecute(
			() -> {
				UserGroupLocalServiceUtil.deleteUserGroup(userGroup1);
				UserGroupLocalServiceUtil.deleteUserGroup(userGroup2);

				return getUserGroupsClusterExecutable.execute();
			});

		Assert.assertTrue(userGroups.toString(), userGroups.isEmpty());

		// Assert node 1 sees no UserGroup

		userGroups = _tomcatNode1.syncExecute(getUserGroupsClusterExecutable);

		Assert.assertTrue(userGroups.toString(), userGroups.isEmpty());
	}

	@Test
	public void testPingPongFlushing() throws Exception {
		String testCacheName = ClusterCacheReplicationTest.class.getName();

		String testKey = "testKey";
		String testValue1 = "testValue1";
		String testValue2 = "testValue2";

		// Assert empty and put value on node 1

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					String value = portalCache.get(testKey);

					portalCache.put(testKey, testValue1);

					return value;
				}));

		// Assert node 1 can see the value it put

		Assert.assertEquals(
			testValue1,
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					return portalCache.get(testKey);
				}));

		// Assert empty and put value on node 2

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					String value = portalCache.get(testKey);

					portalCache.put(testKey, testValue2);

					return value;
				}));

		// Assert node 2 can see the value it put

		Assert.assertEquals(
			testValue2,
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					return portalCache.get(testKey);
				}));

		// Assert node 1 sees no value

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					return portalCache.get(testKey);
				}));
	}

	@Test
	public void testReplicateByCopy() throws Exception {
		String testCacheName = ClusterCacheReplicationTest.class.getName();

		String testKey = "testKey";
		String testValue = "testValue";

		// Assert node 1 is empty

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePutsViaCopy", true);

					return portalCache.get(testKey);
				}));

		// Assert node 2 is empty

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePutsViaCopy", true);

					portalCache.registerPortalCacheListener(
						new TestPortalCacheListener());

					return portalCache.get(testKey);
				}));

		// Assert node1 can see the value it just put

		Assert.assertEquals(
			testValue,
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.put(testKey, testValue);

					return portalCache.get(testKey);
				}));

		// Assert node2 has the same value because replicatePutsViaCopy=true

		Assert.assertEquals(
			testValue,
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener.await(
						portalCache,
						TestPortalCacheListener::getPutCountDownLatch);

					return portalCache.get(testKey);
				}));

		// Assert node1 is empty after removal

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.remove(testKey);

					return portalCache.get(testKey);
				}));

		// Assert node2 is also empty

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener.await(
						portalCache,
						TestPortalCacheListener::getRemoveCountDownLatch);

					return portalCache.get(testKey);
				}));
	}

	@Test
	public void testReplicateByRemove() throws Exception {
		String testCacheName = ClusterCacheReplicationTest.class.getName();

		String testKey = "testKey";
		String testValue = "testValue";

		// Assert node 1 is empty

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePutsViaCopy", false);

					return portalCache.get(testKey);
				}));

		// Assert node 2 is empty

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePutsViaCopy", false);

					portalCache.registerPortalCacheListener(
						new TestPortalCacheListener());

					return portalCache.get(testKey);
				}));

		// Assert node 1 can see the value it just put

		Assert.assertEquals(
			testValue,
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.put(testKey, testValue);

					return portalCache.get(testKey);
				}));

		// Assert node 2 is still empty, and triggered notifyEntryRemoved

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener.await(
						portalCache,
						TestPortalCacheListener::getRemoveCountDownLatch);

					return portalCache.get(testKey);
				}));

		// Assert node1 is empty after removeAll

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.removeAll();

					return portalCache.get(testKey);
				}));

		// Assert node2 is still empty, and triggered notifyEntryRemoved

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener.await(
						portalCache,
						TestPortalCacheListener::getRemoveAllCountDownLatch);

					return portalCache.get(testKey);
				}));
	}

	@Test
	public void testReplicateByUpdateViaCopy() throws Exception {
		String testCacheName = ClusterCacheReplicationTest.class.getName();

		String testKey = "testKey";
		String testValue = "testValue";
		String updateValue = "test.value.update";

		// Assert node 1 is empty

		Assert.assertNull(
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePutsViaCopy", true);

					_setReplicateProperties(
						portalCache, "_replicateUpdatesViaCopy", true);

					return portalCache.get(testKey);
				}));

		// Assert node 2 is empty

		Assert.assertNull(
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					_setReplicateProperties(
						portalCache, "_replicatePutsViaCopy", true);

					_setReplicateProperties(
						portalCache, "_replicateUpdatesViaCopy", true);

					portalCache.registerPortalCacheListener(
						new TestPortalCacheListener());

					return portalCache.get(testKey);
				}));

		// Assert node1 can see the value it just put

		Assert.assertEquals(
			testValue,
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.put(testKey, testValue);

					return portalCache.get(testKey);
				}));

		// Assert node2 has the same value because replicatePutsViaCopy=true

		Assert.assertEquals(
			testValue,
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener.await(
						portalCache,
						TestPortalCacheListener::getPutCountDownLatch);

					return portalCache.get(testKey);
				}));

		// Assert node1 can see the value it just update

		Assert.assertEquals(
			updateValue,
			_tomcatNode1.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					portalCache.put(testKey, updateValue);

					return portalCache.get(testKey);
				}));

		// Assert node 2 has the same value after node 1 update

		Assert.assertEquals(
			updateValue,
			_tomcatNode2.syncExecute(
				() -> {
					PortalCache<String, String> portalCache =
						PortalCacheHelperUtil.getPortalCache(
							PortalCacheManagerNames.MULTI_VM, testCacheName);

					TestPortalCacheListener.await(
						portalCache,
						TestPortalCacheListener::getUpdatedCountDownLatch);

					return portalCache.get(testKey);
				}));
	}

	public static class TestPortalCacheListener
		implements PortalCacheListener<String, String>, Serializable {

		public static void await(
				PortalCache<?, ?> portalCache,
				Function<TestPortalCacheListener, CountDownLatch> function)
			throws InterruptedException {

			TestPortalCacheListener testPortalCacheListener =
				(TestPortalCacheListener)_getListenerByName(
					portalCache, TestPortalCacheListener.class.getName());

			CountDownLatch countDownLatch = function.apply(
				testPortalCacheListener);

			countDownLatch.await();
		}

		public TestPortalCacheListener() {
			_putCountDownLatch = new CountDownLatch(1);
			_removeCountDownLatch = new CountDownLatch(1);
			_removeAllCountDownLatch = new CountDownLatch(1);
			_updatedCountDownLatch = new CountDownLatch(1);
		}

		@Override
		public void dispose() {
		}

		public CountDownLatch getPutCountDownLatch() {
			return _putCountDownLatch;
		}

		public CountDownLatch getRemoveAllCountDownLatch() {
			return _removeAllCountDownLatch;
		}

		public CountDownLatch getRemoveCountDownLatch() {
			return _removeCountDownLatch;
		}

		public CountDownLatch getUpdatedCountDownLatch() {
			return _updatedCountDownLatch;
		}

		@Override
		public void notifyEntryEvicted(
				PortalCache<String, String> portalCache, String key,
				String value, int timeToLive)
			throws PortalCacheException {
		}

		@Override
		public void notifyEntryExpired(
				PortalCache<String, String> portalCache, String key,
				String value, int timeToLive)
			throws PortalCacheException {
		}

		@Override
		public void notifyEntryPut(
				PortalCache<String, String> portalCache, String key,
				String value, int timeToLive)
			throws PortalCacheException {

			_putCountDownLatch.countDown();
		}

		@Override
		public void notifyEntryRemoved(
				PortalCache<String, String> portalCache, String key,
				String value, int timeToLive)
			throws PortalCacheException {

			_removeCountDownLatch.countDown();
		}

		@Override
		public void notifyEntryUpdated(
				PortalCache<String, String> portalCache, String key,
				String value, int timeToLive)
			throws PortalCacheException {

			_updatedCountDownLatch.countDown();
		}

		@Override
		public void notifyRemoveAll(PortalCache<String, String> portalCache)
			throws PortalCacheException {

			_removeAllCountDownLatch.countDown();
		}

		private final CountDownLatch _putCountDownLatch;
		private final CountDownLatch _removeAllCountDownLatch;
		private final CountDownLatch _removeCountDownLatch;
		private final CountDownLatch _updatedCountDownLatch;

	}

	private static PortalCacheListener<?, ?> _getListenerByName(
		PortalCache<?, ?> portalCache, String listenerName) {

		PortalCache<?, ?> unwrapPortalCache = ReflectionTestUtil.getFieldValue(
			portalCache, "_portalCache");

		Object aggregatedPortalCacheListener = ReflectionTestUtil.getFieldValue(
			unwrapPortalCache, "aggregatedPortalCacheListener");

		ConcurrentMap<PortalCacheListener<?, ?>, PortalCacheListenerScope>
			portalCacheListeners = ReflectionTestUtil.getFieldValue(
				aggregatedPortalCacheListener, "_portalCacheListeners");

		PortalCacheListener<?, ?> portalCacheListener = null;

		for (PortalCacheListener<?, ?> listener :
				portalCacheListeners.keySet()) {

			if (Objects.equals(
					listener.getClass(
					).getName(),
					listenerName)) {

				portalCacheListener = listener;
			}
		}

		return portalCacheListener;
	}

	private static void _setReplicateProperties(
		PortalCache<?, ?> portalCache, String replicateProperty,
		boolean value) {

		PortalCacheListener<?, ?> portalCacheListener = _getListenerByName(
			portalCache, _CLASS_NAME_EHCACHE_PORTAL_CACHE_REPLICATOR);

		Object portalCacheReplicator = ReflectionTestUtil.getFieldValue(
			portalCacheListener, "_portalCacheReplicator");

		ReflectionTestUtil.setFieldValue(
			portalCacheReplicator, replicateProperty, value);
	}

	private static final String _CLASS_NAME_EHCACHE_PORTAL_CACHE_REPLICATOR =
		"com.liferay.portal.cache.ehcache.internal.events." +
			"EhcachePortalCacheReplicatorUtil$EhcachePortalCacheReplicator";

	private static TomcatNode _tomcatNode1;
	private static TomcatNode _tomcatNode2;

}