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
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CPOptionCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPOptionCategory> {

	public static final String[] CLASS_NAMES =
		{CPOptionCategory.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPOptionCategory cpOptionCategory) {
		String displayName = cpOptionCategory.getTitleCurrentValue();

		if (Validator.isNull(displayName)) {
			displayName = cpOptionCategory.getName();
		}

		return displayName;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CPOptionCategory cpOptionCategory)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			cpOptionCategory);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cpOptionCategory),
			cpOptionCategory);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpOptionCategoryId)
		throws Exception {

		CPOptionCategory existingCPOptionCategory = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> cpOptionCategoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPOptionCategory.class);

		cpOptionCategoryIds.put(
			cpOptionCategoryId,
			existingCPOptionCategory.getCPOptionCategoryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CPOptionCategory cpOptionCategory)
		throws Exception {

		CPOptionCategory importedCPOptionCategory =
			(CPOptionCategory)cpOptionCategory.clone();

		importedCPOptionCategory.setGroupId(
			portletDataContext.getScopeGroupId());

		CPOptionCategory existingCPOptionCategory =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpOptionCategory.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCPOptionCategory == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPOptionCategory = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPOptionCategory);
		}
		else {
			importedCPOptionCategory.setCPOptionCategoryId(
				existingCPOptionCategory.getCPOptionCategoryId());

			importedCPOptionCategory = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCPOptionCategory);
		}

		portletDataContext.importClassedModel(
			cpOptionCategory, importedCPOptionCategory);
	}

	@Override
	protected StagedModelRepository<CPOptionCategory>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOptionCategory)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPOptionCategory> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPOptionCategory> _stagedModelRepository;

}