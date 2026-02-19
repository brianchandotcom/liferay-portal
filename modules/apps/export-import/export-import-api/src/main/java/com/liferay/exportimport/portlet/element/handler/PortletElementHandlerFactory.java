/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.portlet.element.handler;

import com.liferay.portal.kernel.xml.Element;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Correa
 */
@ProviderType
public interface PortletElementHandlerFactory {

	public PortletElementHandler create(Element element);

}