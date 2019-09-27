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

package com.liferay.knowledge.base.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.exception.NoSuchTemplateException;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBTemplateLocalServiceUtil;
import com.liferay.knowledge.base.service.persistence.KBTemplatePersistence;
import com.liferay.knowledge.base.service.persistence.KBTemplateUtil;
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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class KBTemplatePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.knowledge.base.service"));

	@Before
	public void setUp() {
		_persistence = KBTemplateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<KBTemplate> iterator = _kbTemplates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBTemplate kbTemplate = _persistence.create(pk);

		Assert.assertNotNull(kbTemplate);

		Assert.assertEquals(kbTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		KBTemplate newKBTemplate = addKBTemplate();

		_persistence.remove(newKBTemplate);

		KBTemplate existingKBTemplate = _persistence.fetchByPrimaryKey(
			newKBTemplate.getPrimaryKey());

		Assert.assertNull(existingKBTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addKBTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBTemplate newKBTemplate = _persistence.create(pk);

		newKBTemplate.setMvccVersion(RandomTestUtil.nextLong());

		newKBTemplate.setUuid(RandomTestUtil.randomString());

		newKBTemplate.setGroupId(RandomTestUtil.nextLong());

		newKBTemplate.setCompanyId(RandomTestUtil.nextLong());

		newKBTemplate.setUserId(RandomTestUtil.nextLong());

		newKBTemplate.setUserName(RandomTestUtil.randomString());

		newKBTemplate.setCreateDate(RandomTestUtil.nextDate());

		newKBTemplate.setModifiedDate(RandomTestUtil.nextDate());

		newKBTemplate.setTitle(RandomTestUtil.randomString());

		newKBTemplate.setContent(RandomTestUtil.randomString());

		newKBTemplate.setLastPublishDate(RandomTestUtil.nextDate());

		_kbTemplates.add(_persistence.update(newKBTemplate));

		KBTemplate existingKBTemplate = _persistence.findByPrimaryKey(
			newKBTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingKBTemplate.getMvccVersion(),
			newKBTemplate.getMvccVersion());
		Assert.assertEquals(
			existingKBTemplate.getUuid(), newKBTemplate.getUuid());
		Assert.assertEquals(
			existingKBTemplate.getKbTemplateId(),
			newKBTemplate.getKbTemplateId());
		Assert.assertEquals(
			existingKBTemplate.getGroupId(), newKBTemplate.getGroupId());
		Assert.assertEquals(
			existingKBTemplate.getCompanyId(), newKBTemplate.getCompanyId());
		Assert.assertEquals(
			existingKBTemplate.getUserId(), newKBTemplate.getUserId());
		Assert.assertEquals(
			existingKBTemplate.getUserName(), newKBTemplate.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingKBTemplate.getCreateDate()),
			Time.getShortTimestamp(newKBTemplate.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingKBTemplate.getModifiedDate()),
			Time.getShortTimestamp(newKBTemplate.getModifiedDate()));
		Assert.assertEquals(
			existingKBTemplate.getTitle(), newKBTemplate.getTitle());
		Assert.assertEquals(
			existingKBTemplate.getContent(), newKBTemplate.getContent());
		Assert.assertEquals(
			Time.getShortTimestamp(existingKBTemplate.getLastPublishDate()),
			Time.getShortTimestamp(newKBTemplate.getLastPublishDate()));
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
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		KBTemplate newKBTemplate = addKBTemplate();

		KBTemplate existingKBTemplate = _persistence.findByPrimaryKey(
			newKBTemplate.getPrimaryKey());

		Assert.assertEquals(existingKBTemplate, newKBTemplate);
	}

	@Test(expected = NoSuchTemplateException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<KBTemplate> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"KBTemplate", "mvccVersion", true, "uuid", true, "kbTemplateId",
			true, "groupId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "title",
			true, "lastPublishDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		KBTemplate newKBTemplate = addKBTemplate();

		KBTemplate existingKBTemplate = _persistence.fetchByPrimaryKey(
			newKBTemplate.getPrimaryKey());

		Assert.assertEquals(existingKBTemplate, newKBTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBTemplate missingKBTemplate = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingKBTemplate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		KBTemplate newKBTemplate1 = addKBTemplate();
		KBTemplate newKBTemplate2 = addKBTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKBTemplate1.getPrimaryKey());
		primaryKeys.add(newKBTemplate2.getPrimaryKey());

		Map<Serializable, KBTemplate> kbTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, kbTemplates.size());
		Assert.assertEquals(
			newKBTemplate1, kbTemplates.get(newKBTemplate1.getPrimaryKey()));
		Assert.assertEquals(
			newKBTemplate2, kbTemplates.get(newKBTemplate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, KBTemplate> kbTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kbTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		KBTemplate newKBTemplate = addKBTemplate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKBTemplate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, KBTemplate> kbTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kbTemplates.size());
		Assert.assertEquals(
			newKBTemplate, kbTemplates.get(newKBTemplate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, KBTemplate> kbTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(kbTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		KBTemplate newKBTemplate = addKBTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newKBTemplate.getPrimaryKey());

		Map<Serializable, KBTemplate> kbTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, kbTemplates.size());
		Assert.assertEquals(
			newKBTemplate, kbTemplates.get(newKBTemplate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			KBTemplateLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<KBTemplate>() {

				@Override
				public void performAction(KBTemplate kbTemplate) {
					Assert.assertNotNull(kbTemplate);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		KBTemplate newKBTemplate = addKBTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"kbTemplateId", newKBTemplate.getKbTemplateId()));

		List<KBTemplate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		KBTemplate existingKBTemplate = result.get(0);

		Assert.assertEquals(existingKBTemplate, newKBTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"kbTemplateId", RandomTestUtil.nextLong()));

		List<KBTemplate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		KBTemplate newKBTemplate = addKBTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("kbTemplateId"));

		Object newKbTemplateId = newKBTemplate.getKbTemplateId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"kbTemplateId", new Object[] {newKbTemplateId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingKbTemplateId = result.get(0);

		Assert.assertEquals(existingKbTemplateId, newKbTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KBTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("kbTemplateId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"kbTemplateId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		KBTemplate newKBTemplate = addKBTemplate();

		_persistence.clearCache();

		KBTemplate existingKBTemplate = _persistence.findByPrimaryKey(
			newKBTemplate.getPrimaryKey());

		Assert.assertTrue(
			Objects.equals(
				existingKBTemplate.getUuid(),
				ReflectionTestUtil.invoke(
					existingKBTemplate, "getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(
			Long.valueOf(existingKBTemplate.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				existingKBTemplate, "getOriginalGroupId", new Class<?>[0]));
	}

	protected KBTemplate addKBTemplate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		KBTemplate kbTemplate = _persistence.create(pk);

		kbTemplate.setMvccVersion(RandomTestUtil.nextLong());

		kbTemplate.setUuid(RandomTestUtil.randomString());

		kbTemplate.setGroupId(RandomTestUtil.nextLong());

		kbTemplate.setCompanyId(RandomTestUtil.nextLong());

		kbTemplate.setUserId(RandomTestUtil.nextLong());

		kbTemplate.setUserName(RandomTestUtil.randomString());

		kbTemplate.setCreateDate(RandomTestUtil.nextDate());

		kbTemplate.setModifiedDate(RandomTestUtil.nextDate());

		kbTemplate.setTitle(RandomTestUtil.randomString());

		kbTemplate.setContent(RandomTestUtil.randomString());

		kbTemplate.setLastPublishDate(RandomTestUtil.nextDate());

		_kbTemplates.add(_persistence.update(kbTemplate));

		return kbTemplate;
	}

	private List<KBTemplate> _kbTemplates = new ArrayList<KBTemplate>();
	private KBTemplatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}