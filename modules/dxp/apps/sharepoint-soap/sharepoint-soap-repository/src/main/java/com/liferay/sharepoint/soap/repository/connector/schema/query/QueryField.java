/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;

/**
 * @author Iván Zaera
 */
public class QueryField extends BaseNode {

	public QueryField(String name) {
		_name = name;
	}

	@Override
	protected String getNodeName() {
		return "FieldRef";
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		element.addAttribute("Name", _name);
	}

	private final String _name;

}