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
import com.liferay.commerce.model.CommerceTierPriceEntry;
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
public class CommerceTierPriceEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommerceTierPriceEntry> {

	public static final String[] CLASS_NAMES =
		{CommerceTierPriceEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		CommerceTierPriceEntry commerceTierPriceEntry) {

		return String.valueOf(
			commerceTierPriceEntry.getCommerceTierPriceEntryId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, commerceTierPriceEntry,
			commerceTierPriceEntry.getCommercePriceEntry(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element element = portletDataContext.getExportDataElement(
			commerceTierPriceEntry);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commerceTierPriceEntry),
			commerceTierPriceEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceTierPriceId)
		throws Exception {

		CommerceTierPriceEntry existingCommerceTierPriceEntry =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> commerceTierPriceEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceTierPriceEntry.class);

		commerceTierPriceEntryIds.put(
			commerceTierPriceId,
			existingCommerceTierPriceEntry.getCommerceTierPriceEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws Exception {

		Map<Long, Long> commercePriceEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommercePriceEntry.class);

		long commercePriceEntryId = MapUtil.getLong(
			commercePriceEntryIds,
			commerceTierPriceEntry.getCommercePriceEntryId(),
			commerceTierPriceEntry.getCommercePriceEntryId());

		CommerceTierPriceEntry importedCommerceTierPriceEntry =
			(CommerceTierPriceEntry)commerceTierPriceEntry.clone();

		importedCommerceTierPriceEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCommerceTierPriceEntry.setCommercePriceEntryId(
			commercePriceEntryId);

		CommerceTierPriceEntry existingCommerceTierPriceEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commerceTierPriceEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommerceTierPriceEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommerceTierPriceEntry =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCommerceTierPriceEntry);
		}
		else {
			importedCommerceTierPriceEntry.setCommerceTierPriceEntryId(
				existingCommerceTierPriceEntry.getCommerceTierPriceEntryId());

			importedCommerceTierPriceEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCommerceTierPriceEntry);
		}

		portletDataContext.importClassedModel(
			commerceTierPriceEntry, importedCommerceTierPriceEntry);
	}

	@Override
	protected StagedModelRepository<CommerceTierPriceEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceTierPriceEntry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommerceTierPriceEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommerceTierPriceEntry>
		_stagedModelRepository;

}