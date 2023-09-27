/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.authentication.ldap.web.internal.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.security.ldap.SafePortalLDAP;

/**
 * @author Edward C. Han
 */
public class SafePortalLDAPUtil {

	public static SafePortalLDAP getSafePortalLDAP() {
		return _safePortalLDAPSnapshot.get();
	}

	private static final Snapshot<SafePortalLDAP> _safePortalLDAPSnapshot =
		new Snapshot<>(SafePortalLDAPUtil.class, SafePortalLDAP.class);

}