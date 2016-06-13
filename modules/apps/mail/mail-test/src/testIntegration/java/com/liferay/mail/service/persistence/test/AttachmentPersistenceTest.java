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

package com.liferay.mail.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.mail.exception.NoSuchAttachmentException;
import com.liferay.mail.model.Attachment;
import com.liferay.mail.service.AttachmentLocalServiceUtil;
import com.liferay.mail.service.persistence.AttachmentPersistence;
import com.liferay.mail.service.persistence.AttachmentUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AttachmentPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AttachmentUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Attachment> iterator = _attachments.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Attachment attachment = _persistence.create(pk);

		Assert.assertNotNull(attachment);

		Assert.assertEquals(attachment.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Attachment newAttachment = addAttachment();

		_persistence.remove(newAttachment);

		Attachment existingAttachment = _persistence.fetchByPrimaryKey(newAttachment.getPrimaryKey());

		Assert.assertNull(existingAttachment);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAttachment();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Attachment newAttachment = _persistence.create(pk);

		newAttachment.setCompanyId(RandomTestUtil.nextLong());

		newAttachment.setUserId(RandomTestUtil.nextLong());

		newAttachment.setAccountId(RandomTestUtil.nextLong());

		newAttachment.setFolderId(RandomTestUtil.nextLong());

		newAttachment.setMessageId(RandomTestUtil.nextLong());

		newAttachment.setContentPath(RandomTestUtil.randomString());

		newAttachment.setFileName(RandomTestUtil.randomString());

		newAttachment.setSize(RandomTestUtil.nextLong());

		_attachments.add(_persistence.update(newAttachment));

		Attachment existingAttachment = _persistence.findByPrimaryKey(newAttachment.getPrimaryKey());

		Assert.assertEquals(existingAttachment.getAttachmentId(),
			newAttachment.getAttachmentId());
		Assert.assertEquals(existingAttachment.getCompanyId(),
			newAttachment.getCompanyId());
		Assert.assertEquals(existingAttachment.getUserId(),
			newAttachment.getUserId());
		Assert.assertEquals(existingAttachment.getAccountId(),
			newAttachment.getAccountId());
		Assert.assertEquals(existingAttachment.getFolderId(),
			newAttachment.getFolderId());
		Assert.assertEquals(existingAttachment.getMessageId(),
			newAttachment.getMessageId());
		Assert.assertEquals(existingAttachment.getContentPath(),
			newAttachment.getContentPath());
		Assert.assertEquals(existingAttachment.getFileName(),
			newAttachment.getFileName());
		Assert.assertEquals(existingAttachment.getSize(),
			newAttachment.getSize());
	}

	@Test
	public void testCountByMessageId() throws Exception {
		_persistence.countByMessageId(RandomTestUtil.nextLong());

		_persistence.countByMessageId(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Attachment newAttachment = addAttachment();

		Attachment existingAttachment = _persistence.findByPrimaryKey(newAttachment.getPrimaryKey());

		Assert.assertEquals(existingAttachment, newAttachment);
	}

	@Test(expected = NoSuchAttachmentException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Attachment> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Mail_Attachment",
			"attachmentId", true, "companyId", true, "userId", true,
			"accountId", true, "folderId", true, "messageId", true,
			"contentPath", true, "fileName", true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Attachment newAttachment = addAttachment();

		Attachment existingAttachment = _persistence.fetchByPrimaryKey(newAttachment.getPrimaryKey());

		Assert.assertEquals(existingAttachment, newAttachment);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Attachment missingAttachment = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAttachment);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Attachment newAttachment1 = addAttachment();
		Attachment newAttachment2 = addAttachment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAttachment1.getPrimaryKey());
		primaryKeys.add(newAttachment2.getPrimaryKey());

		Map<Serializable, Attachment> attachments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, attachments.size());
		Assert.assertEquals(newAttachment1,
			attachments.get(newAttachment1.getPrimaryKey()));
		Assert.assertEquals(newAttachment2,
			attachments.get(newAttachment2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Attachment> attachments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(attachments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Attachment newAttachment = addAttachment();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAttachment.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Attachment> attachments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, attachments.size());
		Assert.assertEquals(newAttachment,
			attachments.get(newAttachment.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Attachment> attachments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(attachments.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Attachment newAttachment = addAttachment();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAttachment.getPrimaryKey());

		Map<Serializable, Attachment> attachments = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, attachments.size());
		Assert.assertEquals(newAttachment,
			attachments.get(newAttachment.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AttachmentLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<Attachment>() {
				@Override
				public void performAction(Attachment attachment) {
					Assert.assertNotNull(attachment);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Attachment newAttachment = addAttachment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Attachment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("attachmentId",
				newAttachment.getAttachmentId()));

		List<Attachment> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Attachment existingAttachment = result.get(0);

		Assert.assertEquals(existingAttachment, newAttachment);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Attachment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("attachmentId",
				RandomTestUtil.nextLong()));

		List<Attachment> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Attachment newAttachment = addAttachment();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Attachment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"attachmentId"));

		Object newAttachmentId = newAttachment.getAttachmentId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("attachmentId",
				new Object[] { newAttachmentId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAttachmentId = result.get(0);

		Assert.assertEquals(existingAttachmentId, newAttachmentId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Attachment.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"attachmentId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("attachmentId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Attachment addAttachment() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Attachment attachment = _persistence.create(pk);

		attachment.setCompanyId(RandomTestUtil.nextLong());

		attachment.setUserId(RandomTestUtil.nextLong());

		attachment.setAccountId(RandomTestUtil.nextLong());

		attachment.setFolderId(RandomTestUtil.nextLong());

		attachment.setMessageId(RandomTestUtil.nextLong());

		attachment.setContentPath(RandomTestUtil.randomString());

		attachment.setFileName(RandomTestUtil.randomString());

		attachment.setSize(RandomTestUtil.nextLong());

		_attachments.add(_persistence.update(attachment));

		return attachment;
	}

	private List<Attachment> _attachments = new ArrayList<Attachment>();
	private AttachmentPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}