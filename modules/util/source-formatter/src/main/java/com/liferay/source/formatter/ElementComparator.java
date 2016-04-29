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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.dom4j.Element;
import org.dom4j.Namespace;

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

	protected boolean isSeparatedByComment(Element element1, Element element2) {
		String elementContent1 = element1.asXML();
		String elementContent2 = element2.asXML();

		Namespace namespace1 = element1.getNamespace();
		Namespace namespace2 = element2.getNamespace();

		elementContent1 = StringUtil.replace(
			elementContent1, namespace1.asXML() + StringPool.SPACE,
			StringPool.BLANK);
		elementContent2 = StringUtil.replace(
			elementContent2, namespace2.asXML() + StringPool.SPACE,
			StringPool.BLANK);

		int x =
			_rootElementContent.indexOf(elementContent1) +
				elementContent1.length();
		int y = _rootElementContent.indexOf(elementContent2);

		if (x > y) {
			return false;
		}

		String betweenElementsContent = _rootElementContent.substring(x, y);

		if (betweenElementsContent.contains("<!--")) {
			return true;
		}

		return false;
	}

	protected void setRootElementContent(String rootElementContent) {
		_rootElementContent = rootElementContent;
	}

	private static final String _NAME_ATTRIBUTE_DEFAULT = "name";

	private boolean _importPackage;
	private String _nameAttribute;
	private String _rootElementContent;

}