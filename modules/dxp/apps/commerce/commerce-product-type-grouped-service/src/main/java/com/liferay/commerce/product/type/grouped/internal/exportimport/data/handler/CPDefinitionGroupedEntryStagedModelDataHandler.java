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

package com.liferay.commerce.product.type.grouped.internal.exportimport.data.handler;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
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
public class CPDefinitionGroupedEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionGroupedEntry> {

	public static final String[] CLASS_NAMES =
		{CPDefinitionGroupedEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionGroupedEntry,
			cpDefinitionGroupedEntry.getCPDefinition(),
			PortletDataContext.REFERENCE_TYPE_PARENT);
		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionGroupedEntry,
			cpDefinitionGroupedEntry.getEntryCPDefinition(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element cpDefinitionGroupedEntryElement =
			portletDataContext.getExportDataElement(cpDefinitionGroupedEntry);

		portletDataContext.addClassedModel(
			cpDefinitionGroupedEntryElement,
			ExportImportPathUtil.getModelPath(cpDefinitionGroupedEntry),
			cpDefinitionGroupedEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionGroupedEntryId)
		throws Exception {

		CPDefinitionGroupedEntry existingCPDefinitionGroupedEntry =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpDefinitionGroupedEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionGroupedEntry.class);

		cpDefinitionGroupedEntryIds.put(
			cpDefinitionGroupedEntryId,
			existingCPDefinitionGroupedEntry.getCPDefinitionGroupedEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry)
		throws Exception {

		Map<Long, Long> cpDefinitionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinition.class);

		long cpDefinitionId = MapUtil.getLong(
			cpDefinitionIds, cpDefinitionGroupedEntry.getCPDefinitionId(),
			cpDefinitionGroupedEntry.getCPDefinitionId());
		long entryCPDefinitionId = MapUtil.getLong(
			cpDefinitionIds, cpDefinitionGroupedEntry.getEntryCPDefinitionId(),
			cpDefinitionGroupedEntry.getEntryCPDefinitionId());

		CPDefinitionGroupedEntry importedCPDefinitionGroupedEntry =
			(CPDefinitionGroupedEntry)cpDefinitionGroupedEntry.clone();

		importedCPDefinitionGroupedEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPDefinitionGroupedEntry.setCPDefinitionId(cpDefinitionId);
		importedCPDefinitionGroupedEntry.setEntryCPDefinitionId(
			entryCPDefinitionId);

		CPDefinitionGroupedEntry existingCPDefinitionGroupedEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinitionGroupedEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionGroupedEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionGroupedEntry =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPDefinitionGroupedEntry);
		}
		else {
			importedCPDefinitionGroupedEntry.setCPDefinitionGroupedEntryId(
				existingCPDefinitionGroupedEntry.
					getCPDefinitionGroupedEntryId());

			importedCPDefinitionGroupedEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPDefinitionGroupedEntry);
		}

		portletDataContext.importClassedModel(
			cpDefinitionGroupedEntry, importedCPDefinitionGroupedEntry);
	}

	@Override
	protected StagedModelRepository<CPDefinitionGroupedEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionGroupedEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionGroupedEntry>
		_stagedModelRepository;

}