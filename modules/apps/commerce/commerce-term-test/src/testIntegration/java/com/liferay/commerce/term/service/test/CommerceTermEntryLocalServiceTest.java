/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Andrea Sbarra
 */
@RunWith(Arquillian.class)
public class CommerceTermEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_user = UserTestUtil.addUser(_company);
	}

	@Before
	public void setUp() throws Exception {
		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_company.getGroupId(), _user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		List<CommerceTermEntry> commerceTermEntries =
			_commerceTermEntryLocalService.getCommerceTermEntries(
				_company.getCompanyId(), "payment-terms");

		for (CommerceTermEntry commerceTermEntry : commerceTermEntries) {
			_commerceTermEntryLocalService.deleteCommerceTermEntry(
				commerceTermEntry);
		}
	}

	@Test
	public void testAvoidMaliciousCodeInCommerceTermEntryFields()
		throws Exception {

		frutillaRule.scenario(
			"Add Commerce term entry with clean fields"
		).given(
			"I add a Commerce Term Entry"
		).when(
			"I try to set malicious value in fields"
		).then(
			"The value is escaped."
		);

		String testString =
			"'\"></option><img src=x onerror=alert(document.location)>";

		long time = System.currentTimeMillis();

		Date displayDate = new Date(time - Time.HOUR);
		Date expirationDate = new Date(time + Time.DAY);

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			_user.getTimeZone());

		displayCal.setTime(displayDate);

		int displayDateMonth = displayCal.get(Calendar.MONTH);
		int displayDateDay = displayCal.get(Calendar.DATE);
		int displayDateYear = displayCal.get(Calendar.YEAR);
		int displayDateHour = displayCal.get(Calendar.HOUR);
		int displayDateMinute = displayCal.get(Calendar.MINUTE);

		if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			_user.getTimeZone());

		expirationCalendar.setTime(expirationDate);

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DATE);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);

		if (expirationCalendar.get(Calendar.AM_PM) == Calendar.PM) {
			expirationDateHour += 12;
		}

		CommerceTermEntry commerceTermEntry =
			_commerceTermEntryLocalService.addCommerceTermEntry(
				"ERC", _user.getUserId(), true,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), testString
				).build(),
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, true,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), testString
				).build(),
				"name", 0.0, "payment-terms", null, _serviceContext);

		String expectedString = "'&quot;&gt;<img src=\"x\">";

		Assert.assertEquals(
			"Expected label", expectedString,
			commerceTermEntry.getLabel(
				LanguageUtil.getLanguageId(LocaleUtil.getDefault())));
		Assert.assertEquals(
			"Expected description", expectedString,
			commerceTermEntry.getDescription(
				LanguageUtil.getLanguageId(LocaleUtil.getDefault())));
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private static Company _company;
	private static User _user;

	@Inject
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

	private ServiceContext _serviceContext;

}