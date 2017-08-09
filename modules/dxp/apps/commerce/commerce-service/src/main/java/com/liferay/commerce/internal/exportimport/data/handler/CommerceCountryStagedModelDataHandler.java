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

import com.liferay.commerce.model.CommerceCountry;
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
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CommerceCountryStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommerceCountry> {

	public static final String[] CLASS_NAMES =
		{CommerceCountry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CommerceCountry commerceCountry) {
		return commerceCountry.getNameCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommerceCountry commerceCountry)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			commerceCountry);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commerceCountry),
			commerceCountry);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceCountryId)
		throws Exception {

		CommerceCountry existingCommerceCountry = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> commerceCountryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceCountry.class);

		commerceCountryIds.put(
			commerceCountryId, existingCommerceCountry.getCommerceCountryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommerceCountry commerceCountry)
		throws Exception {

		CommerceCountry importedCommerceCountry =
			(CommerceCountry)commerceCountry.clone();

		importedCommerceCountry.setGroupId(
			portletDataContext.getScopeGroupId());

		CommerceCountry existingCommerceCountry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commerceCountry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommerceCountry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommerceCountry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCommerceCountry);
		}
		else {
			importedCommerceCountry.setCommerceCountryId(
				existingCommerceCountry.getCommerceCountryId());

			importedCommerceCountry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCommerceCountry);
		}

		portletDataContext.importClassedModel(
			commerceCountry, importedCommerceCountry);
	}

	@Override
	protected StagedModelRepository<CommerceCountry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceCountry)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommerceCountry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommerceCountry> _stagedModelRepository;

}