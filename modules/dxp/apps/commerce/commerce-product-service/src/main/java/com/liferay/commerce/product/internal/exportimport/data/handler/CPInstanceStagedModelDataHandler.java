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

import com.liferay.commerce.product.internal.exportimport.content.processor.CPInstanceExportImportContentProcessor;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPInstanceStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPInstance> {

	public static final String[] CLASS_NAMES = {CPInstance.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPInstance cpInstance) {
		return cpInstance.getSku();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, CPInstance cpInstance)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpInstance, cpInstance.getCPDefinition(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element cpInstanceElement = portletDataContext.getExportDataElement(
			cpInstance);

		String ddmContent =
			_cpInstanceExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, cpInstance, cpInstance.getDDMContent(),
					true, false);

		cpInstance.setDDMContent(ddmContent);

		portletDataContext.addClassedModel(
			cpInstanceElement, ExportImportPathUtil.getModelPath(cpInstance),
			cpInstance);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpInstanceId)
		throws Exception {

		CPInstance existingCPInstance = fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpInstanceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPInstance.class);

		cpInstanceIds.put(cpInstanceId, existingCPInstance.getCPInstanceId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, CPInstance cpInstance)
		throws Exception {

		Map<Long, Long> cpDefinitionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinition.class);

		long cpDefinitionId = MapUtil.getLong(
			cpDefinitionIds, cpInstance.getCPDefinitionId(),
			cpInstance.getCPDefinitionId());

		CPInstance importedCPInstance = (CPInstance)cpInstance.clone();

		importedCPInstance.setGroupId(portletDataContext.getScopeGroupId());
		importedCPInstance.setCPDefinitionId(cpDefinitionId);

		String ddmContent =
			_cpInstanceExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, cpInstance, cpInstance.getDDMContent());

		importedCPInstance.setDDMContent(ddmContent);

		CPInstance existingCPInstance =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpInstance.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCPInstance == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPInstance = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPInstance);
		}
		else {
			importedCPInstance.setCPInstanceId(
				existingCPInstance.getCPInstanceId());

			importedCPInstance = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCPInstance);
		}

		portletDataContext.importClassedModel(cpInstance, importedCPInstance);
	}

	@Override
	protected StagedModelRepository<CPInstance> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPInstance)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPInstance> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Reference
	private CPInstanceExportImportContentProcessor
		_cpInstanceExportImportContentProcessor;

	private StagedModelRepository<CPInstance> _stagedModelRepository;

}