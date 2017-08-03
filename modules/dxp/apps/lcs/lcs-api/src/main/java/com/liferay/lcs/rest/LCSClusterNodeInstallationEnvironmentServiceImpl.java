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
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 */
public class LCSClusterNodeInstallationEnvironmentServiceImpl
	extends BaseLCSServiceImpl
	implements LCSClusterNodeInstallationEnvironmentService {

	@Override
	public LCSClusterNodeInstallationEnvironment
		fetchLCSClusterNodeInstallationEnvironment(String key) {

		try {
			return doGetToObject(
				LCSClusterNodeInstallationEnvironmentImpl.class,
				_URL_LCS_CLUSTER_NODE_INSTALLATION_ENVIRONMENT, "key", key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
				return null;
			}

			throw new RuntimeException(jsonwsie);
		}
		catch (JSONWebServiceTransportException jsonwste) {
			throw new RuntimeException(jsonwste);
		}
	}

	private static final String _URL_LCS_CLUSTER_NODE_INSTALLATION_ENVIRONMENT =
		"/osb-lcs-portlet/lcs/jsonws/v1_4" +
			"/LCSClusterNodeInstallationEnvironment";

}