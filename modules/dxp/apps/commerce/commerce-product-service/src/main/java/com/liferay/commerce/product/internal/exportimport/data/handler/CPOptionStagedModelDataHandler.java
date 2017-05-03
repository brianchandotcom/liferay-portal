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

import com.liferay.commerce.product.model.CPOption;
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
public class CPOptionStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPOption> {

	public static final String[] CLASS_NAMES = {CPOption.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPOption cpOption) {
		String displayName = cpOption.getTitleCurrentValue();

		if (Validator.isNull(displayName)) {
			displayName = cpOption.getName();
		}

		return displayName;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, CPOption cpOption)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(cpOption);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(cpOption), cpOption);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpOptionId)
		throws Exception {

		CPOption existingCPOption = fetchMissingReference(uuid, groupId);

		Map<Long, Long> cpOptionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPOption.class);

		cpOptionIds.put(cpOptionId, existingCPOption.getCPOptionId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, CPOption cpOption)
		throws Exception {

		CPOption importedCPOption = (CPOption)cpOption.clone();

		importedCPOption.setGroupId(portletDataContext.getScopeGroupId());

		CPOption existingCPOption =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpOption.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCPOption == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPOption = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPOption);
		}
		else {
			importedCPOption.setCPOptionId(existingCPOption.getCPOptionId());

			importedCPOption = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCPOption);
		}

		portletDataContext.importClassedModel(cpOption, importedCPOption);
	}

	@Override
	protected StagedModelRepository<CPOption> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOption)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPOption> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPOption> _stagedModelRepository;

}