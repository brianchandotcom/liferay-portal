/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalService;
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
import com.liferay.portal.test.rule.Inject;
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

		return _calendarResourceLocalService.
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

	@Inject
	private CalendarResourceLocalService _calendarResourceLocalService;

}