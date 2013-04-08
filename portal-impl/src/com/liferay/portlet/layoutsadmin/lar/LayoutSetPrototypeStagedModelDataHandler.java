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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Daniela Zapata Riesco
 */
public class LayoutSetPrototypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutSetPrototype> {

	@Override
	public String getClassName() {
		return LayoutSetPrototype.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype)
		throws Exception {

		Element layoutSetPrototypeElement =
			portletDataContext.getExportDataStagedModelElement(
				layoutSetPrototype);

		portletDataContext.addClassedModel(
			layoutSetPrototypeElement,
			StagedModelPathUtil.getPath(layoutSetPrototype), layoutSetPrototype,
			LayoutSetPrototypePortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutSetPrototype layoutSetPrototype)
		throws Exception {

		long companyId = portletDataContext.getCompanyId();

		long userId = portletDataContext.getUserId(
			layoutSetPrototype.getUserUuid());

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		Boolean layoutsUpdateable = GetterUtil.getBoolean(
			settingsProperties.getProperty("layoutsUpdateable"), true);

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutSetPrototype, LayoutSetPrototypePortletDataHandler.NAMESPACE);

		LayoutSetPrototype existingLayoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.
				fetchLayoutSetPrototypeByUuidAndCompanyId(
					layoutSetPrototype.getUuid(), companyId);

		if (existingLayoutSetPrototype == null) {
			existingLayoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
					companyId, layoutSetPrototype.getName());
		}

		LayoutSetPrototype importedLayoutSetPrototype = null;

		if (existingLayoutSetPrototype == null) {
			serviceContext.setUuid(layoutSetPrototype.getUuid());

			importedLayoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
					userId, companyId, layoutSetPrototype.getNameMap(),
					layoutSetPrototype.getDescription(),
					layoutSetPrototype.isActive(), layoutsUpdateable,
					serviceContext);
		}
		else {
			importedLayoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
					existingLayoutSetPrototype.getLayoutSetPrototypeId(),
					layoutSetPrototype.getNameMap(),
					layoutSetPrototype.getDescription(),
					layoutSetPrototype.isActive(), layoutsUpdateable,
					serviceContext);
		}

		portletDataContext.importClassedModel(
			layoutSetPrototype, importedLayoutSetPrototype,
			LayoutSetPrototypePortletDataHandler.NAMESPACE);
	}

}