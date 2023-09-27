/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cluster;

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Tina Tian
 */
public class FutureClusterResponses
	extends DefaultNoticeableFuture<ClusterNodeResponses> {

	public FutureClusterResponses(Set<String> clusterNodeIds) {
		_clusterNodeResponses = new ClusterNodeResponses(clusterNodeIds);

		int size = clusterNodeIds.size();

		if (size == 0) {
			set(_clusterNodeResponses);
		}

		_counter = new AtomicInteger(size);
	}

	public boolean addClusterNodeResponse(
		ClusterNodeResponse clusterNodeResponse) {

		if (_clusterNodeResponses.addClusterResponse(clusterNodeResponse)) {
			if (_counter.decrementAndGet() == 0) {
				set(_clusterNodeResponses);
			}

			return true;
		}

		return false;
	}

	@Override
	public ClusterNodeResponses get() throws InterruptedException {
		try {
			return super.get();
		}
		catch (ExecutionException executionException) {
			throw new AssertionError(executionException);
		}
	}

	@Override
	public ClusterNodeResponses get(long timeout, TimeUnit unit)
		throws InterruptedException, TimeoutException {

		try {
			return super.get(timeout, unit);
		}
		catch (ExecutionException executionException) {
			throw new AssertionError(executionException);
		}
	}

	public BlockingQueue<ClusterNodeResponse> getPartialResults() {
		return _clusterNodeResponses.getClusterResponses();
	}

	private final ClusterNodeResponses _clusterNodeResponses;
	private final AtomicInteger _counter;

}