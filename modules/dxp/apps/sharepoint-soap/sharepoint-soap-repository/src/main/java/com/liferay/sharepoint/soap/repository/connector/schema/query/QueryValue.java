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
public class QueryValue extends BaseNode {

	public QueryValue(String value) {
		this(Type.TEXT, value);
	}

	public QueryValue(Type type, String value) {
		_type = type;
		_value = value;
	}

	public enum Type {

		INTEGER, LOOKUP, TEXT

	}

	@Override
	protected String getNodeName() {
		return "Value";
	}

	@Override
	protected String getNodeText() {
		return _value;
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		element.addAttribute("Type", _type.name());
	}

	private final Type _type;
	private final String _value;

}