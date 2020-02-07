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