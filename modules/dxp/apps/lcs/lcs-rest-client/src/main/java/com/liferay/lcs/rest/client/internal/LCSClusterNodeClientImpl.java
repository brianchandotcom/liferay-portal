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

import com.liferay.lcs.rest.client.LCSClientError;
import com.liferay.lcs.rest.client.LCSClusterNode;
import com.liferay.lcs.rest.client.LCSClusterNodeClient;
import com.liferay.lcs.rest.client.exception.DuplicateLCSClusterNodeNameException;
import com.liferay.lcs.rest.client.exception.NoSuchLCSSubscriptionEntryException;
import com.liferay.lcs.rest.client.exception.RequiredLCSClusterNodeNameException;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(immediate = true, service = LCSClusterNodeClient.class)
public class LCSClusterNodeClientImpl implements LCSClusterNodeClient {

	@Override
	public LCSClusterNode addLCSClusterNode(
			long lcsClusterEntryId, String name, String description,
			int buildNumber, String key, String location,
			int processorCoresTotal)
		throws DuplicateLCSClusterNodeNameException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException,
			   NoSuchLCSSubscriptionEntryException,
			   RequiredLCSClusterNodeNameException {

		validate(lcsClusterEntryId, name);

		if ((description != null) && description.equals("")) {
			description = null;
		}

		if ((location != null) && location.equals("")) {
			location = null;
		}

		try {
			return _jsonWebServiceClient.doPostToObject(
				LCSClusterNode.class, _URL_LCS_CLUSTER_NODE, "buildNumber",
				String.valueOf(buildNumber), "name", name, "description",
				description, "lcsClusterEntryId",
				String.valueOf(lcsClusterEntryId), "location", location, "key",
				key, "processorCoresTotal",
				String.valueOf(processorCoresTotal));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (LCSClientError.getRESTError(jsonwsie) ==
					LCSClientError.NO_SUCH_LCS_SUBSCRIPTION_ENTRY) {

				throw new NoSuchLCSSubscriptionEntryException(jsonwsie);
			}

			throw jsonwsie;
		}
	}

	@Override
	public LCSClusterNode fetchLCSClusterNode(String key)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		try {
			return _jsonWebServiceClient.doGetToObject(
				LCSClusterNode.class, _URL_LCS_CLUSTER_NODE + "/" + key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
				return null;
			}

			throw jsonwsie;
		}
	}

	@Override
	public List<LCSClusterNode> getLCSClusterEntryLCSClusterNodes(
			long lcsClusterEntryId)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException {

		List<LCSClusterNode> remoteLCSClusterNodes = null;

		StringBuilder sb = new StringBuilder(5);

		sb.append(_URL_LCS_CLUSTER_NODE);
		sb.append("/find/");
		sb.append(-1);
		sb.append("/");
		sb.append(-1);

		remoteLCSClusterNodes = _jsonWebServiceClient.doGetToList(
			LCSClusterNode.class, sb.toString(), "lcsClusterEntryId",
			String.valueOf(lcsClusterEntryId));

		List<LCSClusterNode> lcsClusterNodes = new ArrayList<LCSClusterNode>();

		for (LCSClusterNode lcsClusterNode : remoteLCSClusterNodes) {
			lcsClusterNodes.add(lcsClusterNode);
		}

		return lcsClusterNodes;
	}

	public void setJSONWebServiceClient(
		JSONWebServiceClient jsonWebServiceClient) {

		_jsonWebServiceClient = jsonWebServiceClient;
	}

	protected void validate(long lcsClusterEntryId, String lcsClusterNodeName)
		throws DuplicateLCSClusterNodeNameException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException,
			   RequiredLCSClusterNodeNameException {

		if ((lcsClusterNodeName == null) || lcsClusterNodeName.equals("")) {
			throw new RequiredLCSClusterNodeNameException();
		}

		List<LCSClusterNode> lcsClusterNodes =
			getLCSClusterEntryLCSClusterNodes(lcsClusterEntryId);

		for (LCSClusterNode lcsClusterNode : lcsClusterNodes) {
			if (lcsClusterNodeName.equalsIgnoreCase(lcsClusterNode.getName())) {
				throw new DuplicateLCSClusterNodeNameException();
			}
		}
	}

	private static final String _URL_LCS_CLUSTER_NODE =
		"/o/osb-lcs-rest/LCSClusterNode";

	@Reference(target = "(component.name=OSBLCSJSONWebServiceClient)")
	private JSONWebServiceClient _jsonWebServiceClient;

}