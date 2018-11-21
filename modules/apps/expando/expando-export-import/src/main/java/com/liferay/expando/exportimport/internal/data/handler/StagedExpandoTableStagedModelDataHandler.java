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

package com.liferay.expando.exportimport.internal.data.handler;

import com.liferay.expando.kernel.model.adapter.StagedExpandoTable;
import com.liferay.exportimport.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedExpandoTableStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedExpandoTable> {

	public static final String[] CLASS_NAMES = {
		StagedExpandoTable.class.getName()
	};

	@Override
	public void deleteStagedModel(StagedExpandoTable stagedExpandoTable)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(stagedExpandoTable);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public List<StagedExpandoTable> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoTable stagedExpandoTable)
		throws Exception {

		Element stagedExpandoTableElement =
			portletDataContext.getExportDataElement(stagedExpandoTable);

		portletDataContext.addClassedModel(
			stagedExpandoTableElement,
			ExportImportPathUtil.getModelPath(stagedExpandoTable),
			stagedExpandoTable);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedExpandoTable stagedExpandoTable)
		throws Exception {

		StagedExpandoTable importedStagedExpandoTable =
			(StagedExpandoTable)stagedExpandoTable.clone();

		List<StagedExpandoTable> stagedExpandoTables =
			_stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
				stagedExpandoTable.getUuid(),
				portletDataContext.getCompanyId());

		if (ListUtil.isEmpty(stagedExpandoTables)) {
			importedStagedExpandoTable = _stagedModelRepository.addStagedModel(
				portletDataContext, stagedExpandoTable);
		}
		else {
			importedStagedExpandoTable = stagedExpandoTables.get(0);
		}

		// Updating the expando table is not necessary because all of its
		// attributes are either IDs or used as IDs

		portletDataContext.importClassedModel(
			stagedExpandoTable, importedStagedExpandoTable);
	}

	@Reference(
		target = "(model.class.name=com.liferay.expando.kernel.model.adapter.StagedExpandoTable)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<StagedExpandoTable> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<StagedExpandoTable> _stagedModelRepository;

}