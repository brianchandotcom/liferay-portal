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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
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
public class CPDefinitionOptionValueRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionOptionValueRel> {

	public static final String[] CLASS_NAMES =
		{CPDefinitionOptionValueRel.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		String displayName = cpDefinitionOptionValueRel.getTitleCurrentValue();

		if (Validator.isNull(displayName)) {
			displayName = cpDefinitionOptionValueRel.getName();
		}

		return displayName;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionOptionValueRel,
			cpDefinitionOptionValueRel.getCPDefinitionOptionRel(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element cpDefinitionOptionValueRelElement =
			portletDataContext.getExportDataElement(cpDefinitionOptionValueRel);

		portletDataContext.addClassedModel(
			cpDefinitionOptionValueRelElement,
			ExportImportPathUtil.getModelPath(cpDefinitionOptionValueRel),
			cpDefinitionOptionValueRel);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionOptionValueRelId)
		throws Exception {

		CPDefinitionOptionValueRel existingCPDefinitionOptionValueRel =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpDefinitionOptionValueRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionOptionValueRel.class);

		cpDefinitionOptionValueRelIds.put(
			cpDefinitionOptionValueRelId,
			existingCPDefinitionOptionValueRel.
				getCPDefinitionOptionValueRelId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws Exception {

		Map<Long, Long> cpDefinitionOptionRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionOptionRel.class);

		long cpDefinitionOptionRelId = MapUtil.getLong(
			cpDefinitionOptionRelIds,
			cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
			cpDefinitionOptionValueRel.getCPDefinitionOptionRelId());

		CPDefinitionOptionValueRel importedCPDefinitionOptionValueRel =
			(CPDefinitionOptionValueRel)cpDefinitionOptionValueRel.clone();

		importedCPDefinitionOptionValueRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPDefinitionOptionValueRel.setCPDefinitionOptionRelId(
			cpDefinitionOptionRelId);

		CPDefinitionOptionValueRel existingCPDefinitionOptionValueRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpDefinitionOptionValueRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionOptionValueRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionOptionValueRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPDefinitionOptionValueRel);
		}
		else {
			importedCPDefinitionOptionValueRel.setCPDefinitionOptionValueRelId(
				existingCPDefinitionOptionValueRel.
					getCPDefinitionOptionValueRelId());

			importedCPDefinitionOptionValueRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPDefinitionOptionValueRel);
		}

		portletDataContext.importClassedModel(
			cpDefinitionOptionValueRel, importedCPDefinitionOptionValueRel);
	}

	@Override
	protected StagedModelRepository<CPDefinitionOptionValueRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionOptionValueRel)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionOptionValueRel>
			stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionOptionValueRel>
		_stagedModelRepository;

}