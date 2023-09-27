/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof QNameImpl)) {
			return false;
		}

		QNameImpl qNameImpl = (QNameImpl)object;

		org.dom4j.QName qName = qNameImpl.getWrappedQName();

		return _qName.equals(qName);
	}

	@Override
	public String getLocalPart() {
		return getName();
	}

	@Override
	public String getName() {
		return _qName.getName();
	}

	@Override
	public Namespace getNamespace() {
		org.dom4j.Namespace namespace = _qName.getNamespace();

		if (namespace == null) {
			return null;
		}

		return new NamespaceImpl(namespace);
	}

	@Override
	public String getNamespacePrefix() {
		return _qName.getNamespacePrefix();
	}

	@Override
	public String getNamespaceURI() {
		return _qName.getNamespaceURI();
	}

	@Override
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

	private final org.dom4j.QName _qName;

}