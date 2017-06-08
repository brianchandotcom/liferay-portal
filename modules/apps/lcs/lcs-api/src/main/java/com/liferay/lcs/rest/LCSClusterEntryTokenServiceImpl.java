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

import javax.servlet.http.HttpServletResponse;

/**
 * @author Igor Beslic
 */
public class LCSClusterEntryTokenServiceImpl
	extends BaseLCSServiceImpl implements LCSClusterEntryTokenService {

	@Override
	public LCSClusterEntryToken fetchLCSClusterEntryToken(
		long lcsClusterEntryTokenId) {

		try {
			LCSClusterEntryToken lcsClusterEntryToken = doGetToObject(
				LCSClusterEntryTokenImpl.class, _URL_LCS_CLUSTER_ENTRY_TOKEN,
				"lcsClusterEntryTokenId",
				String.valueOf(lcsClusterEntryTokenId));

			return lcsClusterEntryToken;
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
				return null;
			}

			throw new RuntimeException(jsonwsie);
		}
	}

	private static final String _URL_LCS_CLUSTER_ENTRY_TOKEN =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSClusterEntryToken";

}