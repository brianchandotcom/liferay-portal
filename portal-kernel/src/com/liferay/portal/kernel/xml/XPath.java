/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xml;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface XPath extends Serializable {

	public boolean booleanValueOf(Object context);

	public Object evaluate(Object context);

	public String getText();

	public boolean matches(Node node);

	public Number numberValueOf(Object context);

	public List<Node> selectNodes(Object context);

	public List<Node> selectNodes(Object context, XPath sortXPath);

	public List<Node> selectNodes(
		Object context, XPath sortXPath, boolean distinct);

	public Node selectSingleNode(Object context);

	public void sort(List<Node> nodes);

	public void sort(List<Node> nodes, boolean distinct);

	public String valueOf(Object context);

}