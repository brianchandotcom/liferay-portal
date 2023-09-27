/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.CDATA;
import com.liferay.portal.kernel.xml.Visitor;

/**
 * @author Brian Wing Shun Chan
 */
public class CDATAImpl extends NodeImpl implements CDATA {

	public CDATAImpl(org.dom4j.CDATA cdata) {
		super(cdata);

		_cdata = cdata;
	}

	@Override
	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitCDATA(this);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CDATAImpl)) {
			return false;
		}

		CDATAImpl cdataImpl = (CDATAImpl)object;

		org.dom4j.CDATA cdata = cdataImpl.getWrappedCDATA();

		return _cdata.equals(cdata);
	}

	public org.dom4j.CDATA getWrappedCDATA() {
		return _cdata;
	}

	@Override
	public int hashCode() {
		return _cdata.hashCode();
	}

	@Override
	public String toString() {
		return _cdata.toString();
	}

	private final org.dom4j.CDATA _cdata;

}