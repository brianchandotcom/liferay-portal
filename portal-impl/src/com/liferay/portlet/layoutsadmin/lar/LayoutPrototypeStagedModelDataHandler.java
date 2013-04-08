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
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Daniela Zapata Riesco
 */
public class LayoutPrototypeStagedModelDataHandler
	extends BaseStagedModelDataHandler <LayoutPrototype> {

	@Override
	public String getClassName() {
		return LayoutPrototype.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPrototype layoutPrototype)
		throws Exception {

		Element layoutPrototypeElement =
			portletDataContext.getExportDataStagedModelElement(layoutPrototype);

		portletDataContext.addClassedModel(
			layoutPrototypeElement,
			StagedModelPathUtil.getPath(layoutPrototype), layoutPrototype,
			LayoutPrototypePortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutPrototype layoutPrototype)
		throws Exception {

		long companyId = portletDataContext.getCompanyId();

		long userId = portletDataContext.getUserId(
			layoutPrototype.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutPrototype, LayoutPrototypePortletDataHandler.NAMESPACE);

			LayoutPrototype existingLayoutPrototype =
				LayoutPrototypeLocalServiceUtil.
					fetchLayoutPrototypeByUuidAndCompanyId(
						layoutPrototype.getUuid(), companyId);

			if (existingLayoutPrototype == null) {
				existingLayoutPrototype =
					LayoutPrototypeLocalServiceUtil.fetchLayoutPrototype(
						companyId, layoutPrototype.getName());
			}

		LayoutPrototype importedLayoutPrototype = null;

		if (existingLayoutPrototype == null) {
			serviceContext.setUuid(layoutPrototype.getUuid());

			importedLayoutPrototype =
				LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
					userId, companyId, layoutPrototype.getNameMap(),
					layoutPrototype.getDescription(),
					layoutPrototype.isActive(), serviceContext);
		}
		else {
			importedLayoutPrototype =
				LayoutPrototypeLocalServiceUtil.updateLayoutPrototype(
					existingLayoutPrototype.getLayoutPrototypeId(),
					layoutPrototype.getNameMap(),
					layoutPrototype.getDescription(),
					layoutPrototype.isActive(), serviceContext);
		}

		portletDataContext.importClassedModel(
			layoutPrototype, importedLayoutPrototype,
			LayoutPrototypePortletDataHandler.NAMESPACE);
	}

}