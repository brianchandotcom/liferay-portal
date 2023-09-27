/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.test.util.CalendarNotificationTemplateTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CalendarNotificationTemplateTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_calendar = CalendarTestUtil.addCalendar(group);
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		User user = TestPropsValues.getUser();

		return CalendarNotificationTemplateTestUtil.
			addCalendarNotificationTemplate(
				_calendar, NotificationTemplateType.INVITE,
				user.getEmailAddress(), user.getFullName(),
				CalendarNotificationTemplateTableReferenceDefinitionTest.class.
					getSimpleName(),
				CalendarNotificationTemplateTableReferenceDefinitionTest.class.
					getName());
	}

	private Calendar _calendar;

}