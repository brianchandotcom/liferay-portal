/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Raposo
 */
@ProviderType
public interface PortletElementUtil {

	public Portlet getPortlet(long companyId, Element portletElement);

	public String getPortletDataHandlerKey(Element portletElement);

	public int getRank(Element portletElement);

	public String getSourcePortletId(Element portletElement);

	public String getTargetPortletId(long companyId, Element portletElement);

	public boolean isMissingPortlet(
		PortletDataContext portletDataContext, Element portletElement);

	public boolean isMissingPortletSupported(Element element);

}