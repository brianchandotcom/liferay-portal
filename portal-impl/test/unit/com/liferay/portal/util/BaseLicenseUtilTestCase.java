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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
@PrepareForTest(LicenseUtil.class)
@RunWith(PowerMockRunner.class)
public abstract class BaseLicenseUtilTestCase extends PowerMockito {

	@Before
	public void setUp() {
		mockStatic(LicenseUtil.class);
	}

	@Test
	public void testOS() throws Exception {
		when(
			LicenseUtil.getMacAddresses()
		).thenReturn(
			getMacAddresses()
		);

		testMacAddresses(LicenseUtil.getMacAddresses());
	}

	protected abstract String getDependenciesFileName();

	protected InputStream getInputStream(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName + ".txt");

		return inputStream;
	}

	protected Set<String> getMacAddresses() throws Exception {
		String osName = getDependenciesFileName();

		InputStream processInputStream = getInputStream(osName);

		Set<String> macAddresses = new HashSet<>();

		Pattern macAddressPattern = getMacAddressPattern();

		String processOutput = StringUtil.read(processInputStream);

		String[] lines = StringUtil.split(processOutput, CharPool.NEW_LINE);

		for (String line : lines) {
			Matcher matcher = macAddressPattern.matcher(line);

			if (!matcher.find()) {
				continue;
			}

			String macAddress = matcher.group(1);

			macAddress = StringUtil.toLowerCase(macAddress);
			macAddress = macAddress.replace(CharPool.DASH, CharPool.COLON);
			macAddress = macAddress.replace(CharPool.PERIOD, CharPool.COLON);

			StringBuilder sb = new StringBuilder(17);

			sb.append(macAddress);

			for (int i = 1; i < 5; ++i) {
				int pos = (i * 3) - 1;

				if (sb.charAt(pos) != CharPool.COLON) {
					sb.insert((i - 1) * 3, CharPool.NUMBER_0);
				}
			}

			if (sb.length() < 17) {
				sb.insert(15, CharPool.NUMBER_0);
			}

			macAddress = sb.toString();

			macAddresses.add(macAddress);
		}

		return macAddresses;
	}

	protected Pattern getMacAddressPattern() {
		return Pattern.compile(
			"\\s((\\p{XDigit}{1,2}(-|:)){5}(\\p{XDigit}{1,2}))(?:\\s|$)");
	}

	protected abstract void testMacAddresses(Set<String> macAddresses);

}