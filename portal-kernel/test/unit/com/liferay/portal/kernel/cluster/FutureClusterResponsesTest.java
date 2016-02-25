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

package com.liferay.portal.kernel.cluster;

import java.net.InetAddress;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class FutureClusterResponsesTest {

	@Test
	public void testMultipleResponseFailure() throws Exception {
		Set<String> clusterNodeIds = new HashSet<>();

		clusterNodeIds.add("1.2.3.4");
		clusterNodeIds.add("1.2.3.5");
		clusterNodeIds.add("1.2.3.6");

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(clusterNodeIds);

		futureClusterResponses.addClusterNodeResponse(
			ClusterNodeResponse.createResultClusterNodeResponse(
				new ClusterNode("1.2.3.4", InetAddress.getLocalHost()), null,
				null));
		futureClusterResponses.addClusterNodeResponse(
			ClusterNodeResponse.createResultClusterNodeResponse(
				new ClusterNode("1.2.3.5", InetAddress.getLocalHost()), null,
				null));

		try {
			futureClusterResponses.get(500, TimeUnit.MILLISECONDS);

			Assert.fail("Should have failed");
		}
		catch (TimeoutException te) {
		}
	}

	@Test
	public void testMultipleResponseSuccess() throws Exception {
		Set<String> clusterNodeIds = new HashSet<>();

		clusterNodeIds.add("1.2.3.4");
		clusterNodeIds.add("1.2.3.5");
		clusterNodeIds.add("1.2.3.6");

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(clusterNodeIds);

		futureClusterResponses.addClusterNodeResponse(
			ClusterNodeResponse.createResultClusterNodeResponse(
				new ClusterNode("1.2.3.4", InetAddress.getLocalHost()), null,
				null));
		futureClusterResponses.addClusterNodeResponse(
			ClusterNodeResponse.createResultClusterNodeResponse(
				new ClusterNode("1.2.3.5", InetAddress.getLocalHost()), null,
				null));
		futureClusterResponses.addClusterNodeResponse(
			ClusterNodeResponse.createResultClusterNodeResponse(
				new ClusterNode("1.2.3.6", InetAddress.getLocalHost()), null,
				null));

		futureClusterResponses.get(500, TimeUnit.MILLISECONDS);
	}

	@Test
	public void testSingleResponseFailure() throws InterruptedException {
		Set<String> clusterNodeIds = new HashSet<>();

		clusterNodeIds.add("1.2.3.4");

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(clusterNodeIds);

		try {
			futureClusterResponses.get(500, TimeUnit.MILLISECONDS);

			Assert.fail("Should have failed");
		}
		catch (TimeoutException te) {
		}
	}

	@Test
	public void testSingleResponseSuccess() throws Exception {
		Set<String> clusterNodeIds = new HashSet<>();

		clusterNodeIds.add("test");

		FutureClusterResponses futureClusterResponses =
			new FutureClusterResponses(clusterNodeIds);

		futureClusterResponses.addClusterNodeResponse(
			ClusterNodeResponse.createResultClusterNodeResponse(
				new ClusterNode("test", InetAddress.getLocalHost()), null,
				null));

		futureClusterResponses.get(500, TimeUnit.MILLISECONDS);
	}

}