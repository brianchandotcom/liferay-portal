/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.launch.LaunchPreviewThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class LaunchPreviewThreadLocalTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddPreservedParameters() {
		Assert.assertEquals(
			_URL,
			PortalUtil.addPreservedParameters(
				new ThemeDisplay(), _URL, false, false));

		try (SafeCloseable safeCloseable =
				LaunchPreviewThreadLocal.setLaunchSetIdWithSafeCloseable(
					_LAUNCH_SET_ID)) {

			String url = PortalUtil.addPreservedParameters(
				new ThemeDisplay(), _URL, false, false);

			Assert.assertEquals(
				String.valueOf(_LAUNCH_SET_ID),
				HttpComponentsUtil.getParameter(
					url, "previewLaunchSetId", false));
			Assert.assertEquals(
				Constants.PREVIEW,
				HttpComponentsUtil.getParameter(url, "p_l_mode", false));
		}

		try (SafeCloseable safeCloseable =
				LaunchPreviewThreadLocal.setLaunchSetIdWithSafeCloseable(0)) {

			String url = PortalUtil.addPreservedParameters(
				new ThemeDisplay(), _URL, false, false);

			Assert.assertEquals(
				"0",
				HttpComponentsUtil.getParameter(
					url, "previewLaunchSetId", false));
			Assert.assertEquals(
				Constants.PREVIEW,
				HttpComponentsUtil.getParameter(url, "p_l_mode", false));
		}
	}

	private static final long _LAUNCH_SET_ID = 42;

	private static final String _URL = "/web/guest/home";

}