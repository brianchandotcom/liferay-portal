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

package com.liferay.portal.security.ldap.internal;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.dummy.DummyDirContext;

import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

/**
 * @author Edward Han
 */
public class PortalLDAPContext extends DummyDirContext {

	public PortalLDAPContext(Attributes attributes) {
		_attributes = attributes;
	}

	public PortalLDAPContext(boolean ignoreCase) {
		_attributes = new BasicAttributes(ignoreCase);
	}

	public void addAttribute(String name, Object object) {
		_attributes.put(name, object);
	}

	public Attributes getAttributes() {
		return _attributes;
	}

	@Override
	public Attributes getAttributes(Name name) throws NamingException {
		return getAttributes(name.toString());
	}

	@Override
	public Attributes getAttributes(Name name, String[] ids)
		throws NamingException {

		return getAttributes(name.toString(), ids);
	}

	@Override
	public Attributes getAttributes(String name) throws NamingException {
		if (Validator.isNotNull(name)) {
			throw new NameNotFoundException();
		}

		return (Attributes)_attributes.clone();
	}

	@Override
	public Attributes getAttributes(String name, String[] ids)
		throws NamingException {

		if (Validator.isNotNull(name)) {
			throw new NameNotFoundException();
		}

		Attributes attributes = new BasicAttributes(true);

		for (String id : ids) {
			Attribute attribute = _attributes.get(id);

			if (attribute != null) {
				attributes.put(attribute);
			}
		}

		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		_attributes = attributes;
	}

	private Attributes _attributes;

}