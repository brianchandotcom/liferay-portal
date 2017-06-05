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

package com.liferay.commerce.product.internal.exportimport.data.handler;

import com.liferay.commerce.product.internal.exportimport.content.processor.CPDefinitionExportImportContentProcessor;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPDefinitionStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinition> {

	public static final String[] CLASS_NAMES = {CPDefinition.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPDefinition cpDefinition) {
		String displayName = cpDefinition.getTitleCurrentValue();

		if (Validator.isNull(displayName)) {
			displayName = cpDefinition.getTitle();
		}

		return displayName;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, CPDefinition cpDefinition)
		throws Exception {

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinition.getCPDefinitionOptionRels()) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpDefinition, cpDefinitionOptionRel,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		CPType cpType = _cpTypeServicesTracker.getCPType(
			cpDefinition.getProductTypeName());

		if (cpType != null) {
			cpType.exportCPDefinition(cpDefinition, portletDataContext);
		}

		Element element = portletDataContext.getExportDataElement(cpDefinition);

		Map<Locale, String> exportedDescriptionMap = new HashMap<>();

		Map<Locale, String> descriptionMap = cpDefinition.getDescriptionMap();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			Locale locale = entry.getKey();
			String description = entry.getValue();

			description =
				_cpDefinitionExportImportContentProcessor.
					replaceExportContentReferences(
						portletDataContext, cpDefinition, description,
						portletDataContext.getBooleanParameter(
							CPPortletDataHandler.NAMESPACE,
							"referenced-content"),
						false);

			exportedDescriptionMap.put(locale, description);
		}

		cpDefinition.setDescriptionMap(exportedDescriptionMap);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cpDefinition),
			cpDefinition);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionId)
		throws Exception {

		CPDefinition existingCPDefinition = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> cpDefinitionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinition.class);

		cpDefinitionIds.put(
			cpDefinitionId, existingCPDefinition.getCPDefinitionId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, CPDefinition cpDefinition)
		throws Exception {

		CPDefinition importedCPDefinition = (CPDefinition)cpDefinition.clone();

		importedCPDefinition.setGroupId(portletDataContext.getScopeGroupId());

		Map<Locale, String> importedDescriptionMap = new HashMap<>();

		Map<Locale, String> descriptionMap =
			importedCPDefinition.getDescriptionMap();

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			Locale locale = entry.getKey();
			String description = entry.getValue();

			description =
				_cpDefinitionExportImportContentProcessor.
					replaceImportContentReferences(
						portletDataContext, cpDefinition, description);

			importedDescriptionMap.put(locale, description);
		}

		importedCPDefinition.setDescriptionMap(importedDescriptionMap);

		CPDefinition existingCPDefinition =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinition.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCPDefinition == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinition = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPDefinition);
		}
		else {
			importedCPDefinition.setCPDefinitionId(
				existingCPDefinition.getCPDefinitionId());

			importedCPDefinition = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCPDefinition);
		}

		portletDataContext.importClassedModel(
			cpDefinition, importedCPDefinition);
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, CPDefinition cpDefinition)
		throws Exception {

		long userId = portletDataContext.getUserId(cpDefinition.getUserUuid());

		CPDefinition existingCPDefinition = fetchStagedModelByUuidAndGroupId(
			cpDefinition.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCPDefinition == null) ||
			!existingCPDefinition.isInTrash()) {

			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			CPDefinition.class.getName());

		if (trashHandler.isRestorable(
				existingCPDefinition.getCPDefinitionId())) {

			trashHandler.restoreTrashEntry(
				userId, existingCPDefinition.getCPDefinitionId());
		}
	}

	@Override
	protected StagedModelRepository<CPDefinition> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinition)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinition> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Reference
	private CPDefinitionExportImportContentProcessor
		_cpDefinitionExportImportContentProcessor;

	@Reference
	private CPTypeServicesTracker _cpTypeServicesTracker;

	private StagedModelRepository<CPDefinition> _stagedModelRepository;

}