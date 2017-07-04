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

package com.liferay.exportimport.test.util.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.test.util.internal.exportimport.staged.model.repository.DummyStagedModelRepository;
import com.liferay.exportimport.test.util.model.Dummy;
import com.liferay.exportimport.test.util.model.DummyFolder;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service =
		{DummyFolderStagedModelDataHandler.class, StagedModelDataHandler.class}
)
public class DummyFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<DummyFolder> {

	public static final String[] CLASS_NAMES = {DummyFolder.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, DummyFolder dummyFolder)
		throws Exception {

		Element dummyFolderElement = portletDataContext.getExportDataElement(
			dummyFolder);

		List<Dummy> dummies =
			((DummyStagedModelRepository)_dummyStagedModelRepository).
				fetchDummiesByFolderId(dummyFolder.getId());

		for (Dummy dummy : dummies) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, dummyFolder, dummy,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		portletDataContext.addClassedModel(
			dummyFolderElement, ExportImportPathUtil.getModelPath(dummyFolder),
			dummyFolder);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, DummyFolder dummyFolder)
		throws Exception {

		DummyFolder importedDummyFolder = (DummyFolder)dummyFolder.clone();

		importedDummyFolder.setGroupId(portletDataContext.getScopeGroupId());

		DummyFolder existingDummyFolder =
			_dummyFolderStagedModelRepository.fetchStagedModelByUuidAndGroupId(
				importedDummyFolder.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingDummyFolder == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			_dummyFolderStagedModelRepository.addStagedModel(
				portletDataContext, importedDummyFolder);
		}
		else {
			importedDummyFolder.setId(existingDummyFolder.getId());

			_dummyFolderStagedModelRepository.updateStagedModel(
				portletDataContext, importedDummyFolder);
		}
	}

	@Override
	protected StagedModelRepository<DummyFolder> getStagedModelRepository() {
		return _dummyFolderStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.exportimport.test.util.model.DummyFolder)",
		unbind = "-"
	)
	protected void setDummyFolderStagedModelRepository(
		StagedModelRepository<DummyFolder> dummyFolderStagedModelRepository) {

		_dummyFolderStagedModelRepository = dummyFolderStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.exportimport.test.util.model.Dummy)",
		unbind = "-"
	)
	protected void setDummyStagedModelRepository(
		StagedModelRepository<Dummy> dummyStagedModelRepository) {

		_dummyStagedModelRepository = dummyStagedModelRepository;
	}

	private StagedModelRepository<DummyFolder>
		_dummyFolderStagedModelRepository;
	private StagedModelRepository<Dummy> _dummyStagedModelRepository;

}