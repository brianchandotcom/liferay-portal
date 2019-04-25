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
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarResourceTestUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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
public class CalendarResourceStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	@Test
	public void testCleanStagedModelDataHandler() throws Exception {
	}

	@Test
	public void testImportedCalendarResourceShouldKeepTheName()
		throws Exception {

		initExport();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup, TestPropsValues.getUserId());

		CalendarResource calendarResource =
			CalendarResourceTestUtil.addCalendarResource(
				stagingGroup, serviceContext);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, calendarResource);

		initImport();

		CalendarResource exportedCalendarResource =
			(CalendarResource)readExportedStagedModel(calendarResource);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedCalendarResource);

		CalendarResource importedCalendarResource =
			(CalendarResource)getStagedModel(
				exportedCalendarResource.getUuid(), liveGroup);

		Assert.assertEquals(
			exportedCalendarResource.getName(),
			importedCalendarResource.getName());
	}

	@Test
	public void testImportedGroupCalendarResourceShouldChangeTheName()
		throws Exception {

		initExport();

		CalendarResource calendarResource =
			CalendarResourceTestUtil.getCalendarResource(stagingGroup);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, calendarResource);

		initImport();

		CalendarResource exportedCalendarResource =
			(CalendarResource)readExportedStagedModel(calendarResource);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedCalendarResource);

		CalendarResource importedCalendarResource =
			(CalendarResource)getStagedModel(
				exportedCalendarResource.getUuid(), liveGroup);

		Assert.assertNotEquals(
			exportedCalendarResource.getName(),
			importedCalendarResource.getName());
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		return CalendarResourceTestUtil.addCalendarResource(group);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return CalendarResourceLocalServiceUtil.
			getCalendarResourceByUuidAndGroupId(uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return CalendarResource.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return false;
	}

}