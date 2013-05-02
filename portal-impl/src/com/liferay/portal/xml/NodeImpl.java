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

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.Visitor;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;
import java.io.Writer;

import java.util.List;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * @author Brian Wing Shun Chan
 */
public class NodeImpl implements Node {

	public NodeImpl(org.dom4j.Node node) {
		_node = node;
	}

	public <T, V extends Visitor<T>> T accept(V visitor) {
		return visitor.visitNode(this);
	}

	public String asXML() {
		return _node.asXML();
	}

	public Node asXPathResult(Element parent) {
		ElementImpl parentImpl = (ElementImpl)parent;

		org.dom4j.Node node = _node.asXPathResult(
			parentImpl.getWrappedElement());

		if (node == null) {
			return null;
		}

		if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else {
			return new NodeImpl(node);
		}
	}

	public String compactString() throws IOException {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		OutputFormat outputFormat = OutputFormat.createCompactFormat();

		XMLWriter xmlWriter = new XMLWriter(
			unsyncByteArrayOutputStream, outputFormat);

		xmlWriter.write(_node);

		return unsyncByteArrayOutputStream.toString(StringPool.UTF8);
	}

	public Node detach() {
		org.dom4j.Node node = _node.detach();

		if (node == null) {
			return null;
		}

		if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else {
			return new NodeImpl(node);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof NodeImpl)) {
			return false;
		}

		org.dom4j.Node node = ((NodeImpl)obj).getWrappedNode();

		return _node.equals(node);
	}

	public String formattedString() throws IOException {
		return XMLFormatter.toString(_node);
	}

	public String formattedString(String indent) throws IOException {
		return XMLFormatter.toString(_node, indent);
	}

	public String formattedString(String indent, boolean expandEmptyElements)
		throws IOException {

		return XMLFormatter.toString(_node, indent, expandEmptyElements);
	}

	public String formattedString(
			String indent, boolean expandEmptyElements, boolean trimText)
		throws IOException {

		return XMLFormatter.toString(
			_node, indent, expandEmptyElements, trimText);
	}

	public Document getDocument() {
		org.dom4j.Document document = _node.getDocument();

		if (document == null) {
			return null;
		}
		else {
			return new DocumentImpl(document);
		}
	}

	public String getName() {
		return _node.getName();
	}

	public Element getParent() {
		org.dom4j.Element element = _node.getParent();

		if (element == null) {
			return null;
		}
		else {
			return new ElementImpl(element);
		}
	}

	public String getPath() {
		return _node.getPath();
	}

	public String getPath(Element context) {
		ElementImpl contextImpl = (ElementImpl)context;

		return _node.getPath(contextImpl.getWrappedElement());
	}

	public String getStringValue() {
		return _node.getStringValue();
	}

	public String getText() {
		return _node.getText();
	}

	public String getUniquePath() {
		return _node.getUniquePath();
	}

	public String getUniquePath(Element context) {
		ElementImpl contextImpl = (ElementImpl)context;

		return _node.getUniquePath(contextImpl.getWrappedElement());
	}

	public org.dom4j.Node getWrappedNode() {
		return _node;
	}

	public boolean hasContent() {
		return _node.hasContent();
	}

	@Override
	public int hashCode() {
		return _node.hashCode();
	}

	public boolean isReadOnly() {
		return _node.isReadOnly();
	}

	public boolean matches(String xPathExpression) {
		return _node.matches(xPathExpression);
	}

	public Number numberValueOf(String xPathExpression) {
		return _node.numberValueOf(xPathExpression);
	}

	public List<Node> selectNodes(String xPathExpression) {
		return SAXReaderImpl.toNewNodes(_node.selectNodes(xPathExpression));
	}

	public List<Node> selectNodes(
		String xPathExpression, String comparisonXPathExpression) {

		return SAXReaderImpl.toNewNodes(
			_node.selectNodes(xPathExpression, comparisonXPathExpression));
	}

	public List<Node> selectNodes(
		String xPathExpression, String comparisonXPathExpression,
		boolean removeDuplicates) {

		return SAXReaderImpl.toNewNodes(
			_node.selectNodes(
				xPathExpression, comparisonXPathExpression, removeDuplicates));
	}

	public Object selectObject(String xPathExpression) {
		Object obj = _node.selectObject(xPathExpression);

		if (obj == null) {
			return null;
		}
		else if (obj instanceof List<?>) {
			return SAXReaderImpl.toNewNodes((List<org.dom4j.Node>)obj);
		}
		else {
			return obj;
		}
	}

	public Node selectSingleNode(String xPathExpression) {
		org.dom4j.Node node = _node.selectSingleNode(xPathExpression);

		if (node == null) {
			return null;
		}

		if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else {
			return new NodeImpl(node);
		}
	}

	public void setName(String name) {
		_node.setName(name);
	}

	public void setText(String text) {
		_node.setText(text);
	}

	public boolean supportsParent() {
		return _node.supportsParent();
	}

	@Override
	public String toString() {
		return _node.toString();
	}

	public String valueOf(String xPathExpression) {
		return _node.valueOf(xPathExpression);
	}

	public void write(Writer writer) throws IOException {
		_node.write(writer);
	}

	private org.dom4j.Node _node;

}