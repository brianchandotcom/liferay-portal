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

import com.liferay.commerce.model.CAvailabilityRangeEntry;
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
public class CAvailabilityRangeEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CAvailabilityRangeEntry> {

	public static final String[] CLASS_NAMES =
		{CAvailabilityRangeEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		CAvailabilityRangeEntry cAvailabilityRangeEntry) {

		return String.valueOf(
			cAvailabilityRangeEntry.getCAvailabilityRangeEntryId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CAvailabilityRangeEntry cAvailabilityRangeEntry)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			cAvailabilityRangeEntry);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cAvailabilityRangeEntry),
			cAvailabilityRangeEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cAvailabilityRangeEntryId)
		throws Exception {

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cAvailabilityRangeEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CAvailabilityRangeEntry.class);

		cAvailabilityRangeEntryIds.put(
			cAvailabilityRangeEntryId,
			existingCAvailabilityRangeEntry.getCAvailabilityRangeEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CAvailabilityRangeEntry cAvailabilityRangeEntry)
		throws Exception {

		CAvailabilityRangeEntry importedCAvailabilityRangeEntry =
			(CAvailabilityRangeEntry)cAvailabilityRangeEntry.clone();

		importedCAvailabilityRangeEntry.setGroupId(
			portletDataContext.getScopeGroupId());

		CAvailabilityRangeEntry existingCAvailabilityRangeEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cAvailabilityRangeEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCAvailabilityRangeEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCAvailabilityRangeEntry =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCAvailabilityRangeEntry);
		}
		else {
			importedCAvailabilityRangeEntry.setCAvailabilityRangeEntryId(
				existingCAvailabilityRangeEntry.getCAvailabilityRangeEntryId());

			importedCAvailabilityRangeEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCAvailabilityRangeEntry);
		}

		portletDataContext.importClassedModel(
			cAvailabilityRangeEntry, importedCAvailabilityRangeEntry);
	}

	@Override
	protected StagedModelRepository<CAvailabilityRangeEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CAvailabilityRangeEntry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CAvailabilityRangeEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CAvailabilityRangeEntry>
		_stagedModelRepository;

}