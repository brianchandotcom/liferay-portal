/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.authentication.ldap.web.internal.util;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.security.ldap.validator.LDAPFilterValidator;

/**
 * @author Shuyang Zhou
 */
public class LDAPFilterValidatorUtil {

	public static LDAPFilterValidator getLDAPFilterValidator() {
		return _ldapFilterValidatorSnapshot.get();
	}

	private static final Snapshot<LDAPFilterValidator>
		_ldapFilterValidatorSnapshot = new Snapshot<>(
			LDAPFilterValidatorUtil.class, LDAPFilterValidator.class);

}