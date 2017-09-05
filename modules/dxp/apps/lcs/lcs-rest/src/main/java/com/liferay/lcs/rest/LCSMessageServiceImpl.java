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
public class LCSMessageServiceImpl
	extends BaseLCSServiceImpl implements LCSMessageService {

	@Override
	public void addCorpProjectLCSMessage(
			long corpProjectId, long sourceMessageId, String content, int type)
		throws JSONWebServiceInvocationException {

		doPost(
			_URL_LCS_MESSAGE, "corpProjectId", String.valueOf(corpProjectId),
			"sourceMessageId", String.valueOf(sourceMessageId), "content",
			content, "type", String.valueOf(type));
	}

	@Override
	public void deleteCorpProjectLCSMessage(
		long corpProjectId, long sourceMessageId) {

		try {
			doDelete(
				_URL_LCS_MESSAGE, "corpProjectId",
				String.valueOf(corpProjectId), "sourceMessageId",
				String.valueOf(sourceMessageId));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	private static final String _URL_LCS_MESSAGE =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSMessage";

}