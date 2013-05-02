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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CDATAImpl)) {
			return false;
		}

		org.dom4j.CDATA cdata = ((CDATAImpl)obj).getWrappedCDATA();

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

	private org.dom4j.CDATA _cdata;

}