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