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

import com.liferay.commerce.model.CommerceAvailabilityRange;
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
public class CommerceAvailabilityRangeStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommerceAvailabilityRange> {

	public static final String[] CLASS_NAMES =
		{CommerceAvailabilityRange.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		CommerceAvailabilityRange commerceAvailabilityRange) {

		return commerceAvailabilityRange.getTitleCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommerceAvailabilityRange commerceAvailabilityRange)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			commerceAvailabilityRange);

		portletDataContext.addClassedModel(
			element,
			ExportImportPathUtil.getModelPath(commerceAvailabilityRange),
			commerceAvailabilityRange);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commerceAvailabilityRangeId)
		throws Exception {

		CommerceAvailabilityRange existingCommerceAvailabilityRange =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> commerceAvailabilityRangeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommerceAvailabilityRange.class);

		commerceAvailabilityRangeIds.put(
			commerceAvailabilityRangeId,
			existingCommerceAvailabilityRange.getCommerceAvailabilityRangeId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommerceAvailabilityRange commerceAvailabilityRange)
		throws Exception {

		CommerceAvailabilityRange importedCommerceAvailabilityRange =
			(CommerceAvailabilityRange)commerceAvailabilityRange.clone();

		importedCommerceAvailabilityRange.setGroupId(
			portletDataContext.getScopeGroupId());

		CommerceAvailabilityRange existingCommerceAvailabilityRange =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commerceAvailabilityRange.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommerceAvailabilityRange == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommerceAvailabilityRange =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCommerceAvailabilityRange);
		}
		else {
			importedCommerceAvailabilityRange.setCommerceAvailabilityRangeId(
				existingCommerceAvailabilityRange.
					getCommerceAvailabilityRangeId());

			importedCommerceAvailabilityRange =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCommerceAvailabilityRange);
		}

		portletDataContext.importClassedModel(
			commerceAvailabilityRange, importedCommerceAvailabilityRange);
	}

	@Override
	protected StagedModelRepository<CommerceAvailabilityRange>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceAvailabilityRange)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommerceAvailabilityRange>
			stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommerceAvailabilityRange>
		_stagedModelRepository;

}