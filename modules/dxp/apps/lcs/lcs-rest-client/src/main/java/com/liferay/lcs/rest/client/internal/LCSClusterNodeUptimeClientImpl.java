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

import com.liferay.lcs.rest.client.LCSClusterNodeUptimeClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = LCSClusterNodeUptimeClient.class)
public class LCSClusterNodeUptimeClientImpl
	extends BaseLCSServiceImpl implements LCSClusterNodeUptimeClient {

	@Override
	public void updateLCSClusterNodeUptime(String key) {
		try {
			jsonWebServiceClient.doPut(
				_URL_LCS_CLUSTER_NODE_UPTIME, "key", key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
				return;
			}

			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void updateLCSClusterNodeUptimes(String key, String uptimesJSON) {
		try {
			jsonWebServiceClient.doPut(
				_URL_LCS_CLUSTER_NODE_UPTIME, "key", key, "uptimesJSON",
				uptimesJSON);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	private static final String _URL_LCS_CLUSTER_NODE_UPTIME =
		"/o/osb-lcs-rest/LCSClusterNodeUptime";

}