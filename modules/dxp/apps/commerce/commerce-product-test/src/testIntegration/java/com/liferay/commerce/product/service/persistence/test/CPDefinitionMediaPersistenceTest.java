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

package com.liferay.commerce.product.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionMediaException;
import com.liferay.commerce.product.model.CPDefinitionMedia;
import com.liferay.commerce.product.service.CPDefinitionMediaLocalServiceUtil;
import com.liferay.commerce.product.service.persistence.CPDefinitionMediaPersistence;
import com.liferay.commerce.product.service.persistence.CPDefinitionMediaUtil;

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
import com.liferay.portal.kernel.util.StringPool;
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
public class CPDefinitionMediaPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.commerce.product.service"));

	@Before
	public void setUp() {
		_persistence = CPDefinitionMediaUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CPDefinitionMedia> iterator = _cpDefinitionMedias.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionMedia cpDefinitionMedia = _persistence.create(pk);

		Assert.assertNotNull(cpDefinitionMedia);

		Assert.assertEquals(cpDefinitionMedia.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		_persistence.remove(newCPDefinitionMedia);

		CPDefinitionMedia existingCPDefinitionMedia = _persistence.fetchByPrimaryKey(newCPDefinitionMedia.getPrimaryKey());

		Assert.assertNull(existingCPDefinitionMedia);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCPDefinitionMedia();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionMedia newCPDefinitionMedia = _persistence.create(pk);

		newCPDefinitionMedia.setUuid(RandomTestUtil.randomString());

		newCPDefinitionMedia.setGroupId(RandomTestUtil.nextLong());

		newCPDefinitionMedia.setCompanyId(RandomTestUtil.nextLong());

		newCPDefinitionMedia.setUserId(RandomTestUtil.nextLong());

		newCPDefinitionMedia.setUserName(RandomTestUtil.randomString());

		newCPDefinitionMedia.setCreateDate(RandomTestUtil.nextDate());

		newCPDefinitionMedia.setModifiedDate(RandomTestUtil.nextDate());

		newCPDefinitionMedia.setCPDefinitionId(RandomTestUtil.nextLong());

		newCPDefinitionMedia.setFileEntryId(RandomTestUtil.nextLong());

		newCPDefinitionMedia.setDDMContent(RandomTestUtil.randomString());

		newCPDefinitionMedia.setPriority(RandomTestUtil.nextInt());

		newCPDefinitionMedia.setCPMediaTypeId(RandomTestUtil.nextLong());

		_cpDefinitionMedias.add(_persistence.update(newCPDefinitionMedia));

		CPDefinitionMedia existingCPDefinitionMedia = _persistence.findByPrimaryKey(newCPDefinitionMedia.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionMedia.getUuid(),
			newCPDefinitionMedia.getUuid());
		Assert.assertEquals(existingCPDefinitionMedia.getCPDefinitionMediaId(),
			newCPDefinitionMedia.getCPDefinitionMediaId());
		Assert.assertEquals(existingCPDefinitionMedia.getGroupId(),
			newCPDefinitionMedia.getGroupId());
		Assert.assertEquals(existingCPDefinitionMedia.getCompanyId(),
			newCPDefinitionMedia.getCompanyId());
		Assert.assertEquals(existingCPDefinitionMedia.getUserId(),
			newCPDefinitionMedia.getUserId());
		Assert.assertEquals(existingCPDefinitionMedia.getUserName(),
			newCPDefinitionMedia.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionMedia.getCreateDate()),
			Time.getShortTimestamp(newCPDefinitionMedia.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCPDefinitionMedia.getModifiedDate()),
			Time.getShortTimestamp(newCPDefinitionMedia.getModifiedDate()));
		Assert.assertEquals(existingCPDefinitionMedia.getCPDefinitionId(),
			newCPDefinitionMedia.getCPDefinitionId());
		Assert.assertEquals(existingCPDefinitionMedia.getFileEntryId(),
			newCPDefinitionMedia.getFileEntryId());
		Assert.assertEquals(existingCPDefinitionMedia.getDDMContent(),
			newCPDefinitionMedia.getDDMContent());
		Assert.assertEquals(existingCPDefinitionMedia.getPriority(),
			newCPDefinitionMedia.getPriority());
		Assert.assertEquals(existingCPDefinitionMedia.getCPMediaTypeId(),
			newCPDefinitionMedia.getCPMediaTypeId());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByCPDefinitionId() throws Exception {
		_persistence.countByCPDefinitionId(RandomTestUtil.nextLong());

		_persistence.countByCPDefinitionId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		CPDefinitionMedia existingCPDefinitionMedia = _persistence.findByPrimaryKey(newCPDefinitionMedia.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionMedia, newCPDefinitionMedia);
	}

	@Test(expected = NoSuchCPDefinitionMediaException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CPDefinitionMedia> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CPDefinitionMedia", "uuid",
			true, "CPDefinitionMediaId", true, "groupId", true, "companyId",
			true, "userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "CPDefinitionId", true, "fileEntryId", true,
			"DDMContent", true, "priority", true, "CPMediaTypeId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		CPDefinitionMedia existingCPDefinitionMedia = _persistence.fetchByPrimaryKey(newCPDefinitionMedia.getPrimaryKey());

		Assert.assertEquals(existingCPDefinitionMedia, newCPDefinitionMedia);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionMedia missingCPDefinitionMedia = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCPDefinitionMedia);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CPDefinitionMedia newCPDefinitionMedia1 = addCPDefinitionMedia();
		CPDefinitionMedia newCPDefinitionMedia2 = addCPDefinitionMedia();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionMedia1.getPrimaryKey());
		primaryKeys.add(newCPDefinitionMedia2.getPrimaryKey());

		Map<Serializable, CPDefinitionMedia> cpDefinitionMedias = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cpDefinitionMedias.size());
		Assert.assertEquals(newCPDefinitionMedia1,
			cpDefinitionMedias.get(newCPDefinitionMedia1.getPrimaryKey()));
		Assert.assertEquals(newCPDefinitionMedia2,
			cpDefinitionMedias.get(newCPDefinitionMedia2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CPDefinitionMedia> cpDefinitionMedias = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionMedias.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionMedia.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CPDefinitionMedia> cpDefinitionMedias = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionMedias.size());
		Assert.assertEquals(newCPDefinitionMedia,
			cpDefinitionMedias.get(newCPDefinitionMedia.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CPDefinitionMedia> cpDefinitionMedias = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cpDefinitionMedias.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCPDefinitionMedia.getPrimaryKey());

		Map<Serializable, CPDefinitionMedia> cpDefinitionMedias = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cpDefinitionMedias.size());
		Assert.assertEquals(newCPDefinitionMedia,
			cpDefinitionMedias.get(newCPDefinitionMedia.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CPDefinitionMediaLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CPDefinitionMedia>() {
				@Override
				public void performAction(CPDefinitionMedia cpDefinitionMedia) {
					Assert.assertNotNull(cpDefinitionMedia);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionMedia.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPDefinitionMediaId",
				newCPDefinitionMedia.getCPDefinitionMediaId()));

		List<CPDefinitionMedia> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CPDefinitionMedia existingCPDefinitionMedia = result.get(0);

		Assert.assertEquals(existingCPDefinitionMedia, newCPDefinitionMedia);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionMedia.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("CPDefinitionMediaId",
				RandomTestUtil.nextLong()));

		List<CPDefinitionMedia> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionMedia.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionMediaId"));

		Object newCPDefinitionMediaId = newCPDefinitionMedia.getCPDefinitionMediaId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPDefinitionMediaId",
				new Object[] { newCPDefinitionMediaId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCPDefinitionMediaId = result.get(0);

		Assert.assertEquals(existingCPDefinitionMediaId, newCPDefinitionMediaId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CPDefinitionMedia.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"CPDefinitionMediaId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("CPDefinitionMediaId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CPDefinitionMedia newCPDefinitionMedia = addCPDefinitionMedia();

		_persistence.clearCache();

		CPDefinitionMedia existingCPDefinitionMedia = _persistence.findByPrimaryKey(newCPDefinitionMedia.getPrimaryKey());

		Assert.assertTrue(Objects.equals(existingCPDefinitionMedia.getUuid(),
				ReflectionTestUtil.invoke(existingCPDefinitionMedia,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(existingCPDefinitionMedia.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingCPDefinitionMedia,
				"getOriginalGroupId", new Class<?>[0]));
	}

	protected CPDefinitionMedia addCPDefinitionMedia()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		CPDefinitionMedia cpDefinitionMedia = _persistence.create(pk);

		cpDefinitionMedia.setUuid(RandomTestUtil.randomString());

		cpDefinitionMedia.setGroupId(RandomTestUtil.nextLong());

		cpDefinitionMedia.setCompanyId(RandomTestUtil.nextLong());

		cpDefinitionMedia.setUserId(RandomTestUtil.nextLong());

		cpDefinitionMedia.setUserName(RandomTestUtil.randomString());

		cpDefinitionMedia.setCreateDate(RandomTestUtil.nextDate());

		cpDefinitionMedia.setModifiedDate(RandomTestUtil.nextDate());

		cpDefinitionMedia.setCPDefinitionId(RandomTestUtil.nextLong());

		cpDefinitionMedia.setFileEntryId(RandomTestUtil.nextLong());

		cpDefinitionMedia.setDDMContent(RandomTestUtil.randomString());

		cpDefinitionMedia.setPriority(RandomTestUtil.nextInt());

		cpDefinitionMedia.setCPMediaTypeId(RandomTestUtil.nextLong());

		_cpDefinitionMedias.add(_persistence.update(cpDefinitionMedia));

		return cpDefinitionMedia;
	}

	private List<CPDefinitionMedia> _cpDefinitionMedias = new ArrayList<CPDefinitionMedia>();
	private CPDefinitionMediaPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}