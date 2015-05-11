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

package com.liferay.portlet.dynamicdatamapping.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateVersionException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateVersionLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateVersionPersistence;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateVersionUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class DDMTemplateVersionPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = DDMTemplateVersionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<DDMTemplateVersion> iterator = _ddmTemplateVersions.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateVersion ddmTemplateVersion = _persistence.create(pk);

		Assert.assertNotNull(ddmTemplateVersion);

		Assert.assertEquals(ddmTemplateVersion.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		_persistence.remove(newDDMTemplateVersion);

		DDMTemplateVersion existingDDMTemplateVersion = _persistence.fetchByPrimaryKey(newDDMTemplateVersion.getPrimaryKey());

		Assert.assertNull(existingDDMTemplateVersion);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addDDMTemplateVersion();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateVersion newDDMTemplateVersion = _persistence.create(pk);

		newDDMTemplateVersion.setGroupId(RandomTestUtil.nextLong());

		newDDMTemplateVersion.setCompanyId(RandomTestUtil.nextLong());

		newDDMTemplateVersion.setUserId(RandomTestUtil.nextLong());

		newDDMTemplateVersion.setUserName(RandomTestUtil.randomString());

		newDDMTemplateVersion.setCreateDate(RandomTestUtil.nextDate());

		newDDMTemplateVersion.setTemplateId(RandomTestUtil.nextLong());

		newDDMTemplateVersion.setVersion(RandomTestUtil.randomString());

		newDDMTemplateVersion.setName(RandomTestUtil.randomString());

		newDDMTemplateVersion.setDescription(RandomTestUtil.randomString());

		newDDMTemplateVersion.setLanguage(RandomTestUtil.randomString());

		newDDMTemplateVersion.setScript(RandomTestUtil.randomString());

		_ddmTemplateVersions.add(_persistence.update(newDDMTemplateVersion));

		DDMTemplateVersion existingDDMTemplateVersion = _persistence.findByPrimaryKey(newDDMTemplateVersion.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateVersion.getTemplateVersionId(),
			newDDMTemplateVersion.getTemplateVersionId());
		Assert.assertEquals(existingDDMTemplateVersion.getGroupId(),
			newDDMTemplateVersion.getGroupId());
		Assert.assertEquals(existingDDMTemplateVersion.getCompanyId(),
			newDDMTemplateVersion.getCompanyId());
		Assert.assertEquals(existingDDMTemplateVersion.getUserId(),
			newDDMTemplateVersion.getUserId());
		Assert.assertEquals(existingDDMTemplateVersion.getUserName(),
			newDDMTemplateVersion.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingDDMTemplateVersion.getCreateDate()),
			Time.getShortTimestamp(newDDMTemplateVersion.getCreateDate()));
		Assert.assertEquals(existingDDMTemplateVersion.getTemplateId(),
			newDDMTemplateVersion.getTemplateId());
		Assert.assertEquals(existingDDMTemplateVersion.getVersion(),
			newDDMTemplateVersion.getVersion());
		Assert.assertEquals(existingDDMTemplateVersion.getName(),
			newDDMTemplateVersion.getName());
		Assert.assertEquals(existingDDMTemplateVersion.getDescription(),
			newDDMTemplateVersion.getDescription());
		Assert.assertEquals(existingDDMTemplateVersion.getLanguage(),
			newDDMTemplateVersion.getLanguage());
		Assert.assertEquals(existingDDMTemplateVersion.getScript(),
			newDDMTemplateVersion.getScript());
	}

	@Test
	public void testCountByTemplateId() throws Exception {
		_persistence.countByTemplateId(RandomTestUtil.nextLong());

		_persistence.countByTemplateId(0L);
	}

	@Test
	public void testCountByT_V() throws Exception {
		_persistence.countByT_V(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByT_V(0L, StringPool.NULL);

		_persistence.countByT_V(0L, (String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		DDMTemplateVersion existingDDMTemplateVersion = _persistence.findByPrimaryKey(newDDMTemplateVersion.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateVersion, newDDMTemplateVersion);
	}

	@Test(expected = NoSuchTemplateVersionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<DDMTemplateVersion> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("DDMTemplateVersion",
			"templateVersionId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true, "templateId",
			true, "version", true, "name", true, "description", true,
			"language", true, "script", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		DDMTemplateVersion existingDDMTemplateVersion = _persistence.fetchByPrimaryKey(newDDMTemplateVersion.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateVersion, newDDMTemplateVersion);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateVersion missingDDMTemplateVersion = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingDDMTemplateVersion);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		DDMTemplateVersion newDDMTemplateVersion1 = addDDMTemplateVersion();
		DDMTemplateVersion newDDMTemplateVersion2 = addDDMTemplateVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMTemplateVersion1.getPrimaryKey());
		primaryKeys.add(newDDMTemplateVersion2.getPrimaryKey());

		Map<Serializable, DDMTemplateVersion> ddmTemplateVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, ddmTemplateVersions.size());
		Assert.assertEquals(newDDMTemplateVersion1,
			ddmTemplateVersions.get(newDDMTemplateVersion1.getPrimaryKey()));
		Assert.assertEquals(newDDMTemplateVersion2,
			ddmTemplateVersions.get(newDDMTemplateVersion2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, DDMTemplateVersion> ddmTemplateVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmTemplateVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMTemplateVersion.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, DDMTemplateVersion> ddmTemplateVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmTemplateVersions.size());
		Assert.assertEquals(newDDMTemplateVersion,
			ddmTemplateVersions.get(newDDMTemplateVersion.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, DDMTemplateVersion> ddmTemplateVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(ddmTemplateVersions.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newDDMTemplateVersion.getPrimaryKey());

		Map<Serializable, DDMTemplateVersion> ddmTemplateVersions = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, ddmTemplateVersions.size());
		Assert.assertEquals(newDDMTemplateVersion,
			ddmTemplateVersions.get(newDDMTemplateVersion.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = DDMTemplateVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					DDMTemplateVersion ddmTemplateVersion = (DDMTemplateVersion)object;

					Assert.assertNotNull(ddmTemplateVersion);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateVersionId",
				newDDMTemplateVersion.getTemplateVersionId()));

		List<DDMTemplateVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		DDMTemplateVersion existingDDMTemplateVersion = result.get(0);

		Assert.assertEquals(existingDDMTemplateVersion, newDDMTemplateVersion);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("templateVersionId",
				RandomTestUtil.nextLong()));

		List<DDMTemplateVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"templateVersionId"));

		Object newTemplateVersionId = newDDMTemplateVersion.getTemplateVersionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("templateVersionId",
				new Object[] { newTemplateVersionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTemplateVersionId = result.get(0);

		Assert.assertEquals(existingTemplateVersionId, newTemplateVersionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(DDMTemplateVersion.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"templateVersionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("templateVersionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		DDMTemplateVersion newDDMTemplateVersion = addDDMTemplateVersion();

		_persistence.clearCache();

		DDMTemplateVersion existingDDMTemplateVersion = _persistence.findByPrimaryKey(newDDMTemplateVersion.getPrimaryKey());

		Assert.assertEquals(existingDDMTemplateVersion.getTemplateId(),
			ReflectionTestUtil.invoke(existingDDMTemplateVersion,
				"getOriginalTemplateId", new Class<?>[0]));
		Assert.assertTrue(Validator.equals(
				existingDDMTemplateVersion.getVersion(),
				ReflectionTestUtil.invoke(existingDDMTemplateVersion,
					"getOriginalVersion", new Class<?>[0])));
	}

	protected DDMTemplateVersion addDDMTemplateVersion()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		DDMTemplateVersion ddmTemplateVersion = _persistence.create(pk);

		ddmTemplateVersion.setGroupId(RandomTestUtil.nextLong());

		ddmTemplateVersion.setCompanyId(RandomTestUtil.nextLong());

		ddmTemplateVersion.setUserId(RandomTestUtil.nextLong());

		ddmTemplateVersion.setUserName(RandomTestUtil.randomString());

		ddmTemplateVersion.setCreateDate(RandomTestUtil.nextDate());

		ddmTemplateVersion.setTemplateId(RandomTestUtil.nextLong());

		ddmTemplateVersion.setVersion(RandomTestUtil.randomString());

		ddmTemplateVersion.setName(RandomTestUtil.randomString());

		ddmTemplateVersion.setDescription(RandomTestUtil.randomString());

		ddmTemplateVersion.setLanguage(RandomTestUtil.randomString());

		ddmTemplateVersion.setScript(RandomTestUtil.randomString());

		_ddmTemplateVersions.add(_persistence.update(ddmTemplateVersion));

		return ddmTemplateVersion;
	}

	private List<DDMTemplateVersion> _ddmTemplateVersions = new ArrayList<DDMTemplateVersion>();
	private DDMTemplateVersionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}