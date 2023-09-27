/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class SharepointConnectionInfoTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConstructorFailsWithInvalidSitePaths() {
		try {
			_buildSharepointConnectionInfoWithSitePath(StringPool.SLASH);

			Assert.fail("IllegalArgumentException not thrown for site path /");
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			_buildSharepointConnectionInfoWithSitePath(
				"sitePathWithoutLeadingSlash");

			Assert.fail(
				"IllegalArgumentException not thrown for site path without a " +
					"leading /");
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}

		try {
			_buildSharepointConnectionInfoWithSitePath(
				"/sitePathWithTrailingSlash/");

			Assert.fail(
				"IllegalArgumentException not thrown for site path with a " +
					"trailing /");
		}
		catch (IllegalArgumentException illegalArgumentException) {
		}
	}

	@Test
	public void testGetServiceURL() {
		String sitePath = "/sitePath";

		SharepointConnectionInfo sharepointConnectionInfo =
			_buildSharepointConnectionInfoWithSitePath(sitePath);

		Assert.assertEquals(
			StringBundler.concat(
				_SERVER_PROTOCOL, "://", _SERVER_ADDRESS, StringPool.COLON,
				_SERVER_PORT, sitePath, StringPool.SLASH),
			String.valueOf(sharepointConnectionInfo.getServiceURL()));
	}

	private SharepointConnectionInfo _buildSharepointConnectionInfoWithSitePath(
		String sitePath) {

		return new SharepointConnectionInfo(
			_SERVER_VERSION, _SERVER_PROTOCOL, _SERVER_ADDRESS, _SERVER_PORT,
			sitePath, _LIBRARY_NAME, _LIBRARY_PATH, _USER_NAME, _PASSWORD);
	}

	private static final String _LIBRARY_NAME = "Documents";

	private static final String _LIBRARY_PATH = "Shared Documents";

	private static final String _PASSWORD = "password";

	private static final String _SERVER_ADDRESS = "liferay-20jf4ic";

	private static final int _SERVER_PORT = 443;

	private static final String _SERVER_PROTOCOL = "https";

	private static final SharepointConnection.ServerVersion _SERVER_VERSION =
		SharepointConnection.ServerVersion.SHAREPOINT_2013;

	private static final String _USER_NAME = "Administrator";

}