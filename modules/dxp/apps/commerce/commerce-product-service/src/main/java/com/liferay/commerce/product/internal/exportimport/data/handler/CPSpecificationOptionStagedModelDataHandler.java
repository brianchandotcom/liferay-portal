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

import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
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
public class CPSpecificationOptionStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPSpecificationOption> {

	public static final String[] CLASS_NAMES =
		{CPSpecificationOption.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPSpecificationOption cpSpecificationOption) {
		String displayName = cpSpecificationOption.getTitleCurrentValue();

		if (Validator.isNull(displayName)) {
			displayName = cpSpecificationOption.getKey();
		}

		return displayName;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPSpecificationOption cpSpecificationOption)
		throws Exception {

		CPOptionCategory cpOptionCategory =
			cpSpecificationOption.getCPOptionCategory();

		if (cpOptionCategory != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, cpSpecificationOption, cpOptionCategory,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}

		Element element = portletDataContext.getExportDataElement(
			cpSpecificationOption);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cpSpecificationOption),
			cpSpecificationOption);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpSpecificationOptionId)
		throws Exception {

		CPSpecificationOption existingCPSpecificationOption =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpSpecificationOptionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPSpecificationOption.class);

		cpSpecificationOptionIds.put(
			cpSpecificationOptionId,
			existingCPSpecificationOption.getCPSpecificationOptionId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPSpecificationOption cpSpecificationOption)
		throws Exception {

		Map<Long, Long> cpOptionCategoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPOptionCategory.class);

		long cpOptionCategoryId = MapUtil.getLong(
			cpOptionCategoryIds, cpSpecificationOption.getCPOptionCategoryId(),
			cpSpecificationOption.getCPOptionCategoryId());

		CPSpecificationOption importedCPSpecificationOption =
			(CPSpecificationOption)cpSpecificationOption.clone();

		importedCPSpecificationOption.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCPSpecificationOption.setCPOptionCategoryId(cpOptionCategoryId);

		CPSpecificationOption existingCPSpecificationOption =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpSpecificationOption.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPSpecificationOption == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPSpecificationOption =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCPSpecificationOption);
		}
		else {
			importedCPSpecificationOption.setCPSpecificationOptionId(
				existingCPSpecificationOption.getCPSpecificationOptionId());

			importedCPSpecificationOption =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCPSpecificationOption);
		}

		portletDataContext.importClassedModel(
			cpSpecificationOption, importedCPSpecificationOption);
	}

	@Override
	protected StagedModelRepository<CPSpecificationOption>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPSpecificationOption)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPSpecificationOption> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPSpecificationOption> _stagedModelRepository;

}