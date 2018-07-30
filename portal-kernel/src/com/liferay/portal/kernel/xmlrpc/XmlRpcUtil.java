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

package com.liferay.portal.kernel.xmlrpc;

/**
 * @author Brian Wing Shun Chan
 */
public class XmlRpcUtil {

	public static Fault createFault(int code, String description) {
		return getXmlRpc().createFault(code, description);
	}

	public static Success createSuccess(String description) {
		return getXmlRpc().createSuccess(description);
	}

	public static Response executeMethod(
			String url, String methodName, Object[] arguments)
		throws XmlRpcException {

		return getXmlRpc().executeMethod(url, methodName, arguments);
	}

	public static XmlRpc getXmlRpc() {
		return _xmlRpc;
	}

	public void setXmlRpc(XmlRpc xmlRpc) {
		_xmlRpc = xmlRpc;
	}

	private static XmlRpc _xmlRpc;

}