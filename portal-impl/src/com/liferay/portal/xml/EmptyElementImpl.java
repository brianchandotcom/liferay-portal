/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.CDATA;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.EmptyElement;
import com.liferay.portal.kernel.xml.Entity;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.Text;

import java.io.IOException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.iterators.EmptyIterator;

/**
 * @author Mate Thurzo
 */
public class EmptyElementImpl extends ElementImpl implements EmptyElement {

	public EmptyElementImpl(org.dom4j.Element element) {
		super(element);
	}

	@Override
	public List<Namespace> additionalNamespaces() {
		return Collections.emptyList();
	}

	@Override
	public Attribute attribute(int index) {
		return null;
	}

	@Override
	public Attribute attribute(QName qName) {
		return null;
	}

	@Override
	public Attribute attribute(String name) {
		return null;
	}

	@Override
	public int attributeCount() {
		return 0;
	}

	@Override
	public Iterator<Attribute> attributeIterator() {
		return EmptyIterator.INSTANCE;
	}

	@Override
	public List<Attribute> attributes() {
		return Collections.emptyList();
	}

	@Override
	public String attributeValue(QName qName) {
		return StringPool.BLANK;
	}

	@Override
	public String attributeValue(QName qName, String defaultValue) {
		return StringPool.BLANK;
	}

	@Override
	public String attributeValue(String name) {
		return StringPool.BLANK;
	}

	@Override
	public String attributeValue(String name, String defaultValue) {
		return StringPool.BLANK;
	}

	@Override
	public Element createCopy() {
		return SAXReaderUtil.emptyElement();
	}

	@Override
	public Element createCopy(QName qName) {
		return SAXReaderUtil.emptyElement();
	}

	@Override
	public Element createCopy(String name) {
		return SAXReaderUtil.emptyElement();
	}

	@Override
	public List<Namespace> declaredNamespaces() {
		return Collections.emptyList();
	}

	@Override
	public Element element(QName qName) {
		return SAXReaderUtil.emptyElement();
	}

	@Override
	public Element element(String name) {
		return SAXReaderUtil.emptyElement();
	}

	@Override
	public Iterator<Element> elementIterator() {
		return EmptyIterator.RESETTABLE_INSTANCE;
	}

	@Override
	public Iterator<Element> elementIterator(QName qName) {
		return EmptyIterator.RESETTABLE_INSTANCE;
	}

	@Override
	public Iterator<Element> elementIterator(String name) {
		return EmptyIterator.RESETTABLE_INSTANCE;
	}

	@Override
	public List<Element> elements() {
		return Collections.emptyList();
	}

	@Override
	public List<Element> elements(QName qName) {
		return Collections.emptyList();
	}

	@Override
	public List<Element> elements(String name) {
		return Collections.emptyList();
	}

	@Override
	public String elementText(QName qName) {
		return StringPool.BLANK;
	}

	@Override
	public String elementText(String name) {
		return StringPool.BLANK;
	}

	@Override
	public String elementTextTrim(QName qName) {
		return StringPool.BLANK;
	}

	@Override
	public String elementTextTrim(String name) {
		return StringPool.BLANK;
	}

	@Override
	public String formattedString() throws IOException {
		return StringPool.BLANK;
	}

	@Override
	public String formattedString(String indent) throws IOException {
		return StringPool.BLANK;
	}

	@Override
	public String formattedString(String indent, boolean expandEmptyElements)
		throws IOException {

		return StringPool.BLANK;
	}

	@Override
	public Object getData() {
		return null;
	}

	@Override
	public Namespace getNamespace() {
		return null;
	}

	@Override
	public Namespace getNamespaceForPrefix(String prefix) {
		return null;
	}

	@Override
	public Namespace getNamespaceForURI(String uri) {
		return null;
	}

	@Override
	public String getNamespacePrefix() {
		return StringPool.BLANK;
	}

	@Override
	public List<Namespace> getNamespacesForURI(String uri) {
		return Collections.emptyList();
	}

	@Override
	public String getNamespaceURI() {
		return StringPool.BLANK;
	}

	@Override
	public String getTextTrim() {
		return StringPool.BLANK;
	}

	@Override
	public org.dom4j.Element getWrappedElement() {
		return (org.dom4j.Element)SAXReaderUtil.emptyElement();
	}

	@Override
	public Node getXPathResult(int index) {
		return null;
	}

	@Override
	public boolean hasMixedContent() {
		return false;
	}

	@Override
	public boolean isRootElement() {
		return false;
	}

	@Override
	public boolean isTextOnly() {
		return false;
	}

	@Override
	public boolean remove(Attribute attribute) {
		return false;
	}

	@Override
	public boolean remove(CDATA cdata) {
		return false;
	}

	@Override
	public boolean remove(Entity entity) {
		return false;
	}

	@Override
	public boolean remove(Namespace namespace) {
		return false;
	}

	@Override
	public boolean remove(Text text) {
		return false;
	}

	@Override
	public String toString() {
		return StringPool.BLANK;
	}

}