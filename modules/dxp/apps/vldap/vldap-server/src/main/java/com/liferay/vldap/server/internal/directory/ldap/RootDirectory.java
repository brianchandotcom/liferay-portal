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

import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.vldap.server.internal.handler.BindLdapHandler;
import com.liferay.vldap.server.internal.util.OIDConstants;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class RootDirectory extends Directory {

	public RootDirectory() {
		addAttribute("namingcontexts", "o=Liferay");
		addAttribute("objectclass", "extensibleObject");
		addAttribute("objectclass", "top");
		addAttribute("subschemasubentry", "cn=" + SchemaDirectory.COMMON_NAME);
		addAttribute(
			"supportedfeatures", OIDConstants.ALL_OPERATIONAL_ATTRIBUTES);
		addAttribute("supportedcontrol", "1.2.840.113556.1.4.319");
		addAttribute("supportedldapversion", "3");
		addAttribute("supportedsaslmechanisms", BindLdapHandler.DIGEST_MD5);
		addAttribute("vendorname", "Liferay, Inc.");
		addAttribute("vendorversion", ReleaseInfo.getVersion());
	}

	@Override
	public String getName() {
		return StringPool.BLANK;
	}

}