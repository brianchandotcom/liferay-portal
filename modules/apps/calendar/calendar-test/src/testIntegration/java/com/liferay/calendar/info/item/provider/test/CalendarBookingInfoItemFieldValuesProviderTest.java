/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.test.util.TemplateTestUtil;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class CalendarBookingInfoItemFieldValuesProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetInfoItemFieldValues() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Calendar calendar = CalendarTestUtil.addCalendar(group);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(calendar);

		LayoutTestUtil.addTypeContentLayout(group);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		TemplateEntry templateEntry = TemplateTestUtil.addTemplateEntry(
			CalendarBooking.class.getName(), StringPool.BLANK, serviceContext);

		InfoItemFieldValuesProvider<CalendarBooking>
			infoItemFieldValuesProvider =
				(InfoItemFieldValuesProvider<CalendarBooking>)
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						CalendarBooking.class.getName());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(calendarBooking);

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		Assert.assertTrue(
			infoItemIdentifier instanceof ClassPKInfoItemIdentifier);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(
			calendarBooking.getPrimaryKey(),
			classPKInfoItemIdentifier.getClassPK());

		Assert.assertEquals(
			CalendarBooking.class.getName(), infoItemReference.getClassName());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertEquals(
			infoFieldValues.toString(), 11, infoFieldValues.size());

		InfoFieldValue<Object> allDayInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("allDay");

		Assert.assertFalse((Boolean)allDayInfoFieldValue.getValue());

		InfoFieldValue<Object> calendarNameInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("calendarName");

		Assert.assertEquals(
			calendar.getName(LocaleUtil.getDefault()),
			calendarNameInfoFieldValue.getValue(LocaleUtil.getDefault()));

		InfoFieldValue<Object> descriptionInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("description");

		Assert.assertEquals(
			calendarBooking.getDescription(LocaleUtil.getDefault()),
			descriptionInfoFieldValue.getValue(LocaleUtil.getDefault()));

		InfoFieldValue<Object> endDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("endDate");

		Assert.assertEquals(
			new Date(calendarBooking.getEndTime()),
			endDateInfoFieldValue.getValue());

		InfoFieldValue<Object> eventURLInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("eventURL");

		Assert.assertNotNull(eventURLInfoFieldValue.getValue());

		InfoFieldValue<Object> invitationsInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("invitations");

		Assert.assertEquals(
			"Accepted (1), Declined (0), Pending (0), Maybe (0)",
			invitationsInfoFieldValue.getValue());

		InfoFieldValue<Object> locationInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("location");

		Assert.assertEquals(
			calendarBooking.getLocation(), locationInfoFieldValue.getValue());

		InfoFieldValue<Object> repetitionsInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("repetitions");

		Assert.assertEquals("False", repetitionsInfoFieldValue.getValue());

		InfoFieldValue<Object> startDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("startDate");

		Assert.assertEquals(
			new Date(calendarBooking.getStartTime()),
			startDateInfoFieldValue.getValue());

		InfoFieldValue<Object> titleInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("title");

		Assert.assertEquals(
			calendarBooking.getTitle(LocaleUtil.getDefault()),
			titleInfoFieldValue.getValue(LocaleUtil.getDefault()));

		InfoFieldValue<Object> templateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
					templateEntry.getTemplateEntryId());

		Assert.assertNotNull(templateInfoFieldValue);
	}

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}