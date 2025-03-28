/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.filter.display.context;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.web.internal.custom.filter.configuration.CustomFilterPortletInstanceConfiguration;
import com.liferay.portal.search.web.internal.custom.filter.display.context.builder.CustomFilterDisplayContextBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class CustomFilterDisplayContextBuilderTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDisplayStyleGroup() throws Exception {
		setUpGroupLocalServiceUtil(_getGroup());
		setUpPortletDisplayStyleGroupExternalReferenceCode(null);

		_assertDisplayContext(_getGroup());

		_groupLocalServiceUtilMockedStatic.verifyNoInteractions();
	}

	@Test
	public void testGetDisplayStyleGroupWithConfiguration() throws Exception {
		Group group = _getGroup();

		setUpGroupLocalServiceUtil(group);
		setUpPortletDisplayStyleGroupExternalReferenceCode(
			group.getExternalReferenceCode());

		_assertDisplayContext(group);

		_groupLocalServiceUtilMockedStatic.verify(
			() -> GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				group.getExternalReferenceCode(), 0L),
			Mockito.times(1));
	}

	protected static PortletDisplay getPortletDisplay() {
		PortletDisplay portletDisplay = Mockito.mock(PortletDisplay.class);

		Mockito.when(
			portletDisplay.getPortletResource()
		).thenReturn(
			"test"
		);

		return portletDisplay;
	}

	protected static ThemeDisplay getThemeDisplay() {
		return getThemeDisplay(null);
	}

	protected static ThemeDisplay getThemeDisplay(Group group) {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			LocaleUtil.getDefault()
		).when(
			themeDisplay
		).getLocale();

		Mockito.doReturn(
			getPortletDisplay()
		).when(
			themeDisplay
		).getPortletDisplay();

		if (group != null) {
			Mockito.doReturn(
				group
			).when(
				themeDisplay
			).getScopeGroup();
		}

		return themeDisplay;
	}

	protected void setUpGroupLocalServiceUtil(Group group) throws Exception {
		_groupLocalServiceUtilMockedStatic.reset();

		Mockito.when(
			GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
				group.getExternalReferenceCode(), 0L)
		).thenReturn(
			group
		);
	}

	protected void setUpPortletDisplayStyleGroupExternalReferenceCode(
		String externalReferenceCode) {

		CustomFilterPortletInstanceConfiguration
			customFilterPortletInstanceConfiguration = Mockito.mock(
				CustomFilterPortletInstanceConfiguration.class);

		Mockito.when(
			customFilterPortletInstanceConfiguration.
				displayStyleGroupExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		configurationProviderUtilMockedStatic.when(
			() -> ConfigurationProviderUtil.getPortletInstanceConfiguration(
				Mockito.any(), Mockito.any())
		).thenReturn(
			customFilterPortletInstanceConfiguration
		);
	}

	protected static MockedStatic<ConfigurationProviderUtil>
		configurationProviderUtilMockedStatic = Mockito.mockStatic(
			ConfigurationProviderUtil.class);

	private void _assertDisplayContext(Group group) throws Exception {
		CustomFilterDisplayContextBuilder customFilterDisplayContextBuilder =
			new CustomFilterDisplayContextBuilder();

		customFilterDisplayContextBuilder.themeDisplay(getThemeDisplay(group));

		CustomFilterDisplayContext customFilterDisplayContext =
			customFilterDisplayContextBuilder.build();

		Assert.assertEquals(
			group.getGroupId(),
			customFilterDisplayContext.getDisplayStyleGroupId());
	}

	private Group _getGroup() {
		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return group;
	}

	private static final MockedStatic<GroupLocalServiceUtil>
		_groupLocalServiceUtilMockedStatic = Mockito.mockStatic(
			GroupLocalServiceUtil.class);

}