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
public class Success implements Response {

	public Success(String description) {
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
		StringBundler sb = new StringBundler(3);

		sb.append("<?xml version=\"1.0\"?><methodResponse><params><param>");
		sb.append(XmlRpcParser.wrapValue(_description));
		sb.append("</param></params></methodResponse>");

		return sb.toString();
	}

	private final String _description;

}