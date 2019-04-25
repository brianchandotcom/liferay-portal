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

package com.liferay.calendar.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingModel;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.calendar.test.util.RecurrenceTestUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarBookingStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testImportedCalendarBookingHasRecurringParentCalendarBookingId()
		throws Exception {

		initExport();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup, TestPropsValues.getUserId());

		Calendar calendar = CalendarTestUtil.addCalendar(
			stagingGroup, serviceContext);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
				TestPropsValues.getUser(), calendar,
				RecurrenceTestUtil.getDailyRecurrence(), serviceContext);

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				TestPropsValues.getUserId(),
				calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), calendarBooking.getTitleMap(),
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(),
				calendarBooking.getStartTime() + Time.DAY * 2,
				calendarBooking.getEndTime() + Time.DAY * 2,
				calendarBooking.isAllDay(), null, false, 0, null, 0, null,
				serviceContext);

		Assert.assertEquals(
			calendarBooking.getCalendarBookingId(),
			calendarBookingInstance.getRecurringCalendarBookingId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, calendarBooking);

		initImport();

		CalendarBooking exportedCalendarBooking =
			(CalendarBooking)readExportedStagedModel(calendarBooking);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedCalendarBooking);

		CalendarBooking importedCalendarBooking =
			(CalendarBooking)getStagedModel(
				exportedCalendarBooking.getUuid(), liveGroup);

		List<CalendarBooking> importedCalendarBookingInstances =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				importedCalendarBooking);

		CalendarBookingModel importedCalendarBookingInstance =
			importedCalendarBookingInstances.get(0);

		Assert.assertNotEquals(
			calendarBooking.getCalendarBookingId(),
			importedCalendarBookingInstance.getRecurringCalendarBookingId());

		Assert.assertEquals(
			importedCalendarBooking.getCalendarBookingId(),
			importedCalendarBookingInstance.getRecurringCalendarBookingId());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Calendar calendar = CalendarTestUtil.getDefaultCalendar(group);

		addDependentStagedModel(
			dependentStagedModelsMap, CalendarResource.class,
			calendar.getCalendarResource());

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			CalendarResource.class.getSimpleName());

		CalendarResource calendarResource =
			(CalendarResource)dependentStagedModels.get(0);

		return CalendarBookingTestUtil.addRegularCalendarBooking(
			TestPropsValues.getUser(), calendarResource.getDefaultCalendar(),
			serviceContext);
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		Calendar calendar = CalendarTestUtil.addCalendar(group, serviceContext);

		CalendarBooking approvedCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
				TestPropsValues.getUser(), calendar, serviceContext);

		stagedModels.add(approvedCalendarBooking);

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group, TestPropsValues.getUserId());

		calendar = CalendarTestUtil.addCalendar(group, serviceContext);

		CalendarBooking pendingCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBookingWithWorkflow(
				TestPropsValues.getUser(), calendar, serviceContext);

		stagedModels.add(pendingCalendarBooking);

		return stagedModels;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return CalendarBookingLocalServiceUtil.
			getCalendarBookingByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CalendarBooking.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

}