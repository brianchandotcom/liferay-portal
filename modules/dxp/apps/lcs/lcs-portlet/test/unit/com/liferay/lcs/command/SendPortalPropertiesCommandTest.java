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

package com.liferay.lcs.command;

import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@PrepareForTest({LCSUtil.class, PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class SendPortalPropertiesCommandTest extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(LCSUtil.class);

		when(
			LCSUtil.getPortalPropertiesBlacklist()
		).thenReturn(
			""
		);

		mockStatic(PropsUtil.class);

		for (String portalPropertiesInsensitiveKey :
				LCSConstants.PORTAL_PROPERTIES_SECURITY_INSENSITIVE) {

			_properties.put(
				portalPropertiesInsensitiveKey, portalPropertiesInsensitiveKey);
			_properties.put("test.key.i." + System.currentTimeMillis(), "test");
			_properties.put(
				"test.key.i." + System.currentTimeMillis() + ".password",
				"test");
		}

		for (String portalPropertiesSensitiveKey :
				LCSConstants.PORTAL_PROPERTIES_SECURITY_SENSITIVE) {

			_properties.put(
				portalPropertiesSensitiveKey, portalPropertiesSensitiveKey);
			_properties.put("test.key.s." + System.currentTimeMillis(), "test");
			_properties.put(
				"test.key.s." + System.currentTimeMillis() + ".password",
				"test");
		}

		when(
			PropsUtil.getProperties()
		).thenReturn(
			_properties
		);
	}

	@Test
	public void testGetSecurityInsensitivePropertiesKeys() {
		SendPortalPropertiesCommand sendPortalPropertiesCommand =
			new SendPortalPropertiesCommand();

		Properties properties =
			sendPortalPropertiesCommand.getSecurityInsensitivePropertiesKeys();

		for (String portalPropertiesInsensitiveKey :
				LCSConstants.PORTAL_PROPERTIES_SECURITY_INSENSITIVE) {

			if (!properties.containsKey(portalPropertiesInsensitiveKey)) {
				Assert.fail();
			}
		}

		for (String portalPropertiesSensitiveKey :
				LCSConstants.PORTAL_PROPERTIES_SECURITY_SENSITIVE) {

			if (properties.containsKey(portalPropertiesSensitiveKey)) {
				Assert.fail();
			}
		}
	}

	private final Properties _properties = new Properties();

}