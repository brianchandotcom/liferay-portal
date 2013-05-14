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

package com.liferay.portal.xmlrpc;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.xmlrpc.Fault;
import com.liferay.portal.kernel.xmlrpc.XmlRpcException;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class FaultImpl implements Fault {

	public FaultImpl(int code, String description) {
		_code = code;
		_description = description;
	}

	@Override
	public int getCode() {
		return _code;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String toString() {
		return "XML-RPC fault " + _code + " " + _description;
	}

	@Override
	public String toXml() throws XmlRpcException {
		StringBundler sb = new StringBundler(17);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<methodResponse>");
		sb.append("<fault>");
		sb.append("<value>");
		sb.append("<struct>");
		sb.append("<member>");
		sb.append("<name>faultCode</name>");
		sb.append(XmlRpcParser.wrapValue(_code));
		sb.append("</member>");
		sb.append("<member>");
		sb.append("<name>faultString</name>");
		sb.append(XmlRpcParser.wrapValue(_description));
		sb.append("</member>");
		sb.append("</struct>");
		sb.append("</value>");
		sb.append("</fault>");
		sb.append("</methodResponse>");

		return sb.toString();
	}

	private int _code;
	private String _description;

}