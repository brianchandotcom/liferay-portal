/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;

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