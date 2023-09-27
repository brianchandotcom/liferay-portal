/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xmlrpc;

import com.liferay.petra.string.StringBundler;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class Fault implements Response {

	public Fault(int code, String description) {
		_code = code;
		_description = description;
	}

	public int getCode() {
		return _code;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String toString() {
		return StringBundler.concat("XML-RPC fault ", _code, " ", _description);
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

	private final int _code;
	private final String _description;

}