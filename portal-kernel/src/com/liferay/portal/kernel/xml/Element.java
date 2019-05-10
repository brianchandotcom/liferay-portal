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

package com.liferay.portal.kernel.xml;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface Element extends Branch {

	public void add(Attribute attribute);

	public void add(CDATA cdata);

	public void add(Entity entity);

	public void add(Namespace namespace);

	public void add(Text text);

	public Element addAttribute(QName qName, String value);

	public Element addAttribute(String name, String value);

	public Element addCDATA(String cdata);

	public Element addComment(String comment);

	public Element addEntity(String name, String text);

	public List<Namespace> additionalNamespaces();

	public Element addNamespace(String prefix, String uri);

	public Element addProcessingInstruction(
		String target, Map<String, String> data);

	public Element addProcessingInstruction(String target, String data);

	public Element addText(String text);

	public void appendAttributes(Element element);

	public Attribute attribute(int index);

	public Attribute attribute(QName qName);

	public Attribute attribute(String name);

	public int attributeCount();

	public Iterator<Attribute> attributeIterator();

	public List<Attribute> attributes();

	public String attributeValue(QName qName);

	public String attributeValue(QName qName, String defaultValue);

	public String attributeValue(String name);

	public String attributeValue(String name, String defaultValue);

	public Element createCopy();

	public Element createCopy(QName qName);

	public Element createCopy(String name);

	public List<Namespace> declaredNamespaces();

	public Element element(QName qName);

	public Element element(String name);

	public Iterator<Element> elementIterator();

	public Iterator<Element> elementIterator(QName qName);

	public Iterator<Element> elementIterator(String name);

	public List<Element> elements();

	public List<Element> elements(QName qName);

	public List<Element> elements(String name);

	public String elementText(QName qName);

	public String elementText(String name);

	public String elementTextTrim(QName qName);

	public String elementTextTrim(String name);

	@Override
	public String formattedString() throws IOException;

	@Override
	public String formattedString(String indent) throws IOException;

	@Override
	public String formattedString(String indent, boolean expandEmptyElements)
		throws IOException;

	public Object getData();

	public Namespace getNamespace();

	public Namespace getNamespaceForPrefix(String prefix);

	public Namespace getNamespaceForURI(String uri);

	public String getNamespacePrefix();

	public List<Namespace> getNamespacesForURI(String uri);

	public String getNamespaceURI();

	public QName getQName();

	public QName getQName(String qualifiedName);

	public String getQualifiedName();

	@Override
	public String getStringValue();

	@Override
	public String getText();

	public String getTextTrim();

	public Node getXPathResult(int index);

	public boolean hasMixedContent();

	public boolean isRootElement();

	public boolean isTextOnly();

	public boolean remove(Attribute attribute);

	public boolean remove(CDATA cdata);

	public boolean remove(Entity entity);

	public boolean remove(Namespace namespace);

	public boolean remove(Text text);

	public void setAttributes(List<Attribute> attributes);

	public void setData(Object data);

	public void setQName(QName qName);

	public void sortAttributes(boolean recursive);

	public void sortElementsByAttribute(
		String elementName, String attributeName);

	public void sortElementsByChildElement(
		String elementName, String childElementName);

}