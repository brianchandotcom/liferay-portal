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

import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
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
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CommercePriceEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommercePriceEntry> {

	public static final String[] CLASS_NAMES =
		{CommercePriceEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CommercePriceEntry commercePriceEntry) {
		return String.valueOf(commercePriceEntry.getCommercePriceEntryId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceEntry commercePriceEntry)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, commercePriceEntry,
			commercePriceEntry.getCommercePriceList(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element element = portletDataContext.getExportDataElement(
			commercePriceEntry);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commercePriceEntry),
			commercePriceEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commercePriceEntryId)
		throws Exception {

		CommercePriceEntry existingCommercePriceEntry = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> commercePriceEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommercePriceEntry.class);

		commercePriceEntryIds.put(
			commercePriceEntryId,
			existingCommercePriceEntry.getCommercePriceEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceEntry commercePriceEntry)
		throws Exception {

		Map<Long, Long> commercePriceListIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommercePriceList.class);

		long commercePriceListId = MapUtil.getLong(
			commercePriceListIds, commercePriceEntry.getCommercePriceListId(),
			commercePriceEntry.getCommercePriceListId());

		CommercePriceEntry importedCommercePriceEntry =
			(CommercePriceEntry)commercePriceEntry.clone();

		importedCommercePriceEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCommercePriceEntry.setCommercePriceListId(commercePriceListId);

		CommercePriceEntry existingCommercePriceEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commercePriceEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommercePriceEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommercePriceEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCommercePriceEntry);
		}
		else {
			importedCommercePriceEntry.setCommercePriceEntryId(
				existingCommercePriceEntry.getCommercePriceEntryId());

			importedCommercePriceEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCommercePriceEntry);
		}

		portletDataContext.importClassedModel(
			commercePriceEntry, importedCommercePriceEntry);
	}

	@Override
	protected StagedModelRepository<CommercePriceEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommercePriceEntry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommercePriceEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommercePriceEntry> _stagedModelRepository;

}