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
import com.liferay.exportimport.test.util.model.Dummy;
import com.liferay.exportimport.test.util.model.DummyReference;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	service = {DummyStagedModelDataHandler.class, StagedModelDataHandler.class}
)
public class DummyStagedModelDataHandler
	extends BaseStagedModelDataHandler<Dummy> {

	public static final String[] CLASS_NAMES = {Dummy.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Dummy dummy)
		throws Exception {

		Element dummyElement = portletDataContext.getExportDataElement(dummy);

		List<DummyReference> dummyReferences = dummy.getDummyReferences();

		for (DummyReference dummyReference : dummyReferences) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, dummy, dummyReference,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		portletDataContext.addClassedModel(
			dummyElement, ExportImportPathUtil.getModelPath(dummy), dummy);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Dummy dummy)
		throws Exception {

		Dummy importedDummy = (Dummy)dummy.clone();

		importedDummy.setGroupId(portletDataContext.getScopeGroupId());

		Dummy existingDummy =
			_dummyStagedModelRepository.fetchStagedModelByUuidAndGroupId(
				importedDummy.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingDummy == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			_dummyStagedModelRepository.addStagedModel(
				portletDataContext, importedDummy);
		}
		else {
			importedDummy.setId(existingDummy.getId());

			_dummyStagedModelRepository.updateStagedModel(
				portletDataContext, importedDummy);
		}
	}

	@Override
	protected StagedModelRepository<Dummy> getStagedModelRepository() {
		return _dummyStagedModelRepository;
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

	@Reference(
		target = "(model.class.name=com.liferay.exportimport.test.util.model.Dummy)",
		unbind = "-"
	)
	protected void setDummyStagedModelRepository(
		StagedModelRepository<Dummy> dummyStagedModelRepository) {

		_dummyStagedModelRepository = dummyStagedModelRepository;
	}

	private StagedModelRepository<DummyReference>
		_dummyReferenceStagedModelRepository;
	private StagedModelRepository<Dummy> _dummyStagedModelRepository;

}