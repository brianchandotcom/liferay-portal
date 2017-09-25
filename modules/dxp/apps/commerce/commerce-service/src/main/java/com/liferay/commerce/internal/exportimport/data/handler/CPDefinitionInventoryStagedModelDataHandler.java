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

package com.liferay.commerce.internal.exportimport.data.handler;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPDefinitionInventoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionInventory> {

	public static final String[] CLASS_NAMES =
		{CPDefinitionInventory.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPDefinitionInventory cpDefinitionInventory) {
		return String.valueOf(
			cpDefinitionInventory.getCPDefinitionInventoryId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionInventory cpDefinitionInventory)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			cpDefinitionInventory);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cpDefinitionInventory),
			cpDefinitionInventory);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionInventoryId)
		throws Exception {

		CPDefinitionInventory existingCPDefinitionInventory =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpDefinitionInventoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionInventory.class);

		cpDefinitionInventoryIds.put(
			cpDefinitionInventoryId,
			existingCPDefinitionInventory.getCPDefinitionInventoryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionInventory cpDefinitionInventory)
		throws Exception {

		CPDefinitionInventory importedCPDefinitionInventory =
			(CPDefinitionInventory)cpDefinitionInventory.clone();

		importedCPDefinitionInventory.setGroupId(
			portletDataContext.getScopeGroupId());

		CPDefinitionInventory existingCPDefinitionInventory =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinitionInventory.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionInventory == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionInventory =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPDefinitionInventory);
		}
		else {
			importedCPDefinitionInventory.setCPDefinitionInventoryId(
				existingCPDefinitionInventory.getCPDefinitionInventoryId());

			importedCPDefinitionInventory =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPDefinitionInventory);
		}

		portletDataContext.importClassedModel(
			cpDefinitionInventory, importedCPDefinitionInventory);
	}

	@Override
	protected StagedModelRepository<CPDefinitionInventory>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CPDefinitionInventory)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionInventory> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionInventory> _stagedModelRepository;

}