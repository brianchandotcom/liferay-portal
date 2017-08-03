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

import com.liferay.vldap.server.internal.util.LdapUtil;

/**
 * @author Jonathan Potter
 */
public class SchemaDirectory extends Directory {

	public static final String COMMON_NAME = "subschema";

	public SchemaDirectory() {
		this(COMMON_NAME);
	}

	public SchemaDirectory(String commonName) {
		addAttribute("objectclass", "subschema");
		addAttribute("objectclass", "top");
		addAttribute("cn", commonName);

		_name = "cn=".concat(LdapUtil.escape(commonName));
	}

	@Override
	protected String getName() {
		return _name;
	}

	private final String _name;

}