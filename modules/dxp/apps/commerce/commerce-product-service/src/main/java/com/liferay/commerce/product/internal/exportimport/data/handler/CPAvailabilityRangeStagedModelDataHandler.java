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

import com.liferay.commerce.product.model.CPAvailabilityRange;
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
public class CPAvailabilityRangeStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPAvailabilityRange> {

	public static final String[] CLASS_NAMES =
		{CPAvailabilityRange.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPAvailabilityRange cpAvailabilityRange) {
		return cpAvailabilityRange.getTitleCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPAvailabilityRange cpAvailabilityRange)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			cpAvailabilityRange);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cpAvailabilityRange),
			cpAvailabilityRange);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpAvailabilityRangeId)
		throws Exception {

		CPAvailabilityRange existingCPAvailabilityRange = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> cpAvailabilityRangeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPAvailabilityRange.class);

		cpAvailabilityRangeIds.put(
			cpAvailabilityRangeId,
			existingCPAvailabilityRange.getCPAvailabilityRangeId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPAvailabilityRange cpAvailabilityRange)
		throws Exception {

		CPAvailabilityRange importedCPAvailabilityRange =
			(CPAvailabilityRange)cpAvailabilityRange.clone();

		importedCPAvailabilityRange.setGroupId(
			portletDataContext.getScopeGroupId());

		CPAvailabilityRange existingCPAvailabilityRange =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpAvailabilityRange.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPAvailabilityRange == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPAvailabilityRange = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPAvailabilityRange);
		}
		else {
			importedCPAvailabilityRange.setCPAvailabilityRangeId(
				existingCPAvailabilityRange.getCPAvailabilityRangeId());

			importedCPAvailabilityRange =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPAvailabilityRange);
		}

		portletDataContext.importClassedModel(
			cpAvailabilityRange, importedCPAvailabilityRange);
	}

	@Override
	protected StagedModelRepository<CPAvailabilityRange>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPAvailabilityRange)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPAvailabilityRange> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPAvailabilityRange> _stagedModelRepository;

}