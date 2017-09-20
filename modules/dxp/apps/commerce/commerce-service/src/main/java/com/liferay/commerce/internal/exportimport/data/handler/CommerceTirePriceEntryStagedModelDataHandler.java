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
import com.liferay.commerce.model.CommerceTirePriceEntry;
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
public class CommerceTirePriceEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommerceTirePriceEntry> {

	public static final String[] CLASS_NAMES =
		{CommerceTirePriceEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		CommerceTirePriceEntry commerceTirePriceEntry) {

		return String.valueOf(
			commerceTirePriceEntry.getCommerceTirePriceEntryId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommerceTirePriceEntry commerceTirePriceEntry)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, commerceTirePriceEntry,
			commerceTirePriceEntry.getCommercePriceEntry(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element element = portletDataContext.getExportDataElement(
			commerceTirePriceEntry);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commerceTirePriceEntry),
			commerceTirePriceEntry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceTirePriceId)
		throws Exception {

		CommerceTirePriceEntry existingCommerceTirePriceEntry =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> commerceTirePriceEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceTirePriceEntry.class);

		commerceTirePriceEntryIds.put(
			commerceTirePriceId,
			existingCommerceTirePriceEntry.getCommerceTirePriceEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommerceTirePriceEntry commerceTirePriceEntry)
		throws Exception {

		Map<Long, Long> commercePriceEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommercePriceEntry.class);

		long commercePriceEntryId = MapUtil.getLong(
			commercePriceEntryIds,
			commerceTirePriceEntry.getCommercePriceEntryId(),
			commerceTirePriceEntry.getCommercePriceEntryId());

		CommerceTirePriceEntry importedCommerceTirePriceEntry =
			(CommerceTirePriceEntry)commerceTirePriceEntry.clone();

		importedCommerceTirePriceEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCommerceTirePriceEntry.setCommercePriceEntryId(
			commercePriceEntryId);

		CommerceTirePriceEntry existingCommerceTirePriceEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commerceTirePriceEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommerceTirePriceEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommerceTirePriceEntry =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCommerceTirePriceEntry);
		}
		else {
			importedCommerceTirePriceEntry.setCommerceTirePriceEntryId(
				existingCommerceTirePriceEntry.getCommerceTirePriceEntryId());

			importedCommerceTirePriceEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCommerceTirePriceEntry);
		}

		portletDataContext.importClassedModel(
			commerceTirePriceEntry, importedCommerceTirePriceEntry);
	}

	@Override
	protected StagedModelRepository<CommerceTirePriceEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceTirePriceEntry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommerceTirePriceEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommerceTirePriceEntry>
		_stagedModelRepository;

}