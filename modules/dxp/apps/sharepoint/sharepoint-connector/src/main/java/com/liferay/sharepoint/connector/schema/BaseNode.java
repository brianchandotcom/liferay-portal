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

package com.liferay.sharepoint.connector.schema;

import com.liferay.portal.kernel.xml.simple.Element;

/**
 * @author Iván Zaera
 */
public abstract class BaseNode implements Node {

	@Override
	public void attach(Element parentElement) {
		_attach(parentElement);
	}

	@Override
	public String toString() {
		return toXmlString();
	}

	@Override
	public String toXmlString() {
		Element element = _attach(null);

		return element.toXMLString();
	}

	protected abstract String getNodeName();

	protected String getNodeText() {
		return null;
	}

	protected void populate(Element element) {
	}

	private Element _attach(Element parentElement) {
		Element element = null;

		if (parentElement == null) {
			element = new Element(getNodeName(), getNodeText(), false);
		}
		else {
			element = parentElement.addElement(getNodeName(), getNodeText());
		}

		populate(element);

		return element;
	}

}