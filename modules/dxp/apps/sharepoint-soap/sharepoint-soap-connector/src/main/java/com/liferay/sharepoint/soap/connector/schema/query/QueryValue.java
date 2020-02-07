/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector.schema.query;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.connector.schema.BaseNode;

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

	public static enum Type {

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