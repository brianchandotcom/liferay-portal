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
 * @author Brian Wing Shun Chan
 */
public class TopDirectory extends Directory {

	public static final String NAME = "Liferay";

	public TopDirectory() {
		this(NAME);
	}

	public TopDirectory(String top) {
		addAttribute("objectclass", "organizationalUnit");
		addAttribute("objectclass", "top");
		addAttribute("o", top);

		_name = "o=".concat(LdapUtil.escape(top));
	}

	@Override
	protected String getName() {
		return _name;
	}

	private final String _name;

}