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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portlet.calendar.model.CalEvent;

import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners = {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class CalEventServiceDataTestUtil {

	public static CalEvent addCalEvent() throws Exception {
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

		_persistence.update(calEvent, false);

		return calEvent;
	}

	private static CalEventPersistence _persistence =
		(CalEventPersistence)PortalBeanLocatorUtil.locate(
			CalEventPersistence.class.getName());

}