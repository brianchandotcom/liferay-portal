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

package com.liferay.portlet.dynamicdatamapping.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class DDMStructureStagedModelDataHandler
	extends BaseStagedModelDataHandler<DDMStructure> {

	public static final String[] CLASS_NAMES = {DDMStructure.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		Element structureElement = portletDataContext.getExportDataElement(
			structure);

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			structure.getCompanyId());

		if (defaultUserId == structure.getUserId()) {
			structureElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			structureElement, ExportImportPathUtil.getModelPath(structure),
			structure, DDMPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		prepareLanguagesForImport(structure);

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structure, DDMPortletDataHandler.NAMESPACE);

		Map<String, String> ddmStructureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		DDMStructure importedStructure = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(structure);

			boolean preloaded = GetterUtil.getBoolean(
				element.attributeValue("preloaded"));

			DDMStructure existingStructure = null;

			if (!preloaded) {
				existingStructure = DDMStructureUtil.fetchByUUID_G(
					structure.getUuid(), portletDataContext.getScopeGroupId());
			}
			else {
				existingStructure = DDMStructureUtil.fetchByG_C_S(
					portletDataContext.getScopeGroupId(),
					structure.getClassNameId(), structure.getStructureKey());
			}

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				importedStructure = DDMStructureLocalServiceUtil.addStructure(
					userId, portletDataContext.getScopeGroupId(),
					structure.getParentStructureId(),
					structure.getClassNameId(), structure.getStructureKey(),
					structure.getNameMap(), structure.getDescriptionMap(),
					structure.getXsd(), structure.getStorageType(),
					structure.getType(), serviceContext);
			}
			else {
				importedStructure =
					DDMStructureLocalServiceUtil.updateStructure(
						existingStructure.getStructureId(),
						structure.getParentStructureId(),
						structure.getNameMap(), structure.getDescriptionMap(),
						structure.getXsd(), serviceContext);
			}
		}
		else {
			importedStructure = DDMStructureLocalServiceUtil.addStructure(
				userId, portletDataContext.getScopeGroupId(),
				structure.getParentStructureId(), structure.getClassNameId(),
				structure.getStructureKey(), structure.getNameMap(),
				structure.getDescriptionMap(), structure.getXsd(),
				structure.getStorageType(), structure.getType(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			structure, importedStructure, DDMPortletDataHandler.NAMESPACE);

		ddmStructureKeys.put(
			structure.getStructureKey(), importedStructure.getStructureKey());
	}

	protected void prepareLanguagesForImport(DDMStructure structure)
		throws PortalException {

		Locale defaultLocale = LocaleUtil.fromLanguageId(
			structure.getDefaultLanguageId());

		Locale[] availableLocales = LocaleUtil.fromLanguageIds(
			structure.getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			DDMStructure.class.getName(), structure.getPrimaryKey(),
			defaultLocale, availableLocales);

		structure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

}