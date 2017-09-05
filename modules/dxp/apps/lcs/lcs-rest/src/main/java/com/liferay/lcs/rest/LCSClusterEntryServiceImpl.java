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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class LCSClusterEntryServiceImpl
	extends BaseLCSServiceImpl implements LCSClusterEntryService {

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
			return doPostToObject(
				LCSClusterEntryImpl.class, _URL_LCS_CLUSTER_ENTRY,
				"lcsProjectId", String.valueOf(lcsProjectId), "name", name,
				"description", description, "location", location,
				"subscriptionType", subscriptionType, "type",
				String.valueOf(type));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public LCSClusterEntry getLCSClusterEntry(long lcsClusterEntryId) {
		try {
			return doGetToObject(
				LCSClusterEntryImpl.class, _URL_LCS_CLUSTER_ENTRY,
				"lcsClusterEntryId", String.valueOf(lcsClusterEntryId));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public List<LCSClusterEntry> getLCSProjectManageableLCSClusterEntries(
		long lcsProjectId, int localLCSClusterEntryType) {

		List<LCSClusterEntryImpl> remoteLcsClusterEntries = null;

		try {
			remoteLcsClusterEntries = doGetToList(
				LCSClusterEntryImpl.class, _URL_LCS_CLUSTER_ENTRY,
				"lcsProjectId", String.valueOf(lcsProjectId), "manage", "true");
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
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

		List<LCSClusterEntryImpl> remoteLcsClusterEntries = null;

		try {
			remoteLcsClusterEntries = doGetToList(
				LCSClusterEntryImpl.class, _URL_LCS_CLUSTER_ENTRY,
				"lcsProjectId", String.valueOf(lcsProjectId));
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}

		List<LCSClusterEntry> lcsClusterEntries =
			new ArrayList<LCSClusterEntry>();

		for (LCSClusterEntry lcsClusterEntry : remoteLcsClusterEntries) {
			lcsClusterEntries.add(lcsClusterEntry);
		}

		return lcsClusterEntries;
	}

	private static final String _URL_LCS_CLUSTER_ENTRY =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSClusterEntry";

}