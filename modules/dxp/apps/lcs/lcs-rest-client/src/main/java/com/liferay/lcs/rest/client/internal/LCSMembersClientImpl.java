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

package com.liferay.lcs.rest.client.internal;

import com.liferay.lcs.rest.client.LCSMembersClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = LCSMembersClient.class)
public class LCSMembersClientImpl implements LCSMembersClient {

	@Override
	public void sendMonitoringUnavailableEmail(String key) {
		try {
			_jsonWebServiceClient.doPut(
				_URL_LCS_MEMBERS_SEND_MONITORING_UNAVAILABLE_EMAIL, "key", key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void sendPatchingToolUnavailableEmail(String key) {
		try {
			_jsonWebServiceClient.doPut(
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
			_jsonWebServiceClient.doPut(
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
			_jsonWebServiceClient.doPut(
				_URL_LCS_MEMBERS_SEND_SERVER_UNEXPECTEDLY_SHUTDOWN_EMAIL, "key",
				key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	private static final String _URL_LCS_MEMBERS = "/o/osb-lcs-rest/LCSMembers";

	private static final String
		_URL_LCS_MEMBERS_SEND_MONITORING_UNAVAILABLE_EMAIL =
			_URL_LCS_MEMBERS + "/sendMonitoringUnavailableEmail";

	private static final String
		_URL_LCS_MEMBERS_SEND_PATCHING_TOOL_UNAVAILABLE_EMAIL =
			_URL_LCS_MEMBERS + "/sendPatchingToolUnavailableEmail";

	private static final String
		_URL_LCS_MEMBERS_SEND_SERVER_MANUALLY_SHUTDOWN_EMAIL =
			_URL_LCS_MEMBERS + "/sendServerManuallyShutdownEmail";

	private static final String
		_URL_LCS_MEMBERS_SEND_SERVER_UNEXPECTEDLY_SHUTDOWN_EMAIL =
			_URL_LCS_MEMBERS + "/sendServerUnexpectedlyShutdownEmail";

	@Reference(target = "(component.name=OSBLCSJSONWebServiceClient)")
	private JSONWebServiceClient _jsonWebServiceClient;

}