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

package com.liferay.poshi.runner.elements;

import java.io.IOException;
import java.io.Writer;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.InvalidXPathException;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.Visitor;
import org.dom4j.XPath;

/**
 * @author Peter Yoo
 */
public class PoshiElementAttribute implements Attribute {

	public PoshiElementAttribute(Attribute attribute) {
		_attribute = attribute;
	}

	@Override
	public void accept(Visitor visitor) {
		_attribute.accept(visitor);
	}

	@Override
	public String asXML() {
		return _attribute.asXML();
	}

	@Override
	public Node asXPathResult(Element parent) {
		return _attribute.asXPathResult(parent);
	}

	public Object clone() {
		return new PoshiElementAttribute((Attribute)_attribute.clone());
	}

	@Override
	public XPath createXPath(String xpathExpression)
		throws InvalidXPathException {

		return _attribute.createXPath(xpathExpression);
	}

	@Override
	public Node detach() {
		return _attribute.detach();
	}

	public Attribute getAttribute() {
		return _attribute;
	}

	@Override
	public Object getData() {
		return _attribute.getData();
	}

	@Override
	public Document getDocument() {
		return _attribute.getDocument();
	}

	@Override
	public String getName() {
		return _attribute.getName();
	}

	@Override
	public Namespace getNamespace() {
		return _attribute.getNamespace();
	}

	@Override
	public String getNamespacePrefix() {
		return _attribute.getNamespacePrefix();
	}

	@Override
	public String getNamespaceURI() {
		return _attribute.getNamespaceURI();
	}

	@Override
	public short getNodeType() {
		return _attribute.getNodeType();
	}

	@Override
	public String getNodeTypeName() {
		return _attribute.getNodeTypeName();
	}

	@Override
	public Element getParent() {
		return _attribute.getParent();
	}

	@Override
	public String getPath() {
		return _attribute.getPath();
	}

	@Override
	public String getPath(Element context) {
		return _attribute.getPath(context);
	}

	@Override
	public QName getQName() {
		return _attribute.getQName();
	}

	@Override
	public String getQualifiedName() {
		return _attribute.getQualifiedName();
	}

	@Override
	public String getStringValue() {
		return _attribute.getStringValue();
	}

	@Override
	public String getText() {
		return _attribute.getText();
	}

	@Override
	public String getUniquePath() {
		return _attribute.getUniquePath();
	}

	@Override
	public String getUniquePath(Element context) {
		return _attribute.getUniquePath(context);
	}

	@Override
	public String getValue() {
		return _attribute.getValue();
	}

	@Override
	public boolean hasContent() {
		return _attribute.hasContent();
	}

	@Override
	public boolean isReadOnly() {
		return _attribute.isReadOnly();
	}

	@Override
	public boolean matches(String xpathExpression) {
		return _attribute.matches(xpathExpression);
	}

	@Override
	public Number numberValueOf(String xpathExpression) {
		return _attribute.numberValueOf(xpathExpression);
	}

	@Override
	public List selectNodes(String xpathExpression) {
		return _attribute.selectNodes(xpathExpression);
	}

	@Override
	public List selectNodes(
		String xpathExpression, String comparisonXPathExpression) {

		return _attribute.selectNodes(
			xpathExpression, comparisonXPathExpression);
	}

	@Override
	public List selectNodes(
		String xpathExpression, String comparisonXPathExpression,
		boolean removeDuplicates) {

		return _attribute.selectNodes(
			xpathExpression, comparisonXPathExpression, removeDuplicates);
	}

	@Override
	public Object selectObject(String xpathExpression) {
		return _attribute.selectObject(xpathExpression);
	}

	@Override
	public Node selectSingleNode(String xpathExpression) {
		return _attribute.selectSingleNode(xpathExpression);
	}

	@Override
	public void setData(Object data) {
		_attribute.setData(data);
	}

	@Override
	public void setDocument(Document document) {
		_attribute.setDocument(document);
	}

	@Override
	public void setName(String name) {
		_attribute.setName(name);
	}

	@Override
	public void setNamespace(Namespace namespace) {
		_attribute.setNamespace(namespace);
	}

	@Override
	public void setParent(Element parent) {
		_attribute.setParent(parent);
	}

	@Override
	public void setText(String text) {
		_attribute.setText(text);
	}

	@Override
	public void setValue(String value) {
		_attribute.setValue(value);
	}

	@Override
	public boolean supportsParent() {
		return _attribute.supportsParent();
	}

	public String toReadableSyntax() {
		StringBuilder sb = new StringBuilder();

		sb.append(getName());
		sb.append(" = \"");
		sb.append(getValue());
		sb.append("\"");

		return sb.toString();
	}

	@Override
	public String valueOf(String xpathExpression) {
		return _attribute.valueOf(xpathExpression);
	}

	@Override
	public void write(Writer writer) throws IOException {
		_attribute.write(writer);
	}

	private final Attribute _attribute;

}