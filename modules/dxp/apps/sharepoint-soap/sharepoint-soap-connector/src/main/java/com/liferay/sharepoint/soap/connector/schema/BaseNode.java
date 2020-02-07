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

package com.liferay.sharepoint.soap.connector.schema;

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