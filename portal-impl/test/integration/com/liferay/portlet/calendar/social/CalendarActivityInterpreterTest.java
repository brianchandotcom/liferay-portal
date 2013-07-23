/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.calendar.social;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.model.CalEventConstants;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.util.CalendarTestUtil;
import com.liferay.portlet.social.BaseSocialActivityInterpreterTestCase;
import com.liferay.portlet.social.model.SocialActivityInterpreter;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CalendarActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@Override
	protected void addActivities() throws Exception {
		_calEvent = CalendarTestUtil.addEvent(group.getGroupId());
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new CalendarActivityInterpreter();
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			CalendarActivityKeys.ADD_EVENT, CalendarActivityKeys.UPDATE_EVENT};
	}

	@Override
	protected void moveEntitiesToTrash() throws Exception {
	}

	@Override
	protected void renameEntities() throws Exception {
		int startDateDay = 1;
		int startDateMonth = 1;
		int startDateYear = 2012;
		int startDateHour = 12;
		int startDateMinute = 0;
		int durationHour = 1;
		int durationMinute = 0;

		CalEventLocalServiceUtil.updateEvent(
			_calEvent.getUserId(), _calEvent.getEventId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), startDateMonth, startDateDay,
			startDateYear, startDateHour, startDateMinute, durationHour,
			durationMinute, false, false, ServiceTestUtil.randomString(), false,
			null, CalEventConstants.REMIND_BY_NONE, 0, 0, serviceContext);
	}

	@Override
	protected void restoreEntitiesFromTrash() throws Exception {
	}

	@Override
	protected boolean supportsTrash() {
		return false;
	}

	private CalEvent _calEvent;

}