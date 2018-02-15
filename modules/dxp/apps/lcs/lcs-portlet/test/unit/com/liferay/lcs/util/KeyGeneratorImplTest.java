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

package com.liferay.lcs.util;

import com.liferay.lcs.rest.client.LCSClusterNode;
import com.liferay.lcs.rest.client.LCSClusterNodeClient;
import com.liferay.lcs.rest.client.internal.LCSClusterNodeClientImpl;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mladen Cikara
 * @author Igor Beslic
 */
@PrepareForTest({DigesterUtil.class, LicenseManagerUtil.class, PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class KeyGeneratorImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		mockStatic(
			DigesterUtil.class, LicenseManagerUtil.class, PropsUtil.class);

		when(
			LicenseManagerUtil.getHostName()
		).thenReturn(
			"www.test.com"
		);

		Set<String> ipAddresses = new HashSet<>();

		ipAddresses.add("172.17.42.1");
		ipAddresses.add("192.168.0.238");

		when(
			LicenseManagerUtil.getIpAddresses()
		).thenReturn(
			ipAddresses
		);

		Set<String> macAddresses = new HashSet<>();

		macAddresses.add("02:42:78:89:ec:18");
		macAddresses.add("34:e6:d7:20:64:60");
		macAddresses.add("80:00:0b:6d:63:16");

		when(
			LicenseManagerUtil.getMacAddresses()
		).thenReturn(
			macAddresses
		);

		when(
			DigesterUtil.digestHex(Digester.MD5, "lcsServerId")
		).thenReturn(
			"mockedLCSServerIdMD5Digest"
		);
	}

	@Test
	public void testLCSServerIdNotFound() throws Exception {
		KeyGeneratorImpl keyGenerator = spy(new KeyGeneratorImpl());

		LCSClusterNodeClient lcsClusterNodeClient = spy(
			new LCSClusterNodeClientImpl());

		doReturn(
			new LCSClusterNode()
		).when(
			lcsClusterNodeClient
		).fetchLCSClusterNode(
			Mockito.anyString()
		);

		keyGenerator.setLCSClusterNodeClient(lcsClusterNodeClient);

		doNothing(
		).when(
			keyGenerator
		).writeLcsServerProperties(
			Matchers.<byte[]>any()
		);

		keyGenerator.getLcsServerId();

		Mockito.verify(
			keyGenerator, Mockito.times(1)
		).generateLcsServerId();

		Mockito.verify(
			keyGenerator, Mockito.times(1)
		).writeLcsServerProperties(
			Matchers.<byte[]>any()
		);
	}

	@Test
	public void testLCSServerIdParametersChanged() {
		KeyGeneratorImpl keyGenerator = new KeyGeneratorImpl();

		InputStream inputStream = null;

		try {
			inputStream = KeyGeneratorImplTest.class.getResourceAsStream(
				"dependencies/data/license/server/lcs_server_id");

			byte[] bytes = IOUtils.toByteArray(inputStream);

			Assert.assertFalse(
				keyGenerator.hasLCSServerIdParametersChanged(bytes));

			inputStream = KeyGeneratorImplTest.class.getResourceAsStream(
				"dependencies/data/license/server/lcs_server_id_modified");

			bytes = IOUtils.toByteArray(inputStream);

			Assert.assertTrue(
				keyGenerator.hasLCSServerIdParametersChanged(bytes));
		}
		catch (IOException ioe) {
			Assert.fail(ioe.getMessage());
		}
	}

	@Test
	public void testNoRegisteredServerFound() throws Exception {
		KeyGeneratorImpl keyGenerator = spy(new KeyGeneratorImpl());

		LCSClusterNodeClient lcsClusterNodeClient = spy(
			new LCSClusterNodeClientImpl());

		doReturn(
			null
		).when(
			lcsClusterNodeClient
		).fetchLCSClusterNode(
			Mockito.anyString()
		);

		keyGenerator.setLCSClusterNodeClient(lcsClusterNodeClient);

		doReturn(
			"lcsServerId"
		).when(
			keyGenerator
		).getLcsServerId(
			Matchers.anyBoolean()
		);

		String key = keyGenerator.getKey();

		Assert.assertEquals("mockedLCSServerIdMD5Digest", key);
	}

	@Test
	public void testRegisteredServerFound() throws Exception {
		KeyGeneratorImpl keyGenerator = spy(new KeyGeneratorImpl());

		LCSClusterNodeClient lcsClusterNodeClient = spy(
			new LCSClusterNodeClientImpl());

		doReturn(
			new LCSClusterNode()
		).when(
			lcsClusterNodeClient
		).fetchLCSClusterNode(
			Matchers.anyString()
		);

		keyGenerator.setLCSClusterNodeClient(lcsClusterNodeClient);

		doReturn(
			"lcsServerId"
		).when(
			keyGenerator
		).getLcsServerId();

		keyGenerator.getKey();

		Mockito.verify(
			keyGenerator, Mockito.times(1)
		).getLcsServerId();
	}

}