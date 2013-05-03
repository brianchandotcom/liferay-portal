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
import com.liferay.portal.kernel.xml.QName;

/**
 * @author Brian Wing Shun Chan
 */
public class QNameImpl implements QName {

	public QNameImpl(org.dom4j.QName qName) {
		_qName = qName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof QNameImpl)) {
			return false;
		}

		org.dom4j.QName qName = ((QNameImpl)obj).getWrappedQName();

		return _qName.equals(qName);
	}

	public String getLocalPart() {
		return getName();
	}

	public String getName() {
		return _qName.getName();
	}

	public Namespace getNamespace() {
		org.dom4j.Namespace namespace = _qName.getNamespace();

		if (namespace == null) {
			return null;
		}
		else {
			return new NamespaceImpl(namespace);
		}
	}

	public String getNamespacePrefix() {
		return _qName.getNamespacePrefix();
	}

	public String getNamespaceURI() {
		return _qName.getNamespaceURI();
	}

	public String getQualifiedName() {
		return _qName.getQualifiedName();
	}

	public org.dom4j.QName getWrappedQName() {
		return _qName;
	}

	@Override
	public int hashCode() {
		return _qName.hashCode();
	}

	@Override
	public String toString() {
		return _qName.toString();
	}

	private org.dom4j.QName _qName;

}