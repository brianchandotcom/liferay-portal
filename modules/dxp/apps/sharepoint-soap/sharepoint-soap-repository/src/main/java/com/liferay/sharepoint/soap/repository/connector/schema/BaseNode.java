/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema;

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