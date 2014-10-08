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

import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;

import java.util.HashSet;
import java.util.List;
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

	public FutureClusterResponses(List<Address> addresses) {
		_clusterNodeResponses = new ClusterNodeResponses();

		int size = addresses.size();

		if (size == 0) {
			set(_clusterNodeResponses);
		}

		_counter = new AtomicInteger(size);
		_expectedReplyAddress = new HashSet<Address>(addresses);
	}

	public void addClusterNodeResponse(
		ClusterNodeResponse clusterNodeResponse) {

		_clusterNodeResponses.addClusterResponse(clusterNodeResponse);

		if (_counter.decrementAndGet() == 0) {
			set(_clusterNodeResponses);
		}
	}

	public boolean expectsReply(Address address) {
		return _expectedReplyAddress.contains(address);
	}

	@Override
	public ClusterNodeResponses get() throws InterruptedException {
		try {
			return super.get();
		}
		catch (ExecutionException ee) {
			throw new AssertionError(ee);
		}
	}

	@Override
	public ClusterNodeResponses get(long timeout, TimeUnit unit)
		throws InterruptedException, TimeoutException {

		try {
			return super.get(timeout, unit);
		}
		catch (ExecutionException ee) {
			throw new AssertionError(ee);
		}
	}

	public BlockingQueue<ClusterNodeResponse> getPartialResults() {
		return _clusterNodeResponses.getClusterResponses();
	}

	private final ClusterNodeResponses _clusterNodeResponses;
	private final AtomicInteger _counter;
	private final Set<Address> _expectedReplyAddress;

}