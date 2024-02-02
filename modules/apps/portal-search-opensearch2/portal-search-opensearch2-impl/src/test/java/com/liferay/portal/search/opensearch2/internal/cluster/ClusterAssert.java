/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.cluster;

import com.liferay.portal.search.opensearch2.internal.connection.ClusterHealthResponseUtil;
import com.liferay.portal.search.opensearch2.internal.connection.HealthExpectations;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class ClusterAssert {

	public static void assertHealth(
		OpenSearchConnectionManager openSearchConnectionManager,
		HealthExpectations healthExpectations) {

		Assert.assertEquals(
			String.valueOf(healthExpectations),
			String.valueOf(
				new HealthExpectations(
					ClusterHealthResponseUtil.getClusterHealthResponse(
						openSearchConnectionManager, healthExpectations))));
	}

}