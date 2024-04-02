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
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.DisplayPageInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_calendar = CalendarTestUtil.addCalendar(_group);

		_calendarBooking = CalendarBookingTestUtil.addRegularCalendarBooking(
			_calendar);

		LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	public void testGetInfoForm() throws Exception {
		InfoItemFormProvider<CalendarBooking> infoItemFormProvider =
			(InfoItemFormProvider<CalendarBooking>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoItemFormProvider.class,
					CalendarBooking.class.getName());

		InfoForm infoForm = infoItemFormProvider.getInfoForm(_calendarBooking);

		List<InfoField<?>> infoFields = infoForm.getAllInfoFields();

		infoFields.sort(
			Comparator.comparing(
				InfoField::getName, String::compareToIgnoreCase));

		Assert.assertEquals(infoFields.toString(), 11, infoFields.size());

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

	@Test
	public void testGetInfoItemFieldValues() throws Exception {
		InfoItemFieldValuesProvider<CalendarBooking>
			infoItemFieldValuesProvider =
				(InfoItemFieldValuesProvider<CalendarBooking>)
					_infoItemServiceRegistry.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						CalendarBooking.class.getName());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(
				_calendarBooking);

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
			_calendarBooking.getPrimaryKey(),
			classPKInfoItemIdentifier.getClassPK());

		Assert.assertEquals(
			CalendarBooking.class.getName(), infoItemReference.getClassName());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertEquals(
			infoFieldValues.toString(), 10, infoFieldValues.size());

		InfoFieldValue<Object> allDayInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("allDay");

		Assert.assertFalse((Boolean)allDayInfoFieldValue.getValue());

		InfoFieldValue<Object> calendarNameInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("calendarName");

		Assert.assertEquals(
			_calendar.getName(LocaleUtil.getDefault()),
			calendarNameInfoFieldValue.getValue(LocaleUtil.getDefault()));

		InfoFieldValue<Object> descriptionInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("description");

		Assert.assertEquals(
			_calendarBooking.getDescription(LocaleUtil.getDefault()),
			descriptionInfoFieldValue.getValue(LocaleUtil.getDefault()));

		InfoFieldValue<Object> endDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("endDate");

		Assert.assertEquals(
			new Date(_calendarBooking.getEndTime()),
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
			_calendarBooking.getLocation(), locationInfoFieldValue.getValue());

		InfoFieldValue<Object> repetitionsInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("repetitions");

		Assert.assertEquals("False", repetitionsInfoFieldValue.getValue());

		InfoFieldValue<Object> startDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("startDate");

		Assert.assertEquals(
			new Date(_calendarBooking.getStartTime()),
			startDateInfoFieldValue.getValue());

		InfoFieldValue<Object> titleInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("title");

		Assert.assertEquals(
			_calendarBooking.getTitle(LocaleUtil.getDefault()),
			titleInfoFieldValue.getValue(LocaleUtil.getDefault()));
	}

	@DeleteAfterTestRun
	private Calendar _calendar;

	@DeleteAfterTestRun
	private CalendarBooking _calendarBooking;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}