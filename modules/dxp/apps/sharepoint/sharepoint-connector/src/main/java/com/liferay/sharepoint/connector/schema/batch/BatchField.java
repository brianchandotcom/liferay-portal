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

package com.liferay.sharepoint.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.connector.schema.BaseNode;

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