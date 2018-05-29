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

import com.liferay.oauth.exception.NoSuchApplicationException;
import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.service.OAuthApplicationLocalServiceUtil;
import com.liferay.oauth.service.persistence.OAuthApplicationPersistence;
import com.liferay.oauth.service.persistence.OAuthApplicationUtil;

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
public class OAuthApplicationPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.oauth.service"));

	@Before
	public void setUp() {
		_persistence = OAuthApplicationUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<OAuthApplication> iterator = _oAuthApplications.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthApplication oAuthApplication = _persistence.create(pk);

		Assert.assertNotNull(oAuthApplication);

		Assert.assertEquals(oAuthApplication.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		_persistence.remove(newOAuthApplication);

		OAuthApplication existingOAuthApplication = _persistence.fetchByPrimaryKey(newOAuthApplication.getPrimaryKey());

		Assert.assertNull(existingOAuthApplication);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addOAuthApplication();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthApplication newOAuthApplication = _persistence.create(pk);

		newOAuthApplication.setCompanyId(RandomTestUtil.nextLong());

		newOAuthApplication.setUserId(RandomTestUtil.nextLong());

		newOAuthApplication.setUserName(RandomTestUtil.randomString());

		newOAuthApplication.setCreateDate(RandomTestUtil.nextDate());

		newOAuthApplication.setModifiedDate(RandomTestUtil.nextDate());

		newOAuthApplication.setName(RandomTestUtil.randomString());

		newOAuthApplication.setDescription(RandomTestUtil.randomString());

		newOAuthApplication.setConsumerKey(RandomTestUtil.randomString());

		newOAuthApplication.setConsumerSecret(RandomTestUtil.randomString());

		newOAuthApplication.setAccessLevel(RandomTestUtil.nextInt());

		newOAuthApplication.setLogoId(RandomTestUtil.nextLong());

		newOAuthApplication.setShareableAccessToken(RandomTestUtil.randomBoolean());

		newOAuthApplication.setCallbackURI(RandomTestUtil.randomString());

		newOAuthApplication.setWebsiteURL(RandomTestUtil.randomString());

		_oAuthApplications.add(_persistence.update(newOAuthApplication));

		OAuthApplication existingOAuthApplication = _persistence.findByPrimaryKey(newOAuthApplication.getPrimaryKey());

		Assert.assertEquals(existingOAuthApplication.getOAuthApplicationId(),
			newOAuthApplication.getOAuthApplicationId());
		Assert.assertEquals(existingOAuthApplication.getCompanyId(),
			newOAuthApplication.getCompanyId());
		Assert.assertEquals(existingOAuthApplication.getUserId(),
			newOAuthApplication.getUserId());
		Assert.assertEquals(existingOAuthApplication.getUserName(),
			newOAuthApplication.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingOAuthApplication.getCreateDate()),
			Time.getShortTimestamp(newOAuthApplication.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingOAuthApplication.getModifiedDate()),
			Time.getShortTimestamp(newOAuthApplication.getModifiedDate()));
		Assert.assertEquals(existingOAuthApplication.getName(),
			newOAuthApplication.getName());
		Assert.assertEquals(existingOAuthApplication.getDescription(),
			newOAuthApplication.getDescription());
		Assert.assertEquals(existingOAuthApplication.getConsumerKey(),
			newOAuthApplication.getConsumerKey());
		Assert.assertEquals(existingOAuthApplication.getConsumerSecret(),
			newOAuthApplication.getConsumerSecret());
		Assert.assertEquals(existingOAuthApplication.getAccessLevel(),
			newOAuthApplication.getAccessLevel());
		Assert.assertEquals(existingOAuthApplication.getLogoId(),
			newOAuthApplication.getLogoId());
		Assert.assertEquals(existingOAuthApplication.isShareableAccessToken(),
			newOAuthApplication.isShareableAccessToken());
		Assert.assertEquals(existingOAuthApplication.getCallbackURI(),
			newOAuthApplication.getCallbackURI());
		Assert.assertEquals(existingOAuthApplication.getWebsiteURL(),
			newOAuthApplication.getWebsiteURL());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByConsumerKey() throws Exception {
		_persistence.countByConsumerKey("");

		_persistence.countByConsumerKey("null");

		_persistence.countByConsumerKey((String)null);
	}

	@Test
	public void testCountByC_N() throws Exception {
		_persistence.countByC_N(RandomTestUtil.nextLong(), "");

		_persistence.countByC_N(0L, "null");

		_persistence.countByC_N(0L, (String)null);
	}

	@Test
	public void testCountByU_N() throws Exception {
		_persistence.countByU_N(RandomTestUtil.nextLong(), "");

		_persistence.countByU_N(0L, "null");

		_persistence.countByU_N(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		OAuthApplication existingOAuthApplication = _persistence.findByPrimaryKey(newOAuthApplication.getPrimaryKey());

		Assert.assertEquals(existingOAuthApplication, newOAuthApplication);
	}

	@Test(expected = NoSuchApplicationException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<OAuthApplication> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("OAuth_OAuthApplication",
			"oAuthApplicationId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true, "consumerKey", true, "consumerSecret",
			true, "accessLevel", true, "logoId", true, "shareableAccessToken",
			true, "callbackURI", true, "websiteURL", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		OAuthApplication existingOAuthApplication = _persistence.fetchByPrimaryKey(newOAuthApplication.getPrimaryKey());

		Assert.assertEquals(existingOAuthApplication, newOAuthApplication);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthApplication missingOAuthApplication = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingOAuthApplication);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		OAuthApplication newOAuthApplication1 = addOAuthApplication();
		OAuthApplication newOAuthApplication2 = addOAuthApplication();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthApplication1.getPrimaryKey());
		primaryKeys.add(newOAuthApplication2.getPrimaryKey());

		Map<Serializable, OAuthApplication> oAuthApplications = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, oAuthApplications.size());
		Assert.assertEquals(newOAuthApplication1,
			oAuthApplications.get(newOAuthApplication1.getPrimaryKey()));
		Assert.assertEquals(newOAuthApplication2,
			oAuthApplications.get(newOAuthApplication2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, OAuthApplication> oAuthApplications = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(oAuthApplications.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthApplication.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, OAuthApplication> oAuthApplications = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, oAuthApplications.size());
		Assert.assertEquals(newOAuthApplication,
			oAuthApplications.get(newOAuthApplication.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, OAuthApplication> oAuthApplications = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(oAuthApplications.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newOAuthApplication.getPrimaryKey());

		Map<Serializable, OAuthApplication> oAuthApplications = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, oAuthApplications.size());
		Assert.assertEquals(newOAuthApplication,
			oAuthApplications.get(newOAuthApplication.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = OAuthApplicationLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<OAuthApplication>() {
				@Override
				public void performAction(OAuthApplication oAuthApplication) {
					Assert.assertNotNull(oAuthApplication);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthApplication.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("oAuthApplicationId",
				newOAuthApplication.getOAuthApplicationId()));

		List<OAuthApplication> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		OAuthApplication existingOAuthApplication = result.get(0);

		Assert.assertEquals(existingOAuthApplication, newOAuthApplication);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthApplication.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("oAuthApplicationId",
				RandomTestUtil.nextLong()));

		List<OAuthApplication> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthApplication.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"oAuthApplicationId"));

		Object newOAuthApplicationId = newOAuthApplication.getOAuthApplicationId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("oAuthApplicationId",
				new Object[] { newOAuthApplicationId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingOAuthApplicationId = result.get(0);

		Assert.assertEquals(existingOAuthApplicationId, newOAuthApplicationId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(OAuthApplication.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"oAuthApplicationId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("oAuthApplicationId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		OAuthApplication newOAuthApplication = addOAuthApplication();

		_persistence.clearCache();

		OAuthApplication existingOAuthApplication = _persistence.findByPrimaryKey(newOAuthApplication.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingOAuthApplication.getConsumerKey(),
				ReflectionTestUtil.invoke(existingOAuthApplication,
					"getOriginalConsumerKey", new Class<?>[0])));
	}

	protected OAuthApplication addOAuthApplication() throws Exception {
		long pk = RandomTestUtil.nextLong();

		OAuthApplication oAuthApplication = _persistence.create(pk);

		oAuthApplication.setCompanyId(RandomTestUtil.nextLong());

		oAuthApplication.setUserId(RandomTestUtil.nextLong());

		oAuthApplication.setUserName(RandomTestUtil.randomString());

		oAuthApplication.setCreateDate(RandomTestUtil.nextDate());

		oAuthApplication.setModifiedDate(RandomTestUtil.nextDate());

		oAuthApplication.setName(RandomTestUtil.randomString());

		oAuthApplication.setDescription(RandomTestUtil.randomString());

		oAuthApplication.setConsumerKey(RandomTestUtil.randomString());

		oAuthApplication.setConsumerSecret(RandomTestUtil.randomString());

		oAuthApplication.setAccessLevel(RandomTestUtil.nextInt());

		oAuthApplication.setLogoId(RandomTestUtil.nextLong());

		oAuthApplication.setShareableAccessToken(RandomTestUtil.randomBoolean());

		oAuthApplication.setCallbackURI(RandomTestUtil.randomString());

		oAuthApplication.setWebsiteURL(RandomTestUtil.randomString());

		_oAuthApplications.add(_persistence.update(oAuthApplication));

		return oAuthApplication;
	}

	private List<OAuthApplication> _oAuthApplications = new ArrayList<OAuthApplication>();
	private OAuthApplicationPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}