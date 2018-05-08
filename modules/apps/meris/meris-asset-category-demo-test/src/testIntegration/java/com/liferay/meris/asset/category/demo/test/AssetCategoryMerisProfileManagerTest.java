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

package com.liferay.meris.asset.category.demo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.meris.MerisProfile;
import com.liferay.meris.MerisProfileManager;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class AssetCategoryMerisProfileManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_user = UserTestUtil.addUser();

		_merisProfileId = String.valueOf(_user.getUserId());
	}

	@Test
	public void testGetMerisProfile() {
		Assert.assertNotNull(
			"Meris profile was not found",
			_merisProfileManager.getMerisProfile(_merisProfileId));
	}

	@Test
	public void testGetMerisProfiles() {
		Comparator<MerisProfile> comparator = Comparator.comparing(
			p -> p.getMerisProfileId());

		List<MerisProfile> merisProfiles =
			_merisProfileManager.getMerisProfiles(0, 1, comparator);

		Assert.assertFalse(
			"No meris profiles were found", merisProfiles.isEmpty());
	}

	@Inject
	private static MerisProfileManager _merisProfileManager;

	private String _merisProfileId;

	@DeleteAfterTestRun
	private User _user;

}