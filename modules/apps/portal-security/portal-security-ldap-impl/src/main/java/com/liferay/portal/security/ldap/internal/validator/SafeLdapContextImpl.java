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

package com.liferay.portal.security.ldap.internal.validator;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.ldap.SafeLdapContext;
import com.liferay.portal.security.ldap.util.LDAPUtil;
import com.liferay.portal.security.ldap.validator.LDAPFilter;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.ExtendedRequest;
import javax.naming.ldap.ExtendedResponse;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.LdapName;

/**
 * @author Tomas Polesovsky
 */
public class SafeLdapContextImpl implements SafeLdapContext {

	public SafeLdapContextImpl(LdapContext ldapContext) {
		_ldapContext = ldapContext;
	}

	@Override
	public Object addToEnvironment(String propName, Object propVal)
		throws NamingException {

		return _ldapContext.addToEnvironment(propName, propVal);
	}

	@Override
	public void bind(Name name, Object obj) throws NamingException {
		_ldapContext.bind(name, obj);
	}

	@Override
	public void bind(Name name, Object obj, Attributes attrs)
		throws NamingException {

		_ldapContext.bind(name, obj, attrs);
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {
		warn();

		_ldapContext.bind(LDAPUtil.asLdapName(name), obj);
	}

	@Override
	public void bind(String name, Object obj, Attributes attrs)
		throws NamingException {

		warn();

		_ldapContext.bind(LDAPUtil.asLdapName(name), obj, attrs);
	}

	@Override
	public void close() throws NamingException {
		_ldapContext.close();
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {
		return _ldapContext.composeName(name, prefix);
	}

	@Override
	public String composeName(String name, String prefix)
		throws NamingException {

		warn();

		return _ldapContext.composeName(name, prefix);
	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		return _ldapContext.createSubcontext(name);
	}

	@Override
	public DirContext createSubcontext(Name name, Attributes attrs)
		throws NamingException {

		return _ldapContext.createSubcontext(name, attrs);
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		warn();

		return _ldapContext.createSubcontext(LDAPUtil.asLdapName(name));
	}

	@Override
	public DirContext createSubcontext(String name, Attributes attrs)
		throws NamingException {

		warn();

		return _ldapContext.createSubcontext(LDAPUtil.asLdapName(name), attrs);
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		_ldapContext.destroySubcontext(name);
	}

	@Override
	public void destroySubcontext(String name) throws NamingException {
		warn();

		_ldapContext.destroySubcontext(LDAPUtil.asLdapName(name));
	}

	@Override
	public ExtendedResponse extendedOperation(ExtendedRequest request)
		throws NamingException {

		return _ldapContext.extendedOperation(request);
	}

	@Override
	public Attributes getAttributes(Name name) throws NamingException {
		return _ldapContext.getAttributes(name);
	}

	@Override
	public Attributes getAttributes(Name name, String[] attrIds)
		throws NamingException {

		return _ldapContext.getAttributes(name, attrIds);
	}

	@Override
	public Attributes getAttributes(String name) throws NamingException {
		warn();

		return _ldapContext.getAttributes(LDAPUtil.asLdapName(name));
	}

	@Override
	public Attributes getAttributes(String name, String[] attrIds)
		throws NamingException {

		warn();

		return _ldapContext.getAttributes(LDAPUtil.asLdapName(name), attrIds);
	}

	@Override
	public Control[] getConnectControls() throws NamingException {
		return _ldapContext.getConnectControls();
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return _ldapContext.getEnvironment();
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		return _ldapContext.getNameInNamespace();
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {
		return _ldapContext.getNameParser(name);
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		warn();

		return _ldapContext.getNameParser(LDAPUtil.asLdapName(name));
	}

	@Override
	public Control[] getRequestControls() throws NamingException {
		return _ldapContext.getRequestControls();
	}

	@Override
	public Control[] getResponseControls() throws NamingException {
		return _ldapContext.getResponseControls();
	}

	@Override
	public DirContext getSchema(Name name) throws NamingException {
		return _ldapContext.getSchema(name);
	}

	@Override
	public DirContext getSchema(String name) throws NamingException {
		warn();

		return _ldapContext.getSchema(LDAPUtil.asLdapName(name));
	}

	@Override
	public DirContext getSchemaClassDefinition(Name name)
		throws NamingException {

		return _ldapContext.getSchemaClassDefinition(name);
	}

	@Override
	public DirContext getSchemaClassDefinition(String name)
		throws NamingException {

		warn();

		return _ldapContext.getSchemaClassDefinition(LDAPUtil.asLdapName(name));
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name)
		throws NamingException {

		return _ldapContext.list(name);
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String name)
		throws NamingException {

		warn();

		return _ldapContext.list(LDAPUtil.asLdapName(name));
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name name)
		throws NamingException {

		return _ldapContext.listBindings(name);
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name)
		throws NamingException {

		warn();

		return _ldapContext.listBindings(LDAPUtil.asLdapName(name));
	}

	@Override
	public Object lookup(Name name) throws NamingException {
		return _ldapContext.lookup(name);
	}

	@Override
	public Object lookup(String name) throws NamingException {
		warn();

		return _ldapContext.lookup(LDAPUtil.asLdapName(name));
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		return _ldapContext.lookupLink(name);
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		warn();

		return _ldapContext.lookupLink(LDAPUtil.asLdapName(name));
	}

	@Override
	public void modifyAttributes(Name name, int modOp, Attributes attrs)
		throws NamingException {

		_ldapContext.modifyAttributes(name, modOp, attrs);
	}

	@Override
	public void modifyAttributes(Name name, ModificationItem[] mods)
		throws NamingException {

		_ldapContext.modifyAttributes(name, mods);
	}

	@Override
	public void modifyAttributes(String name, int modOp, Attributes attrs)
		throws NamingException {

		warn();

		_ldapContext.modifyAttributes(LDAPUtil.asLdapName(name), modOp, attrs);
	}

	@Override
	public void modifyAttributes(String name, ModificationItem[] mods)
		throws NamingException {

		warn();

		_ldapContext.modifyAttributes(LDAPUtil.asLdapName(name), mods);
	}

	@Override
	public LdapContext newInstance(Control[] requestControls)
		throws NamingException {

		return _ldapContext.newInstance(requestControls);
	}

	@Override
	public void rebind(Name name, Object obj) throws NamingException {
		_ldapContext.rebind(name, obj);
	}

	@Override
	public void rebind(Name name, Object obj, Attributes attrs)
		throws NamingException {

		_ldapContext.rebind(name, obj, attrs);
	}

	@Override
	public void rebind(String name, Object obj) throws NamingException {
		warn();

		_ldapContext.rebind(LDAPUtil.asLdapName(name), obj);
	}

	@Override
	public void rebind(String name, Object obj, Attributes attrs)
		throws NamingException {

		warn();

		_ldapContext.rebind(LDAPUtil.asLdapName(name), obj, attrs);
	}

	@Override
	public void reconnect(Control[] connCtls) throws NamingException {
		_ldapContext.reconnect(connCtls);
	}

	@Override
	public Object removeFromEnvironment(String propName)
		throws NamingException {

		return _ldapContext.removeFromEnvironment(propName);
	}

	@Override
	public void rename(Name oldName, Name newName) throws NamingException {
		_ldapContext.rename(oldName, newName);
	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {
		warn();

		_ldapContext.rename(
			LDAPUtil.asLdapName(oldName), LDAPUtil.asLdapName(newName));
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			LdapName ldapName, LDAPFilter ldapFilter,
			SearchControls searchControls)
		throws NamingException {

		return _ldapContext.search(
			ldapName, ldapFilter.generateFilter(), ldapFilter.getArguments(),
			searchControls);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			Name name, Attributes matchingAttributes)
		throws NamingException {

		return _ldapContext.search(name, matchingAttributes);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			Name name, Attributes matchingAttributes,
			String[] attributesToReturn)
		throws NamingException {

		return _ldapContext.search(
			name, matchingAttributes, attributesToReturn);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			Name name, String filterExpr, Object[] filterArgs,
			SearchControls cons)
		throws NamingException {

		warn();

		return _ldapContext.search(name, filterExpr, filterArgs, cons);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			Name name, String filter, SearchControls cons)
		throws NamingException {

		warn();

		return _ldapContext.search(name, filter, cons);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			String name, Attributes matchingAttributes)
		throws NamingException {

		warn();

		return _ldapContext.search(
			LDAPUtil.asLdapName(name), matchingAttributes);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			String name, Attributes matchingAttributes,
			String[] attributesToReturn)
		throws NamingException {

		warn();

		return _ldapContext.search(
			LDAPUtil.asLdapName(name), matchingAttributes, attributesToReturn);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			String name, String filter, Object[] filterArgs,
			SearchControls cons)
		throws NamingException {

		warn();

		return _ldapContext.search(
			LDAPUtil.asLdapName(name), filter, filterArgs, cons);
	}

	@Override
	public NamingEnumeration<SearchResult> search(
			String name, String filter, SearchControls cons)
		throws NamingException {

		warn();

		return _ldapContext.search(LDAPUtil.asLdapName(name), filter, cons);
	}

	@Override
	public void setRequestControls(Control[] requestControls)
		throws NamingException {

		_ldapContext.setRequestControls(requestControls);
	}

	@Override
	public String toString() {
		return "SafeLDAPContextImpl{_ldapContext=" + _ldapContext + '}';
	}

	@Override
	public void unbind(Name name) throws NamingException {
		_ldapContext.unbind(name);
	}

	@Override
	public void unbind(String name) throws NamingException {
		warn();

		_ldapContext.unbind(LDAPUtil.asLdapName(name));
	}

	protected void warn() {
		if (_log.isDebugEnabled()) {
			_log.debug("Unsafe LDAP Search method used", new Exception());
		}
		else if (_log.isWarnEnabled()) {
			_log.warn("Unsafe LDAP Search method used");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SafeLdapContextImpl.class);

	private final LdapContext _ldapContext;

}