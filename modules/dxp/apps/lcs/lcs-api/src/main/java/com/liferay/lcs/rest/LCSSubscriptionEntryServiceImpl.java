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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Igor Beslic
 */
public class LCSSubscriptionEntryServiceImpl
	extends BaseLCSServiceImpl implements LCSSubscriptionEntryService {

	@Override
	public void addCorpProjectLCSSubscriptionEntries(
			long corpProjectId, String lcsSubscriptionEntriesJSON)
		throws JSONWebServiceInvocationException {

		doPost(
			_URL_LCS_SUBSCRIPTION_ENTRY, "corpProjectId",
			String.valueOf(corpProjectId), "lcsSubscriptionEntriesJSON",
			lcsSubscriptionEntriesJSON);

		if (_logger.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();

			sb.append("Sent LCS subscription message with corp project ID ");
			sb.append(corpProjectId);
			sb.append(" and LCS subscription entries ");
			sb.append(lcsSubscriptionEntriesJSON);

			_logger.info(sb.toString());
		}
	}

	@Override
	public LCSSubscriptionEntry fetchLCSSubscriptionEntry(String key) {
		try {
			return doGetToObject(
				LCSSubscriptionEntryImpl.class, _URL_LCS_SUBSCRIPTION_ENTRY,
				"key", key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
				return null;
			}

			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void incrementServerUsed(String key)
		throws JSONWebServiceInvocationException {

		doPost(_URL_LCS_SUBSCRIPTION_ENTRY, "key", key);
	}

	private static final String _URL_LCS_SUBSCRIPTION_ENTRY =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSSubscriptionEntry";

	private static final Logger _logger = LoggerFactory.getLogger(
		LCSSubscriptionEntryServiceImpl.class);

}