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
public interface PortletQName {

	public static final String PRIVATE_RENDER_PARAMETER_NAMESPACE = "priv_r_p_";

	public static final String PUBLIC_RENDER_PARAMETER_NAMESPACE = "p_r_p_";

	public static final String REMOVE_PUBLIC_RENDER_PARAMETER_NAMESPACE =
		"r_p_r_p_";

	public String getKey(QName qName);

	public String getKey(String uri, String localPart);

	public String getPublicRenderParameterIdentifier(
		String publicRenderParameterName);

	public String getPublicRenderParameterName(QName qName);

	public QName getQName(
		Element qNameEl, Element nameEl, String defaultNamespace);

	public QName getQName(String publicRenderParameterName);

	public String getRemovePublicRenderParameterName(QName qName);

	public void setPublicRenderParameterIdentifier(
		String publicRenderParameterName, String identifier);

}