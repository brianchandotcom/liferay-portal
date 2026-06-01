/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.configuration;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Kyle Bischof
 */
public class PubsubAutoConfigurationTest {

	@Test
	public void testServiceAccountCredentialsProviderBeanNotNull() {
		PubsubAutoConfiguration pubsubAutoConfiguration =
			new PubsubAutoConfiguration();

		Assert.assertNotNull(
			pubsubAutoConfiguration.serviceAccountCredentialsProvider());
	}

}