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

package com.liferay.google.places.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.PrefsPropsUtil;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rodrigo Paulino
 */
@PrepareForTest(PrefsPropsUtil.class)
@RunWith(PowerMockRunner.class)
public class GooglePlacesUtilTest extends PowerMockito {

	@Before
	public void setUp() {
		_setUpPrefsPropsUtil();
	}

	@Test
	public void testGetCompanyGooglePlacesAPIKey() {
		Assert.assertEquals(
			_COMPANY_GOOGLE_PLACES_API_KEY,
			GooglePlacesUtil.getGooglePlacesAPIKey(0));
	}

	@Test
	public void testGetGroupGooglePlacesAPIKey1() {
		Assert.assertEquals(
			_COMPANY_GOOGLE_PLACES_API_KEY,
			GooglePlacesUtil.getGooglePlacesAPIKey(
				0, 0, _mockGroupLocalService(false, false)));
	}

	@Test
	public void testGetGroupGooglePlacesAPIKey2() {
		Assert.assertEquals(
			_GROUP_GOOGLE_PLACES_API_KEY,
			GooglePlacesUtil.getGooglePlacesAPIKey(
				0, 0, _mockGroupLocalService(true, false)));
	}

	@Test
	public void testGetGroupGooglePlacesAPIKey3() {
		Assert.assertEquals(
			_LIVE_GROUP_GOOGLE_PLACES_API_KEY,
			GooglePlacesUtil.getGooglePlacesAPIKey(
				0, 0, _mockGroupLocalService(true, true)));
	}

	private Group _mockGroup(boolean stagingGroup, String googlePlacesAPIKey) {
		Group group = mock(Group.class);

		if (!_LIVE_GROUP_GOOGLE_PLACES_API_KEY.equals(googlePlacesAPIKey)) {
			when(
				group.isStagingGroup()
			).thenReturn(
				stagingGroup
			);
		}

		if (stagingGroup) {
			Group liveGroup = _mockGroup(
				false, _LIVE_GROUP_GOOGLE_PLACES_API_KEY);

			when(
				group.getLiveGroup()
			).thenReturn(
				liveGroup
			);
		}
		else {
			when(
				group.getTypeSettingsProperty(Matchers.anyString())
			).thenReturn(
				googlePlacesAPIKey
			);
		}

		return group;
	}

	private GroupLocalService _mockGroupLocalService(
		boolean mockGroup, boolean stagingGroup) {

		GroupLocalService groupLocalService = mock(GroupLocalService.class);

		if (mockGroup) {
			Group group = _mockGroup(
				stagingGroup, _GROUP_GOOGLE_PLACES_API_KEY);

			when(
				groupLocalService.fetchGroup(Matchers.anyLong())
			).thenReturn(
				group
			);
		}

		return groupLocalService;
	}

	private PortletPreferences _mockPortletPreferences() {
		PortletPreferences portletPreferences = mock(PortletPreferences.class);

		when(
			portletPreferences.getValue(
				Matchers.anyString(), Matchers.anyString())
		).thenReturn(
			_COMPANY_GOOGLE_PLACES_API_KEY
		);

		return portletPreferences;
	}

	private void _setUpPrefsPropsUtil() {
		mockStatic(PrefsPropsUtil.class);

		PortletPreferences portletPreferences = _mockPortletPreferences();

		when(
			PrefsPropsUtil.getPreferences(Matchers.anyLong())
		).thenReturn(
			portletPreferences
		);
	}

	private static final String _COMPANY_GOOGLE_PLACES_API_KEY =
		"companyGooglePlacesAPIKey";

	private static final String _GROUP_GOOGLE_PLACES_API_KEY =
		"groupGooglePlacesAPIKey";

	private static final String _LIVE_GROUP_GOOGLE_PLACES_API_KEY =
		"liveGroupGooglePlacesAPIKey";

}