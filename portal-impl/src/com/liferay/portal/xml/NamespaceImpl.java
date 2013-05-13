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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Visitor;

/**
 * @author Brian Wing Shun Chan
 */
public class NamespaceImpl extends NodeImpl implements Namespace {

	public NamespaceImpl(org.dom4j.Namespace namespace) {
		super(namespace);

		_namespace = namespace;
	}

	@Override
	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitNamespace(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof NamespaceImpl)) {
			return false;
		}

		org.dom4j.Namespace namespace =
			((NamespaceImpl)obj).getWrappedNamespace();

		return _namespace.equals(namespace);
	}

	@Override
	public short getNodeType() {
		return _namespace.getNodeType();
	}

	@Override
	public String getPrefix() {
		return _namespace.getPrefix();
	}

	@Override
	public String getURI() {
		return _namespace.getURI();
	}

	public org.dom4j.Namespace getWrappedNamespace() {
		return _namespace;
	}

	@Override
	public String getXPathNameStep() {
		return _namespace.getXPathNameStep();
	}

	@Override
	public int hashCode() {
		return _namespace.hashCode();
	}

	@Override
	public String toString() {
		return _namespace.toString();
	}

	private org.dom4j.Namespace _namespace;

}