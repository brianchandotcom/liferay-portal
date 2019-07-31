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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ReleaseInfo;
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