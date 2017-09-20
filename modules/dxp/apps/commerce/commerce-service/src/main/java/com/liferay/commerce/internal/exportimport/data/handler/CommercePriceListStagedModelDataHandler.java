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

import com.liferay.commerce.model.CommercePriceList;
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
public class CommercePriceListStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommercePriceList> {

	public static final String[] CLASS_NAMES =
		{CommercePriceList.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(CommercePriceList commercePriceList) {
		return commercePriceList.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceList commercePriceList)
		throws Exception {

		Element element = portletDataContext.getExportDataElement(
			commercePriceList);

		portletDataContext.addClassedModel(
			element, ExportImportPathUtil.getModelPath(commercePriceList),
			commercePriceList);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commercePriceListId)
		throws Exception {

		CommercePriceList existingCommercePriceList = fetchMissingReference(
			uuid, groupId);

		Map<Long, Long> commercePriceListIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommercePriceList.class);

		commercePriceListIds.put(
			commercePriceListId,
			existingCommercePriceList.getCommercePriceListId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceList commercePriceList)
		throws Exception {

		CommercePriceList importedCommercePriceList =
			(CommercePriceList)commercePriceList.clone();

		importedCommercePriceList.setGroupId(
			portletDataContext.getScopeGroupId());

		CommercePriceList existingCommercePriceList =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commercePriceList.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommercePriceList == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommercePriceList = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCommercePriceList);
		}
		else {
			importedCommercePriceList.setCommercePriceListId(
				existingCommercePriceList.getCommercePriceListId());

			importedCommercePriceList =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCommercePriceList);
		}

		portletDataContext.importClassedModel(
			commercePriceList, importedCommercePriceList);
	}

	@Override
	protected StagedModelRepository<CommercePriceList>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommercePriceList)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommercePriceList> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommercePriceList> _stagedModelRepository;

}