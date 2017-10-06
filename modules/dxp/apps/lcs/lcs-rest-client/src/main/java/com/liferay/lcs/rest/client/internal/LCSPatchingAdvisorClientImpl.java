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

import com.liferay.lcs.rest.client.LCSPatchingAdvisorClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = LCSPatchingAdvisorClient.class)
public class LCSPatchingAdvisorClientImpl implements LCSPatchingAdvisorClient {

	@Override
	public List<String> getInstallablePatchIds(
			String key, int patchingToolVersion, String[] installedPatchIds)
		throws Exception {

		StringBuilder sb = new StringBuilder(
			6 + installedPatchIds.length * 2 - 1);

		sb.append(_URL_PATCHING_ADVISOR);
		sb.append("/find/");
		sb.append(key);
		sb.append(StringPool.SLASH);
		sb.append(patchingToolVersion);
		sb.append(StringPool.SLASH);

		for (int i = 0; i < installedPatchIds.length; i++) {
			sb.append(installedPatchIds[i]);

			if ((i + 1) < installedPatchIds.length) {
				sb.append(",");
			}
		}

		return _jsonWebServiceClient.doGetToList(String.class, sb.toString());
	}

	private static final String _URL_PATCHING_ADVISOR =
		"/o/osb-lcs-rest/LCSPatchingAdvisor";

	@Reference(target = "(component.name=OSBLCSJSONWebServiceClient)")
	private JSONWebServiceClient _jsonWebServiceClient;

}