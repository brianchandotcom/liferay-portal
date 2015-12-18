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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;
import com.liferay.portlet.bundle.friendlyurlmappertrackerimpl.TestFriendlyURLMapper;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Philip Jones
 */
public class FriendlyURLMapperTrackerImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.friendlyurlmappertrackerimpl"));

	@Test
	public void testGetFriendlyURLMapper() throws Exception {
		Portlet portlet = new PortletImpl();

		portlet.setPortletClass("com.liferay.portlet.StrutsPortlet");
		portlet.setPortletId("FriendlyURLMapperTrackerImplTest");

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			new FriendlyURLMapperTrackerImpl(portlet);

		FriendlyURLMapper friendlyURLMapper =
			friendlyURLMapperTracker.getFriendlyURLMapper();

		Class<?> clazz = friendlyURLMapper.getClass();

		Assert.assertEquals(
			TestFriendlyURLMapper.class.getName(), clazz.getName());
	}

}