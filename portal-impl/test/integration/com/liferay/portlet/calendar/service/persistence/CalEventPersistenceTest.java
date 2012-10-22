/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.calendar.NoSuchEventException;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.impl.CalEventModelImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class CalEventPersistenceTest {
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
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

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
		long pk = ServiceTestUtil.nextLong();

		CalEvent newCalEvent = _persistence.create(pk);

		newCalEvent.setUuid(ServiceTestUtil.randomString());

		newCalEvent.setGroupId(ServiceTestUtil.nextLong());

		newCalEvent.setCompanyId(ServiceTestUtil.nextLong());

		newCalEvent.setUserId(ServiceTestUtil.nextLong());

		newCalEvent.setUserName(ServiceTestUtil.randomString());

		newCalEvent.setCreateDate(ServiceTestUtil.nextDate());

		newCalEvent.setModifiedDate(ServiceTestUtil.nextDate());

		newCalEvent.setTitle(ServiceTestUtil.randomString());

		newCalEvent.setDescription(ServiceTestUtil.randomString());

		newCalEvent.setLocation(ServiceTestUtil.randomString());

		newCalEvent.setStartDate(ServiceTestUtil.nextDate());

		newCalEvent.setEndDate(ServiceTestUtil.nextDate());

		newCalEvent.setDurationHour(ServiceTestUtil.nextInt());

		newCalEvent.setDurationMinute(ServiceTestUtil.nextInt());

		newCalEvent.setAllDay(ServiceTestUtil.randomBoolean());

		newCalEvent.setTimeZoneSensitive(ServiceTestUtil.randomBoolean());

		newCalEvent.setType(ServiceTestUtil.randomString());

		newCalEvent.setRepeating(ServiceTestUtil.randomBoolean());

		newCalEvent.setRecurrence(ServiceTestUtil.randomString());

		newCalEvent.setRemindBy(ServiceTestUtil.nextInt());

		newCalEvent.setFirstReminder(ServiceTestUtil.nextInt());

		newCalEvent.setSecondReminder(ServiceTestUtil.nextInt());

		_persistence.update(newCalEvent);

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
	public void testFindByPrimaryKeyExisting() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		CalEvent existingCalEvent = _persistence.findByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertEquals(existingCalEvent, newCalEvent);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchEventException");
		}
		catch (NoSuchEventException nsee) {
		}
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CalEvent newCalEvent = addCalEvent();

		CalEvent existingCalEvent = _persistence.fetchByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertEquals(existingCalEvent, newCalEvent);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		CalEvent missingCalEvent = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCalEvent);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CalEvent newCalEvent = addCalEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				CalEvent.class.getClassLoader());

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
				CalEvent.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("eventId",
				ServiceTestUtil.nextLong()));

		List<CalEvent> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CalEvent newCalEvent = addCalEvent();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CalEvent.class,
				CalEvent.class.getClassLoader());

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
				CalEvent.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("eventId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("eventId",
				new Object[] { ServiceTestUtil.nextLong() }));

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

		CalEventModelImpl existingCalEventModelImpl = (CalEventModelImpl)_persistence.findByPrimaryKey(newCalEvent.getPrimaryKey());

		Assert.assertTrue(Validator.equals(
				existingCalEventModelImpl.getUuid(),
				existingCalEventModelImpl.getOriginalUuid()));
		Assert.assertEquals(existingCalEventModelImpl.getGroupId(),
			existingCalEventModelImpl.getOriginalGroupId());
	}

	protected CalEvent addCalEvent() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		CalEvent calEvent = _persistence.create(pk);

		calEvent.setUuid(ServiceTestUtil.randomString());

		calEvent.setGroupId(ServiceTestUtil.nextLong());

		calEvent.setCompanyId(ServiceTestUtil.nextLong());

		calEvent.setUserId(ServiceTestUtil.nextLong());

		calEvent.setUserName(ServiceTestUtil.randomString());

		calEvent.setCreateDate(ServiceTestUtil.nextDate());

		calEvent.setModifiedDate(ServiceTestUtil.nextDate());

		calEvent.setTitle(ServiceTestUtil.randomString());

		calEvent.setDescription(ServiceTestUtil.randomString());

		calEvent.setLocation(ServiceTestUtil.randomString());

		calEvent.setStartDate(ServiceTestUtil.nextDate());

		calEvent.setEndDate(ServiceTestUtil.nextDate());

		calEvent.setDurationHour(ServiceTestUtil.nextInt());

		calEvent.setDurationMinute(ServiceTestUtil.nextInt());

		calEvent.setAllDay(ServiceTestUtil.randomBoolean());

		calEvent.setTimeZoneSensitive(ServiceTestUtil.randomBoolean());

		calEvent.setType(ServiceTestUtil.randomString());

		calEvent.setRepeating(ServiceTestUtil.randomBoolean());

		calEvent.setRecurrence(ServiceTestUtil.randomString());

		calEvent.setRemindBy(ServiceTestUtil.nextInt());

		calEvent.setFirstReminder(ServiceTestUtil.nextInt());

		calEvent.setSecondReminder(ServiceTestUtil.nextInt());

		_persistence.update(calEvent);

		return calEvent;
	}

	private static Log _log = LogFactoryUtil.getLog(CalEventPersistenceTest.class);
	private CalEventPersistence _persistence = (CalEventPersistence)PortalBeanLocatorUtil.locate(CalEventPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}