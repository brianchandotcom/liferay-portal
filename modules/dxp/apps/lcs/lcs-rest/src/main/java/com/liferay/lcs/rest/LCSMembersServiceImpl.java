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

package com.liferay.lcs.rest;

import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;

/**
 * @author Riccardo Ferrari
 */
public class LCSMembersServiceImpl
	extends BaseLCSServiceImpl implements LCSMembersService {

	@Override
	public void sendMonitoringUnavailableEmail(String key) {
		try {
			doPut(
				_URL_LCS_MEMBERS_SEND_MONITORING_UNAVAILABLE_EMAIL, "key", key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void sendPatchingToolUnavailableEmail(String key) {
		try {
			doPut(
				_URL_LCS_MEMBERS_SEND_PATCHING_TOOL_UNAVAILABLE_EMAIL, "key",
				key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void sendServerManuallyShutdownEmail(String key) {
		try {
			doPut(
				_URL_LCS_MEMBERS_SEND_SERVER_MANUALLY_SHUTDOWN_EMAIL, "key",
				key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void sendServerUnexpectedlyShutdownEmail(String key) {
		try {
			doPut(
				_URL_LCS_MEMBERS_SEND_SERVER_UNEXPECTEDLY_SHUTDOWN_EMAIL, "key",
				key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	private static final String _URL_LCS_MEMBERS =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSMembers";

	private static final String
		_URL_LCS_MEMBERS_SEND_MONITORING_UNAVAILABLE_EMAIL =
			_URL_LCS_MEMBERS + "/send-monitoring-unavailable-email";

	private static final String
		_URL_LCS_MEMBERS_SEND_PATCHING_TOOL_UNAVAILABLE_EMAIL =
			_URL_LCS_MEMBERS + "/send-patching-tool-unavailable-email";

	private static final String
		_URL_LCS_MEMBERS_SEND_SERVER_MANUALLY_SHUTDOWN_EMAIL =
			_URL_LCS_MEMBERS + "/send-server-manually-shutdown-email";

	private static final String
		_URL_LCS_MEMBERS_SEND_SERVER_UNEXPECTEDLY_SHUTDOWN_EMAIL =
			_URL_LCS_MEMBERS + "/send-server-unexpectedly-shutdown-email";

}