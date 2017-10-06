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

import com.liferay.lcs.rest.client.LCSMessageClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.portal.kernel.util.StringPool;

import org.osgi.service.component.annotations.Component;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = LCSMessageClient.class)
public class LCSMessageClientImpl
	extends BaseLCSServiceImpl implements LCSMessageClient {

	@Override
	public void addCorpProjectLCSMessage(
			long corpProjectId, long sourceMessageId, String content, int type)
		throws JSONWebServiceInvocationException {

		jsonWebServiceClient.doPost(
			_URL_LCS_MESSAGE, "corpProjectId", String.valueOf(corpProjectId),
			"sourceMessageId", String.valueOf(sourceMessageId), "content",
			content, "type", String.valueOf(type));
	}

	@Override
	public void deleteCorpProjectLCSMessage(
		long corpProjectId, long sourceMessageId) {

		try {
			jsonWebServiceClient.doDelete(
				_URL_LCS_MESSAGE + StringPool.SLASH + corpProjectId +
					StringPool.SLASH + sourceMessageId);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	private static final String _URL_LCS_MESSAGE = "/o/osb-lcs-rest/LCSMessage";

}