/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.Visitor;

/**
 * @author Brian Wing Shun Chan
 */
public class AttributeImpl extends NodeImpl implements Attribute {

	public AttributeImpl(org.dom4j.Attribute attribute) {
		super(attribute);

		_attribute = attribute;
	}

	@Override
	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitAttribute(this);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AttributeImpl)) {
			return false;
		}

		AttributeImpl attributeImpl = (AttributeImpl)object;

		org.dom4j.Attribute attribute = attributeImpl.getWrappedAttribute();

		return _attribute.equals(attribute);
	}

	@Override
	public Object getData() {
		return _attribute.getData();
	}

	@Override
	public Namespace getNamespace() {
		org.dom4j.Namespace namespace = _attribute.getNamespace();

		if (namespace == null) {
			return null;
		}

		return new NamespaceImpl(namespace);
	}

	@Override
	public String getNamespacePrefix() {
		return _attribute.getNamespacePrefix();
	}

	@Override
	public String getNamespaceURI() {
		return _attribute.getNamespaceURI();
	}

	@Override
	public QName getQName() {
		org.dom4j.QName qName = _attribute.getQName();

		if (qName == null) {
			return null;
		}

		return new QNameImpl(qName);
	}

	@Override
	public String getQualifiedName() {
		return _attribute.getQualifiedName();
	}

	@Override
	public String getValue() {
		return _attribute.getValue();
	}

	public org.dom4j.Attribute getWrappedAttribute() {
		return _attribute;
	}

	@Override
	public int hashCode() {
		return _attribute.hashCode();
	}

	@Override
	public void setData(Object data) {
		_attribute.setData(data);
	}

	@Override
	public void setNamespace(Namespace namespace) {
		NamespaceImpl namespaceImpl = (NamespaceImpl)namespace;

		_attribute.setNamespace(namespaceImpl.getWrappedNamespace());
	}

	@Override
	public void setValue(String value) {
		_attribute.setValue(value);
	}

	@Override
	public String toString() {
		return _attribute.toString();
	}

	private final org.dom4j.Attribute _attribute;

}