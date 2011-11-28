/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.xml.xpath;

import org.jaxen.NamespaceContext;

/**
 * @author Eduardo Lundgren
 */
public class LiferayNamespaceContext implements NamespaceContext {

	public LiferayNamespaceContext() {
		_contextMap = new LiferayNamespaceContextMap();
	}

	public LiferayNamespaceContext(LiferayNamespaceContextMap contextMap) {
		_contextMap = contextMap;
	}

	public void setContextMap(LiferayNamespaceContextMap contextMap) {
		_contextMap = contextMap;
	}

	public String translateNamespacePrefixToUri(String prefix) {
		return _contextMap.get(prefix);
	}

	private LiferayNamespaceContextMap _contextMap;

}