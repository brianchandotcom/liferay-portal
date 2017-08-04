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

package com.liferay.commerce.product.internal.exportimport.data.handler;

import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.xml.Element;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPMeasurementUnitStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPMeasurementUnit> {

	public static final String[] CLASS_NAMES =
		{CPMeasurementUnit.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPMeasurementUnit cpMeasurementUnit) {
		return cpMeasurementUnit.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPMeasurementUnit cpMeasurementUnit)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			cpMeasurementUnit);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cpMeasurementUnit),
			cpMeasurementUnit);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceCurrencyId)
		throws Exception {

		CPMeasurementUnit existingCPMeasurementUnit = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> cpMeasurementUnitIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPMeasurementUnit.class);

		cpMeasurementUnitIds.put(
			commerceCurrencyId,
			existingCPMeasurementUnit.getCPMeasurementUnitId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPMeasurementUnit cpMeasurementUnit)
		throws Exception {

		CPMeasurementUnit importedCPMeasurementUnit =
			(CPMeasurementUnit)cpMeasurementUnit.clone();

		importedCPMeasurementUnit.setGroupId(
			portletDataContext.getScopeGroupId());

		CPMeasurementUnit existingCPMeasurementUnit =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpMeasurementUnit.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPMeasurementUnit == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPMeasurementUnit = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPMeasurementUnit);
		}
		else {
			importedCPMeasurementUnit.setCPMeasurementUnitId(
				existingCPMeasurementUnit.getCPMeasurementUnitId());

			importedCPMeasurementUnit = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCPMeasurementUnit);
		}

		portletDataContext.importClassedModel(
			cpMeasurementUnit, importedCPMeasurementUnit);
	}

	@Override
	protected StagedModelRepository<CPMeasurementUnit>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPMeasurementUnit)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPMeasurementUnit> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPMeasurementUnit> _stagedModelRepository;

}