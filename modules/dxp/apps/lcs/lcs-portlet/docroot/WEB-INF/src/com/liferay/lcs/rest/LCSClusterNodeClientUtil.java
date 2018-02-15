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

import com.liferay.lcs.rest.client.LCSClusterNode;
import com.liferay.lcs.rest.client.LCSClusterNodeClient;
import com.liferay.lcs.rest.client.exception.DuplicateLCSClusterNodeNameException;
import com.liferay.lcs.rest.client.exception.NoSuchLCSSubscriptionEntryException;
import com.liferay.lcs.rest.client.exception.RequiredLCSClusterNodeNameException;
import com.liferay.lcs.util.KeyGenerator;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class LCSClusterNodeClientUtil {

	public static LCSClusterNode addLCSClusterNode(
			long lcsClusterEntryId, String name, String description,
			int buildNumber, String location, int processorCoresTotal)
		throws DuplicateLCSClusterNodeNameException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException,
			   NoSuchLCSSubscriptionEntryException,
			   RequiredLCSClusterNodeNameException {

		return _lcsClusterNodeClient.addLCSClusterNode(
			lcsClusterEntryId, name, description, buildNumber,
			_keyGenerator.getKey(), location, processorCoresTotal);
	}

	public static LCSClusterNode fetchLCSClusterNode()
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		return _lcsClusterNodeClient.fetchLCSClusterNode(
			_keyGenerator.getKey());
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSClusterNodeService(
		LCSClusterNodeClient lcsClusterNodeClient) {

		_lcsClusterNodeClient = lcsClusterNodeClient;
	}

	private static KeyGenerator _keyGenerator;
	private static LCSClusterNodeClient _lcsClusterNodeClient;

}