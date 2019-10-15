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

package com.liferay.vldap.server.internal.directory.ldap;

/**
 * @author Minhchau Dang
 */
public class SambaGroup {

	public SambaGroup(String name, String sambaSID, String gidNumber) {
		_name = name;
		_sambaSID = sambaSID;
		_gidNumber = gidNumber;
	}

	public String getGIDNumber() {
		return _gidNumber;
	}

	public String getName() {
		return _name;
	}

	public String getSambaSID() {
		return _sambaSID;
	}

	private final String _gidNumber;
	private final String _name;
	private final String _sambaSID;

}