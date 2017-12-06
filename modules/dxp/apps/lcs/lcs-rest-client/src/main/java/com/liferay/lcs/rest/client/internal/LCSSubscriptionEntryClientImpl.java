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

import com.liferay.lcs.rest.client.LCSSubscriptionEntry;
import com.liferay.lcs.rest.client.LCSSubscriptionEntryClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(immediate = true, service = LCSSubscriptionEntryClient.class)
public class LCSSubscriptionEntryClientImpl
	implements LCSSubscriptionEntryClient {

	@Override
	public LCSSubscriptionEntry fetchLCSSubscriptionEntry(String key)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		try {
			return _jsonWebServiceClient.doGetToObject(
				LCSSubscriptionEntry.class,
				_URL_LCS_SUBSCRIPTION_ENTRY + "/find/" + key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
				return null;
			}

			throw jsonwsie;
		}
	}

	public void setJSONWebServiceClient(
		JSONWebServiceClient jsonWebServiceClient) {

		_jsonWebServiceClient = jsonWebServiceClient;
	}

	private static final String _URL_LCS_SUBSCRIPTION_ENTRY =
		"/o/osb-lcs-rest/LCSSubscriptionEntry";

	@Reference(target = "(component.name=OSBLCSJSONWebServiceClient)")
	private JSONWebServiceClient _jsonWebServiceClient;

}