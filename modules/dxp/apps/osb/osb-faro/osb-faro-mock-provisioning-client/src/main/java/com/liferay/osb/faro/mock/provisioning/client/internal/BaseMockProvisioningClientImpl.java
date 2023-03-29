/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.mock.provisioning.client.internal;

import com.liferay.osb.faro.provisioning.client.ProvisioningClient;
import com.liferay.osb.faro.provisioning.client.model.OSBAccountEntry;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
public abstract class BaseMockProvisioningClientImpl
	implements ProvisioningClient {

	@Override
	public void addCorpProjectUsers(String corpProjectUuid, String[] userUuids)
		throws Exception {

		provisioningClient.addCorpProjectUsers(corpProjectUuid, userUuids);
	}

	@Override
	public void addUserCorpProjectRoles(
			String corpProjectUuid, String[] userUuids, String roleName)
		throws Exception {

		provisioningClient.addUserCorpProjectRoles(
			corpProjectUuid, userUuids, roleName);
	}

	@Override
	public void deleteUserCorpProjectRoles(
			String corpProjectUuid, String[] userUuids, String roleName)
		throws Exception {

		provisioningClient.deleteUserCorpProjectRoles(
			corpProjectUuid, userUuids, roleName);
	}

	@Override
	public List<OSBAccountEntry> getOSBAccountEntries(
			String userUuid, String[] productEntryIds)
		throws Exception {

		return provisioningClient.getOSBAccountEntries(
			userUuid, productEntryIds);
	}

	@Override
	public OSBAccountEntry getOSBAccountEntry(String corpProjectUuid)
		throws Exception {

		return provisioningClient.getOSBAccountEntry(corpProjectUuid);
	}

	@Override
	public void unsetCorpProjectUsers(
			String corpProjectUuid, String[] userUuids)
		throws Exception {

		provisioningClient.unsetCorpProjectUsers(corpProjectUuid, userUuids);
	}

	@Reference(
		target = "(component.name=com.liferay.osb.faro.provisioning.client.internal.ProvisioningClientImpl)"
	)
	protected ProvisioningClient provisioningClient;

}