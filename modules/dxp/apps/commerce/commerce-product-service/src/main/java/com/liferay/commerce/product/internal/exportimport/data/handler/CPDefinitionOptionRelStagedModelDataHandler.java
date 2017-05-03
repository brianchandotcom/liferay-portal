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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPDefinitionOptionRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionOptionRel> {

	public static final String[] CLASS_NAMES = {
		CPDefinitionOptionRel.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPDefinitionOptionRel cpDefinitionOptionRel) {
		String displayName = cpDefinitionOptionRel.getTitleCurrentValue();

		if (Validator.isNull(displayName)) {
			displayName = cpDefinitionOptionRel.getName();
		}

		return displayName;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionOptionRel,
			cpDefinitionOptionRel.getCPDefinition(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionOptionRel,
			cpDefinitionOptionRel.getCPOption(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels()) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpDefinitionOptionRel,
				cpDefinitionOptionValueRel,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		Element cpDefinitionOptionRelElement =
			portletDataContext.getExportDataElement(cpDefinitionOptionRel);

		portletDataContext.addClassedModel(
			cpDefinitionOptionRelElement,
			ExportImportPathUtil.getModelPath(cpDefinitionOptionRel),
			cpDefinitionOptionRel);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionOptionRelId)
		throws Exception {

		CPDefinitionOptionRel existingCPDefinitionOptionRel =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpDefinitionOptionRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionOptionRel.class);

		cpDefinitionOptionRelIds.put(
			cpDefinitionOptionRelId,
			existingCPDefinitionOptionRel.getCPDefinitionOptionRelId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws Exception {

		Map<Long, Long> cpDefinitionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinition.class);

		long cpDefinitionId = MapUtil.getLong(
			cpDefinitionIds, cpDefinitionOptionRel.getCPDefinitionId(),
			cpDefinitionOptionRel.getCPDefinitionId());

		Map<Long, Long> cpOptionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPOption.class);

		long cpOptionId = MapUtil.getLong(
			cpOptionIds, cpDefinitionOptionRel.getCPOptionId(),
			cpDefinitionOptionRel.getCPOptionId());

		CPDefinitionOptionRel importedCPDefinitionOptionRel =
			(CPDefinitionOptionRel)cpDefinitionOptionRel.clone();

		importedCPDefinitionOptionRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPDefinitionOptionRel.setCPDefinitionId(cpDefinitionId);
		importedCPDefinitionOptionRel.setCPOptionId(cpOptionId);

		CPDefinitionOptionRel existingCPDefinitionOptionRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinitionOptionRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionOptionRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionOptionRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPDefinitionOptionRel);
		}
		else {
			importedCPDefinitionOptionRel.setCPDefinitionOptionRelId(
				existingCPDefinitionOptionRel.getCPDefinitionOptionRelId());

			importedCPDefinitionOptionRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPDefinitionOptionRel);
		}

		portletDataContext.importClassedModel(
			cpDefinitionOptionRel, importedCPDefinitionOptionRel);
	}

	@Override
	protected StagedModelRepository<CPDefinitionOptionRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionOptionRel)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionOptionRel> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionOptionRel> _stagedModelRepository;

}