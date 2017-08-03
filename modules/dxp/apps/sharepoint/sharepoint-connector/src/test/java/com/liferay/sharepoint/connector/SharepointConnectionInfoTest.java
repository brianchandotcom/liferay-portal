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

package com.liferay.sharepoint.connector;

import com.liferay.portal.kernel.util.StringPool;

import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class SharepointConnectionInfoTest {

	@Test
	public void testConstructorFailsWithInvalidSitePaths() {
		try {
			_buildSharepointConnectionInfoWithSitePath(StringPool.SLASH);

			Assert.fail("IllegalArgumentException not thrown for site path /");
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			_buildSharepointConnectionInfoWithSitePath(
				"sitePathWithoutLeadingSlash");

			Assert.fail(
				"IllegalArgumentException not thrown for site path without a " +
					"leading /");
		}
		catch (IllegalArgumentException iae) {
		}

		try {
			_buildSharepointConnectionInfoWithSitePath(
				"/sitePathWithTrailingSlash/");

			Assert.fail(
				"IllegalArgumentException not thrown for site path with a " +
					"trailing /");
		}
		catch (IllegalArgumentException iae) {
		}
	}

	@Test
	public void testGetServiceURL() {
		final String sitePath = "/sitePath";

		SharepointConnectionInfo sharepointConnectionInfo =
			_buildSharepointConnectionInfoWithSitePath(sitePath);

		URL serviceURL = sharepointConnectionInfo.getServiceURL();

		Assert.assertEquals(
			_SERVER_PROTOCOL + "://" + _SERVER_ADDRESS + StringPool.COLON +
				_SERVER_PORT + sitePath + StringPool.SLASH,
			serviceURL.toString());
	}

	private SharepointConnectionInfo _buildSharepointConnectionInfoWithSitePath(
		String sitePath) {

		return new SharepointConnectionInfo(
			_SERVER_VERSION, _SERVER_PROTOCOL, _SERVER_ADDRESS, _SERVER_PORT,
			sitePath, _LIBRARY_NAME, _LIBRARY_PATH, _USERNAME, _PASSWORD);
	}

	private static final String _LIBRARY_NAME = "Documents";

	private static final String _LIBRARY_PATH = "Shared Documents";

	private static final String _PASSWORD = "password";

	private static final String _SERVER_ADDRESS = "liferay-20jf4ic";

	private static final int _SERVER_PORT = 443;

	private static final String _SERVER_PROTOCOL = "https";

	private static final SharepointConnection.ServerVersion _SERVER_VERSION =
		SharepointConnection.ServerVersion.SHAREPOINT_2013;

	private static final String _USERNAME = "Administrator";

}