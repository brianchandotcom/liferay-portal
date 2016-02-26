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

/**
 * @author Marcellus Tavares
 */
public interface Visitor<T> {

	public T visitAttribute(Attribute attribute);

	public T visitCDATA(CDATA cdata);

	public T visitComment(Comment comment);

	public T visitDocument(Document document);

	public T visitElement(Element element);

	public T visitEntity(Entity entity);

	public T visitNamespace(Namespace namespace);

	public T visitNode(Node node);

	public T visitProcessInstruction(
		ProcessingInstruction processingInstruction);

	public T visitText(Text text);

}