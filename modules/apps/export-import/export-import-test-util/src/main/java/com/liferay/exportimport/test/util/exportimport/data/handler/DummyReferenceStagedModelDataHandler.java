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
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.test.util.model.DummyReference;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service = {
		DummyReferenceStagedModelDataHandler.class, StagedModelDataHandler.class
	}
)
public class DummyReferenceStagedModelDataHandler
	extends BaseStagedModelDataHandler<DummyReference> {

	public static final String[] CLASS_NAMES = {DummyReference.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DummyReference dummyReference)
		throws Exception {

		Element dummyReferenceElement = portletDataContext.getExportDataElement(
			dummyReference);

		portletDataContext.addClassedModel(
			dummyReferenceElement,
			ExportImportPathUtil.getModelPath(dummyReference), dummyReference);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DummyReference dummyReference)
		throws Exception {

		DummyReference importedDummyReference =
			(DummyReference)dummyReference.clone();

		importedDummyReference.setGroupId(portletDataContext.getScopeGroupId());

		DummyReference existingDummyReference =
			_dummyReferenceStagedModelRepository.
				fetchStagedModelByUuidAndGroupId(
					importedDummyReference.getUuid(),
					portletDataContext.getScopeGroupId());

		if ((existingDummyReference == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			_dummyReferenceStagedModelRepository.addStagedModel(
				portletDataContext, importedDummyReference);
		}
		else {
			importedDummyReference.setId(existingDummyReference.getId());

			_dummyReferenceStagedModelRepository.updateStagedModel(
				portletDataContext, importedDummyReference);
		}
	}

	@Override
	protected StagedModelRepository<DummyReference> getStagedModelRepository() {
		return _dummyReferenceStagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.exportimport.test.util.model.DummyReference)",
		unbind = "-"
	)
	protected void setDummyReferenceStagedModelRepository(
		StagedModelRepository<DummyReference>
			dummyReferenceStagedModelRepository) {

		_dummyReferenceStagedModelRepository =
			dummyReferenceStagedModelRepository;
	}

	private StagedModelRepository<DummyReference>
		_dummyReferenceStagedModelRepository;

}