/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.comparator;

import com.liferay.portal.kernel.util.NaturalOrderStringComparator;

import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class ElementComparator extends NaturalOrderStringComparator {

	public ElementComparator() {
		this(_NAME_ATTRIBUTE_DEFAULT);
	}

	public ElementComparator(boolean importPackage) {
		this(_NAME_ATTRIBUTE_DEFAULT, importPackage);
	}

	public ElementComparator(String nameAttribute) {
		this(nameAttribute, false);
	}

	public ElementComparator(String nameAttribute, boolean importPackage) {
		_nameAttribute = nameAttribute;
		_importPackage = importPackage;
	}

	public int compare(Element element1, Element element2) {
		String elementName1 = getElementName(element1);
		String elementName2 = getElementName(element2);

		if ((elementName1 == null) || (elementName2 == null)) {
			return 0;
		}

		if (_importPackage) {
			return elementName1.compareTo(elementName2);
		}

		return super.compare(elementName1, elementName2);
	}

	public String getElementName(Element element) {
		return element.attributeValue(getNameAttribute());
	}

	protected String getNameAttribute() {
		return _nameAttribute;
	}

	protected String getTagValue(Element element) {
		return getTagValue(element, getNameAttribute());
	}

	protected String getTagValue(Element element, String tagName) {
		Element nameElement = element.element(tagName);

		if (nameElement == null) {
			return null;
		}

		return nameElement.getText();
	}

	private static final String _NAME_ATTRIBUTE_DEFAULT = "name";

	private final boolean _importPackage;
	private final String _nameAttribute;

}