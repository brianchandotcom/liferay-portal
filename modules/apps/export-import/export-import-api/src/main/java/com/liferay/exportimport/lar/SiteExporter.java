/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.lar;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;

/**
 * @author Petteri Karttunen
 */
public interface SiteExporter {

	public void addSitesElement(
			PortletDataContext portletDataContext, Element element)
		throws PortalException;

	public void exportSites(
			PortletDataContext portletDataContext,
			UnsafeConsumer<PortletDataContext, Exception>
				exportSiteUnsafeConsumer)
		throws Exception;

}