/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.web.internal.admin.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.model.Entry;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalService;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Feliphe Marinho
 */
@RunWith(Arquillian.class)
public class AddSchedulerMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		try (InputStream inputStream = clazz.getResourceAsStream(
				"dependencies/reports_admin_template_sample.jrxml")) {

			_definition = _definitionLocalService.addDefinition(
				TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
				HashMapBuilder.put(
					LocaleUtil.US, RandomTestUtil.randomString()
				).build(),
				null, 0,
				_jsonFactory.createJSONArray(
				).put(
					JSONUtil.put(
						"key", "dateReportDefinitionParameter1"
					).put(
						"type", "date"
					).put(
						"value", "2023-11-27"
					)
				).put(
					JSONUtil.put(
						"key", "dateReportDefinitionParameter2"
					).put(
						"type", "date"
					).put(
						"value", "2023-11-27"
					)
				).put(
					JSONUtil.put(
						"key", "dateReportDefinitionParameter3"
					).put(
						"type", "date"
					).put(
						"value", "2023-11-27"
					)
				).put(
					JSONUtil.put(
						"key", "textReportDefinitionParameter1"
					).put(
						"type", "text"
					).put(
						"value", "textReportDefinitionParameter1Value"
					)
				).put(
					JSONUtil.put(
						"key", "textReportDefinitionParameter2"
					).put(
						"type", "text"
					).put(
						"value", " "
					)
				).toString(),
				"reports_admin_template_sample", inputStream,
				ServiceContextTestUtil.getServiceContext());

			_setUpMockLiferayPortletActionRequest();
		}
	}

	@Test
	public void testDoProcessActionReportDefinitionParameterValues()
		throws Exception {

		_addDateParameters("dateReportDefinitionParameter1", "2023-11-27");
		_mockLiferayPortletActionRequest.addParameter(
			"parameterValuetextReportDefinitionParameter1",
			"textReportDefinitionParameter1Value");
		_mockLiferayPortletActionRequest.addParameter(
			"parameterValuetextReportDefinitionParameter2", " ");
		_mockLiferayPortletActionRequest.addParameter(
			"useVariabledateReportDefinitionParameter2", "endDate");
		_mockLiferayPortletActionRequest.addParameter(
			"useVariabledateReportDefinitionParameter3", "startDate");

		_mvcActionCommand.processAction(
			_mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		List<Entry> entries = _entryLocalService.getEntries(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(entries.toString(), 1, entries.size());

		Entry entry = entries.get(0);

		_entryLocalService.unscheduleEntry(entry.getEntryId());

		Assert.assertEquals(
			_jsonFactory.createJSONArray(
			).put(
				JSONUtil.put(
					"key", "dateReportDefinitionParameter1"
				).put(
					"value", "2023-11-27"
				)
			).put(
				JSONUtil.put(
					"key", "dateReportDefinitionParameter2"
				).put(
					"value", _SCHEDULER_END_DATE_STRING
				)
			).put(
				JSONUtil.put(
					"key", "dateReportDefinitionParameter3"
				).put(
					"value", _SCHEDULER_START_DATE_STRING
				)
			).put(
				JSONUtil.put(
					"key", "textReportDefinitionParameter1"
				).put(
					"value", "textReportDefinitionParameter1Value"
				)
			).put(
				JSONUtil.put(
					"key", "textReportDefinitionParameter2"
				).put(
					"value", StringPool.BLANK
				)
			).toString(),
			entry.getReportParameters());
	}

	private void _addDateParameters(String parameterNamePrefix, String value)
		throws Exception {

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(_dateFormat.parse(value));

		_mockLiferayPortletActionRequest.addParameter(
			parameterNamePrefix + "AmPm", String.valueOf(0));
		_mockLiferayPortletActionRequest.addParameter(
			parameterNamePrefix + "Day",
			String.valueOf(calendar.get(Calendar.DATE)));
		_mockLiferayPortletActionRequest.addParameter(
			parameterNamePrefix + "Hour",
			String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
		_mockLiferayPortletActionRequest.addParameter(
			parameterNamePrefix + "Minute",
			String.valueOf(calendar.get(Calendar.MINUTE)));
		_mockLiferayPortletActionRequest.addParameter(
			parameterNamePrefix + "Month",
			String.valueOf(calendar.get(Calendar.MONTH)));
		_mockLiferayPortletActionRequest.addParameter(
			parameterNamePrefix + "Year",
			String.valueOf(calendar.get(Calendar.YEAR)));
	}

	private void _setUpMockLiferayPortletActionRequest() throws Exception {
		_mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		_mockLiferayPortletActionRequest.addParameter(
			"definitionId", String.valueOf(_definition.getDefinitionId()));
		_mockLiferayPortletActionRequest.addParameter(
			"endDateType", String.valueOf(_END_DATE_TYPE_END_BY));
		_mockLiferayPortletActionRequest.addParameter("format", "pdf");
		_mockLiferayPortletActionRequest.addParameter(
			"recurrenceType", String.valueOf(Recurrence.NO_RECURRENCE));
		_mockLiferayPortletActionRequest.addParameter(
			"reportName", "reports_admin_template_sample");

		_addDateParameters("schedulerEndDate", _SCHEDULER_END_DATE_STRING);
		_addDateParameters("schedulerStartDate", _SCHEDULER_START_DATE_STRING);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setTimeZone(TimeZoneUtil.getDefault());
		themeDisplay.setUser(TestPropsValues.getUser());

		_mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
	}

	private static final int _END_DATE_TYPE_END_BY = 1;

	private static final String _SCHEDULER_END_DATE_STRING = "2023-11-27";

	private static final String _SCHEDULER_START_DATE_STRING = "1999-05-18";

	@Inject
	private CompanyLocalService _companyLocalService;

	private final DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd");
	private Definition _definition;

	@Inject
	private DefinitionLocalService _definitionLocalService;

	@Inject
	private EntryLocalService _entryLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	private MockLiferayPortletActionRequest _mockLiferayPortletActionRequest;

	@Inject(filter = "mvc.command.name=/reports_admin/add_scheduler")
	private MVCActionCommand _mvcActionCommand;

}