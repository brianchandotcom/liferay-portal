/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.oauth.exception.NoSuchUserException;
import com.liferay.oauth.model.OAuthUser;
import com.liferay.oauth.service.OAuthUserLocalServiceUtil;
import com.liferay.oauth.service.persistence.OAuthUserPersistence;
import com.liferay.oauth.service.persistence.OAuthUserUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class OAuthUserPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.oauth.service"));

	@Before
	public void setUp() {
		_persistence = OAuthUserUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<OAuthUser> iterator = _oAuthUsers.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthUser oAuthUser = _persistence.create(pk);

		Assert.assertNotNull(oAuthUser);

		Assert.assertEquals(oAuthUser.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		_persistence.remove(newOAuthUser);

		OAuthUser existingOAuthUser = _persistence.fetchByPrimaryKey(newOAuthUser.getPrimaryKey());

		Assert.assertNull(existingOAuthUser);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addOAuthUser();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthUser newOAuthUser = _persistence.create(pk);

		newOAuthUser.setCompanyId(RandomTestUtil.nextLong());

		newOAuthUser.setUserId(RandomTestUtil.nextLong());

		newOAuthUser.setUserName(RandomTestUtil.randomString());

		newOAuthUser.setCreateDate(RandomTestUtil.nextDate());

		newOAuthUser.setModifiedDate(RandomTestUtil.nextDate());

		newOAuthUser.setOAuthApplicationId(RandomTestUtil.nextLong());

		newOAuthUser.setAccessToken(RandomTestUtil.randomString());

		newOAuthUser.setAccessSecret(RandomTestUtil.randomString());

		_oAuthUsers.add(_persistence.update(newOAuthUser));

		OAuthUser existingOAuthUser = _persistence.findByPrimaryKey(newOAuthUser.getPrimaryKey());

		Assert.assertEquals(existingOAuthUser.getOAuthUserId(),
			newOAuthUser.getOAuthUserId());
		Assert.assertEquals(existingOAuthUser.getCompanyId(),
			newOAuthUser.getCompanyId());
		Assert.assertEquals(existingOAuthUser.getUserId(),
			newOAuthUser.getUserId());
		Assert.assertEquals(existingOAuthUser.getUserName(),
			newOAuthUser.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingOAuthUser.getCreateDate()),
			Time.getShortTimestamp(newOAuthUser.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingOAuthUser.getModifiedDate()),
			Time.getShortTimestamp(newOAuthUser.getModifiedDate()));
		Assert.assertEquals(existingOAuthUser.getOAuthApplicationId(),
			newOAuthUser.getOAuthApplicationId());
		Assert.assertEquals(existingOAuthUser.getAccessToken(),
			newOAuthUser.getAccessToken());
		Assert.assertEquals(existingOAuthUser.getAccessSecret(),
			newOAuthUser.getAccessSecret());
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByOAuthApplicationId() throws Exception {
		_persistence.countByOAuthApplicationId(RandomTestUtil.nextLong());

		_persistence.countByOAuthApplicationId(0L);
	}

	@Test
	public void testCountByAccessToken() throws Exception {
		_persistence.countByAccessToken("");

		_persistence.countByAccessToken("null");

		_persistence.countByAccessToken((String)null);
	}

	@Test
	public void testCountByU_OAI() throws Exception {
		_persistence.countByU_OAI(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByU_OAI(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		OAuthUser existingOAuthUser = _persistence.findByPrimaryKey(newOAuthUser.getPrimaryKey());

		Assert.assertEquals(existingOAuthUser, newOAuthUser);
	}

	@Test(expected = NoSuchUserException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<OAuthUser> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("OAuth_OAuthUser",
			"oAuthUserId", true, "companyId", true, "userId", true, "userName",
			true, "createDate", true, "modifiedDate", true,
			"oAuthApplicationId", true, "accessToken", true, "accessSecret",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		OAuthUser existingOAuthUser = _persistence.fetchByPrimaryKey(newOAuthUser.getPrimaryKey());

		Assert.assertEquals(existingOAuthUser, newOAuthUser);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthUser missingOAuthUser = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOAuthUser);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		OAuthUser newOAuthUser1 = addOAuthUser();
		OAuthUser newOAuthUser2 = addOAuthUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthUser1.getPrimaryKey());
		primaryKeys.add(newOAuthUser2.getPrimaryKey());

		Map<Serializable, OAuthUser> oAuthUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, oAuthUsers.size());
		Assert.assertEquals(newOAuthUser1,
			oAuthUsers.get(newOAuthUser1.getPrimaryKey()));
		Assert.assertEquals(newOAuthUser2,
			oAuthUsers.get(newOAuthUser2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, OAuthUser> oAuthUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(oAuthUsers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthUser.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, OAuthUser> oAuthUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, oAuthUsers.size());
		Assert.assertEquals(newOAuthUser,
			oAuthUsers.get(newOAuthUser.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, OAuthUser> oAuthUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(oAuthUsers.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthUser.getPrimaryKey());

		Map<Serializable, OAuthUser> oAuthUsers = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, oAuthUsers.size());
		Assert.assertEquals(newOAuthUser,
			oAuthUsers.get(newOAuthUser.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = OAuthUserLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<OAuthUser>() {
				@Override
				public void performAction(OAuthUser oAuthUser) {
					Assert.assertNotNull(oAuthUser);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("oAuthUserId",
				newOAuthUser.getOAuthUserId()));

		List<OAuthUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		OAuthUser existingOAuthUser = result.get(0);

		Assert.assertEquals(existingOAuthUser, newOAuthUser);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("oAuthUserId",
				RandomTestUtil.nextLong()));

		List<OAuthUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("oAuthUserId"));

		Object newOAuthUserId = newOAuthUser.getOAuthUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("oAuthUserId",
				new Object[] { newOAuthUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingOAuthUserId = result.get(0);

		Assert.assertEquals(existingOAuthUserId, newOAuthUserId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthUser.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("oAuthUserId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("oAuthUserId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		OAuthUser newOAuthUser = addOAuthUser();

		_persistence.clearCache();

		OAuthUser existingOAuthUser = _persistence.findByPrimaryKey(newOAuthUser.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingOAuthUser.getAccessToken(),
				ReflectionTestUtil.invoke(existingOAuthUser,
					"getOriginalAccessToken", new Class<?>[0])));

		Assert.assertEquals(Long.valueOf(existingOAuthUser.getUserId()),
			ReflectionTestUtil.<Long>invoke(existingOAuthUser,
				"getOriginalUserId", new Class<?>[0]));
		Assert.assertEquals(Long.valueOf(
				existingOAuthUser.getOAuthApplicationId()),
			ReflectionTestUtil.<Long>invoke(existingOAuthUser,
				"getOriginalOAuthApplicationId", new Class<?>[0]));
	}

	protected OAuthUser addOAuthUser() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthUser oAuthUser = _persistence.create(pk);

		oAuthUser.setCompanyId(RandomTestUtil.nextLong());

		oAuthUser.setUserId(RandomTestUtil.nextLong());

		oAuthUser.setUserName(RandomTestUtil.randomString());

		oAuthUser.setCreateDate(RandomTestUtil.nextDate());

		oAuthUser.setModifiedDate(RandomTestUtil.nextDate());

		oAuthUser.setOAuthApplicationId(RandomTestUtil.nextLong());

		oAuthUser.setAccessToken(RandomTestUtil.randomString());

		oAuthUser.setAccessSecret(RandomTestUtil.randomString());

		_oAuthUsers.add(_persistence.update(oAuthUser));

		return oAuthUser;
	}

	private List<OAuthUser> _oAuthUsers = new ArrayList<OAuthUser>();
	private OAuthUserPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}