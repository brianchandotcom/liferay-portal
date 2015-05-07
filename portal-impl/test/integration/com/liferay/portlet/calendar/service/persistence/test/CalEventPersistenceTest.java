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

package com.liferay.portlet.calendar.service.persistence.test;

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
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.calendar.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.persistence.CalEventPersistence;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;

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
public class CalEventPersistenceTest {
	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = CalEventUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CalEvent> iterator = _calEvents.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CalEvent calEvent = _persistence.create(pk);

		Assert.assertNotNull(calEvent);

		Assert.assertEquals(calEvent.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		_persistence.remove(newCalEvent);

		CalEvent existingCalEvent = _persistence.fetchByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertNull(existingCalEvent);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCalEvent();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CalEvent newCalEvent = _persistence.create(pk);

		newCalEvent.setUuid(RandomTestUtil.randomString());

		newCalEvent.setGroupId(RandomTestUtil.nextLong());

		newCalEvent.setCompanyId(RandomTestUtil.nextLong());

		newCalEvent.setUserId(RandomTestUtil.nextLong());

		newCalEvent.setUserName(RandomTestUtil.randomString());

		newCalEvent.setCreateDate(RandomTestUtil.nextDate());

		newCalEvent.setModifiedDate(RandomTestUtil.nextDate());

		newCalEvent.setTitle(RandomTestUtil.randomString());

		newCalEvent.setDescription(RandomTestUtil.randomString());

		newCalEvent.setLocation(RandomTestUtil.randomString());

		newCalEvent.setStartDate(RandomTestUtil.nextDate());

		newCalEvent.setEndDate(RandomTestUtil.nextDate());

		newCalEvent.setDurationHour(RandomTestUtil.nextInt());

		newCalEvent.setDurationMinute(RandomTestUtil.nextInt());

		newCalEvent.setAllDay(RandomTestUtil.randomBoolean());

		newCalEvent.setTimeZoneSensitive(RandomTestUtil.randomBoolean());

		newCalEvent.setType(RandomTestUtil.randomString());

		newCalEvent.setRepeating(RandomTestUtil.randomBoolean());

		newCalEvent.setRecurrence(RandomTestUtil.randomString());

		newCalEvent.setRemindBy(RandomTestUtil.nextInt());

		newCalEvent.setFirstReminder(RandomTestUtil.nextInt());

		newCalEvent.setSecondReminder(RandomTestUtil.nextInt());

		_calEvents.add(_persistence.update(newCalEvent));

		CalEvent existingCalEvent = _persistence.findByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertEquals(existingCalEvent.getUuid(), newCalEvent.getUuid());
		Assert.assertEquals(existingCalEvent.getEventId(),
			newCalEvent.getEventId());
		Assert.assertEquals(existingCalEvent.getGroupId(),
			newCalEvent.getGroupId());
		Assert.assertEquals(existingCalEvent.getCompanyId(),
			newCalEvent.getCompanyId());
		Assert.assertEquals(existingCalEvent.getUserId(),
			newCalEvent.getUserId());
		Assert.assertEquals(existingCalEvent.getUserName(),
			newCalEvent.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCalEvent.getCreateDate()),
			Time.getShortTimestamp(newCalEvent.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCalEvent.getModifiedDate()),
			Time.getShortTimestamp(newCalEvent.getModifiedDate()));
		Assert.assertEquals(existingCalEvent.getTitle(), newCalEvent.getTitle());
		Assert.assertEquals(existingCalEvent.getDescription(),
			newCalEvent.getDescription());
		Assert.assertEquals(existingCalEvent.getLocation(),
			newCalEvent.getLocation());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCalEvent.getStartDate()),
			Time.getShortTimestamp(newCalEvent.getStartDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCalEvent.getEndDate()),
			Time.getShortTimestamp(newCalEvent.getEndDate()));
		Assert.assertEquals(existingCalEvent.getDurationHour(),
			newCalEvent.getDurationHour());
		Assert.assertEquals(existingCalEvent.getDurationMinute(),
			newCalEvent.getDurationMinute());
		Assert.assertEquals(existingCalEvent.getAllDay(),
			newCalEvent.getAllDay());
		Assert.assertEquals(existingCalEvent.getTimeZoneSensitive(),
			newCalEvent.getTimeZoneSensitive());
		Assert.assertEquals(existingCalEvent.getType(), newCalEvent.getType());
		Assert.assertEquals(existingCalEvent.getRepeating(),
			newCalEvent.getRepeating());
		Assert.assertEquals(existingCalEvent.getRecurrence(),
			newCalEvent.getRecurrence());
		Assert.assertEquals(existingCalEvent.getRemindBy(),
			newCalEvent.getRemindBy());
		Assert.assertEquals(existingCalEvent.getFirstReminder(),
			newCalEvent.getFirstReminder());
		Assert.assertEquals(existingCalEvent.getSecondReminder(),
			newCalEvent.getSecondReminder());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByNotRemindBy() throws Exception {
		_persistence.countByNotRemindBy(RandomTestUtil.nextInt());

		_persistence.countByNotRemindBy(0);
	}

	@Test
	public void testCountByG_T() throws Exception {
		_persistence.countByG_T(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByG_T(0L, StringPool.NULL);

		_persistence.countByG_T(0L, (String)null);
	}

	@Test
	public void testCountByG_TArrayable() throws Exception {
		_persistence.countByG_T(RandomTestUtil.nextLong(),
			new String[] {
				RandomTestUtil.randomString(), StringPool.BLANK, StringPool.NULL,
				null, null
			});
	}

	@Test
	public void testCountByG_R() throws Exception {
		_persistence.countByG_R(RandomTestUtil.nextLong(),
			RandomTestUtil.randomBoolean());

		_persistence.countByG_R(0L, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_T_R() throws Exception {
		_persistence.countByG_T_R(RandomTestUtil.nextLong(), StringPool.BLANK,
			RandomTestUtil.randomBoolean());

		_persistence.countByG_T_R(0L, StringPool.NULL,
			RandomTestUtil.randomBoolean());

		_persistence.countByG_T_R(0L, (String)null,
			RandomTestUtil.randomBoolean());
	}

	@Test
	public void testCountByG_T_RArrayable() throws Exception {
		_persistence.countByG_T_R(RandomTestUtil.nextLong(),
			new String[] {
				RandomTestUtil.randomString(), StringPool.BLANK, StringPool.NULL,
				null, null
			}, RandomTestUtil.randomBoolean());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		CalEvent existingCalEvent = _persistence.findByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertEquals(existingCalEvent, newCalEvent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEventException");
		}
		catch (NoSuchEventException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CalEvent> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CalEvent", "uuid", true,
			"eventId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"title", true, "description", true, "location", true, "startDate",
			true, "endDate", true, "durationHour", true, "durationMinute",
			true, "allDay", true, "timeZoneSensitive", true, "type", true,
			"repeating", true, "recurrence", true, "remindBy", true,
			"firstReminder", true, "secondReminder", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		CalEvent existingCalEvent = _persistence.fetchByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertEquals(existingCalEvent, newCalEvent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CalEvent missingCalEvent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCalEvent);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CalEvent newCalEvent1 = addCalEvent();
		CalEvent newCalEvent2 = addCalEvent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCalEvent1.getPrimaryKey());
		primaryKeys.add(newCalEvent2.getPrimaryKey());

		Map<Serializable, CalEvent> calEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, calEvents.size());
		Assert.assertEquals(newCalEvent1,
			calEvents.get(newCalEvent1.getPrimaryKey()));
		Assert.assertEquals(newCalEvent2,
			calEvents.get(newCalEvent2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CalEvent> calEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(calEvents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CalEvent newCalEvent = addCalEvent();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCalEvent.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CalEvent> calEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, calEvents.size());
		Assert.assertEquals(newCalEvent,
			calEvents.get(newCalEvent.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CalEvent> calEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(calEvents.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CalEvent newCalEvent = addCalEvent();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCalEvent.getPrimaryKey());

		Map<Serializable, CalEvent> calEvents = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, calEvents.size());
		Assert.assertEquals(newCalEvent,
			calEvents.get(newCalEvent.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CalEventLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					CalEvent calEvent = (CalEvent)object;

					Assert.assertNotNull(calEvent);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CalEvent newCalEvent = addCalEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("eventId",
				newCalEvent.getEventId()));

		List<CalEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CalEvent existingCalEvent = result.get(0);

		Assert.assertEquals(existingCalEvent, newCalEvent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("eventId",
				RandomTestUtil.nextLong()));

		List<CalEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CalEvent newCalEvent = addCalEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("eventId"));

		Object newEventId = newCalEvent.getEventId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("eventId",
				new Object[] { newEventId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEventId = result.get(0);

		Assert.assertEquals(existingEventId, newEventId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("eventId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("eventId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		CalEvent newCalEvent = addCalEvent();

		_persistence.clearCache();

		CalEvent existingCalEvent = _persistence.findByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertTrue(Validator.equals(existingCalEvent.getUuid(),
				ReflectionTestUtil.invoke(existingCalEvent, "getOriginalUuid",
					new Class<?>[0])));
		Assert.assertEquals(existingCalEvent.getGroupId(),
			ReflectionTestUtil.invoke(existingCalEvent, "getOriginalGroupId",
				new Class<?>[0]));
	}

	protected CalEvent addCalEvent() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CalEvent calEvent = _persistence.create(pk);

		calEvent.setUuid(RandomTestUtil.randomString());

		calEvent.setGroupId(RandomTestUtil.nextLong());

		calEvent.setCompanyId(RandomTestUtil.nextLong());

		calEvent.setUserId(RandomTestUtil.nextLong());

		calEvent.setUserName(RandomTestUtil.randomString());

		calEvent.setCreateDate(RandomTestUtil.nextDate());

		calEvent.setModifiedDate(RandomTestUtil.nextDate());

		calEvent.setTitle(RandomTestUtil.randomString());

		calEvent.setDescription(RandomTestUtil.randomString());

		calEvent.setLocation(RandomTestUtil.randomString());

		calEvent.setStartDate(RandomTestUtil.nextDate());

		calEvent.setEndDate(RandomTestUtil.nextDate());

		calEvent.setDurationHour(RandomTestUtil.nextInt());

		calEvent.setDurationMinute(RandomTestUtil.nextInt());

		calEvent.setAllDay(RandomTestUtil.randomBoolean());

		calEvent.setTimeZoneSensitive(RandomTestUtil.randomBoolean());

		calEvent.setType(RandomTestUtil.randomString());

		calEvent.setRepeating(RandomTestUtil.randomBoolean());

		calEvent.setRecurrence(RandomTestUtil.randomString());

		calEvent.setRemindBy(RandomTestUtil.nextInt());

		calEvent.setFirstReminder(RandomTestUtil.nextInt());

		calEvent.setSecondReminder(RandomTestUtil.nextInt());

		_calEvents.add(_persistence.update(calEvent));

		return calEvent;
	}

	private List<CalEvent> _calEvents = new ArrayList<CalEvent>();
	private CalEventPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}