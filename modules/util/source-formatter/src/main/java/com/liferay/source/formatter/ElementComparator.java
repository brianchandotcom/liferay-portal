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

package com.liferay.source.formatter;

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

	protected String getElementName(Element element) {
		return element.attributeValue(getNameAttribute());
	}

	protected String getNameAttribute() {
		return _nameAttribute;
	}

	private static final String _NAME_ATTRIBUTE_DEFAULT = "name";

	private final boolean _importPackage;
	private final String _nameAttribute;

}