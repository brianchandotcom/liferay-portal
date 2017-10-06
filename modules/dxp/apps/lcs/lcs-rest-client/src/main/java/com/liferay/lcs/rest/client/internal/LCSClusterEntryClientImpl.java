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

import com.liferay.lcs.rest.client.LCSClusterEntry;
import com.liferay.lcs.rest.client.LCSClusterEntryClient;
import com.liferay.lcs.rest.client.exception.DuplicateLCSClusterEntryNameException;
import com.liferay.lcs.rest.client.exception.RequiredLCSClusterEntryNameException;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
@Component(immediate = true, service = LCSClusterEntryClient.class)
public class LCSClusterEntryClientImpl
	extends BaseLCSServiceImpl implements LCSClusterEntryClient {

	@Override
	public LCSClusterEntry addLCSClusterEntry(
		long lcsProjectId, String name, String description, String location,
		String subscriptionType, int type) {

		validate(lcsProjectId, name);

		if ((description != null) && description.equals("")) {
			description = null;
		}

		if ((location != null) && location.equals("")) {
			location = null;
		}

		try {
			return jsonWebServiceClient.doPostToObject(
				LCSClusterEntry.class, _URL_LCS_CLUSTER_ENTRY, "lcsProjectId",
				String.valueOf(lcsProjectId), "name", name, "description",
				description, "location", location, "subscriptionType",
				subscriptionType, "type", String.valueOf(type));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
		catch (JSONWebServiceSerializeException jsonwsse) {
			throw new RuntimeException(jsonwsse);
		}
	}

	@Override
	public LCSClusterEntry getLCSClusterEntry(long lcsClusterEntryId) {
		try {
			return jsonWebServiceClient.doGetToObject(
				LCSClusterEntry.class,
				_URL_LCS_CLUSTER_ENTRY + StringPool.SLASH + lcsClusterEntryId);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
		catch (JSONWebServiceSerializeException jsonwsse) {
			throw new RuntimeException(jsonwsse);
		}
	}

	@Override
	public List<LCSClusterEntry> getLCSProjectManageableLCSClusterEntries(
		long lcsProjectId, int localLCSClusterEntryType) {

		List<LCSClusterEntry> remoteLcsClusterEntries = null;

		try {
			StringBuilder sb = new StringBuilder(5);

			sb.append(_URL_LCS_CLUSTER_ENTRY);
			sb.append("/find/");
			sb.append(lcsProjectId);
			sb.append(StringPool.SLASH);
			sb.append("true");

			remoteLcsClusterEntries = jsonWebServiceClient.doGetToList(
				LCSClusterEntry.class, sb.toString());
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
		catch (JSONWebServiceSerializeException jsonwsse) {
			throw new RuntimeException(jsonwsse);
		}

		List<LCSClusterEntry> lcsClusterEntries =
			new ArrayList<LCSClusterEntry>();

		for (LCSClusterEntry lcsClusterEntry : remoteLcsClusterEntries) {
			if (lcsClusterEntry.getType() != localLCSClusterEntryType) {
				continue;
			}

			lcsClusterEntries.add(lcsClusterEntry);
		}

		return lcsClusterEntries;
	}

	protected void validate(long lcsProjectId, String lcsClusterEntryName) {
		if ((lcsClusterEntryName == null) || lcsClusterEntryName.equals("")) {
			throw new RequiredLCSClusterEntryNameException();
		}

		List<LCSClusterEntry> lcsClusterEntries =
			_getLCSProjectLCSClusterEntries(lcsProjectId);

		for (LCSClusterEntry lcsClusterEntry : lcsClusterEntries) {
			if (lcsClusterEntryName.equalsIgnoreCase(
					lcsClusterEntry.getName())) {

				throw new DuplicateLCSClusterEntryNameException();
			}
		}
	}

	private List<LCSClusterEntry> _getLCSProjectLCSClusterEntries(
		long lcsProjectId) {

		List<LCSClusterEntry> remoteLcsClusterEntries = null;

		try {
			remoteLcsClusterEntries = jsonWebServiceClient.doGetToList(
				LCSClusterEntry.class,
				_URL_LCS_CLUSTER_ENTRY + "/find/" + lcsProjectId);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
		catch (JSONWebServiceSerializeException jsonwsse) {
			throw new RuntimeException(jsonwsse);
		}

		List<LCSClusterEntry> lcsClusterEntries =
			new ArrayList<LCSClusterEntry>();

		for (LCSClusterEntry lcsClusterEntry : remoteLcsClusterEntries) {
			lcsClusterEntries.add(lcsClusterEntry);
		}

		return lcsClusterEntries;
	}

	private static final String _URL_LCS_CLUSTER_ENTRY =
		"/o/osb-lcs-rest/LCSClusterEntry";

}