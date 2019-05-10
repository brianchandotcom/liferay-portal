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
import java.io.Serializable;
import java.io.Writer;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface Node extends Serializable {

	public <T, V extends Visitor<T>> T accept(V visitor);

	public String asXML();

	public Node asXPathResult(Element parent);

	public String compactString() throws IOException;

	public Node detach();

	public String formattedString() throws IOException;

	public String formattedString(String indent) throws IOException;

	public String formattedString(String indent, boolean expandEmptyElements)
		throws IOException;

	public String formattedString(
			String indent, boolean expandEmptyElements, boolean trimText)
		throws IOException;

	public Document getDocument();

	public String getName();

	public Element getParent();

	public String getPath();

	public String getPath(Element context);

	public String getStringValue();

	public String getText();

	public String getUniquePath();

	public String getUniquePath(Element context);

	public boolean hasContent();

	public boolean isReadOnly();

	public boolean matches(String xPathExpression);

	public Number numberValueOf(String xPathExpression);

	public List<Node> selectNodes(String xPathExpression);

	public List<Node> selectNodes(
		String xPathExpression, String comparisonXPathExpression);

	public List<Node> selectNodes(
		String xPathExpression, String comparisonXPathExpression,
		boolean removeDuplicates);

	public Object selectObject(String xPathExpression);

	public Node selectSingleNode(String xPathExpression);

	public void setName(String name);

	public void setText(String text);

	public boolean supportsParent();

	public String valueOf(String xPathExpression);

	public void write(Writer writer) throws IOException;

}