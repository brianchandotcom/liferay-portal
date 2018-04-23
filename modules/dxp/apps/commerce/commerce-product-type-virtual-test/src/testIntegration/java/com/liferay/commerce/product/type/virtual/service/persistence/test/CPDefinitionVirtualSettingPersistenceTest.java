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

package com.liferay.commerce.product.type.virtual.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.type.virtual.exception.NoSuchCPDefinitionVirtualSettingException;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalServiceUtil;
import com.liferay.commerce.product.type.virtual.service.persistence.CPDefinitionVirtualSettingPersistence;
import com.liferay.commerce.product.type.virtual.service.persistence.CPDefinitionVirtualSettingUtil;

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
public class CPDefinitionVirtualSettingPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.type.virtual.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionVirtualSettingUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionVirtualSetting> iterator = _cpDefinitionVirtualSettings.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = _persistence.create(pk);

		Assert.assertNotNull(cpDefinitionVirtualSetting);

		Assert.assertEquals(cpDefinitionVirtualSetting.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		_persistence.remove(newCPDefinitionVirtualSetting);

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting = _persistence.fetchByPrimaryKey(newCPDefinitionVirtualSetting.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionVirtualSetting);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionVirtualSetting();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = _persistence.create(pk);

		newCPDefinitionVirtualSetting.setUuid(RandomTestUtil.randomString());

		newCPDefinitionVirtualSetting.setGroupId(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setUserName(RandomTestUtil.randomString());

		newCPDefinitionVirtualSetting.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionVirtualSetting.setModifiedDate(RandomTestUtil.nextDate());

		newCPDefinitionVirtualSetting.setCPDefinitionId(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setFileEntryId(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setUrl(RandomTestUtil.randomString());

		newCPDefinitionVirtualSetting.setActivationStatus(RandomTestUtil.randomString());

		newCPDefinitionVirtualSetting.setDuration(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setMaxUsages(RandomTestUtil.nextInt());

		newCPDefinitionVirtualSetting.setUseSample(RandomTestUtil.randomBoolean());

		newCPDefinitionVirtualSetting.setSampleFileEntryId(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setSampleUrl(RandomTestUtil.randomString());

		newCPDefinitionVirtualSetting.setTermsOfUseRequired(RandomTestUtil.randomBoolean());

		newCPDefinitionVirtualSetting.setTermsOfUseContent(RandomTestUtil.randomString());

		newCPDefinitionVirtualSetting.setTermsOfUseJournalArticleResourcePrimKey(RandomTestUtil.nextLong());

		newCPDefinitionVirtualSetting.setLastPublishDate(RandomTestUtil.nextDate());

		_cpDefinitionVirtualSettings.add(_persistence.update(
				newCPDefinitionVirtualSetting));

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting = _persistence.findByPrimaryKey(newCPDefinitionVirtualSetting.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionVirtualSetting.getUuid(),
			newCPDefinitionVirtualSetting.getUuid());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getCPDefinitionVirtualSettingId(),
			newCPDefinitionVirtualSetting.getCPDefinitionVirtualSettingId());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getGroupId(),
			newCPDefinitionVirtualSetting.getGroupId());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getCompanyId(),
			newCPDefinitionVirtualSetting.getCompanyId());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getUserId(),
			newCPDefinitionVirtualSetting.getUserId());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getUserName(),
			newCPDefinitionVirtualSetting.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionVirtualSetting.getCreateDate()),
			Time.getShortTimestamp(
				newCPDefinitionVirtualSetting.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionVirtualSetting.getModifiedDate()),
			Time.getShortTimestamp(
				newCPDefinitionVirtualSetting.getModifiedDate()));
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getCPDefinitionId(),
			newCPDefinitionVirtualSetting.getCPDefinitionId());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getFileEntryId(),
			newCPDefinitionVirtualSetting.getFileEntryId());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getUrl(),
			newCPDefinitionVirtualSetting.getUrl());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getActivationStatus(),
			newCPDefinitionVirtualSetting.getActivationStatus());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getDuration(),
			newCPDefinitionVirtualSetting.getDuration());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getMaxUsages(),
			newCPDefinitionVirtualSetting.getMaxUsages());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.isUseSample(),
			newCPDefinitionVirtualSetting.isUseSample());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getSampleFileEntryId(),
			newCPDefinitionVirtualSetting.getSampleFileEntryId());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getSampleUrl(),
			newCPDefinitionVirtualSetting.getSampleUrl());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.isTermsOfUseRequired(),
			newCPDefinitionVirtualSetting.isTermsOfUseRequired());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getTermsOfUseContent(),
			newCPDefinitionVirtualSetting.getTermsOfUseContent());
		Assert.assertEquals(existingCPDefinitionVirtualSetting.getTermsOfUseJournalArticleResourcePrimKey(),
			newCPDefinitionVirtualSetting.getTermsOfUseJournalArticleResourcePrimKey());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionVirtualSetting.getLastPublishDate()),
			Time.getShortTimestamp(
				newCPDefinitionVirtualSetting.getLastPublishDate()));
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G("", RandomTestUtil.nextLong());

		_persistence.countByUUID_G("null", 0L);

		_persistence.countByUUID_G((String)null, 0L);
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
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting = _persistence.findByPrimaryKey(newCPDefinitionVirtualSetting.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionVirtualSetting,
			newCPDefinitionVirtualSetting);
	}

	@Test(expected = NoSuchCPDefinitionVirtualSettingException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionVirtualSetting> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPDefinitionVirtualSetting",
			"uuid", true, "CPDefinitionVirtualSettingId", true, "groupId",
			true, "companyId", true, "userId", true, "userName", true,
			"createDate", true, "modifiedDate", true, "CPDefinitionId", true,
			"fileEntryId", true, "url", true, "activationStatus", true,
			"duration", true, "maxUsages", true, "useSample", true,
			"sampleFileEntryId", true, "sampleUrl", true, "termsOfUseRequired",
			true, "termsOfUseContent", true,
			"termsOfUseJournalArticleResourcePrimKey", true, "lastPublishDate",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting = _persistence.fetchByPrimaryKey(newCPDefinitionVirtualSetting.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionVirtualSetting,
			newCPDefinitionVirtualSetting);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionVirtualSetting missingCPDefinitionVirtualSetting = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionVirtualSetting);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting1 = addCPDefinitionVirtualSetting();
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting2 = addCPDefinitionVirtualSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionVirtualSetting1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionVirtualSetting2.getPrimaryKey());

		Map<Serializable, CPDefinitionVirtualSetting> cpDefinitionVirtualSettings =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpDefinitionVirtualSettings.size());
		Assert.assertEquals(newCPDefinitionVirtualSetting1,
			cpDefinitionVirtualSettings.get(
				newCPDefinitionVirtualSetting1.getPrimaryKey()));
		Assert.assertEquals(newCPDefinitionVirtualSetting2,
			cpDefinitionVirtualSettings.get(
				newCPDefinitionVirtualSetting2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionVirtualSetting> cpDefinitionVirtualSettings =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionVirtualSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionVirtualSetting.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionVirtualSetting> cpDefinitionVirtualSettings =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionVirtualSettings.size());
		Assert.assertEquals(newCPDefinitionVirtualSetting,
			cpDefinitionVirtualSettings.get(
				newCPDefinitionVirtualSetting.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionVirtualSetting> cpDefinitionVirtualSettings =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionVirtualSettings.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionVirtualSetting.getPrimaryKey());

		Map<Serializable, CPDefinitionVirtualSetting> cpDefinitionVirtualSettings =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionVirtualSettings.size());
		Assert.assertEquals(newCPDefinitionVirtualSetting,
			cpDefinitionVirtualSettings.get(
				newCPDefinitionVirtualSetting.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPDefinitionVirtualSettingLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPDefinitionVirtualSetting>() {
				@Override
				public void performAction(
					CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
					Assert.assertNotNull(cpDefinitionVirtualSetting);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionVirtualSetting.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPDefinitionVirtualSettingId",
				newCPDefinitionVirtualSetting.getCPDefinitionVirtualSettingId()));

		List<CPDefinitionVirtualSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting = result.get(0);

		Assert.assertEquals(existingCPDefinitionVirtualSetting,
			newCPDefinitionVirtualSetting);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionVirtualSetting.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"CPDefinitionVirtualSettingId", RandomTestUtil.nextLong()));

		List<CPDefinitionVirtualSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionVirtualSetting.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionVirtualSettingId"));

		Object newCPDefinitionVirtualSettingId = newCPDefinitionVirtualSetting.getCPDefinitionVirtualSettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPDefinitionVirtualSettingId",
				new Object[] { newCPDefinitionVirtualSettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionVirtualSettingId = result.get(0);

		Assert.assertEquals(existingCPDefinitionVirtualSettingId,
			newCPDefinitionVirtualSettingId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionVirtualSetting.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionVirtualSettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"CPDefinitionVirtualSettingId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionVirtualSetting newCPDefinitionVirtualSetting = addCPDefinitionVirtualSetting();

		_persistence.clearCache();

		CPDefinitionVirtualSetting existingCPDefinitionVirtualSetting = _persistence.findByPrimaryKey(newCPDefinitionVirtualSetting.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingCPDefinitionVirtualSetting.getUuid(),
				ReflectionTestUtil.invoke(existingCPDefinitionVirtualSetting,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingCPDefinitionVirtualSetting.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingCPDefinitionVirtualSetting, "getOriginalGroupId",
				new Class<?>[0]));

		Assert.assertEquals(Long.valueOf(
				existingCPDefinitionVirtualSetting.getCPDefinitionId()),
			ReflectionTestUtil.<Long>invoke(
				existingCPDefinitionVirtualSetting,
				"getOriginalCPDefinitionId", new Class<?>[0]));
	}

	protected CPDefinitionVirtualSetting addCPDefinitionVirtualSetting()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting = _persistence.create(pk);

		cpDefinitionVirtualSetting.setUuid(RandomTestUtil.randomString());

		cpDefinitionVirtualSetting.setGroupId(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setUserId(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setUserName(RandomTestUtil.randomString());

		cpDefinitionVirtualSetting.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionVirtualSetting.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionVirtualSetting.setCPDefinitionId(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setFileEntryId(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setUrl(RandomTestUtil.randomString());

		cpDefinitionVirtualSetting.setActivationStatus(RandomTestUtil.randomString());

		cpDefinitionVirtualSetting.setDuration(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setMaxUsages(RandomTestUtil.nextInt());

		cpDefinitionVirtualSetting.setUseSample(RandomTestUtil.randomBoolean());

		cpDefinitionVirtualSetting.setSampleFileEntryId(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setSampleUrl(RandomTestUtil.randomString());

		cpDefinitionVirtualSetting.setTermsOfUseRequired(RandomTestUtil.randomBoolean());

		cpDefinitionVirtualSetting.setTermsOfUseContent(RandomTestUtil.randomString());

		cpDefinitionVirtualSetting.setTermsOfUseJournalArticleResourcePrimKey(RandomTestUtil.nextLong());

		cpDefinitionVirtualSetting.setLastPublishDate(RandomTestUtil.nextDate());

		_cpDefinitionVirtualSettings.add(_persistence.update(
				cpDefinitionVirtualSetting));

		return cpDefinitionVirtualSetting;
	}

	private List<CPDefinitionVirtualSetting> _cpDefinitionVirtualSettings = new ArrayList<CPDefinitionVirtualSetting>();
	private CPDefinitionVirtualSettingPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}