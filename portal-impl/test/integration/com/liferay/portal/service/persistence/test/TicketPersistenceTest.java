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

package com.liferay.portal.service.persistence.test;

import com.liferay.portal.NoSuchTicketException;
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
import com.liferay.portal.model.Ticket;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.service.persistence.TicketPersistence;
import com.liferay.portal.service.persistence.TicketUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;

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
public class TicketPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = TicketUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Ticket> iterator = _tickets.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ticket ticket = _persistence.create(pk);

		Assert.assertNotNull(ticket);

		Assert.assertEquals(ticket.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Ticket newTicket = addTicket();

		_persistence.remove(newTicket);

		Ticket existingTicket = _persistence.fetchByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertNull(existingTicket);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTicket();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ticket newTicket = _persistence.create(pk);

		newTicket.setMvccVersion(RandomTestUtil.nextLong());

		newTicket.setCompanyId(RandomTestUtil.nextLong());

		newTicket.setCreateDate(RandomTestUtil.nextDate());

		newTicket.setClassNameId(RandomTestUtil.nextLong());

		newTicket.setClassPK(RandomTestUtil.nextLong());

		newTicket.setKey(RandomTestUtil.randomString());

		newTicket.setType(RandomTestUtil.nextInt());

		newTicket.setExtraInfo(RandomTestUtil.randomString());

		newTicket.setExpirationDate(RandomTestUtil.nextDate());

		_tickets.add(_persistence.update(newTicket));

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertEquals(existingTicket.getMvccVersion(),
			newTicket.getMvccVersion());
		Assert.assertEquals(existingTicket.getTicketId(),
			newTicket.getTicketId());
		Assert.assertEquals(existingTicket.getCompanyId(),
			newTicket.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTicket.getCreateDate()),
			Time.getShortTimestamp(newTicket.getCreateDate()));
		Assert.assertEquals(existingTicket.getClassNameId(),
			newTicket.getClassNameId());
		Assert.assertEquals(existingTicket.getClassPK(), newTicket.getClassPK());
		Assert.assertEquals(existingTicket.getKey(), newTicket.getKey());
		Assert.assertEquals(existingTicket.getType(), newTicket.getType());
		Assert.assertEquals(existingTicket.getExtraInfo(),
			newTicket.getExtraInfo());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTicket.getExpirationDate()),
			Time.getShortTimestamp(newTicket.getExpirationDate()));
	}

	@Test
	public void testCountByKey() throws Exception {
		_persistence.countByKey(StringPool.BLANK);

		_persistence.countByKey(StringPool.NULL);

		_persistence.countByKey((String)null);
	}

	@Test
	public void testCountByC_C_T() throws Exception {
		_persistence.countByC_C_T(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextInt());

		_persistence.countByC_C_T(0L, 0L, 0);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Ticket newTicket = addTicket();

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertEquals(existingTicket, newTicket);
	}

	@Test(expected = NoSuchTicketException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<Ticket> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Ticket", "mvccVersion",
			true, "ticketId", true, "companyId", true, "createDate", true,
			"classNameId", true, "classPK", true, "key", true, "type", true,
			"extraInfo", true, "expirationDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Ticket newTicket = addTicket();

		Ticket existingTicket = _persistence.fetchByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertEquals(existingTicket, newTicket);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ticket missingTicket = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTicket);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Ticket newTicket1 = addTicket();
		Ticket newTicket2 = addTicket();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTicket1.getPrimaryKey());
		primaryKeys.add(newTicket2.getPrimaryKey());

		Map<Serializable, Ticket> tickets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, tickets.size());
		Assert.assertEquals(newTicket1, tickets.get(newTicket1.getPrimaryKey()));
		Assert.assertEquals(newTicket2, tickets.get(newTicket2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Ticket> tickets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(tickets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Ticket newTicket = addTicket();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTicket.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Ticket> tickets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, tickets.size());
		Assert.assertEquals(newTicket, tickets.get(newTicket.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Ticket> tickets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(tickets.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Ticket newTicket = addTicket();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTicket.getPrimaryKey());

		Map<Serializable, Ticket> tickets = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, tickets.size());
		Assert.assertEquals(newTicket, tickets.get(newTicket.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = TicketLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Ticket ticket = (Ticket)object;

					Assert.assertNotNull(ticket);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Ticket newTicket = addTicket();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ticketId",
				newTicket.getTicketId()));

		List<Ticket> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Ticket existingTicket = result.get(0);

		Assert.assertEquals(existingTicket, newTicket);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("ticketId",
				RandomTestUtil.nextLong()));

		List<Ticket> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Ticket newTicket = addTicket();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ticketId"));

		Object newTicketId = newTicket.getTicketId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("ticketId",
				new Object[] { newTicketId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTicketId = result.get(0);

		Assert.assertEquals(existingTicketId, newTicketId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Ticket.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ticketId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ticketId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		Ticket newTicket = addTicket();

		_persistence.clearCache();

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingTicket.getKey(),
				ReflectionTestUtil.invoke(existingTicket, "getOriginalKey",
					new Class<?>[0])));
	}

	protected Ticket addTicket() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Ticket ticket = _persistence.create(pk);

		ticket.setMvccVersion(RandomTestUtil.nextLong());

		ticket.setCompanyId(RandomTestUtil.nextLong());

		ticket.setCreateDate(RandomTestUtil.nextDate());

		ticket.setClassNameId(RandomTestUtil.nextLong());

		ticket.setClassPK(RandomTestUtil.nextLong());

		ticket.setKey(RandomTestUtil.randomString());

		ticket.setType(RandomTestUtil.nextInt());

		ticket.setExtraInfo(RandomTestUtil.randomString());

		ticket.setExpirationDate(RandomTestUtil.nextDate());

		_tickets.add(_persistence.update(ticket));

		return ticket;
	}

	private List<Ticket> _tickets = new ArrayList<Ticket>();
	private TicketPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}