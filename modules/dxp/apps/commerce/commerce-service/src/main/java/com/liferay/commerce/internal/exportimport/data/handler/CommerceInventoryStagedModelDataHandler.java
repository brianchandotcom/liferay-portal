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

import com.liferay.commerce.model.CommerceInventory;
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
public class CommerceInventoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommerceInventory> {

	public static final String[] CLASS_NAMES =
		{CommerceInventory.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CommerceInventory commerceInventory) {
		return String.valueOf(commerceInventory.getCommerceInventoryId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommerceInventory commerceInventory)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			commerceInventory);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commerceInventory),
			commerceInventory);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceInventoryId)
		throws Exception {

		CommerceInventory existingCommerceInventory = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> commerceInventoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceInventory.class);

		commerceInventoryIds.put(
			commerceInventoryId,
			existingCommerceInventory.getCommerceInventoryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommerceInventory commerceInventory)
		throws Exception {

		CommerceInventory importedCommerceInventory =
			(CommerceInventory)commerceInventory.clone();

		importedCommerceInventory.setGroupId(
			portletDataContext.getScopeGroupId());

		CommerceInventory existingCommerceInventory =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commerceInventory.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommerceInventory == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommerceInventory = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCommerceInventory);
		}
		else {
			importedCommerceInventory.setCommerceInventoryId(
				existingCommerceInventory.getCommerceInventoryId());

			importedCommerceInventory =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCommerceInventory);
		}

		portletDataContext.importClassedModel(
			commerceInventory, importedCommerceInventory);
	}

	@Override
	protected StagedModelRepository<CommerceInventory>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceInventory)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommerceInventory> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommerceInventory> _stagedModelRepository;

}