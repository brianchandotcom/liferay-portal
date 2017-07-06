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
import com.liferay.commerce.product.model.CPOptionValue;
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
public class CPOptionValueStagedModelDataHandler
	extends BaseStagedModelDataHandler<CPOptionValue> {

	public static final String[] CLASS_NAMES = {CPOptionValue.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CPOptionValue cpOptionValue) {
		String displayName = cpOptionValue.getTitleCurrentValue();

		if (Validator.isNull(displayName)) {
			displayName = cpOptionValue.getKey();
		}

		return displayName;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, CPOptionValue cpOptionValue)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, cpOptionValue, cpOptionValue.getCPOption(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element cpOptionValueElement = portletDataContext.getExportDataElement(
			cpOptionValue);

		portletDataContext.addClassedModel(
			cpOptionValueElement,
			ExportImportPathUtil.getModelPath(cpOptionValue), cpOptionValue);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long cpOptionValueId)
		throws Exception {

		CPOptionValue existingCPOptionValue = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> cpOptionValueIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPOptionValue.class);

		cpOptionValueIds.put(
			cpOptionValueId, existingCPOptionValue.getCPOptionValueId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, CPOptionValue cpOptionValue)
		throws Exception {

		Map<Long, Long> cpOptionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CPOption.class);

		long cpOptionId = MapUtil.getLong(
			cpOptionIds, cpOptionValue.getCPOptionId(),
			cpOptionValue.getCPOptionId());

		CPOptionValue importedCPOptionValue =
			(CPOptionValue)cpOptionValue.clone();

		importedCPOptionValue.setGroupId(portletDataContext.getScopeGroupId());
		importedCPOptionValue.setCPOptionId(cpOptionId);

		CPOptionValue existingCPOptionValue =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				cpOptionValue.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCPOptionValue == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCPOptionValue = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCPOptionValue);
		}
		else {
			importedCPOptionValue.setCPOptionValueId(
				existingCPOptionValue.getCPOptionValueId());

			importedCPOptionValue = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCPOptionValue);
		}

		portletDataContext.importClassedModel(
			cpOptionValue, importedCPOptionValue);
	}

	@Override
	protected StagedModelRepository<CPOptionValue> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPOptionValue)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CPOptionValue> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CPOptionValue> _stagedModelRepository;

}