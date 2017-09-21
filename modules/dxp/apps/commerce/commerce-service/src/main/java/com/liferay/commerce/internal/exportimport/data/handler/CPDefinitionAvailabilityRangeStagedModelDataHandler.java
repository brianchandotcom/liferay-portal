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

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
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
public class CPDefinitionAvailabilityRangeStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionAvailabilityRange> {

	public static final String[] CLASS_NAMES =
		{CPDefinitionAvailabilityRange.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {

		return String.valueOf(
			cpDefinitionAvailabilityRange.getCPDefinitionAvailabilityRangeId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			cpDefinitionAvailabilityRange);

		portletDataContext.addClassedModel(
			element,
			ExportImportPathUtil.getModelPath(cpDefinitionAvailabilityRange),
			cpDefinitionAvailabilityRange);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionAvailabilityRangeId)
		throws Exception {

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpDefinitionAvailabilityRangeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionAvailabilityRange.class);

		cpDefinitionAvailabilityRangeIds.put(
			cpDefinitionAvailabilityRangeId,
			existingCPDefinitionAvailabilityRange.
				getCPDefinitionAvailabilityRangeId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange)
		throws Exception {

		CPDefinitionAvailabilityRange importedCPDefinitionAvailabilityRange =
			(CPDefinitionAvailabilityRange)
				cpDefinitionAvailabilityRange.clone();

		importedCPDefinitionAvailabilityRange.setGroupId(
			portletDataContext.getScopeGroupId());

		CPDefinitionAvailabilityRange existingCPDefinitionAvailabilityRange =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinitionAvailabilityRange.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionAvailabilityRange == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionAvailabilityRange =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPDefinitionAvailabilityRange);
		}
		else {
			importedCPDefinitionAvailabilityRange.
				setCPDefinitionAvailabilityRangeId(
					existingCPDefinitionAvailabilityRange.
						getCPDefinitionAvailabilityRangeId());

			importedCPDefinitionAvailabilityRange =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPDefinitionAvailabilityRange);
		}

		portletDataContext.importClassedModel(
			cpDefinitionAvailabilityRange,
			importedCPDefinitionAvailabilityRange);
	}

	@Override
	protected StagedModelRepository<CPDefinitionAvailabilityRange>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CPDefinitionAvailabilityRange)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionAvailabilityRange>
			stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionAvailabilityRange>
		_stagedModelRepository;

}