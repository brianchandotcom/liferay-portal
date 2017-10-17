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

package com.liferay.lcs.rest.client;

import com.liferay.lcs.rest.client.internal.LCSClusterEntryClientImpl;
import com.liferay.lcs.rest.client.internal.LCSClusterEntryTokenClientImpl;
import com.liferay.lcs.rest.client.internal.LCSClusterNodeClientImpl;
import com.liferay.lcs.rest.client.internal.LCSClusterNodeUptimeClientImpl;
import com.liferay.lcs.rest.client.internal.LCSMembersClientImpl;
import com.liferay.lcs.rest.client.internal.LCSMessageClientImpl;
import com.liferay.lcs.rest.client.internal.LCSMetadataClientImpl;
import com.liferay.lcs.rest.client.internal.LCSPatchingAdvisorClientImpl;
import com.liferay.lcs.rest.client.internal.LCSProjectClientImpl;
import com.liferay.lcs.rest.client.internal.LCSRoleClientImpl;
import com.liferay.lcs.rest.client.internal.LCSSubscriptionEntryClientImpl;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;

/**
 * @author Peter Shin
 */
public class LCSClientUtil {

	public static LCSClusterEntryClient createLCSClusterEntryClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSClusterEntryClientImpl lcsClusterEntryClientImpl =
			new LCSClusterEntryClientImpl();

		lcsClusterEntryClientImpl.setJSONWebServiceClient(jsonWebServiceClient);

		return lcsClusterEntryClientImpl;
	}

	public static LCSClusterEntryTokenClient createLCSClusterEntryTokenClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSClusterEntryTokenClientImpl lcsClusterEntryTokenClientImpl =
			new LCSClusterEntryTokenClientImpl();

		lcsClusterEntryTokenClientImpl.setJSONWebServiceClient(
			jsonWebServiceClient);

		return lcsClusterEntryTokenClientImpl;
	}

	public static LCSClusterNodeClient createLCSClusterNodeClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSClusterNodeClientImpl lcsClusterNodeClientImpl =
			new LCSClusterNodeClientImpl();

		lcsClusterNodeClientImpl.setJSONWebServiceClient(jsonWebServiceClient);

		return lcsClusterNodeClientImpl;
	}

	public static LCSClusterNodeUptimeClient createLCSClusterNodeUptimeClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSClusterNodeUptimeClientImpl lcsClusterNodeUptimeClientImpl =
			new LCSClusterNodeUptimeClientImpl();

		lcsClusterNodeUptimeClientImpl.setJSONWebServiceClient(
			jsonWebServiceClient);

		return lcsClusterNodeUptimeClientImpl;
	}

	public static LCSMembersClient createLCSMembersClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSMembersClientImpl lcsMembersClientImpl = new LCSMembersClientImpl();

		lcsMembersClientImpl.setJSONWebServiceClient(jsonWebServiceClient);

		return lcsMembersClientImpl;
	}

	public static LCSMessageClient createLCSMessageClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSMessageClientImpl lcsMessageClientImpl = new LCSMessageClientImpl();

		lcsMessageClientImpl.setJSONWebServiceClient(jsonWebServiceClient);

		return lcsMessageClientImpl;
	}

	public static LCSMetadataClient createLCSMetadataClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSMetadataClientImpl lcsMetadataClientImpl =
			new LCSMetadataClientImpl();

		lcsMetadataClientImpl.setJSONWebServiceClient(jsonWebServiceClient);

		return lcsMetadataClientImpl;
	}

	public static LCSPatchingAdvisorClient createLCSPatchingAdvisorClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSPatchingAdvisorClientImpl lcsPatchingAdvisorClientImpl =
			new LCSPatchingAdvisorClientImpl();

		lcsPatchingAdvisorClientImpl.setJSONWebServiceClient(
			jsonWebServiceClient);

		return lcsPatchingAdvisorClientImpl;
	}

	public static LCSProjectClient createLCSProjectClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSProjectClientImpl lcsProjectClientImpl = new LCSProjectClientImpl();

		lcsProjectClientImpl.setJSONWebServiceClient(jsonWebServiceClient);

		return lcsProjectClientImpl;
	}

	public static LCSRoleClient createLCSRoleClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSRoleClientImpl lcsRoleClientImpl = new LCSRoleClientImpl();

		lcsRoleClientImpl.setJSONWebServiceClient(jsonWebServiceClient);

		return lcsRoleClientImpl;
	}

	public static LCSSubscriptionEntryClient createLCSSubscriptionEntryClient(
		JSONWebServiceClient jsonWebServiceClient) {

		LCSSubscriptionEntryClientImpl lcsSubscriptionEntryClientImpl =
			new LCSSubscriptionEntryClientImpl();

		lcsSubscriptionEntryClientImpl.setJSONWebServiceClient(
			jsonWebServiceClient);

		return lcsSubscriptionEntryClientImpl;
	}

}