/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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