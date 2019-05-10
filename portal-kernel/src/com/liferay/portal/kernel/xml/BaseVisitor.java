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

import java.util.ArrayList;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public abstract class BaseVisitor<T> implements Visitor<T> {

	@Override
	public T visitAttribute(Attribute attribute) {
		return handleAttribute(attribute);
	}

	@Override
	public T visitCDATA(CDATA cdata) {
		return handleCDATA(cdata);
	}

	@Override
	public T visitComment(Comment comment) {
		return handleComment(comment);
	}

	@Override
	public T visitDocument(Document document) {
		List<T> nodeResults = new ArrayList<>(document.nodeCount());

		for (int i = 0, size = document.nodeCount(); i < size; i++) {
			Node node = document.node(i);

			T nodeResult = node.accept(this);

			nodeResults.add(nodeResult);
		}

		return handleDocument(document, nodeResults);
	}

	@Override
	public T visitElement(Element element) {
		List<Attribute> attributes = element.attributes();

		List<T> attributeResults = new ArrayList<>(attributes.size());

		for (int i = 0, size = element.attributeCount(); i < size; i++) {
			Attribute attribute = element.attribute(i);

			T atrributeResult = attribute.accept(this);

			attributeResults.add(atrributeResult);
		}

		List<T> nodeResults = new ArrayList<>(element.nodeCount());

		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node node = element.node(i);

			T nodeResult = node.accept(this);

			if (nodeResult != null) {
				nodeResults.add(nodeResult);
			}
		}

		return handleElement(element, attributeResults, nodeResults);
	}

	@Override
	public T visitEntity(Entity entity) {
		return handleEntity(entity);
	}

	@Override
	public T visitNamespace(Namespace namespace) {
		return handleNamespace(namespace);
	}

	@Override
	public T visitNode(Node node) {
		return handleNode(node);
	}

	@Override
	public T visitProcessInstruction(
		ProcessingInstruction processingInstruction) {

		return handleProcessInstruction(processingInstruction);
	}

	@Override
	public T visitText(Text text) {
		return handleText(text);
	}

	protected abstract T handleAttribute(Attribute attribute);

	protected abstract T handleCDATA(CDATA cdata);

	protected abstract T handleComment(Comment comment);

	protected abstract T handleDocument(Document document, List<T> nodeResults);

	protected abstract T handleElement(
		Element element, List<T> attributeResults, List<T> nodeResults);

	protected abstract T handleEntity(Entity entity);

	protected abstract T handleNamespace(Namespace namespace);

	protected abstract T handleNode(Node node);

	protected abstract T handleProcessInstruction(
		ProcessingInstruction processingInstruction);

	protected abstract T handleText(Text text);

}