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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

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
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				uuid, groupId);

		if (ddmStructure != null) {
			DDMStructureLocalServiceUtil.deleteStructure(ddmStructure);
		}
	}

	@Override
	public void doImportCompanyStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		DDMStructure existingStructure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				structure.getUuid(), portletDataContext.getCompanyGroupId());

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		structureIds.put(
			structure.getStructureId(), existingStructure.getStructureId());

		Map<String, String> structureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		structureKeys.put(
			structure.getStructureKey(), existingStructure.getStructureKey());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(DDMStructure structure) {
		return structure.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		Element structureElement = portletDataContext.getExportDataElement(
			structure);

		if (structure.getParentStructureId() !=
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID) {

			DDMStructure parentStructure =
				DDMStructureLocalServiceUtil.getStructure(
					structure.getParentStructureId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, structure, parentStructure,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			structure.getCompanyId());

		if (defaultUserId == structure.getUserId()) {
			structureElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			structureElement, ExportImportPathUtil.getModelPath(structure),
			structure);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DDMStructure structure)
		throws Exception {

		prepareLanguagesForImport(structure);

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		if (structure.getParentStructureId() !=
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID) {

			Element structureElement =
				portletDataContext.getReferenceDataElement(
					structure, DDMStructure.class,
					structure.getParentStructureId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, structureElement);
		}

		Map<Long, Long> structureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long parentStructureId = MapUtil.getLong(
			structureIds, structure.getParentStructureId(),
			structure.getParentStructureId());

		Map<String, String> structureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			structure);

		DDMStructure importedStructure = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Element element =
				portletDataContext.getImportDataStagedModelElement(structure);

			boolean preloaded = GetterUtil.getBoolean(
				element.attributeValue("preloaded"));

			DDMStructure existingStructure = null;

			if (!preloaded) {
				existingStructure =
					DDMStructureLocalServiceUtil.
						fetchDDMStructureByUuidAndGroupId(
							structure.getUuid(),
							portletDataContext.getScopeGroupId());
			}
			else {
				existingStructure = DDMStructureLocalServiceUtil.fetchStructure(
					portletDataContext.getScopeGroupId(),
					structure.getClassNameId(), structure.getStructureKey());
			}

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				importedStructure = DDMStructureLocalServiceUtil.addStructure(
					userId, portletDataContext.getScopeGroupId(),
					parentStructureId, structure.getClassNameId(),
					structure.getStructureKey(), structure.getNameMap(),
					structure.getDescriptionMap(), structure.getXsd(),
					structure.getStorageType(), structure.getType(),
					serviceContext);
			}
			else {
				importedStructure =
					DDMStructureLocalServiceUtil.updateStructure(
						existingStructure.getStructureId(), parentStructureId,
						structure.getNameMap(), structure.getDescriptionMap(),
						structure.getXsd(), serviceContext);
			}
		}
		else {
			importedStructure = DDMStructureLocalServiceUtil.addStructure(
				userId, portletDataContext.getScopeGroupId(), parentStructureId,
				structure.getClassNameId(), structure.getStructureKey(),
				structure.getNameMap(), structure.getDescriptionMap(),
				structure.getXsd(), structure.getStorageType(),
				structure.getType(), serviceContext);
		}

		portletDataContext.importClassedModel(structure, importedStructure);

		structureKeys.put(
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

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		DDMStructure structure =
			DDMStructureLocalServiceUtil.fetchDDMStructureByUuidAndGroupId(
				uuid, groupId);

		if (structure == null) {
			return false;
		}

		return true;
	}

}