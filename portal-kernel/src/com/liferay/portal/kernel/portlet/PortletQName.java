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