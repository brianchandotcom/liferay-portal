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
public class SafeLdapName extends LdapName {

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

	protected SafeLdapName(List<Rdn> rdns) {
		super(rdns);
	}

	protected SafeLdapName(String name) throws InvalidNameException {
		super(name);
	}

}