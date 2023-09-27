/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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