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

package com.liferay.notification.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalServiceUtil;
import com.liferay.notification.service.persistence.NotificationTemplatePersistence;
import com.liferay.notification.service.persistence.NotificationTemplateUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
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
public class NotificationTemplatePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.notification.service"));

	@Before
	public void setUp() {
		_persistence = NotificationTemplateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationTemplate> iterator =
			_notificationTemplates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplate notificationTemplate = _persistence.create(pk);

		Assert.assertNotNull(notificationTemplate);

		Assert.assertEquals(notificationTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationTemplate newNotificationTemplate =
			addNotificationTemplate();

		_persistence.remove(newNotificationTemplate);

		NotificationTemplate existingNotificationTemplate =
			_persistence.fetchByPrimaryKey(
				newNotificationTemplate.getPrimaryKey());

		Assert.assertNull(existingNotificationTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplate newNotificationTemplate = _persistence.create(pk);

		newNotificationTemplate.setMvccVersion(RandomTestUtil.nextLong());

		newNotificationTemplate.setUuid(RandomTestUtil.randomString());

		newNotificationTemplate.setCompanyId(RandomTestUtil.nextLong());

		newNotificationTemplate.setUserId(RandomTestUtil.nextLong());

		newNotificationTemplate.setUserName(RandomTestUtil.randomString());

		newNotificationTemplate.setCreateDate(RandomTestUtil.nextDate());

		newNotificationTemplate.setModifiedDate(RandomTestUtil.nextDate());

		newNotificationTemplate.setObjectDefinitionId(
			RandomTestUtil.nextLong());

		newNotificationTemplate.setBcc(RandomTestUtil.randomString());

		newNotificationTemplate.setBody(RandomTestUtil.randomString());

		newNotificationTemplate.setCc(RandomTestUtil.randomString());

		newNotificationTemplate.setDescription(RandomTestUtil.randomString());

		newNotificationTemplate.setFrom(RandomTestUtil.randomString());

		newNotificationTemplate.setFromName(RandomTestUtil.randomString());

		newNotificationTemplate.setName(RandomTestUtil.randomString());

		newNotificationTemplate.setSubject(RandomTestUtil.randomString());

		newNotificationTemplate.setTo(RandomTestUtil.randomString());

		_notificationTemplates.add(
			_persistence.update(newNotificationTemplate));

		NotificationTemplate existingNotificationTemplate =
			_persistence.findByPrimaryKey(
				newNotificationTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplate.getMvccVersion(),
			newNotificationTemplate.getMvccVersion());
		Assert.assertEquals(
			existingNotificationTemplate.getUuid(),
			newNotificationTemplate.getUuid());
		Assert.assertEquals(
			existingNotificationTemplate.getNotificationTemplateId(),
			newNotificationTemplate.getNotificationTemplateId());
		Assert.assertEquals(
			existingNotificationTemplate.getCompanyId(),
			newNotificationTemplate.getCompanyId());
		Assert.assertEquals(
			existingNotificationTemplate.getUserId(),
			newNotificationTemplate.getUserId());
		Assert.assertEquals(
			existingNotificationTemplate.getUserName(),
			newNotificationTemplate.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationTemplate.getCreateDate()),
			Time.getShortTimestamp(newNotificationTemplate.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationTemplate.getModifiedDate()),
			Time.getShortTimestamp(newNotificationTemplate.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationTemplate.getObjectDefinitionId(),
			newNotificationTemplate.getObjectDefinitionId());
		Assert.assertEquals(
			existingNotificationTemplate.getBcc(),
			newNotificationTemplate.getBcc());
		Assert.assertEquals(
			existingNotificationTemplate.getBody(),
			newNotificationTemplate.getBody());
		Assert.assertEquals(
			existingNotificationTemplate.getCc(),
			newNotificationTemplate.getCc());
		Assert.assertEquals(
			existingNotificationTemplate.getDescription(),
			newNotificationTemplate.getDescription());
		Assert.assertEquals(
			existingNotificationTemplate.getFrom(),
			newNotificationTemplate.getFrom());
		Assert.assertEquals(
			existingNotificationTemplate.getFromName(),
			newNotificationTemplate.getFromName());
		Assert.assertEquals(
			existingNotificationTemplate.getName(),
			newNotificationTemplate.getName());
		Assert.assertEquals(
			existingNotificationTemplate.getSubject(),
			newNotificationTemplate.getSubject());
		Assert.assertEquals(
			existingNotificationTemplate.getTo(),
			newNotificationTemplate.getTo());
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
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationTemplate newNotificationTemplate =
			addNotificationTemplate();

		NotificationTemplate existingNotificationTemplate =
			_persistence.findByPrimaryKey(
				newNotificationTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplate, newNotificationTemplate);
	}

	@Test(expected = NoSuchNotificationTemplateException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationTemplate> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"NotificationTemplate", "mvccVersion", true, "uuid", true,
			"notificationTemplateId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true,
			"objectDefinitionId", true, "bcc", true, "cc", true, "description",
			true, "from", true, "fromName", true, "name", true, "subject", true,
			"to", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationTemplate newNotificationTemplate =
			addNotificationTemplate();

		NotificationTemplate existingNotificationTemplate =
			_persistence.fetchByPrimaryKey(
				newNotificationTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationTemplate, newNotificationTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplate missingNotificationTemplate =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationTemplate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationTemplate newNotificationTemplate1 =
			addNotificationTemplate();
		NotificationTemplate newNotificationTemplate2 =
			addNotificationTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationTemplate1.getPrimaryKey());
		primaryKeys.add(newNotificationTemplate2.getPrimaryKey());

		Map<Serializable, NotificationTemplate> notificationTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, notificationTemplates.size());
		Assert.assertEquals(
			newNotificationTemplate1,
			notificationTemplates.get(
				newNotificationTemplate1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationTemplate2,
			notificationTemplates.get(
				newNotificationTemplate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationTemplate> notificationTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationTemplate newNotificationTemplate =
			addNotificationTemplate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationTemplate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationTemplate> notificationTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationTemplates.size());
		Assert.assertEquals(
			newNotificationTemplate,
			notificationTemplates.get(newNotificationTemplate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationTemplate> notificationTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationTemplate newNotificationTemplate =
			addNotificationTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationTemplate.getPrimaryKey());

		Map<Serializable, NotificationTemplate> notificationTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationTemplates.size());
		Assert.assertEquals(
			newNotificationTemplate,
			notificationTemplates.get(newNotificationTemplate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationTemplateLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationTemplate>() {

				@Override
				public void performAction(
					NotificationTemplate notificationTemplate) {

					Assert.assertNotNull(notificationTemplate);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationTemplate newNotificationTemplate =
			addNotificationTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationTemplateId",
				newNotificationTemplate.getNotificationTemplateId()));

		List<NotificationTemplate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationTemplate existingNotificationTemplate = result.get(0);

		Assert.assertEquals(
			existingNotificationTemplate, newNotificationTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationTemplateId", RandomTestUtil.nextLong()));

		List<NotificationTemplate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationTemplate newNotificationTemplate =
			addNotificationTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationTemplateId"));

		Object newNotificationTemplateId =
			newNotificationTemplate.getNotificationTemplateId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationTemplateId",
				new Object[] {newNotificationTemplateId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationTemplateId = result.get(0);

		Assert.assertEquals(
			existingNotificationTemplateId, newNotificationTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationTemplateId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationTemplateId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected NotificationTemplate addNotificationTemplate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationTemplate notificationTemplate = _persistence.create(pk);

		notificationTemplate.setMvccVersion(RandomTestUtil.nextLong());

		notificationTemplate.setUuid(RandomTestUtil.randomString());

		notificationTemplate.setCompanyId(RandomTestUtil.nextLong());

		notificationTemplate.setUserId(RandomTestUtil.nextLong());

		notificationTemplate.setUserName(RandomTestUtil.randomString());

		notificationTemplate.setCreateDate(RandomTestUtil.nextDate());

		notificationTemplate.setModifiedDate(RandomTestUtil.nextDate());

		notificationTemplate.setObjectDefinitionId(RandomTestUtil.nextLong());

		notificationTemplate.setBcc(RandomTestUtil.randomString());

		notificationTemplate.setBody(RandomTestUtil.randomString());

		notificationTemplate.setCc(RandomTestUtil.randomString());

		notificationTemplate.setDescription(RandomTestUtil.randomString());

		notificationTemplate.setFrom(RandomTestUtil.randomString());

		notificationTemplate.setFromName(RandomTestUtil.randomString());

		notificationTemplate.setName(RandomTestUtil.randomString());

		notificationTemplate.setSubject(RandomTestUtil.randomString());

		notificationTemplate.setTo(RandomTestUtil.randomString());

		_notificationTemplates.add(_persistence.update(notificationTemplate));

		return notificationTemplate;
	}

	private List<NotificationTemplate> _notificationTemplates =
		new ArrayList<NotificationTemplate>();
	private NotificationTemplatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}