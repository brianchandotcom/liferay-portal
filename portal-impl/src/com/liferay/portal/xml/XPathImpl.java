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

import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.xml.xpath.LiferayFunctionContext;
import com.liferay.portal.xml.xpath.LiferayNamespaceContext;

import java.util.List;
import java.util.Map;

import org.jaxen.FunctionContext;
import org.jaxen.NamespaceContext;

/**
 * @author Brian Wing Shun Chan
 */
public class XPathImpl implements XPath {

	public XPathImpl(
		org.dom4j.XPath xPath, Map<String, String> namespaceContextMap) {

		_xPath = xPath;

		_xPath.setFunctionContext(_functionContext);

		NamespaceContext namespaceContext = new LiferayNamespaceContext(
			namespaceContextMap);

		_xPath.setNamespaceContext(namespaceContext);
	}

	public boolean booleanValueOf(Object context) {
		return _xPath.booleanValueOf(toOldContext(context));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof XPathImpl)) {
			return false;
		}

		org.dom4j.XPath xPath = ((XPathImpl)obj).getWrappedXPath();

		return _xPath.equals(xPath);
	}

	public Object evaluate(Object context) {
		return toNewContext(_xPath.evaluate(toOldContext(context)));
	}

	public String getText() {
		return _xPath.getText();
	}

	public org.dom4j.XPath getWrappedXPath() {
		return _xPath;
	}

	@Override
	public int hashCode() {
		return _xPath.hashCode();
	}

	public boolean matches(Node node) {
		NodeImpl nodeImpl = (NodeImpl)node;

		return _xPath.matches(nodeImpl.getWrappedNode());
	}

	public Number numberValueOf(Object context) {
		return _xPath.numberValueOf(toOldContext(context));
	}

	public List<Node> selectNodes(Object context) {
		return SAXReaderImpl.toNewNodes(
			_xPath.selectNodes(toOldContext(context)));
	}

	public List<Node> selectNodes(Object context, XPath sortXPath) {
		XPathImpl sortXPathImpl = (XPathImpl)sortXPath;

		return SAXReaderImpl.toNewNodes(
			_xPath.selectNodes(
				toOldContext(context), sortXPathImpl.getWrappedXPath()));
	}

	public List<Node> selectNodes(
		Object context, XPath sortXPath, boolean distinct) {

		XPathImpl sortXPathImpl = (XPathImpl)sortXPath;

		return SAXReaderImpl.toNewNodes(
			_xPath.selectNodes(
				toOldContext(context), sortXPathImpl.getWrappedXPath(),
				distinct));
	}

	public Node selectSingleNode(Object context) {
		org.dom4j.Node node = _xPath.selectSingleNode(toOldContext(context));

		if (node == null) {
			return null;
		}
		else if (node instanceof org.dom4j.Element) {
			return new ElementImpl((org.dom4j.Element)node);
		}
		else {
			return new NodeImpl(node);
		}
	}

	public void sort(List<Node> nodes) {
		_xPath.sort(SAXReaderImpl.toOldNodes(nodes));
	}

	public void sort(List<Node> nodes, boolean distinct) {
		_xPath.sort(SAXReaderImpl.toOldNodes(nodes), distinct);
	}

	@Override
	public String toString() {
		return _xPath.toString();
	}

	public String valueOf(Object context) {
		return _xPath.valueOf(toOldContext(context));
	}

	protected Object toNewContext(Object context) {
		if (context == null) {
			return null;
		}
		else if (context instanceof org.dom4j.Document) {
			org.dom4j.Document document = (org.dom4j.Document)context;

			return new DocumentImpl(document);
		}
		else if (context instanceof org.dom4j.Node) {
			org.dom4j.Node node = (org.dom4j.Node)context;

			return new NodeImpl(node);
		}
		else if (context instanceof List<?>) {
			return SAXReaderImpl.toNewNodes((List<org.dom4j.Node>)context);
		}
		else {
			return context;
		}
	}

	protected Object toOldContext(Object context) {
		if (context == null) {
			return null;
		}
		else if (context instanceof DocumentImpl) {
			DocumentImpl documentImpl = (DocumentImpl)context;

			return documentImpl.getWrappedDocument();
		}
		else if (context instanceof NodeImpl) {
			NodeImpl nodeImpl = (NodeImpl)context;

			return nodeImpl.getWrappedNode();
		}
		else if (context instanceof List<?>) {
			return SAXReaderImpl.toOldNodes((List<Node>)context);
		}
		else {
			return context;
		}
	}

	private static FunctionContext _functionContext =
		new LiferayFunctionContext();

	private org.dom4j.XPath _xPath;

}