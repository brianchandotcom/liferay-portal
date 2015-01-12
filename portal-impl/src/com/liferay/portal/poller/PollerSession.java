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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.poller.PollerRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Edward Han
 */
public class PollerSession {

	public PollerSession(String pollerSessionId) {
		_pollerSessionId = pollerSessionId;
	}

	public synchronized boolean beginPortletProcessing(
		PollerRequest pollerRequest, String responseId) {

		String portletId = pollerRequest.getPortletId();

		// Do not process a new request if there is a request already pending.
		// This prevents flooding the server in the event of slow receive
		// requests.

		if (_pendingResponseIds.containsKey(portletId)) {
			return false;
		}

		_pendingResponseIds.put(portletId, responseId);

		_pollerRequests.put(portletId, pollerRequest);

		return true;
	}

	public synchronized boolean completePortletProcessing(
		String portletId, String responseId) {

		String pendingResponseId = _pendingResponseIds.get(portletId);

		if (responseId.equals(pendingResponseId)) {
			_pendingResponseIds.remove(portletId);

			_pollerRequests.remove(portletId);
		}

		return _pendingResponseIds.isEmpty();
	}

	public String getPollerSessionId() {
		return _pollerSessionId;
	}

	private final Map<String, String> _pendingResponseIds = new HashMap<>();
	private final Map<String, PollerRequest> _pollerRequests = new HashMap<>();
	private final String _pollerSessionId;

}