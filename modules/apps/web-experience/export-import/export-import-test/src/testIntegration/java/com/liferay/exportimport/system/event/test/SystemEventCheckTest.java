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

package com.liferay.exportimport.system.event.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.service.SystemEventLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class SystemEventCheckTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	public void doTestSystemEventCheck() throws Exception {
		_group = GroupTestUtil.addGroup();

		Calendar calendar = Calendar.getInstance();

		calendar.add(
			Calendar.HOUR, -PropsValues.STAGING_SYSTEM_EVENT_MAX_AGE - 1);

		Date createDate = calendar.getTime();

		List<SystemEvent> expiredSystemEvents = new LinkedList<>();

		for (int i = 0; i < 5; i++) {
			SystemEvent systemEvent = addSystemEvent();

			systemEvent.setCreateDate(createDate);

			SystemEventLocalServiceUtil.updateSystemEvent(systemEvent);

			expiredSystemEvents.add(systemEvent);
		}

		validate(expiredSystemEvents, false);

		List<SystemEvent> nonexpiredSystemEvents = new LinkedList<>();

		for (int i = 0; i < 7; i++) {
			nonexpiredSystemEvents.add(addSystemEvent());
		}

		validate(nonexpiredSystemEvents, false);

		SystemEventLocalServiceUtil.checkSystemEvents();

		if (PropsValues.STAGING_SYSTEM_EVENT_MAX_AGE <= 0) {
			validate(expiredSystemEvents, false);
		}
		else {
			validate(expiredSystemEvents, true);
		}

		validate(nonexpiredSystemEvents, false);
	}

	public void setPortalProperty(String propertyName, Object value)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, propertyName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

	@Test
	public void testSystemEventCheckWithMaxAge0() throws Exception {
		int stagingSystemEventMaxAge = PropsValues.STAGING_SYSTEM_EVENT_MAX_AGE;

		setPortalProperty("STAGING_SYSTEM_EVENT_MAX_AGE", 0);

		doTestSystemEventCheck();

		setPortalProperty(
			"STAGING_SYSTEM_EVENT_MAX_AGE", stagingSystemEventMaxAge);
	}

	@Test
	public void testSystemEventCheckWithMaxAge1() throws Exception {
		int stagingSystemEventMaxAge = PropsValues.STAGING_SYSTEM_EVENT_MAX_AGE;

		setPortalProperty("STAGING_SYSTEM_EVENT_MAX_AGE", 1);

		doTestSystemEventCheck();

		setPortalProperty(
			"STAGING_SYSTEM_EVENT_MAX_AGE", stagingSystemEventMaxAge);
	}

	protected SystemEvent addSystemEvent() throws Exception {
		return SystemEventLocalServiceUtil.addSystemEvent(
			TestPropsValues.getUserId(), _group.getGroupId(),
			Group.class.getName(), RandomTestUtil.nextLong(),
			PortalUUIDUtil.generate(), StringPool.BLANK,
			SystemEventConstants.TYPE_DELETE, StringPool.BLANK);
	}

	protected void validate(List<SystemEvent> systemEvents, boolean missing) {
		for (SystemEvent systemEvent : systemEvents) {
			SystemEvent actualSystemEvent =
				SystemEventLocalServiceUtil.fetchSystemEvent(
					_group.getGroupId(), PortalUtil.getClassNameId(Group.class),
					systemEvent.getClassPK(), SystemEventConstants.TYPE_DELETE);

			if (missing) {
				Assert.assertEquals(null, actualSystemEvent);
			}
			else {
				Assert.assertEquals(systemEvent, actualSystemEvent);
			}
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}