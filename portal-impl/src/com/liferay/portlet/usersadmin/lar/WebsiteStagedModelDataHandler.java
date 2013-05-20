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

package com.liferay.portlet.usersadmin.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WebsiteLocalServiceUtil;

/**
 * @author David Mendez Gonzalez
 */
public class WebsiteStagedModelDataHandler
	extends BaseStagedModelDataHandler<Website> {

	public static final String[] CLASS_NAMES = {Website.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Website website)
		throws Exception {

		Element websiteElement = portletDataContext.getExportDataElement(
			website);

		portletDataContext.addClassedModel(
			websiteElement, ExportImportPathUtil.getModelPath(website), website,
			UsersAdminPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Website website)
		throws Exception {

		long userId = portletDataContext.getUserId(website.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			website, UsersAdminPortletDataHandler.NAMESPACE);

		Website existingWebsite =
			WebsiteLocalServiceUtil.fetchWebsiteByUuidAndCompanyId(
				website.getUuid(), portletDataContext.getCompanyId());

		Website importedWebsite = null;

		if (existingWebsite == null) {
			serviceContext.setUuid(website.getUuid());

			importedWebsite = WebsiteLocalServiceUtil.addWebsite(
				userId, website.getClassName(), website.getClassPK(),
				website.getUrl(), website.getTypeId(), website.isPrimary(),
				serviceContext);
		}
		else {
			importedWebsite = WebsiteLocalServiceUtil.updateWebsite(
				existingWebsite.getWebsiteId(), website.getUrl(),
				website.getTypeId(), website.isPrimary());

		}

		portletDataContext.importClassedModel(
			website, importedWebsite, UsersAdminPortletDataHandler.NAMESPACE);
	}

}