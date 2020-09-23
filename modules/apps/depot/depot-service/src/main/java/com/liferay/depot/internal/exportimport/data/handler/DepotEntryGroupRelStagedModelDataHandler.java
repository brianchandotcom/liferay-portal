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

package com.liferay.depot.internal.exportimport.data.handler;

import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DepotEntryGroupRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<DepotEntryGroupRel> {

	public static final String[] CLASS_NAMES = {
		DepotEntryGroupRel.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DepotEntryGroupRel depotEntryGroupRel)
		throws Exception {

		portletDataContext.addClassedModel(
			portletDataContext.getExportDataElement(depotEntryGroupRel),
			ExportImportPathUtil.getModelPath(depotEntryGroupRel),
			depotEntryGroupRel);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DepotEntryGroupRel depotEntryGroupRel)
		throws Exception {

		DepotEntryGroupRel importedDepotEntryGroupRel =
			(DepotEntryGroupRel)depotEntryGroupRel.clone();

		importedDepotEntryGroupRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedDepotEntryGroupRel.setToGroupId(
			portletDataContext.getScopeGroupId());

		DepotEntryGroupRel existingDepotEntryGroupRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				depotEntryGroupRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingDepotEntryGroupRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedDepotEntryGroupRel = _stagedModelRepository.addStagedModel(
				portletDataContext, importedDepotEntryGroupRel);
		}
		else {
			importedDepotEntryGroupRel.setMvccVersion(
				existingDepotEntryGroupRel.getMvccVersion());

			importedDepotEntryGroupRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedDepotEntryGroupRel);
		}

		portletDataContext.importClassedModel(
			depotEntryGroupRel, importedDepotEntryGroupRel);
	}

	@Override
	protected StagedModelRepository<DepotEntryGroupRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.depot.model.DepotEntryGroupRel)",
		unbind = "-"
	)
	private StagedModelRepository<DepotEntryGroupRel> _stagedModelRepository;

}