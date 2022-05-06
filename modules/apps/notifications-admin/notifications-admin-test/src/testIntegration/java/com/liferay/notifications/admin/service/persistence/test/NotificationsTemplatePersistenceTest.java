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

package com.liferay.notifications.admin.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.notifications.admin.exception.NoSuchNotificationsTemplateException;
import com.liferay.notifications.admin.model.NotificationsTemplate;
import com.liferay.notifications.admin.service.NotificationsTemplateLocalServiceUtil;
import com.liferay.notifications.admin.service.persistence.NotificationsTemplatePersistence;
import com.liferay.notifications.admin.service.persistence.NotificationsTemplateUtil;
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
public class NotificationsTemplatePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.notifications.admin.service"));

	@Before
	public void setUp() {
		_persistence = NotificationsTemplateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<NotificationsTemplate> iterator =
			_notificationsTemplates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationsTemplate notificationsTemplate = _persistence.create(pk);

		Assert.assertNotNull(notificationsTemplate);

		Assert.assertEquals(notificationsTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		_persistence.remove(newNotificationsTemplate);

		NotificationsTemplate existingNotificationsTemplate =
			_persistence.fetchByPrimaryKey(
				newNotificationsTemplate.getPrimaryKey());

		Assert.assertNull(existingNotificationsTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addNotificationsTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationsTemplate newNotificationsTemplate = _persistence.create(
			pk);

		newNotificationsTemplate.setMvccVersion(RandomTestUtil.nextLong());

		newNotificationsTemplate.setUuid(RandomTestUtil.randomString());

		newNotificationsTemplate.setGroupId(RandomTestUtil.nextLong());

		newNotificationsTemplate.setCompanyId(RandomTestUtil.nextLong());

		newNotificationsTemplate.setUserId(RandomTestUtil.nextLong());

		newNotificationsTemplate.setUserName(RandomTestUtil.randomString());

		newNotificationsTemplate.setCreateDate(RandomTestUtil.nextDate());

		newNotificationsTemplate.setModifiedDate(RandomTestUtil.nextDate());

		newNotificationsTemplate.setName(RandomTestUtil.randomString());

		newNotificationsTemplate.setDescription(RandomTestUtil.randomString());

		newNotificationsTemplate.setFrom(RandomTestUtil.randomString());

		newNotificationsTemplate.setFromName(RandomTestUtil.randomString());

		newNotificationsTemplate.setTo(RandomTestUtil.randomString());

		newNotificationsTemplate.setCc(RandomTestUtil.randomString());

		newNotificationsTemplate.setBcc(RandomTestUtil.randomString());

		newNotificationsTemplate.setEnabled(RandomTestUtil.randomBoolean());

		newNotificationsTemplate.setSubject(RandomTestUtil.randomString());

		newNotificationsTemplate.setBody(RandomTestUtil.randomString());

		_notificationsTemplates.add(
			_persistence.update(newNotificationsTemplate));

		NotificationsTemplate existingNotificationsTemplate =
			_persistence.findByPrimaryKey(
				newNotificationsTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationsTemplate.getMvccVersion(),
			newNotificationsTemplate.getMvccVersion());
		Assert.assertEquals(
			existingNotificationsTemplate.getUuid(),
			newNotificationsTemplate.getUuid());
		Assert.assertEquals(
			existingNotificationsTemplate.getNotificationsTemplateId(),
			newNotificationsTemplate.getNotificationsTemplateId());
		Assert.assertEquals(
			existingNotificationsTemplate.getGroupId(),
			newNotificationsTemplate.getGroupId());
		Assert.assertEquals(
			existingNotificationsTemplate.getCompanyId(),
			newNotificationsTemplate.getCompanyId());
		Assert.assertEquals(
			existingNotificationsTemplate.getUserId(),
			newNotificationsTemplate.getUserId());
		Assert.assertEquals(
			existingNotificationsTemplate.getUserName(),
			newNotificationsTemplate.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationsTemplate.getCreateDate()),
			Time.getShortTimestamp(newNotificationsTemplate.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingNotificationsTemplate.getModifiedDate()),
			Time.getShortTimestamp(newNotificationsTemplate.getModifiedDate()));
		Assert.assertEquals(
			existingNotificationsTemplate.getName(),
			newNotificationsTemplate.getName());
		Assert.assertEquals(
			existingNotificationsTemplate.getDescription(),
			newNotificationsTemplate.getDescription());
		Assert.assertEquals(
			existingNotificationsTemplate.getFrom(),
			newNotificationsTemplate.getFrom());
		Assert.assertEquals(
			existingNotificationsTemplate.getFromName(),
			newNotificationsTemplate.getFromName());
		Assert.assertEquals(
			existingNotificationsTemplate.getTo(),
			newNotificationsTemplate.getTo());
		Assert.assertEquals(
			existingNotificationsTemplate.getCc(),
			newNotificationsTemplate.getCc());
		Assert.assertEquals(
			existingNotificationsTemplate.getBcc(),
			newNotificationsTemplate.getBcc());
		Assert.assertEquals(
			existingNotificationsTemplate.isEnabled(),
			newNotificationsTemplate.isEnabled());
		Assert.assertEquals(
			existingNotificationsTemplate.getSubject(),
			newNotificationsTemplate.getSubject());
		Assert.assertEquals(
			existingNotificationsTemplate.getBody(),
			newNotificationsTemplate.getBody());
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
	public void testCountByG_E() throws Exception {
		_persistence.countByG_E(
			RandomTestUtil.nextLong(), RandomTestUtil.randomBoolean());

		_persistence.countByG_E(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		NotificationsTemplate existingNotificationsTemplate =
			_persistence.findByPrimaryKey(
				newNotificationsTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationsTemplate, newNotificationsTemplate);
	}

	@Test(expected = NoSuchNotificationsTemplateException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<NotificationsTemplate> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"NotificationsTemplate", "mvccVersion", true, "uuid", true,
			"notificationsTemplateId", true, "groupId", true, "companyId", true,
			"userId", true, "userName", true, "createDate", true,
			"modifiedDate", true, "name", true, "description", true, "from",
			true, "fromName", true, "to", true, "cc", true, "bcc", true,
			"enabled", true, "subject", true, "body", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		NotificationsTemplate existingNotificationsTemplate =
			_persistence.fetchByPrimaryKey(
				newNotificationsTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingNotificationsTemplate, newNotificationsTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		NotificationsTemplate missingNotificationsTemplate =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingNotificationsTemplate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		NotificationsTemplate newNotificationsTemplate1 =
			addNotificationsTemplate();
		NotificationsTemplate newNotificationsTemplate2 =
			addNotificationsTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationsTemplate1.getPrimaryKey());
		primaryKeys.add(newNotificationsTemplate2.getPrimaryKey());

		Map<Serializable, NotificationsTemplate> notificationsTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, notificationsTemplates.size());
		Assert.assertEquals(
			newNotificationsTemplate1,
			notificationsTemplates.get(
				newNotificationsTemplate1.getPrimaryKey()));
		Assert.assertEquals(
			newNotificationsTemplate2,
			notificationsTemplates.get(
				newNotificationsTemplate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, NotificationsTemplate> notificationsTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationsTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationsTemplate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, NotificationsTemplate> notificationsTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationsTemplates.size());
		Assert.assertEquals(
			newNotificationsTemplate,
			notificationsTemplates.get(
				newNotificationsTemplate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, NotificationsTemplate> notificationsTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(notificationsTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newNotificationsTemplate.getPrimaryKey());

		Map<Serializable, NotificationsTemplate> notificationsTemplates =
			_persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, notificationsTemplates.size());
		Assert.assertEquals(
			newNotificationsTemplate,
			notificationsTemplates.get(
				newNotificationsTemplate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			NotificationsTemplateLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<NotificationsTemplate>() {

				@Override
				public void performAction(
					NotificationsTemplate notificationsTemplate) {

					Assert.assertNotNull(notificationsTemplate);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationsTemplateId",
				newNotificationsTemplate.getNotificationsTemplateId()));

		List<NotificationsTemplate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(1, result.size());

		NotificationsTemplate existingNotificationsTemplate = result.get(0);

		Assert.assertEquals(
			existingNotificationsTemplate, newNotificationsTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationsTemplateId", RandomTestUtil.nextLong()));

		List<NotificationsTemplate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationsTemplateId"));

		Object newNotificationsTemplateId =
			newNotificationsTemplate.getNotificationsTemplateId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationsTemplateId",
				new Object[] {newNotificationsTemplateId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingNotificationsTemplateId = result.get(0);

		Assert.assertEquals(
			existingNotificationsTemplateId, newNotificationsTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("notificationsTemplateId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"notificationsTemplateId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newNotificationsTemplate.getPrimaryKey()));
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

		NotificationsTemplate newNotificationsTemplate =
			addNotificationsTemplate();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			NotificationsTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"notificationsTemplateId",
				newNotificationsTemplate.getNotificationsTemplateId()));

		List<NotificationsTemplate> result = _persistence.findWithDynamicQuery(
			dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		NotificationsTemplate notificationsTemplate) {

		Assert.assertEquals(
			notificationsTemplate.getUuid(),
			ReflectionTestUtil.invoke(
				notificationsTemplate, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(notificationsTemplate.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				notificationsTemplate, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected NotificationsTemplate addNotificationsTemplate()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		NotificationsTemplate notificationsTemplate = _persistence.create(pk);

		notificationsTemplate.setMvccVersion(RandomTestUtil.nextLong());

		notificationsTemplate.setUuid(RandomTestUtil.randomString());

		notificationsTemplate.setGroupId(RandomTestUtil.nextLong());

		notificationsTemplate.setCompanyId(RandomTestUtil.nextLong());

		notificationsTemplate.setUserId(RandomTestUtil.nextLong());

		notificationsTemplate.setUserName(RandomTestUtil.randomString());

		notificationsTemplate.setCreateDate(RandomTestUtil.nextDate());

		notificationsTemplate.setModifiedDate(RandomTestUtil.nextDate());

		notificationsTemplate.setName(RandomTestUtil.randomString());

		notificationsTemplate.setDescription(RandomTestUtil.randomString());

		notificationsTemplate.setFrom(RandomTestUtil.randomString());

		notificationsTemplate.setFromName(RandomTestUtil.randomString());

		notificationsTemplate.setTo(RandomTestUtil.randomString());

		notificationsTemplate.setCc(RandomTestUtil.randomString());

		notificationsTemplate.setBcc(RandomTestUtil.randomString());

		notificationsTemplate.setEnabled(RandomTestUtil.randomBoolean());

		notificationsTemplate.setSubject(RandomTestUtil.randomString());

		notificationsTemplate.setBody(RandomTestUtil.randomString());

		_notificationsTemplates.add(_persistence.update(notificationsTemplate));

		return notificationsTemplate;
	}

	private List<NotificationsTemplate> _notificationsTemplates =
		new ArrayList<NotificationsTemplate>();
	private NotificationsTemplatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}