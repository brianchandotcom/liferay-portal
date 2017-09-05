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
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class LCSClusterNodeServiceImpl
	extends BaseLCSServiceImpl implements LCSClusterNodeService {

	@Override
	public LCSClusterNode addLCSClusterNode(
		long lcsClusterEntryId, String name, String description,
		int buildNumber, long heartbeatInterval, long lastHeartbeat, String key,
		String location, int patchingToolVersion, String portalEdition,
		int processorCoresTotal, String protocolVersion) {

		validate(lcsClusterEntryId, name);

		if ((description != null) && description.equals("")) {
			description = null;
		}

		if ((location != null) && location.equals("")) {
			location = null;
		}

		try {
			return doPostToObject(
				LCSClusterNodeImpl.class, _URL_LCS_CLUSTER_NODE, "buildNumber",
				String.valueOf(buildNumber), "name", name, "description",
				description, "heartbeatInterval",
				String.valueOf(heartbeatInterval), "lastHeartbeat",
				String.valueOf(lastHeartbeat), "key", key, "lcsClusterEntryId",
				String.valueOf(lcsClusterEntryId), "location", location,
				"patchingToolVersion", String.valueOf(patchingToolVersion),
				"portalEdition", portalEdition, "processorCoresTotal",
				String.valueOf(processorCoresTotal), "protocolVersion",
				protocolVersion);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (RESTError.getRESTError(jsonwsie) ==
					RESTError.NO_SUCH_LCS_SUBSCRIPTION_ENTRY) {

				throw new NoSuchLCSSubscriptionEntryException(jsonwsie);
			}

			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public LCSClusterNode addLCSClusterNode(
		long lcsClusterEntryId, String name, String description,
		int buildNumber, String key, String location, int processorCoresTotal) {

		validate(lcsClusterEntryId, name);

		if ((description != null) && description.equals("")) {
			description = null;
		}

		if ((location != null) && location.equals("")) {
			location = null;
		}

		try {
			return doPostToObject(
				LCSClusterNodeImpl.class, _URL_LCS_CLUSTER_NODE, "buildNumber",
				String.valueOf(buildNumber), "name", name, "description",
				description, "key", key, "lcsClusterEntryId",
				String.valueOf(lcsClusterEntryId), "location", location,
				"processorCoresTotal", String.valueOf(processorCoresTotal));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (RESTError.getRESTError(jsonwsie) ==
					RESTError.NO_SUCH_LCS_SUBSCRIPTION_ENTRY) {

				throw new NoSuchLCSSubscriptionEntryException(jsonwsie);
			}

			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public LCSClusterNode fetchLCSClusterNode(String key) {
		try {
			return doGetToObject(
				LCSClusterNodeImpl.class, _URL_LCS_CLUSTER_NODE, "key", key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			if (jsonwsie.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
				return null;
			}

			throw new RuntimeException(jsonwsie);
		}
		catch (JSONWebServiceTransportException jsonwste) {
			throw new RuntimeException(jsonwste);
		}
	}

	@Override
	public List<LCSClusterNode> getLCSClusterEntryLCSClusterNodes(
		long lcsClusterEntryId) {

		List<LCSClusterNodeImpl> remoteLCSClusterNodes = null;

		try {
			remoteLCSClusterNodes = doGetToList(
				LCSClusterNodeImpl.class, _URL_LCS_CLUSTER_NODE,
				"lcsClusterEntryId", String.valueOf(lcsClusterEntryId));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}

		List<LCSClusterNode> lcsClusterNodes = new ArrayList<LCSClusterNode>();

		for (LCSClusterNode lcsClusterNode : remoteLCSClusterNodes) {
			lcsClusterNodes.add(lcsClusterNode);
		}

		return lcsClusterNodes;
	}

	@Override
	public List<LCSClusterNode> getLCSClusterNodes(
		int status, int start, int end) {

		List<LCSClusterNodeImpl> remoteLCSClusterNodes = null;

		try {
			remoteLCSClusterNodes = doGetToList(
				LCSClusterNodeImpl.class, _URL_LCS_CLUSTER_NODE, "status",
				String.valueOf(status), "start", String.valueOf(start), "end",
				String.valueOf(end));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}

		List<LCSClusterNode> lcsClusterNodes = new ArrayList<LCSClusterNode>();

		for (LCSClusterNode lcsClusterNode : remoteLCSClusterNodes) {
			lcsClusterNodes.add(lcsClusterNode);
		}

		return lcsClusterNodes;
	}

	@Override
	public void mergeStatus(String key, int status) {
		try {
			doPut(
				_URL_LCS_CLUSTER_NODE, "key", key, "status",
				String.valueOf(status), "andOperator", String.valueOf(false));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void updateBuildNumber(String key, int buildNumber) {
		try {
			doPut(
				_URL_LCS_CLUSTER_NODE, "key", key, "buildNumber",
				String.valueOf(buildNumber));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void updateConfigurationModifiedDate(String key) {
		try {
			doPut(
				_URL_LCS_CLUSTER_NODE, "key", key, "configurationModifiedDate",
				String.valueOf(System.currentTimeMillis()));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void updateStatus(String key, int status) {
		try {
			doPut(
				_URL_LCS_CLUSTER_NODE, "key", key, "status",
				String.valueOf(status), "andOperator", String.valueOf(true));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void verifyLCSClusterEntryLCSClusterNodesPropertiesDifferences(
		String key) {

		try {
			doPut(_URL_LCS_CLUSTER_NODE, "key", key);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public void verifyLCSClusterNodeClusterLink(
		String key, String siblingKeys) {

		try {
			doPut(
				_URL_LCS_CLUSTER_NODE, "key", key, "siblingKeys", siblingKeys);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	protected void validate(long lcsClusterEntryId, String lcsClusterNodeName) {
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
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSClusterNode";

}