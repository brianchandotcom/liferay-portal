/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.dummy;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

/**
 * @author Brian Wing Shun Chan
 */
public class DummyContext implements Context {

	@Override
	public Object addToEnvironment(String propName, Object propVal)
		throws NamingException {

		throw new OperationNotSupportedException();
	}

	@Override
	public void bind(Name name, Object object) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void bind(String name, Object object) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void close() throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public String composeName(String name, String prefix)
		throws NamingException {

		throw new OperationNotSupportedException();
	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void destroySubcontext(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name)
		throws NamingException {

		throw new OperationNotSupportedException();
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String name)
		throws NamingException {

		throw new OperationNotSupportedException();
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name name)
		throws NamingException {

		throw new OperationNotSupportedException();
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name)
		throws NamingException {

		throw new OperationNotSupportedException();
	}

	@Override
	public Object lookup(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Object lookup(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void rebind(Name name, Object object) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void rebind(String name, Object object) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public Object removeFromEnvironment(String propName)
		throws NamingException {

		throw new OperationNotSupportedException();
	}

	@Override
	public void rename(Name oldName, Name newName) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void unbind(Name name) throws NamingException {
		throw new OperationNotSupportedException();
	}

	@Override
	public void unbind(String name) throws NamingException {
		throw new OperationNotSupportedException();
	}

}