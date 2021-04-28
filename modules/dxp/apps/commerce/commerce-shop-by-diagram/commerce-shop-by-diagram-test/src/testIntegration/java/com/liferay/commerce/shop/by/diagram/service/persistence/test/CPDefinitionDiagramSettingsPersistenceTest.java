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
import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramSettingsException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingsLocalServiceUtil;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramSettingsPersistence;
import com.liferay.commerce.shop.by.diagram.service.persistence.CPDefinitionDiagramSettingsUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
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
public class CPDefinitionDiagramSettingsPersistenceTest {

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
		_persistence = CPDefinitionDiagramSettingsUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionDiagramSettings> iterator =
			_cpDefinitionDiagramSettingses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			_persistence.create(pk);

		Assert.assertNotNull(cpDefinitionDiagramSettings);

		Assert.assertEquals(cpDefinitionDiagramSettings.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		_persistence.remove(newCPDefinitionDiagramSettings);

		CPDefinitionDiagramSettings existingCPDefinitionDiagramSettings =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramSettings.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionDiagramSettings);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionDiagramSettings();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			_persistence.create(pk);

		newCPDefinitionDiagramSettings.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramSettings.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionDiagramSettings.setUserName(
			RandomTestUtil.randomString());

		newCPDefinitionDiagramSettings.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionDiagramSettings.setModifiedDate(
			RandomTestUtil.nextDate());

		newCPDefinitionDiagramSettings.setCPDefinitionId(
			RandomTestUtil.nextLong());

		newCPDefinitionDiagramSettings.setAssetCategoryId(
			RandomTestUtil.nextLong());

		newCPDefinitionDiagramSettings.setName(RandomTestUtil.randomString());

		_cpDefinitionDiagramSettingses.add(
			_persistence.update(newCPDefinitionDiagramSettings));

		CPDefinitionDiagramSettings existingCPDefinitionDiagramSettings =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramSettings.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramSettings.
				getCPDefinitionDiagramSettingsId(),
			newCPDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSettings.getCompanyId(),
			newCPDefinitionDiagramSettings.getCompanyId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSettings.getUserId(),
			newCPDefinitionDiagramSettings.getUserId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSettings.getUserName(),
			newCPDefinitionDiagramSettings.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramSettings.getCreateDate()),
			Time.getShortTimestamp(
				newCPDefinitionDiagramSettings.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCPDefinitionDiagramSettings.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionDiagramSettings.getModifiedDate()));
		Assert.assertEquals(
			existingCPDefinitionDiagramSettings.getCPDefinitionId(),
			newCPDefinitionDiagramSettings.getCPDefinitionId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSettings.getAssetCategoryId(),
			newCPDefinitionDiagramSettings.getAssetCategoryId());
		Assert.assertEquals(
			existingCPDefinitionDiagramSettings.getName(),
			newCPDefinitionDiagramSettings.getName());
	}

	@Test
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testCountByAssetCategoryId() throws Exception {
		_persistence.countByAssetCategoryId(RandomTestUtil.nextLong());

		_persistence.countByAssetCategoryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		CPDefinitionDiagramSettings existingCPDefinitionDiagramSettings =
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramSettings.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramSettings,
			newCPDefinitionDiagramSettings);
	}

	@Test(expected = NoSuchCPDefinitionDiagramSettingsException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionDiagramSettings>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CPDefinitionDiagramSettings", "CPDefinitionDiagramSettingsId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "CPDefinitionId", true,
			"assetCategoryId", true, "name", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		CPDefinitionDiagramSettings existingCPDefinitionDiagramSettings =
			_persistence.fetchByPrimaryKey(
				newCPDefinitionDiagramSettings.getPrimaryKey());

		Assert.assertEquals(
			existingCPDefinitionDiagramSettings,
			newCPDefinitionDiagramSettings);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSettings missingCPDefinitionDiagramSettings =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionDiagramSettings);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings1 =
			addCPDefinitionDiagramSettings();
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings2 =
			addCPDefinitionDiagramSettings();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramSettings1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionDiagramSettings2.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramSettings>
			cpDefinitionDiagramSettingses = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, cpDefinitionDiagramSettingses.size());
		Assert.assertEquals(
			newCPDefinitionDiagramSettings1,
			cpDefinitionDiagramSettingses.get(
				newCPDefinitionDiagramSettings1.getPrimaryKey()));
		Assert.assertEquals(
			newCPDefinitionDiagramSettings2,
			cpDefinitionDiagramSettingses.get(
				newCPDefinitionDiagramSettings2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionDiagramSettings>
			cpDefinitionDiagramSettingses = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramSettingses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramSettings.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionDiagramSettings>
			cpDefinitionDiagramSettingses = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramSettingses.size());
		Assert.assertEquals(
			newCPDefinitionDiagramSettings,
			cpDefinitionDiagramSettingses.get(
				newCPDefinitionDiagramSettings.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionDiagramSettings>
			cpDefinitionDiagramSettingses = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(cpDefinitionDiagramSettingses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionDiagramSettings.getPrimaryKey());

		Map<Serializable, CPDefinitionDiagramSettings>
			cpDefinitionDiagramSettingses = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, cpDefinitionDiagramSettingses.size());
		Assert.assertEquals(
			newCPDefinitionDiagramSettings,
			cpDefinitionDiagramSettingses.get(
				newCPDefinitionDiagramSettings.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CPDefinitionDiagramSettingsLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CPDefinitionDiagramSettings>() {

				@Override
				public void performAction(
					CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

					Assert.assertNotNull(cpDefinitionDiagramSettings);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSettings.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramSettingsId",
				newCPDefinitionDiagramSettings.
					getCPDefinitionDiagramSettingsId()));

		List<CPDefinitionDiagramSettings> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionDiagramSettings existingCPDefinitionDiagramSettings =
			result.get(0);

		Assert.assertEquals(
			existingCPDefinitionDiagramSettings,
			newCPDefinitionDiagramSettings);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSettings.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramSettingsId", RandomTestUtil.nextLong()));

		List<CPDefinitionDiagramSettings> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSettings.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramSettingsId"));

		Object newCPDefinitionDiagramSettingsId =
			newCPDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramSettingsId",
				new Object[] {newCPDefinitionDiagramSettingsId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionDiagramSettingsId = result.get(0);

		Assert.assertEquals(
			existingCPDefinitionDiagramSettingsId,
			newCPDefinitionDiagramSettingsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSettings.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("CPDefinitionDiagramSettingsId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"CPDefinitionDiagramSettingsId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCPDefinitionDiagramSettings.getPrimaryKey()));
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

		CPDefinitionDiagramSettings newCPDefinitionDiagramSettings =
			addCPDefinitionDiagramSettings();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CPDefinitionDiagramSettings.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"CPDefinitionDiagramSettingsId",
				newCPDefinitionDiagramSettings.
					getCPDefinitionDiagramSettingsId()));

		List<CPDefinitionDiagramSettings> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CPDefinitionDiagramSettings cpDefinitionDiagramSettings) {

		Assert.assertEquals(
			Long.valueOf(cpDefinitionDiagramSettings.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(
				cpDefinitionDiagramSettings, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "CPDefinitionId"));
	}

	protected CPDefinitionDiagramSettings addCPDefinitionDiagramSettings()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			_persistence.create(pk);

		cpDefinitionDiagramSettings.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionDiagramSettings.setUserId(RandomTestUtil.nextLong());

		cpDefinitionDiagramSettings.setUserName(RandomTestUtil.randomString());

		cpDefinitionDiagramSettings.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramSettings.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionDiagramSettings.setCPDefinitionId(
			RandomTestUtil.nextLong());

		cpDefinitionDiagramSettings.setAssetCategoryId(
			RandomTestUtil.nextLong());

		cpDefinitionDiagramSettings.setName(RandomTestUtil.randomString());

		_cpDefinitionDiagramSettingses.add(
			_persistence.update(cpDefinitionDiagramSettings));

		return cpDefinitionDiagramSettings;
	}

	private List<CPDefinitionDiagramSettings> _cpDefinitionDiagramSettingses =
		new ArrayList<CPDefinitionDiagramSettings>();
	private CPDefinitionDiagramSettingsPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}