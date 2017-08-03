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

package com.liferay.lcs.activation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Igor Beslic
 */
@RunWith(PowerMockRunner.class)
public class LCSClusterEntryTokenContentAdvisorTest extends PowerMockito {

	@Test
	public void testConstructorFromParameters() {
		Map<String, String> lcsServicesConfiguration =
			new HashMap<String, String>();

		lcsServicesConfiguration.put("service-1", "true");
		lcsServicesConfiguration.put("service-2", "true");
		lcsServicesConfiguration.put("service-3", "false");
		lcsServicesConfiguration.put("service-4", "true");

		String portalPropertiesBlacklist = "property.key.1,property.key.2";

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisorA =
			new LCSClusterEntryTokenContentAdvisor(
				"accessSecretPart", "accessTokenPart", "consumerKeyPart",
				"consumerSecretPart", "lcs.liferay.com", "443", "https",
				lcsServicesConfiguration, portalPropertiesBlacklist);

		String contentA = lcsClusterEntryTokenContentAdvisorA.getContent();

		Assert.assertTrue(
			contentA.contains("\"dataCenterHostName\":\"lcs.liferay.com\""));
		Assert.assertTrue(
			contentA.contains(
				"\"portalPropertiesBlacklist\":" +
					"\"property.key.1,property.key.2\""));
		Assert.assertTrue(contentA.contains("lcsServicesConfiguration"));

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisorB =
			new LCSClusterEntryTokenContentAdvisor(
				"accessSecretPart", "accessTokenPart", "consumerKeyPart",
				"consumerSecretPart", "lcs.liferay.com", "443", "https",
				lcsServicesConfiguration, null);

		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisorB.getPortalPropertiesBlacklist());

		String contentB = lcsClusterEntryTokenContentAdvisorB.getContent();

		Assert.assertFalse(contentB.contains("portalPropertiesBlacklist"));

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisorC =
			new LCSClusterEntryTokenContentAdvisor(
				"accessSecretPart", "accessTokenPart", lcsServicesConfiguration,
				portalPropertiesBlacklist);

		Assert.assertNull(lcsClusterEntryTokenContentAdvisorC.getConsumerKey());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisorC.getConsumerSecret());

		String contentC = lcsClusterEntryTokenContentAdvisorC.getContent();

		Assert.assertTrue(
			contentC.contains(
				"\"portalPropertiesBlacklist\":" +
					"\"property.key.1,property.key.2\""));

		lcsServicesConfiguration.clear();

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisorD =
			new LCSClusterEntryTokenContentAdvisor(
				"accessSecretPart", "accessTokenPart", lcsServicesConfiguration,
				null);

		String contentD = lcsClusterEntryTokenContentAdvisorD.getContent();

		Assert.assertFalse(contentD.contains("portalPropertiesBlacklist"));
	}

	@Test
	public void testConstructorFromSerializedTokenContent() throws Exception {
		String contentV1 = getLCSClusterEntryTokenContent(
			"lcs_cluster_entry_token_content_v1.txt");

		LCSClusterEntryTokenContentAdvisor lcsClusterEntryTokenContentAdvisor =
			new LCSClusterEntryTokenContentAdvisor(contentV1);

		Assert.assertEquals(
			"accessTokenPart",
			lcsClusterEntryTokenContentAdvisor.getAccessToken());
		Assert.assertEquals(
			"accessSecretPart",
			lcsClusterEntryTokenContentAdvisor.getAccessSecret());
		Assert.assertNull(lcsClusterEntryTokenContentAdvisor.getConsumerKey());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getConsumerSecret());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getDataCenterHostName());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getDataCenterHostPort());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getDataCenterProtocol());
		Assert.assertTrue(
			lcsClusterEntryTokenContentAdvisor.
				getLCSServicesConfiguration().isEmpty());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getPortalPropertiesBlacklist());

		String contentV2 = getLCSClusterEntryTokenContent(
			"lcs_cluster_entry_token_content_v2.txt");

		lcsClusterEntryTokenContentAdvisor =
			new LCSClusterEntryTokenContentAdvisor(contentV2);

		Assert.assertEquals(
			"accessTokenPart",
			lcsClusterEntryTokenContentAdvisor.getAccessToken());
		Assert.assertEquals(
			"accessSecretPart",
			lcsClusterEntryTokenContentAdvisor.getAccessSecret());
		Assert.assertNull(lcsClusterEntryTokenContentAdvisor.getConsumerKey());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getConsumerSecret());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getDataCenterHostName());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getDataCenterHostPort());
		Assert.assertNull(
			lcsClusterEntryTokenContentAdvisor.getDataCenterProtocol());
		Assert.assertFalse(
			lcsClusterEntryTokenContentAdvisor.
				getLCSServicesConfiguration().isEmpty());
		Assert.assertEquals(
			"property.key.1,property.key.2",
			lcsClusterEntryTokenContentAdvisor.getPortalPropertiesBlacklist());

		String contentV3 = getLCSClusterEntryTokenContent(
			"lcs_cluster_entry_token_content_v3.json");

		lcsClusterEntryTokenContentAdvisor =
			new LCSClusterEntryTokenContentAdvisor(contentV3);

		Assert.assertEquals(
			"accessTokenPart",
			lcsClusterEntryTokenContentAdvisor.getAccessToken());
		Assert.assertEquals(
			"accessSecretPart",
			lcsClusterEntryTokenContentAdvisor.getAccessSecret());
		Assert.assertEquals(
			"consumerKeyPart",
			lcsClusterEntryTokenContentAdvisor.getConsumerKey());
		Assert.assertEquals(
			"consumerSecretPart",
			lcsClusterEntryTokenContentAdvisor.getConsumerSecret());
		Assert.assertEquals(
			"lcs.liferay.com",
			lcsClusterEntryTokenContentAdvisor.getDataCenterHostName());
		Assert.assertEquals(
			"443", lcsClusterEntryTokenContentAdvisor.getDataCenterHostPort());
		Assert.assertEquals(
			"https",
			lcsClusterEntryTokenContentAdvisor.getDataCenterProtocol());
		Assert.assertFalse(
			lcsClusterEntryTokenContentAdvisor.
				getLCSServicesConfiguration().isEmpty());
		Assert.assertEquals(
			"property.key.1,property.key.2",
			lcsClusterEntryTokenContentAdvisor.getPortalPropertiesBlacklist());
	}

	protected String getLCSClusterEntryTokenContent(String fileName)
		throws IOException {

		InputStream inputStream = null;

		try {
			Class<?> clazz = LCSClusterEntryTokenContentAdvisorTest.class;

			inputStream = clazz.getResourceAsStream("dependencies/" + fileName);

			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));

			StringBuilder sb = new StringBuilder();

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

}