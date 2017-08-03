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