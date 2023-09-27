/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.commerce.notification.service.CommerceNotificationTemplateLocalServiceUtil;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplatePersistence;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateUtil;
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
public class CommerceNotificationTemplatePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED,
				"com.liferay.commerce.notification.service"));

	@Before
	public void setUp() {
		_persistence = CommerceNotificationTemplateUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceNotificationTemplate> iterator =
			_commerceNotificationTemplates.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplate commerceNotificationTemplate =
			_persistence.create(pk);

		Assert.assertNotNull(commerceNotificationTemplate);

		Assert.assertEquals(commerceNotificationTemplate.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		_persistence.remove(newCommerceNotificationTemplate);

		CommerceNotificationTemplate existingCommerceNotificationTemplate =
			_persistence.fetchByPrimaryKey(
				newCommerceNotificationTemplate.getPrimaryKey());

		Assert.assertNull(existingCommerceNotificationTemplate);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceNotificationTemplate();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplate newCommerceNotificationTemplate =
			_persistence.create(pk);

		newCommerceNotificationTemplate.setMvccVersion(
			RandomTestUtil.nextLong());

		newCommerceNotificationTemplate.setUuid(RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setGroupId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplate.setCompanyId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplate.setUserId(RandomTestUtil.nextLong());

		newCommerceNotificationTemplate.setUserName(
			RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationTemplate.setModifiedDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationTemplate.setName(RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setDescription(
			RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setFrom(RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setFromName(
			RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setTo(RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setCc(RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setBcc(RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setType(RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setEnabled(
			RandomTestUtil.randomBoolean());

		newCommerceNotificationTemplate.setSubject(
			RandomTestUtil.randomString());

		newCommerceNotificationTemplate.setBody(RandomTestUtil.randomString());

		_commerceNotificationTemplates.add(
			_persistence.update(newCommerceNotificationTemplate));

		CommerceNotificationTemplate existingCommerceNotificationTemplate =
			_persistence.findByPrimaryKey(
				newCommerceNotificationTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationTemplate.getMvccVersion(),
			newCommerceNotificationTemplate.getMvccVersion());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getUuid(),
			newCommerceNotificationTemplate.getUuid());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.
				getCommerceNotificationTemplateId(),
			newCommerceNotificationTemplate.
				getCommerceNotificationTemplateId());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getGroupId(),
			newCommerceNotificationTemplate.getGroupId());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getCompanyId(),
			newCommerceNotificationTemplate.getCompanyId());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getUserId(),
			newCommerceNotificationTemplate.getUserId());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getUserName(),
			newCommerceNotificationTemplate.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationTemplate.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceNotificationTemplate.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationTemplate.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceNotificationTemplate.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getName(),
			newCommerceNotificationTemplate.getName());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getDescription(),
			newCommerceNotificationTemplate.getDescription());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getFrom(),
			newCommerceNotificationTemplate.getFrom());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getFromName(),
			newCommerceNotificationTemplate.getFromName());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getTo(),
			newCommerceNotificationTemplate.getTo());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getCc(),
			newCommerceNotificationTemplate.getCc());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getBcc(),
			newCommerceNotificationTemplate.getBcc());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getType(),
			newCommerceNotificationTemplate.getType());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.isEnabled(),
			newCommerceNotificationTemplate.isEnabled());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getSubject(),
			newCommerceNotificationTemplate.getSubject());
		Assert.assertEquals(
			existingCommerceNotificationTemplate.getBody(),
			newCommerceNotificationTemplate.getBody());
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
	public void testCountByG_T_E() throws Exception {
		_persistence.countByG_T_E(
			RandomTestUtil.nextLong(), "", RandomTestUtil.randomBoolean());

		_persistence.countByG_T_E(0L, "null", RandomTestUtil.randomBoolean());

		_persistence.countByG_T_E(
			0L, (String)null, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		CommerceNotificationTemplate existingCommerceNotificationTemplate =
			_persistence.findByPrimaryKey(
				newCommerceNotificationTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationTemplate,
			newCommerceNotificationTemplate);
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

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(
			0, QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceNotificationTemplate>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CommerceNotificationTemplate", "mvccVersion", true, "uuid", true,
			"commerceNotificationTemplateId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "name", true, "description", true,
			"fromName", true, "type", true, "enabled", true, "subject", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		CommerceNotificationTemplate existingCommerceNotificationTemplate =
			_persistence.fetchByPrimaryKey(
				newCommerceNotificationTemplate.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationTemplate,
			newCommerceNotificationTemplate);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplate missingCommerceNotificationTemplate =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceNotificationTemplate);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceNotificationTemplate newCommerceNotificationTemplate1 =
			addCommerceNotificationTemplate();
		CommerceNotificationTemplate newCommerceNotificationTemplate2 =
			addCommerceNotificationTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationTemplate1.getPrimaryKey());
		primaryKeys.add(newCommerceNotificationTemplate2.getPrimaryKey());

		Map<Serializable, CommerceNotificationTemplate>
			commerceNotificationTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, commerceNotificationTemplates.size());
		Assert.assertEquals(
			newCommerceNotificationTemplate1,
			commerceNotificationTemplates.get(
				newCommerceNotificationTemplate1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceNotificationTemplate2,
			commerceNotificationTemplates.get(
				newCommerceNotificationTemplate2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceNotificationTemplate>
			commerceNotificationTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceNotificationTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationTemplate.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceNotificationTemplate>
			commerceNotificationTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceNotificationTemplates.size());
		Assert.assertEquals(
			newCommerceNotificationTemplate,
			commerceNotificationTemplates.get(
				newCommerceNotificationTemplate.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceNotificationTemplate>
			commerceNotificationTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceNotificationTemplates.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationTemplate.getPrimaryKey());

		Map<Serializable, CommerceNotificationTemplate>
			commerceNotificationTemplates = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceNotificationTemplates.size());
		Assert.assertEquals(
			newCommerceNotificationTemplate,
			commerceNotificationTemplates.get(
				newCommerceNotificationTemplate.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceNotificationTemplateLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceNotificationTemplate>() {

				@Override
				public void performAction(
					CommerceNotificationTemplate commerceNotificationTemplate) {

					Assert.assertNotNull(commerceNotificationTemplate);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateId",
				newCommerceNotificationTemplate.
					getCommerceNotificationTemplateId()));

		List<CommerceNotificationTemplate> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceNotificationTemplate existingCommerceNotificationTemplate =
			result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationTemplate,
			newCommerceNotificationTemplate);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateId", RandomTestUtil.nextLong()));

		List<CommerceNotificationTemplate> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceNotificationTemplateId"));

		Object newCommerceNotificationTemplateId =
			newCommerceNotificationTemplate.getCommerceNotificationTemplateId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationTemplateId",
				new Object[] {newCommerceNotificationTemplateId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceNotificationTemplateId = result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationTemplateId,
			newCommerceNotificationTemplateId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceNotificationTemplateId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationTemplateId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceNotificationTemplate.getPrimaryKey()));
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

		CommerceNotificationTemplate newCommerceNotificationTemplate =
			addCommerceNotificationTemplate();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationTemplate.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationTemplateId",
				newCommerceNotificationTemplate.
					getCommerceNotificationTemplateId()));

		List<CommerceNotificationTemplate> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceNotificationTemplate commerceNotificationTemplate) {

		Assert.assertEquals(
			commerceNotificationTemplate.getUuid(),
			ReflectionTestUtil.invoke(
				commerceNotificationTemplate, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(commerceNotificationTemplate.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				commerceNotificationTemplate, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected CommerceNotificationTemplate addCommerceNotificationTemplate()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceNotificationTemplate commerceNotificationTemplate =
			_persistence.create(pk);

		commerceNotificationTemplate.setMvccVersion(RandomTestUtil.nextLong());

		commerceNotificationTemplate.setUuid(RandomTestUtil.randomString());

		commerceNotificationTemplate.setGroupId(RandomTestUtil.nextLong());

		commerceNotificationTemplate.setCompanyId(RandomTestUtil.nextLong());

		commerceNotificationTemplate.setUserId(RandomTestUtil.nextLong());

		commerceNotificationTemplate.setUserName(RandomTestUtil.randomString());

		commerceNotificationTemplate.setCreateDate(RandomTestUtil.nextDate());

		commerceNotificationTemplate.setModifiedDate(RandomTestUtil.nextDate());

		commerceNotificationTemplate.setName(RandomTestUtil.randomString());

		commerceNotificationTemplate.setDescription(
			RandomTestUtil.randomString());

		commerceNotificationTemplate.setFrom(RandomTestUtil.randomString());

		commerceNotificationTemplate.setFromName(RandomTestUtil.randomString());

		commerceNotificationTemplate.setTo(RandomTestUtil.randomString());

		commerceNotificationTemplate.setCc(RandomTestUtil.randomString());

		commerceNotificationTemplate.setBcc(RandomTestUtil.randomString());

		commerceNotificationTemplate.setType(RandomTestUtil.randomString());

		commerceNotificationTemplate.setEnabled(RandomTestUtil.randomBoolean());

		commerceNotificationTemplate.setSubject(RandomTestUtil.randomString());

		commerceNotificationTemplate.setBody(RandomTestUtil.randomString());

		_commerceNotificationTemplates.add(
			_persistence.update(commerceNotificationTemplate));

		return commerceNotificationTemplate;
	}

	private List<CommerceNotificationTemplate> _commerceNotificationTemplates =
		new ArrayList<CommerceNotificationTemplate>();
	private CommerceNotificationTemplatePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}