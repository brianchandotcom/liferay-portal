/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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