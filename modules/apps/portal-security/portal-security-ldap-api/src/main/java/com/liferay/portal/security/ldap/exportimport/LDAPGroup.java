/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.exportimport;

/**
 * @author Michael C. Han
 */
public class LDAPGroup {

	public long getCompanyId() {
		return _companyId;
	}

	public String getDescription() {
		return _description;
	}

	public String getGroupName() {
		return _groupName;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	private long _companyId;
	private String _description;
	private String _groupName;

}