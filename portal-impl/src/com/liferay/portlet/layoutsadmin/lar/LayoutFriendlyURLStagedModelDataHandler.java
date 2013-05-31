/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.layoutsadmin.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.LayoutFriendlyURLUtil;

/**
 * @author Sergio González
 */
public class LayoutFriendlyURLStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutFriendlyURL> {

	public static final String[] CLASS_NAMES =
		{LayoutFriendlyURL.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL)
		throws Exception {

		Element layoutFriendlyURLElement =
			portletDataContext.getExportDataElement(layoutFriendlyURL);

		portletDataContext.addClassedModel(
			layoutFriendlyURLElement,
			ExportImportPathUtil.getModelPath(layoutFriendlyURL),
			layoutFriendlyURL, LayoutPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL)
		throws Exception {

		long userId = portletDataContext.getUserId(
			layoutFriendlyURL.getUserUuid());

		long plid = portletDataContext.getPlid();

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutFriendlyURL, LayoutPortletDataHandler.NAMESPACE);

		LayoutFriendlyURL importedLayoutFriendlyURL = null;

		if (portletDataContext.isDataStrategyMirror()) {
			LayoutFriendlyURL existingLayoutFriendlyURL =
				LayoutFriendlyURLUtil.fetchByUUID_G(
					layoutFriendlyURL.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingLayoutFriendlyURL == null) {
				serviceContext.setUuid(layoutFriendlyURL.getUuid());

				importedLayoutFriendlyURL =
					LayoutFriendlyURLLocalServiceUtil.addLayoutFriendlyURL(
						userId, portletDataContext.getCompanyId(),
						portletDataContext.getScopeGroupId(), plid,
						portletDataContext.isPrivateLayout(),
						layoutFriendlyURL.getFriendlyURL(),
						layoutFriendlyURL.getLanguageId(), serviceContext);
			}
			else {
				importedLayoutFriendlyURL =
					LayoutFriendlyURLLocalServiceUtil.updateLayoutFriendlyURL(
						userId, portletDataContext.getCompanyId(),
						portletDataContext.getScopeGroupId(), plid,
						portletDataContext.isPrivateLayout(),
						layoutFriendlyURL.getFriendlyURL(),
						layoutFriendlyURL.getLanguageId(), serviceContext);
			}
		}
		else {
			importedLayoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.addLayoutFriendlyURL(
					userId, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), plid,
					portletDataContext.isPrivateLayout(),
					layoutFriendlyURL.getFriendlyURL(),
					layoutFriendlyURL.getLanguageId(), serviceContext);
		}

		portletDataContext.importClassedModel(
			layoutFriendlyURL, importedLayoutFriendlyURL,
			LayoutPortletDataHandler.NAMESPACE);
	}

}