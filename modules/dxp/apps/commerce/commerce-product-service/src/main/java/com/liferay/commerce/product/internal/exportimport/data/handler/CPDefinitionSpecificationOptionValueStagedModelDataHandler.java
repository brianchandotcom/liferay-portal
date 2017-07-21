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
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPDefinitionSpecificationOptionValueStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPDefinitionSpecificationOptionValue> {

	public static final String[] CLASS_NAMES =
		{CPDefinitionSpecificationOptionValue.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionSpecificationOptionValue,
			cpDefinitionSpecificationOptionValue.getCPDefinition(),
			PortletDataContext.REFERENCE_TYPE_PARENT);
		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpDefinitionSpecificationOptionValue,
			cpDefinitionSpecificationOptionValue.getCPSpecificationOption(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		CPOptionCategory cpOptionCategory =
			cpDefinitionSpecificationOptionValue.getCPOptionCategory();

		if (cpOptionCategory != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpDefinitionSpecificationOptionValue,
				cpOptionCategory, PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		Element cpDefinitionSpecificationOptionValueElement =
			portletDataContext.getExportDataElement(
				cpDefinitionSpecificationOptionValue);

		portletDataContext.addClassedModel(
			cpDefinitionSpecificationOptionValueElement,
			ExportImportPathUtil.getModelPath(
				cpDefinitionSpecificationOptionValue),
			cpDefinitionSpecificationOptionValue);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpDefinitionSpecificationOptionValueId)
		throws Exception {

		CPDefinitionSpecificationOptionValue
			existingCPDefinitionSpecificationOptionValue =
				fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpDefinitionSpecificationOptionValueIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionSpecificationOptionValue.class);

		cpDefinitionSpecificationOptionValueIds.put(
			cpDefinitionSpecificationOptionValueId,
			existingCPDefinitionSpecificationOptionValue.
				getCPDefinitionSpecificationOptionValueId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws Exception {

		Map<Long, Long> cpDefinitionOptionRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPDefinitionOptionRel.class);

		long cpDefinitionId = MapUtil.getLong(
			cpDefinitionOptionRelIds,
			cpDefinitionSpecificationOptionValue.getCPDefinitionId(),
			cpDefinitionSpecificationOptionValue.getCPDefinitionId());

		Map<Long, Long> cpSpecificationOptionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPSpecificationOption.class);

		long cpSpecificationOptionId = MapUtil.getLong(
			cpSpecificationOptionIds,
			cpDefinitionSpecificationOptionValue.getCPSpecificationOptionId(),
			cpDefinitionSpecificationOptionValue.getCPSpecificationOptionId());

		Map<Long, Long> cpOptionCategoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPOptionCategory.class);

		long cpOptionCategoryId = MapUtil.getLong(
			cpOptionCategoryIds,
			cpDefinitionSpecificationOptionValue.getCPOptionCategoryId(),
			cpDefinitionSpecificationOptionValue.getCPOptionCategoryId());

		CPDefinitionSpecificationOptionValue
			importedCPDefinitionSpecificationOptionValue =
				(CPDefinitionSpecificationOptionValue)
					cpDefinitionSpecificationOptionValue.clone();

		importedCPDefinitionSpecificationOptionValue.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPDefinitionSpecificationOptionValue.setCPDefinitionId(
			cpDefinitionId);
		importedCPDefinitionSpecificationOptionValue.setCPSpecificationOptionId(
			cpSpecificationOptionId);
		importedCPDefinitionSpecificationOptionValue.setCPOptionCategoryId(
			cpOptionCategoryId);

		CPDefinitionSpecificationOptionValue
			existingCPDefinitionSpecificationOptionValue =
				_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					cpDefinitionSpecificationOptionValue.getUuid(),
					portletDataContext.getScopeGroupId());

		if ((existingCPDefinitionSpecificationOptionValue == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPDefinitionSpecificationOptionValue =
				_stagedModelRepository.addStagedModel(
					portletDataContext,
					importedCPDefinitionSpecificationOptionValue);
		}
		else {
			importedCPDefinitionSpecificationOptionValue.
				setCPDefinitionSpecificationOptionValueId(
					existingCPDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId());

			importedCPDefinitionSpecificationOptionValue =
				_stagedModelRepository.updateStagedModel(
					portletDataContext,
					importedCPDefinitionSpecificationOptionValue);
		}

		portletDataContext.importClassedModel(
			cpDefinitionSpecificationOptionValue,
			importedCPDefinitionSpecificationOptionValue);
	}

	@Override
	protected StagedModelRepository<CPDefinitionSpecificationOptionValue>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPDefinitionSpecificationOptionValue>
			stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPDefinitionSpecificationOptionValue>
		_stagedModelRepository;

}