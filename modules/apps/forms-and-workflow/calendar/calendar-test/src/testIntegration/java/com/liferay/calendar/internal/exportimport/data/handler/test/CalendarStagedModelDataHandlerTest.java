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
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.lar.test.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testImportedCalendarShouldKeepTheName() throws Exception {
		initExport();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup, TestPropsValues.getUserId());

		Calendar calendar = CalendarTestUtil.addCalendar(
			stagingGroup, serviceContext);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, calendar);

		initImport();

		Calendar exportedCalendar = (Calendar)readExportedStagedModel(calendar);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedCalendar);

		Calendar importedCalendar = (Calendar)getStagedModel(
			exportedCalendar.getUuid(), liveGroup);

		Assert.assertEquals(
			exportedCalendar.getName(), importedCalendar.getName());
	}

	@Test
	public void testImportedDefaultCalendarShouldChangeTheName()
		throws Exception {

		initExport();

		Calendar calendar = CalendarTestUtil.getDefaultCalendar(stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, calendar);

		initImport();

		Calendar exportedCalendar = (Calendar)readExportedStagedModel(calendar);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedCalendar);

		Calendar importedCalendar = (Calendar)getStagedModel(
			exportedCalendar.getUuid(), liveGroup);

		Assert.assertNotEquals(
			exportedCalendar.getName(), importedCalendar.getName());
	}

	@Override
	protected Map<String, List<StagedModel>> addDependentStagedModelsMap(
			Group group)
		throws Exception {

		Map<String, List<StagedModel>> dependentStagedModelsMap =
			new HashMap<>();

		Calendar calendar = CalendarTestUtil.getDefaultCalendar(group);

		addDependentStagedModel(
			dependentStagedModelsMap, Calendar.class, calendar);

		return dependentStagedModelsMap;
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return CalendarTestUtil.addCalendar(group);
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		CalendarLocalServiceUtil.deleteCalendar((Calendar)stagedModel);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return CalendarLocalServiceUtil.getCalendarByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Calendar.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return false;
	}

}