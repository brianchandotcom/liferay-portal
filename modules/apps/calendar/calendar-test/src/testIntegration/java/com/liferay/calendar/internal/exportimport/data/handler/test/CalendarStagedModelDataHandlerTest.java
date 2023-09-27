/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
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
public class CalendarStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testImportedCalendarShouldKeepTheName() throws Exception {
		initExport();

		Calendar calendar = CalendarTestUtil.addCalendarResourceCalendar(
			stagingGroup);

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

		CalendarResource exportedCalendarResource =
			exportedCalendar.getCalendarResource();

		CalendarResource importedCalendarResource =
			importedCalendar.getCalendarResource();

		Assert.assertEquals(
			exportedCalendarResource.getName(),
			importedCalendarResource.getName());
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

		CalendarResource exportedCalendarResource =
			exportedCalendar.getCalendarResource();

		CalendarResource importedCalendarResource =
			importedCalendar.getCalendarResource();

		Assert.assertNotEquals(
			exportedCalendarResource.getName(),
			importedCalendarResource.getName());
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

		_calendarLocalService.deleteCalendar((Calendar)stagedModel);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _calendarLocalService.getCalendarByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return Calendar.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return false;
	}

	@Inject
	private CalendarLocalService _calendarLocalService;

}