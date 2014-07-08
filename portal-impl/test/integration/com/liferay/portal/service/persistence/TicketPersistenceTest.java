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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchTicketException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.model.impl.TicketModelImpl;
import com.liferay.portal.service.TicketLocalServiceUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.test.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class TicketPersistenceTest {
	@Before
	public void setUp() {
		_modelListeners = _persistence.getListeners();

		for (ModelListener<Ticket> modelListener : _modelListeners) {
			_persistence.unregisterListener(modelListener);
		}
	}

	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();

		for (ModelListener<Ticket> modelListener : _modelListeners) {
			_persistence.registerListener(modelListener);
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

		_persistence.update(newTicket);

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
	public void testCountByKey() {
		try {
			_persistence.countByKey(StringPool.BLANK);

			_persistence.countByKey(StringPool.NULL);

			_persistence.countByKey((String)null);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Ticket newTicket = addTicket();

		Ticket existingTicket = _persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertEquals(existingTicket, newTicket);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchTicketException");
		}
		catch (NoSuchTicketException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
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
				Ticket.class.getClassLoader());

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
				Ticket.class.getClassLoader());

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
				Ticket.class.getClassLoader());

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
				Ticket.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("ticketId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("ticketId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		Ticket newTicket = addTicket();

		_persistence.clearCache();

		TicketModelImpl existingTicketModelImpl = (TicketModelImpl)_persistence.findByPrimaryKey(newTicket.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingTicketModelImpl.getKey(),
				existingTicketModelImpl.getOriginalKey()));
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

		_persistence.update(ticket);

		return ticket;
	}

	private static Log _log = LogFactoryUtil.getLog(TicketPersistenceTest.class);
	private ModelListener<Ticket>[] _modelListeners;
	private TicketPersistence _persistence = (TicketPersistence)PortalBeanLocatorUtil.locate(TicketPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}