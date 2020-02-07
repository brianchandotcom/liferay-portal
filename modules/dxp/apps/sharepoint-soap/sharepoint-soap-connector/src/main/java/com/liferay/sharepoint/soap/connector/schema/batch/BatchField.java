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

package com.liferay.sharepoint.soap.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.connector.schema.BaseNode;

/**
 * @author Iván Zaera
 */
public class BatchField extends BaseNode {

	public BatchField(String name, long value) {
		this(name, String.valueOf(value));
	}

	public BatchField(String name, String value) {
		_name = name;
		_value = value;
	}

	@Override
	protected String getNodeName() {
		return "Field";
	}

	@Override
	protected String getNodeText() {
		return _value;
	}

	@Override
	protected void populate(Element element) {
		element.addAttribute("Name", _name);
	}

	private final String _name;
	private final String _value;

}