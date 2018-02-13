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

import com.liferay.lcs.rest.client.LCSClusterEntry;
import com.liferay.lcs.rest.client.LCSClusterEntryClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class LCSClusterEntryServiceUtil {

	public static LCSClusterEntry getLCSClusterEntry(long lcsClusterEntryId)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return _lcsClusterEntryClient.getLCSClusterEntry(lcsClusterEntryId);
	}

	public void setLCSClusterEntryService(
		LCSClusterEntryClient lcsClusterEntryClient) {

		_lcsClusterEntryClient = lcsClusterEntryClient;
	}

	private static LCSClusterEntryClient _lcsClusterEntryClient;

}