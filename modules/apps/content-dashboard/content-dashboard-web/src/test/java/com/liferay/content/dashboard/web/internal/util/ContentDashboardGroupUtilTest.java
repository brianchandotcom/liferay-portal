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

package com.liferay.content.dashboard.web.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Cristina González
 */
public class ContentDashboardGroupUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetGroupName() throws PortalException {
		Locale locale = LocaleUtil.US;
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDescriptiveName(locale)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Assert.assertEquals(
			group.getDescriptiveName(locale),
			ContentDashboardGroupUtil.getGroupName(group, locale));
	}

	@Test
	public void testGetGroupNameWithDescriptiveNameException()
		throws PortalException {

		Locale locale = LocaleUtil.US;
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getDescriptiveName(locale)
		).thenThrow(
			new PortalException()
		);

		Mockito.when(
			group.getName(locale)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Assert.assertEquals(
			group.getName(locale),
			ContentDashboardGroupUtil.getGroupName(group, locale));
	}

	@Test
	public void testGetGroupNameWithoutDescriptiveName()
		throws PortalException {

		Locale locale = LocaleUtil.US;
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getName(locale)
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Assert.assertEquals(
			group.getName(locale),
			ContentDashboardGroupUtil.getGroupName(group, locale));
	}

}