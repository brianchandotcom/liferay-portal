/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.QName;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletQNameUtil {

	public static String getKey(QName qName) {
		return _portletQName.getKey(qName);
	}

	public static String getKey(String uri, String localPart) {
		return _portletQName.getKey(uri, localPart);
	}

	public static PortletQName getPortletQName() {
		return _portletQName;
	}

	public static String getPublicRenderParameterIdentifier(
		String publicRenderParameterName) {

		return _portletQName.getPublicRenderParameterIdentifier(
			publicRenderParameterName);
	}

	public static String getPublicRenderParameterName(QName qName) {
		return _portletQName.getPublicRenderParameterName(qName);
	}

	public static QName getQName(
		Element qNameEl, Element nameEl, String defaultNamespace) {

		return _portletQName.getQName(qNameEl, nameEl, defaultNamespace);
	}

	public static QName getQName(String publicRenderParameterName) {
		return _portletQName.getQName(publicRenderParameterName);
	}

	public static String getRemovePublicRenderParameterName(QName qName) {
		return _portletQName.getRemovePublicRenderParameterName(qName);
	}

	public static void setPublicRenderParameterIdentifier(
		String publicRenderParameterName, String identifier) {

		_portletQName.setPublicRenderParameterIdentifier(
			publicRenderParameterName, identifier);
	}

	public void setPortletQName(PortletQName portletQName) {
		_portletQName = portletQName;
	}

	private static PortletQName _portletQName;

}