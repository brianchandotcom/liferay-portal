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

package com.liferay.mail.reader.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mail.reader.exception.NoSuchMessageException;
import com.liferay.mail.reader.model.Message;
import com.liferay.mail.reader.service.MessageLocalServiceUtil;
import com.liferay.mail.reader.service.persistence.MessagePersistence;
import com.liferay.mail.reader.service.persistence.MessageUtil;
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
public class MessagePersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.mail.reader.service"));

	@Before
	public void setUp() {
		_persistence = MessageUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Message> iterator = _messages.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Message message = _persistence.create(pk);

		Assert.assertNotNull(message);

		Assert.assertEquals(message.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Message newMessage = addMessage();

		_persistence.remove(newMessage);

		Message existingMessage = _persistence.fetchByPrimaryKey(
			newMessage.getPrimaryKey());

		Assert.assertNull(existingMessage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addMessage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Message newMessage = _persistence.create(pk);

		newMessage.setCompanyId(RandomTestUtil.nextLong());

		newMessage.setUserId(RandomTestUtil.nextLong());

		newMessage.setUserName(RandomTestUtil.randomString());

		newMessage.setCreateDate(RandomTestUtil.nextDate());

		newMessage.setModifiedDate(RandomTestUtil.nextDate());

		newMessage.setAccountId(RandomTestUtil.nextLong());

		newMessage.setFolderId(RandomTestUtil.nextLong());

		newMessage.setSender(RandomTestUtil.randomString());

		newMessage.setTo(RandomTestUtil.randomString());

		newMessage.setCc(RandomTestUtil.randomString());

		newMessage.setBcc(RandomTestUtil.randomString());

		newMessage.setSentDate(RandomTestUtil.nextDate());

		newMessage.setSubject(RandomTestUtil.randomString());

		newMessage.setPreview(RandomTestUtil.randomString());

		newMessage.setBody(RandomTestUtil.randomString());

		newMessage.setFlags(RandomTestUtil.randomString());

		newMessage.setSize(RandomTestUtil.nextLong());

		newMessage.setRemoteMessageId(RandomTestUtil.nextLong());

		newMessage.setContentType(RandomTestUtil.randomString());

		_messages.add(_persistence.update(newMessage));

		Message existingMessage = _persistence.findByPrimaryKey(
			newMessage.getPrimaryKey());

		Assert.assertEquals(
			existingMessage.getMessageId(), newMessage.getMessageId());
		Assert.assertEquals(
			existingMessage.getCompanyId(), newMessage.getCompanyId());
		Assert.assertEquals(
			existingMessage.getUserId(), newMessage.getUserId());
		Assert.assertEquals(
			existingMessage.getUserName(), newMessage.getUserName());
		Assert.assertEquals(
			Time.getShortTimestamp(existingMessage.getCreateDate()),
			Time.getShortTimestamp(newMessage.getCreateDate()));
		Assert.assertEquals(
			Time.getShortTimestamp(existingMessage.getModifiedDate()),
			Time.getShortTimestamp(newMessage.getModifiedDate()));
		Assert.assertEquals(
			existingMessage.getAccountId(), newMessage.getAccountId());
		Assert.assertEquals(
			existingMessage.getFolderId(), newMessage.getFolderId());
		Assert.assertEquals(
			existingMessage.getSender(), newMessage.getSender());
		Assert.assertEquals(existingMessage.getTo(), newMessage.getTo());
		Assert.assertEquals(existingMessage.getCc(), newMessage.getCc());
		Assert.assertEquals(existingMessage.getBcc(), newMessage.getBcc());
		Assert.assertEquals(
			Time.getShortTimestamp(existingMessage.getSentDate()),
			Time.getShortTimestamp(newMessage.getSentDate()));
		Assert.assertEquals(
			existingMessage.getSubject(), newMessage.getSubject());
		Assert.assertEquals(
			existingMessage.getPreview(), newMessage.getPreview());
		Assert.assertEquals(existingMessage.getBody(), newMessage.getBody());
		Assert.assertEquals(existingMessage.getFlags(), newMessage.getFlags());
		Assert.assertEquals(existingMessage.getSize(), newMessage.getSize());
		Assert.assertEquals(
			existingMessage.getRemoteMessageId(),
			newMessage.getRemoteMessageId());
		Assert.assertEquals(
			existingMessage.getContentType(), newMessage.getContentType());
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByFolderId() throws Exception {
		_persistence.countByFolderId(RandomTestUtil.nextLong());

		_persistence.countByFolderId(0L);
	}

	@Test
	public void testCountByF_R() throws Exception {
		_persistence.countByF_R(
			RandomTestUtil.nextLong(), RandomTestUtil.nextLong());

		_persistence.countByF_R(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Message newMessage = addMessage();

		Message existingMessage = _persistence.findByPrimaryKey(
			newMessage.getPrimaryKey());

		Assert.assertEquals(existingMessage, newMessage);
	}

	@Test(expected = NoSuchMessageException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Message> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"Mail_Message", "messageId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"accountId", true, "folderId", true, "sender", true, "sentDate",
			true, "subject", true, "preview", true, "flags", true, "size", true,
			"remoteMessageId", true, "contentType", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Message newMessage = addMessage();

		Message existingMessage = _persistence.fetchByPrimaryKey(
			newMessage.getPrimaryKey());

		Assert.assertEquals(existingMessage, newMessage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Message missingMessage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingMessage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Message newMessage1 = addMessage();
		Message newMessage2 = addMessage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMessage1.getPrimaryKey());
		primaryKeys.add(newMessage2.getPrimaryKey());

		Map<Serializable, Message> messages = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, messages.size());
		Assert.assertEquals(
			newMessage1, messages.get(newMessage1.getPrimaryKey()));
		Assert.assertEquals(
			newMessage2, messages.get(newMessage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Message> messages = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(messages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Message newMessage = addMessage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMessage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Message> messages = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, messages.size());
		Assert.assertEquals(
			newMessage, messages.get(newMessage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Message> messages = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(messages.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Message newMessage = addMessage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newMessage.getPrimaryKey());

		Map<Serializable, Message> messages = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, messages.size());
		Assert.assertEquals(
			newMessage, messages.get(newMessage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery =
			MessageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Message>() {

				@Override
				public void performAction(Message message) {
					Assert.assertNotNull(message);

					count.increment();
				}

			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Message newMessage = addMessage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("messageId", newMessage.getMessageId()));

		List<Message> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Message existingMessage = result.get(0);

		Assert.assertEquals(existingMessage, newMessage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("messageId", RandomTestUtil.nextLong()));

		List<Message> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Message newMessage = addMessage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("messageId"));

		Object newMessageId = newMessage.getMessageId();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"messageId", new Object[] {newMessageId}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingMessageId = result.get(0);

		Assert.assertEquals(existingMessageId, newMessageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Message.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("messageId"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"messageId", new Object[] {RandomTestUtil.nextLong()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Message newMessage = addMessage();

		_persistence.clearCache();

		Message existingMessage = _persistence.findByPrimaryKey(
			newMessage.getPrimaryKey());

		Assert.assertEquals(
			Long.valueOf(existingMessage.getFolderId()),
			ReflectionTestUtil.<Long>invoke(
				existingMessage, "getOriginalFolderId", new Class<?>[0]));
		Assert.assertEquals(
			Long.valueOf(existingMessage.getRemoteMessageId()),
			ReflectionTestUtil.<Long>invoke(
				existingMessage, "getOriginalRemoteMessageId",
				new Class<?>[0]));
	}

	protected Message addMessage() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Message message = _persistence.create(pk);

		message.setCompanyId(RandomTestUtil.nextLong());

		message.setUserId(RandomTestUtil.nextLong());

		message.setUserName(RandomTestUtil.randomString());

		message.setCreateDate(RandomTestUtil.nextDate());

		message.setModifiedDate(RandomTestUtil.nextDate());

		message.setAccountId(RandomTestUtil.nextLong());

		message.setFolderId(RandomTestUtil.nextLong());

		message.setSender(RandomTestUtil.randomString());

		message.setTo(RandomTestUtil.randomString());

		message.setCc(RandomTestUtil.randomString());

		message.setBcc(RandomTestUtil.randomString());

		message.setSentDate(RandomTestUtil.nextDate());

		message.setSubject(RandomTestUtil.randomString());

		message.setPreview(RandomTestUtil.randomString());

		message.setBody(RandomTestUtil.randomString());

		message.setFlags(RandomTestUtil.randomString());

		message.setSize(RandomTestUtil.nextLong());

		message.setRemoteMessageId(RandomTestUtil.nextLong());

		message.setContentType(RandomTestUtil.randomString());

		_messages.add(_persistence.update(message));

		return message;
	}

	private List<Message> _messages = new ArrayList<Message>();
	private MessagePersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}