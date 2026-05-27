/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.DisplayPageInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.test.util.TemplateTestUtil;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class CalendarBookingInfoItemFormProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetInfoForm() throws Exception {
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

		InfoItemFormProvider<CalendarBooking> infoItemFormProvider =
			(InfoItemFormProvider<CalendarBooking>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFormProvider.class,
					CalendarBooking.class.getName());

		InfoForm infoForm = infoItemFormProvider.getInfoForm(calendarBooking);

		List<InfoField<?>> infoFields = infoForm.getAllInfoFields();

		infoFields.sort(
			Comparator.comparing(
				InfoField::getName, String::compareToIgnoreCase));

		Assert.assertEquals(infoFields.toString(), 12, infoFields.size());

		Iterator<InfoField<?>> iterator = infoFields.iterator();

		InfoField infoField = iterator.next();

		Assert.assertEquals(
			BooleanInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("allDay", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("calendarName", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertTrue(
			infoField.getInfoFieldType() instanceof HTMLInfoFieldType);
		Assert.assertEquals(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				templateEntry.getTemplateEntryId(),
			infoField.getName());

		infoField = iterator.next();

		Assert.assertEquals(
			HTMLInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("description", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			DisplayPageInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("displayPageURL", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			DateInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("endDate", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			URLInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("eventURL", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("invitations", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("location", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("repetitions", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			DateInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("startDate", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());

		infoField = iterator.next();

		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());
		Assert.assertEquals("title", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
	}

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}