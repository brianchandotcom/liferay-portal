/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;
import javax.naming.InvalidNameException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapNameFactory {

	public static SafeLdapName from(Attribute attribute, int pos)
		throws NamingException {

		return new SafeLdapName((String)attribute.get(pos));
	}

	public static SafeLdapName from(Binding binding)
		throws InvalidNameException {

		return new SafeLdapName(binding.getNameInNamespace());
	}

	public static SafeLdapName from(
			String rdnName, String rdnValue, LdapName baseDNLdapName)
		throws InvalidNameException {

		List<Rdn> rdns = new ArrayList<>(baseDNLdapName.getRdns());

		rdns.add(new Rdn(rdnName, rdnValue));

		return new SafeLdapName(rdns);
	}

	public static SafeLdapName fromUnsafe(String fullDN)
		throws InvalidNameException {

		return new SafeLdapName(fullDN);
	}

}