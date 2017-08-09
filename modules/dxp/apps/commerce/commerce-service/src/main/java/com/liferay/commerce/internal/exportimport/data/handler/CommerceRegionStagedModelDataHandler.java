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
import com.liferay.commerce.model.CommerceRegion;
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
public class CommerceRegionStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommerceRegion> {

	public static final String[] CLASS_NAMES = {CommerceRegion.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CommerceRegion commerceRegion) {
		return commerceRegion.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommerceRegion commerceRegion)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, commerceRegion,
			commerceRegion.getCommerceCountry(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element element = portletDataContext.getExportDataElement(
			commerceRegion);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commerceRegion),
			commerceRegion);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceRegionId)
		throws Exception {

		CommerceRegion existingCommerceRegion = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> commerceRegionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceRegion.class);

		commerceRegionIds.put(
			commerceRegionId, existingCommerceRegion.getCommerceRegionId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommerceRegion commerceRegion)
		throws Exception {

		Map<Long, Long> commerceCountryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceCountry.class);

		long commerceCountryId = MapUtil.getLong(
			commerceCountryIds, commerceRegion.getCommerceCountryId(),
			commerceRegion.getCommerceCountryId());

		CommerceRegion importedCommerceRegion =
			(CommerceRegion)commerceRegion.clone();

		importedCommerceRegion.setGroupId(portletDataContext.getScopeGroupId());
		importedCommerceRegion.setCommerceCountryId(commerceCountryId);

		CommerceRegion existingCommerceRegion =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commerceRegion.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingCommerceRegion == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommerceRegion = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCommerceRegion);
		}
		else {
			importedCommerceRegion.setCommerceRegionId(
				existingCommerceRegion.getCommerceRegionId());

			importedCommerceRegion = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCommerceRegion);
		}

		portletDataContext.importClassedModel(
			commerceRegion, importedCommerceRegion);
	}

	@Override
	protected StagedModelRepository<CommerceRegion> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceRegion)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommerceRegion> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommerceRegion> _stagedModelRepository;

}