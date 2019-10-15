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