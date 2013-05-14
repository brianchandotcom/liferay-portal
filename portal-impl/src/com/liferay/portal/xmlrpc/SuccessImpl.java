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
import com.liferay.portal.kernel.xmlrpc.Success;
import com.liferay.portal.kernel.xmlrpc.XmlRpcException;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class SuccessImpl implements Success {

	public SuccessImpl(String description) {
		_description = description;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String toString() {
		return "XML-RPC success " + _description;
	}

	@Override
	public String toXml() throws XmlRpcException {
		StringBundler sb = new StringBundler(8);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<methodResponse>");
		sb.append("<params>");
		sb.append("<param>");
		sb.append(XmlRpcParser.wrapValue(_description));
		sb.append("</param>");
		sb.append("</params>");
		sb.append("</methodResponse>");

		return sb.toString();
	}

	private String _description;

}