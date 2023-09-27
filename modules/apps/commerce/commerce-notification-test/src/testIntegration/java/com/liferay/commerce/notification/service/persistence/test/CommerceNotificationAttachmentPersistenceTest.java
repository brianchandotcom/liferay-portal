/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.notification.exception.NoSuchNotificationAttachmentException;
import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
import com.liferay.commerce.notification.service.CommerceNotificationAttachmentLocalServiceUtil;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationAttachmentPersistence;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationAttachmentUtil;
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
public class CommerceNotificationAttachmentPersistenceTest {

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
		_persistence = CommerceNotificationAttachmentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CommerceNotificationAttachment> iterator =
			_commerceNotificationAttachments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationAttachment commerceNotificationAttachment =
			_persistence.create(pk);

		Assert.assertNotNull(commerceNotificationAttachment);

		Assert.assertEquals(commerceNotificationAttachment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		_persistence.remove(newCommerceNotificationAttachment);

		CommerceNotificationAttachment existingCommerceNotificationAttachment =
			_persistence.fetchByPrimaryKey(
				newCommerceNotificationAttachment.getPrimaryKey());

		Assert.assertNull(existingCommerceNotificationAttachment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCommerceNotificationAttachment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationAttachment newCommerceNotificationAttachment =
			_persistence.create(pk);

		newCommerceNotificationAttachment.setMvccVersion(
			RandomTestUtil.nextLong());

		newCommerceNotificationAttachment.setUuid(
			RandomTestUtil.randomString());

		newCommerceNotificationAttachment.setGroupId(RandomTestUtil.nextLong());

		newCommerceNotificationAttachment.setCompanyId(
			RandomTestUtil.nextLong());

		newCommerceNotificationAttachment.setUserId(RandomTestUtil.nextLong());

		newCommerceNotificationAttachment.setUserName(
			RandomTestUtil.randomString());

		newCommerceNotificationAttachment.setCreateDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationAttachment.setModifiedDate(
			RandomTestUtil.nextDate());

		newCommerceNotificationAttachment.setCommerceNotificationQueueEntryId(
			RandomTestUtil.nextLong());

		newCommerceNotificationAttachment.setFileEntryId(
			RandomTestUtil.nextLong());

		newCommerceNotificationAttachment.setDeleteOnSend(
			RandomTestUtil.randomBoolean());

		_commerceNotificationAttachments.add(
			_persistence.update(newCommerceNotificationAttachment));

		CommerceNotificationAttachment existingCommerceNotificationAttachment =
			_persistence.findByPrimaryKey(
				newCommerceNotificationAttachment.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationAttachment.getMvccVersion(),
			newCommerceNotificationAttachment.getMvccVersion());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.getUuid(),
			newCommerceNotificationAttachment.getUuid());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.
				getCommerceNotificationAttachmentId(),
			newCommerceNotificationAttachment.
				getCommerceNotificationAttachmentId());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.getGroupId(),
			newCommerceNotificationAttachment.getGroupId());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.getCompanyId(),
			newCommerceNotificationAttachment.getCompanyId());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.getUserId(),
			newCommerceNotificationAttachment.getUserId());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.getUserName(),
			newCommerceNotificationAttachment.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationAttachment.getCreateDate()),
			Time.getShortTimestamp(
				newCommerceNotificationAttachment.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(
				existingCommerceNotificationAttachment.getModifiedDate()),
			Time.getShortTimestamp(
				newCommerceNotificationAttachment.getModifiedDate()));
		Assert.assertEquals(
			existingCommerceNotificationAttachment.
				getCommerceNotificationQueueEntryId(),
			newCommerceNotificationAttachment.
				getCommerceNotificationQueueEntryId());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.getFileEntryId(),
			newCommerceNotificationAttachment.getFileEntryId());
		Assert.assertEquals(
			existingCommerceNotificationAttachment.isDeleteOnSend(),
			newCommerceNotificationAttachment.isDeleteOnSend());
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
	public void testCountByCommerceNotificationQueueEntryId() throws Exception {
		_persistence.countByCommerceNotificationQueueEntryId(
			RandomTestUtil.nextLong());

		_persistence.countByCommerceNotificationQueueEntryId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		CommerceNotificationAttachment existingCommerceNotificationAttachment =
			_persistence.findByPrimaryKey(
				newCommerceNotificationAttachment.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationAttachment,
			newCommerceNotificationAttachment);
	}

	@Test(expected = NoSuchNotificationAttachmentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<CommerceNotificationAttachment>
		getOrderByComparator() {

		return OrderByComparatorFactoryUtil.create(
			"CNotificationAttachment", "mvccVersion", true, "uuid", true,
			"commerceNotificationAttachmentId", true, "groupId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "commerceNotificationQueueEntryId",
			true, "fileEntryId", true, "deleteOnSend", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		CommerceNotificationAttachment existingCommerceNotificationAttachment =
			_persistence.fetchByPrimaryKey(
				newCommerceNotificationAttachment.getPrimaryKey());

		Assert.assertEquals(
			existingCommerceNotificationAttachment,
			newCommerceNotificationAttachment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CommerceNotificationAttachment missingCommerceNotificationAttachment =
			_persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCommerceNotificationAttachment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		CommerceNotificationAttachment newCommerceNotificationAttachment1 =
			addCommerceNotificationAttachment();
		CommerceNotificationAttachment newCommerceNotificationAttachment2 =
			addCommerceNotificationAttachment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationAttachment1.getPrimaryKey());
		primaryKeys.add(newCommerceNotificationAttachment2.getPrimaryKey());

		Map<Serializable, CommerceNotificationAttachment>
			commerceNotificationAttachments = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(2, commerceNotificationAttachments.size());
		Assert.assertEquals(
			newCommerceNotificationAttachment1,
			commerceNotificationAttachments.get(
				newCommerceNotificationAttachment1.getPrimaryKey()));
		Assert.assertEquals(
			newCommerceNotificationAttachment2,
			commerceNotificationAttachments.get(
				newCommerceNotificationAttachment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CommerceNotificationAttachment>
			commerceNotificationAttachments = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceNotificationAttachments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationAttachment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CommerceNotificationAttachment>
			commerceNotificationAttachments = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceNotificationAttachments.size());
		Assert.assertEquals(
			newCommerceNotificationAttachment,
			commerceNotificationAttachments.get(
				newCommerceNotificationAttachment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CommerceNotificationAttachment>
			commerceNotificationAttachments = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertTrue(commerceNotificationAttachments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCommerceNotificationAttachment.getPrimaryKey());

		Map<Serializable, CommerceNotificationAttachment>
			commerceNotificationAttachments = _persistence.fetchByPrimaryKeys(
				primaryKeys);

		Assert.assertEquals(1, commerceNotificationAttachments.size());
		Assert.assertEquals(
			newCommerceNotificationAttachment,
			commerceNotificationAttachments.get(
				newCommerceNotificationAttachment.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			CommerceNotificationAttachmentLocalServiceUtil.
				getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod
				<CommerceNotificationAttachment>() {

				@Override
				public void performAction(
					CommerceNotificationAttachment
						commerceNotificationAttachment) {

					Assert.assertNotNull(commerceNotificationAttachment);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationAttachment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationAttachmentId",
				newCommerceNotificationAttachment.
					getCommerceNotificationAttachmentId()));

		List<CommerceNotificationAttachment> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CommerceNotificationAttachment existingCommerceNotificationAttachment =
			result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationAttachment,
			newCommerceNotificationAttachment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationAttachment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationAttachmentId", RandomTestUtil.nextLong()));

		List<CommerceNotificationAttachment> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationAttachment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceNotificationAttachmentId"));

		Object newCommerceNotificationAttachmentId =
			newCommerceNotificationAttachment.
				getCommerceNotificationAttachmentId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationAttachmentId",
				new Object[] {newCommerceNotificationAttachmentId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCommerceNotificationAttachmentId = result.get(0);

		Assert.assertEquals(
			existingCommerceNotificationAttachmentId,
			newCommerceNotificationAttachmentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationAttachment.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(
			ProjectionFactoryUtil.property("commerceNotificationAttachmentId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"commerceNotificationAttachmentId",
				new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		_persistence.clearCache();

		_assertOriginalValues(
			_persistence.findByPrimaryKey(
				newCommerceNotificationAttachment.getPrimaryKey()));
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

		CommerceNotificationAttachment newCommerceNotificationAttachment =
			addCommerceNotificationAttachment();

		if (clearSession) {
			Session session = _persistence.openSession();

			session.flush();

			session.clear();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			CommerceNotificationAttachment.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"commerceNotificationAttachmentId",
				newCommerceNotificationAttachment.
					getCommerceNotificationAttachmentId()));

		List<CommerceNotificationAttachment> result =
			_persistence.findWithDynamicQuery(dynamicQuery);

		_assertOriginalValues(result.get(0));
	}

	private void _assertOriginalValues(
		CommerceNotificationAttachment commerceNotificationAttachment) {

		Assert.assertEquals(
			commerceNotificationAttachment.getUuid(),
			ReflectionTestUtil.invoke(
				commerceNotificationAttachment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "uuid_"));
		Assert.assertEquals(
			Long.valueOf(commerceNotificationAttachment.getGroupId()),
			ReflectionTestUtil.<Long>invoke(
				commerceNotificationAttachment, "getColumnOriginalValue",
				new Class<?>[] {String.class}, "groupId"));
	}

	protected CommerceNotificationAttachment addCommerceNotificationAttachment()
		throws Exception {

		long pk = RandomTestUtil.nextLong();

		CommerceNotificationAttachment commerceNotificationAttachment =
			_persistence.create(pk);

		commerceNotificationAttachment.setMvccVersion(
			RandomTestUtil.nextLong());

		commerceNotificationAttachment.setUuid(RandomTestUtil.randomString());

		commerceNotificationAttachment.setGroupId(RandomTestUtil.nextLong());

		commerceNotificationAttachment.setCompanyId(RandomTestUtil.nextLong());

		commerceNotificationAttachment.setUserId(RandomTestUtil.nextLong());

		commerceNotificationAttachment.setUserName(
			RandomTestUtil.randomString());

		commerceNotificationAttachment.setCreateDate(RandomTestUtil.nextDate());

		commerceNotificationAttachment.setModifiedDate(
			RandomTestUtil.nextDate());

		commerceNotificationAttachment.setCommerceNotificationQueueEntryId(
			RandomTestUtil.nextLong());

		commerceNotificationAttachment.setFileEntryId(
			RandomTestUtil.nextLong());

		commerceNotificationAttachment.setDeleteOnSend(
			RandomTestUtil.randomBoolean());

		_commerceNotificationAttachments.add(
			_persistence.update(commerceNotificationAttachment));

		return commerceNotificationAttachment;
	}

	private List<CommerceNotificationAttachment>
		_commerceNotificationAttachments =
			new ArrayList<CommerceNotificationAttachment>();
	private CommerceNotificationAttachmentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}