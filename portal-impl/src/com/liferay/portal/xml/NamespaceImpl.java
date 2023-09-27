/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NamespaceImpl)) {
			return false;
		}

		NamespaceImpl namespaceImpl = (NamespaceImpl)object;

		org.dom4j.Namespace namespace = namespaceImpl.getWrappedNamespace();

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

	private final org.dom4j.Namespace _namespace;

}