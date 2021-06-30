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

package com.liferay.commerce.shop.by.diagram.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramSettingException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingLocalServiceUtil;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramSettingPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramSettingUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.test.AssertUtils;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class CPDefinitionDiagramSettingPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.shop.by.diagram.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionDiagramSettingUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionDiagramSetting> iterator =
			_cpDefinitionDiagramSettings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_persistence.create(pk);

		Assert.assertNotNull(cpDefinitionDiagramSetting);

		Assert.assertEquals(cpDefinitionDiagramSetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		_persistence.remove(newCPDefinitionDiagramSetting);

		CPDefinitionDiagramSetting existingCPDefinitionDiagramSetting =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramSetting.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionDiagramSetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionDiagramSetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			_persistence.create(pk);

		newCPDefinitionDiagramSetting.setUuid(RandomTestUtil.randomString());

		newCPDefinitionDiagramSetting.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramSetting.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramSetting.setUserName(
			RandomTestUtil.randomString());

		newCPDefinitionDiagramSetting.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionDiagramSetting.setModifiedDate(
			RandomTestUtil.nextDate());

		newCPDefinitionDiagramSetting.setCPDefinitionId(
			RandomTestUtil.nextLong());

		newCPDefinitionDiagramSetting.setRadius(RandomTestUtil.nextDouble());

		newCPDefinitionDiagramSetting.setColor(RandomTestUtil.randomString());

		_cpDefinitionDiagramSettings.add(
			_persistence.update(newCPDefinitionDiagramSetting));

		CPDefinitionDiagramSetting existingCPDefinitionDiagramSetting =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramSetting.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramSetting.getUuid(),
			newCPDefinitionDiagramSetting.getUuid());
		Assert.assertEquals(
			existingCPDefinitionDiagramSetting.
				getCPDefinitionDiagramSettingId(),
			newCPDefinitionDiagramSetting.getCPDefinitionDiagramSettingId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSetting.getCompanyId(),
			newCPDefinitionDiagramSetting.getCompanyId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSetting.getUserId(),
			newCPDefinitionDiagramSetting.getUserId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSetting.getUserName(),
			newCPDefinitionDiagramSetting.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramSetting.getCreateDate()),
			Time.getShortTimestamp(
				newCPDefinitionDiagramSetting.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramSetting.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionDiagramSetting.getModifiedDate()));
		Assert.assertEquals(
			existingCPDefinitionDiagramSetting.getCPDefinitionId(),
			newCPDefinitionDiagramSetting.getCPDefinitionId());
		AssertUtils.assertEquals(
			existingCPDefinitionDiagramSetting.getRadius(),
			newCPDefinitionDiagramSetting.getRadius());
		Assert.assertEquals(
			existingCPDefinitionDiagramSetting.getColor(),
			newCPDefinitionDiagramSetting.getColor());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C("", RandomTestUtil.nextLong());

		_persistence.countByUuid_C("null", 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		CPDefinitionDiagramSetting existingCPDefinitionDiagramSetting =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramSetting.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramSetting, newCPDefinitionDiagramSetting);
	}

	@Test(expected = NoSuchCPDefinitionDiagramSettingException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionDiagramSetting>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CPDefinitionDiagramSetting", "uuid", true,
			"CPDefinitionDiagramSettingId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"CPDefinitionId", true, "radius", true, "color", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		CPDefinitionDiagramSetting existingCPDefinitionDiagramSetting =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramSetting.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramSetting, newCPDefinitionDiagramSetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSetting missingCPDefinitionDiagramSetting =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionDiagramSetting);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting1 =
			addCPDefinitionDiagramSetting();
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting2 =
			addCPDefinitionDiagramSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramSetting1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionDiagramSetting2.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramSetting>
			cpDefinitionDiagramSettings = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, cpDefinitionDiagramSettings.size());
		Assert.assertEquals(
			newCPDefinitionDiagramSetting1,
			cpDefinitionDiagramSettings.get(
				newCPDefinitionDiagramSetting1.getPrimaryKey()));
		Assert.assertEquals(
			newCPDefinitionDiagramSetting2,
			cpDefinitionDiagramSettings.get(
				newCPDefinitionDiagramSetting2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionDiagramSetting>
			cpDefinitionDiagramSettings = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramSetting.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionDiagramSetting>
			cpDefinitionDiagramSettings = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramSettings.size());
		Assert.assertEquals(
			newCPDefinitionDiagramSetting,
			cpDefinitionDiagramSettings.get(
				newCPDefinitionDiagramSetting.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionDiagramSetting>
			cpDefinitionDiagramSettings = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramSetting.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramSetting>
			cpDefinitionDiagramSettings = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramSettings.size());
		Assert.assertEquals(
			newCPDefinitionDiagramSetting,
			cpDefinitionDiagramSettings.get(
				newCPDefinitionDiagramSetting.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CPDefinitionDiagramSettingLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionDiagramSetting>() {

				@Override
				public void performAction(
					CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

					Assert.assertNotNull(cpDefinitionDiagramSetting);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSetting.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramSettingId",
				newCPDefinitionDiagramSetting.
					getCPDefinitionDiagramSettingId()));

		List<CPDefinitionDiagramSetting> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionDiagramSetting existingCPDefinitionDiagramSetting =
			result.get(0);

		Assert.assertEquals(
			existingCPDefinitionDiagramSetting, newCPDefinitionDiagramSetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSetting.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramSettingId", RandomTestUtil.nextLong()));

		List<CPDefinitionDiagramSetting> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSetting.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramSettingId"));

		Object newCPDefinitionDiagramSettingId =
			newCPDefinitionDiagramSetting.getCPDefinitionDiagramSettingId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramSettingId",
				new Object[] {newCPDefinitionDiagramSettingId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionDiagramSettingId = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionDiagramSettingId,
			newCPDefinitionDiagramSettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSetting.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramSettingId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramSettingId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramSetting.getPrimaryKey()));
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromDatabase()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(true);
	}

	@Test
	public void testResetOriginalValuesWithDynamicQueryLoadFromSession()
		throws Exception {

		_testResetOriginalValuesWithDynamicQuery(false);
	}

	private void _testResetOriginalValuesWithDynamicQuery(boolean clearSession)
		throws Exception {

		CPDefinitionDiagramSetting newCPDefinitionDiagramSetting =
			addCPDefinitionDiagramSetting();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSetting.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramSettingId",
				newCPDefinitionDiagramSetting.
					getCPDefinitionDiagramSettingId()));

		List<CPDefinitionDiagramSetting> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting) {

		Assert.assertEquals(
			Long.valueOf(cpDefinitionDiagramSetting.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionDiagramSetting, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "CPDefinitionId"));
	}

	protected CPDefinitionDiagramSetting addCPDefinitionDiagramSetting()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			_persistence.create(pk);

		cpDefinitionDiagramSetting.setUuid(RandomTestUtil.randomString());

		cpDefinitionDiagramSetting.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionDiagramSetting.setUserId(RandomTestUtil.nextLong());

		cpDefinitionDiagramSetting.setUserName(RandomTestUtil.randomString());

		cpDefinitionDiagramSetting.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramSetting.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramSetting.setCPDefinitionId(RandomTestUtil.nextLong());

		cpDefinitionDiagramSetting.setRadius(RandomTestUtil.nextDouble());

		cpDefinitionDiagramSetting.setColor(RandomTestUtil.randomString());

		_cpDefinitionDiagramSettings.add(
			_persistence.update(cpDefinitionDiagramSetting));

		return cpDefinitionDiagramSetting;
	}

	private List<CPDefinitionDiagramSetting> _cpDefinitionDiagramSettings =
		new ArrayList<CPDefinitionDiagramSetting>();
	private CPDefinitionDiagramSettingPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}