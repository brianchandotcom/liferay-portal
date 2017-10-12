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

import com.liferay.lcs.rest.client.LCSRole;
import com.liferay.lcs.rest.client.LCSRoleClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(immediate = true, service = LCSRoleClient.class)
public class LCSRoleClientImpl implements LCSRoleClient {

	@Override
	public boolean hasUserLCSAdministratorLCSRole(long lcsProjectId)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException {

		StringBuilder sb = new StringBuilder(7);

		sb.append(_URL_LCS_ROLE);
		sb.append("/find/");
		sb.append(lcsProjectId);
		sb.append(StringPool.SLASH);
		sb.append("true");
		sb.append(StringPool.SLASH);
		sb.append("false");

		List<LCSRole> lcsRoles = _jsonWebServiceClient.doGetToList(
			LCSRole.class, sb.toString());

		if (lcsRoles.isEmpty()) {
			return false;
		}

		return true;
	}

	public void setJSONWebServiceClient(
		JSONWebServiceClient jsonWebServiceClient) {

		_jsonWebServiceClient = jsonWebServiceClient;
	}

	private static final String _URL_LCS_ROLE = "/o/osb-lcs-rest/LCSRole";

	@Reference(target = "(component.name=OSBLCSJSONWebServiceClient)")
	private JSONWebServiceClient _jsonWebServiceClient;

}