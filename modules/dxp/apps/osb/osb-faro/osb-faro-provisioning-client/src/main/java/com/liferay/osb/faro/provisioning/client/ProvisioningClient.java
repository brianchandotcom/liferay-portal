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