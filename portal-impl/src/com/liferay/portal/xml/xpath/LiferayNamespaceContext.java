/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml.xpath;

import java.util.Map;

import org.jaxen.NamespaceContext;

/**
 * @author Eduardo Lundgren
 */
public class LiferayNamespaceContext implements NamespaceContext {

	public LiferayNamespaceContext(Map<String, String> namespaceContextMap) {
		_namespaceContextMap = namespaceContextMap;
	}

	@Override
	public String translateNamespacePrefixToUri(String prefix) {
		if (_namespaceContextMap == null) {
			return null;
		}

		return _namespaceContextMap.get(prefix);
	}

	private Map<String, String> _namespaceContextMap;

}