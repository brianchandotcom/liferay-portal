/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.provisioning.client;

import com.liferay.osb.faro.provisioning.client.model.OSBAccountEntry;

import java.util.List;

/**
 * @author Matthew Kong
 */
public interface ProvisioningClient {

	public void addCorpProjectUsers(String corpProjectUuid, String[] userUuids)
		throws Exception;

	public void addUserCorpProjectRoles(
			String corpProjectUuid, String[] userUuids, String roleName)
		throws Exception;

	public void deleteUserCorpProjectRoles(
			String corpProjectUuid, String[] userUuids, String roleName)
		throws Exception;

	public List<OSBAccountEntry> getOSBAccountEntries(
			String userUuid, String[] productEntryIds)
		throws Exception;

	public OSBAccountEntry getOSBAccountEntry(String corpProjectUuid)
		throws Exception;

	public void unsetCorpProjectUsers(
			String corpProjectUuid, String[] userUuids)
		throws Exception;

}