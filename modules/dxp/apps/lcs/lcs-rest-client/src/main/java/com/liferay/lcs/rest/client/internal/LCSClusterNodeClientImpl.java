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
import com.liferay.portal.kernel.util.StringPool;

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
			int buildNumber, long heartbeatInterval, long lastHeartbeat,
			String key, String location, int patchingToolVersion,
			String portalEdition, int processorCoresTotal,
			String protocolVersion)
		throws DuplicateLCSClusterNodeNameException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
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
				description, "heartbeatInterval",
				String.valueOf(heartbeatInterval), "lastHeartbeat",
				String.valueOf(lastHeartbeat), "lcsClusterEntryId",
				String.valueOf(lcsClusterEntryId), "location", location, "key",
				key, "patchingToolVersion", String.valueOf(patchingToolVersion),
				"portalEdition", portalEdition, "processorCoresTotal",
				String.valueOf(processorCoresTotal), "protocolVersion",
				protocolVersion);
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
	public LCSClusterNode addLCSClusterNode(
			long lcsClusterEntryId, String name, String description,
			int buildNumber, String key, String location,
			int processorCoresTotal)
		throws DuplicateLCSClusterNodeNameException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
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
				LCSClusterNode.class,
				_URL_LCS_CLUSTER_NODE + StringPool.SLASH + key);
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
			   JSONWebServiceSerializeException {

		List<LCSClusterNode> remoteLCSClusterNodes = null;

		StringBuilder sb = new StringBuilder(5);

		sb.append(_URL_LCS_CLUSTER_NODE);
		sb.append("/find/");
		sb.append(-1);
		sb.append(StringPool.SLASH);
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

	@Override
	public List<LCSClusterNode> getLCSClusterNodes(
			int status, int start, int end)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException {

		List<LCSClusterNode> remoteLCSClusterNodes = null;

		StringBuilder sb = new StringBuilder(5);

		sb.append(_URL_LCS_CLUSTER_NODE);
		sb.append("/find/");
		sb.append(start);
		sb.append(StringPool.SLASH);
		sb.append(end);

		remoteLCSClusterNodes = _jsonWebServiceClient.doGetToList(
			LCSClusterNode.class, sb.toString(), "status",
			String.valueOf(status));

		List<LCSClusterNode> lcsClusterNodes = new ArrayList<LCSClusterNode>();

		for (LCSClusterNode lcsClusterNode : remoteLCSClusterNodes) {
			lcsClusterNodes.add(lcsClusterNode);
		}

		return lcsClusterNodes;
	}

	@Override
	public void mergeStatus(String key, int status)
		throws JSONWebServiceInvocationException {

		_jsonWebServiceClient.doPut(
			_URL_LCS_CLUSTER_NODE, "key", key, "status", String.valueOf(status),
			"merge", String.valueOf(true));
	}

	@Override
	public void updateBuildNumber(String key, int buildNumber)
		throws JSONWebServiceInvocationException {

		_jsonWebServiceClient.doPut(
			_URL_LCS_CLUSTER_NODE + "/updateBuildNumber", "key", key,
			"buildNumber", String.valueOf(buildNumber));
	}

	@Override
	public void updateStatus(String key, int status)
		throws JSONWebServiceInvocationException {

		_jsonWebServiceClient.doPut(
			_URL_LCS_CLUSTER_NODE, "key", key, "status", String.valueOf(status),
			"merge", String.valueOf(true));
	}

	@Override
	public void verifyLCSClusterEntryLCSClusterNodesPropertiesDifferences(
			String key)
		throws JSONWebServiceInvocationException {

		_jsonWebServiceClient.doPut(_URL_LCS_CLUSTER_NODE, "key", key);
	}

	@Override
	public void verifyLCSClusterNodeClusterLink(String key, String siblingKeys)
		throws JSONWebServiceInvocationException {

		_jsonWebServiceClient.doPut(
			_URL_LCS_CLUSTER_NODE, "key", key, "siblingKeys", siblingKeys);
	}

	protected void validate(long lcsClusterEntryId, String lcsClusterNodeName)
		throws DuplicateLCSClusterNodeNameException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
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