/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.jndi;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.pacl.PACLPolicy;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLContext implements Context {

	public PACLContext(Context context, PACLPolicy paclPolicy) {
		_context = context;
		_paclPolicy = paclPolicy;
	}

	public Object addToEnvironment(String propName, Object propVal)
		throws NamingException {

		return _context.addToEnvironment(propName, propVal);
	}

	public void bind(Name name, Object obj) throws NamingException {
		checkPACLPolicy(name);

		_context.bind(name, obj);
	}

	public void bind(String name, Object obj) throws NamingException {
		checkPACLPolicy(name);

		_context.bind(name, obj);
	}

	public void close() throws NamingException {
		_context.close();
	}

	public Name composeName(Name name, Name prefix) throws NamingException {
		checkPACLPolicy(name);

		return _context.composeName(name, prefix);
	}

	public String composeName(String name, String prefix)
		throws NamingException {

		checkPACLPolicy(name);

		return _context.composeName(name, prefix);
	}

	public Context createSubcontext(Name name) throws NamingException {
		checkPACLPolicy(name);

		return _context.createSubcontext(name);
	}

	public Context createSubcontext(String name) throws NamingException {
		checkPACLPolicy(name);

		return _context.createSubcontext(name);
	}

	public void destroySubcontext(Name name) throws NamingException {
		checkPACLPolicy(name);

		_context.destroySubcontext(name);
	}

	public void destroySubcontext(String name) throws NamingException {
		checkPACLPolicy(name);

		_context.destroySubcontext(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Context)) {
			return false;
		}

		Context context = (Context)obj;

		if (Validator.equals(_context, context)) {
			return true;
		}

		return false;
	}

	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return _context.getEnvironment();
	}

	public String getNameInNamespace() throws NamingException {
		return _context.getNameInNamespace();
	}

	public NameParser getNameParser(Name name) throws NamingException {
		checkPACLPolicy(name);

		return _context.getNameParser(name);
	}

	public NameParser getNameParser(String name) throws NamingException {
		checkPACLPolicy(name);

		return _context.getNameParser(name);
	}

	@Override
	public int hashCode() {
		return _context.hashCode();
	}

	public NamingEnumeration<NameClassPair> list(Name name)
		throws NamingException {

		checkPACLPolicy(name);

		return _context.list(name);
	}

	public NamingEnumeration<NameClassPair> list(String name)
		throws NamingException {

		checkPACLPolicy(name);

		return _context.list(name);
	}

	public NamingEnumeration<Binding> listBindings(Name name)
		throws NamingException {

		checkPACLPolicy(name);

		return _context.listBindings(name);
	}

	public NamingEnumeration<Binding> listBindings(String name)
		throws NamingException {

		checkPACLPolicy(name);

		return _context.listBindings(name);
	}

	public Object lookup(Name name) throws NamingException {
		checkPACLPolicy(name);

		return _context.lookup(name);
	}

	public Object lookup(String name) throws NamingException {
		checkPACLPolicy(name);

		return _context.lookup(name);
	}

	public Object lookupLink(Name name) throws NamingException {
		checkPACLPolicy(name);

		return _context.lookupLink(name);
	}

	public Object lookupLink(String name) throws NamingException {
		checkPACLPolicy(name);

		return _context.lookupLink(name);
	}

	public void rebind(Name name, Object obj) throws NamingException {
		checkPACLPolicy(name);

		_context.rebind(name, obj);
	}

	public void rebind(String name, Object obj) throws NamingException {
		checkPACLPolicy(name);

		_context.rebind(name, obj);
	}

	public Object removeFromEnvironment(String propName)
		throws NamingException {

		return _context.removeFromEnvironment(propName);
	}

	public void rename(Name oldName, Name newName) throws NamingException {
		checkPACLPolicy(oldName);
		checkPACLPolicy(newName);

		_context.rename(oldName, newName);
	}

	public void rename(String oldName, String newName) throws NamingException {
		checkPACLPolicy(oldName);
		checkPACLPolicy(newName);

		_context.rename(oldName, newName);
	}

	public void unbind(Name name) throws NamingException {
		checkPACLPolicy(name);

		_context.unbind(name);
	}

	public void unbind(String name) throws NamingException {
		checkPACLPolicy(name);

		_context.unbind(name);
	}

	protected void checkPACLPolicy(Name name) {
		checkPACLPolicy(name.toString());
	}

	protected void checkPACLPolicy(String name) {
		if (!_paclPolicy.hasJNDI(name)) {
			throw new SecurityException(
				"Attempted to use unapproved JNDI " + name);
		}
	}

	private Context _context;
	private PACLPolicy _paclPolicy;

}