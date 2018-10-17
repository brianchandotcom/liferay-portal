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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.QName;

import javax.portlet.annotations.PortletQName;

/**
 * @author Shuyang Zhou
 */
public class PortletQNameUtil {

	public static javax.xml.namespace.QName getQName(
		Element qNameEl, Element nameEl, String defaultNamespace) {

		QName qName =
			com.liferay.portal.kernel.portlet.PortletQNameUtil.getQName(
				qNameEl, nameEl, defaultNamespace);

		return new javax.xml.namespace.QName(
			qName.getNamespaceURI(), qName.getLocalPart(),
			qName.getNamespacePrefix());
	}

	public static javax.xml.namespace.QName toQName(PortletQName portletQName) {
		return new javax.xml.namespace.QName(
			portletQName.namespaceURI(), portletQName.localPart());
	}

}