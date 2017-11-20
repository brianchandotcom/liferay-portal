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

package com.liferay.commerce.price.list.qualification.type.internal.exportimport.data.handler;

import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
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
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class CommercePriceListUserRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<CommercePriceListUserRel> {

	public static final String[] CLASS_NAMES =
		{CommercePriceListUserRel.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(
		CommercePriceListUserRel commercePriceListUserRel) {

		return String.valueOf(
			commercePriceListUserRel.getCommercePriceListUserRelId());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceListUserRel commercePriceListUserRel)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, commercePriceListUserRel,
			commercePriceListUserRel.getCommercePriceListQualificationTypeRel(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		Element commercePriceListUserRelElement =
			portletDataContext.getExportDataElement(commercePriceListUserRel);

		portletDataContext.addClassedModel(
			commercePriceListUserRelElement,
			ExportImportPathUtil.getModelPath(commercePriceListUserRel),
			commercePriceListUserRel);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long commercePriceListUserRelId)
		throws Exception {

		CommercePriceListUserRel existingCommercePriceListUserRel =
			fetchMissingReference(uuid, groupId);

		Map<Long, Long> commercePriceListUserRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommercePriceListUserRel.class);

		commercePriceListUserRelIds.put(
			commercePriceListUserRelId,
			existingCommercePriceListUserRel.getCommercePriceListUserRelId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceListUserRel commercePriceListUserRel)
		throws Exception {

		Map<Long, Long> commercePriceListQualificationTypeRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				CommercePriceListQualificationTypeRel.class);

		long commercePriceListQualificationTypeRelId = MapUtil.getLong(
			commercePriceListQualificationTypeRelIds,
			commercePriceListUserRel.
				getCommercePriceListQualificationTypeRelId(),
			commercePriceListUserRel.
				getCommercePriceListQualificationTypeRelId());

		CommercePriceListUserRel importedCommercePriceListUserRel =
			(CommercePriceListUserRel)commercePriceListUserRel.clone();

		importedCommercePriceListUserRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedCommercePriceListUserRel.
			setCommercePriceListQualificationTypeRelId(
				commercePriceListQualificationTypeRelId);

		CommercePriceListUserRel existingCommercePriceListUserRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				commercePriceListUserRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingCommercePriceListUserRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedCommercePriceListUserRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedCommercePriceListUserRel);
		}
		else {
			importedCommercePriceListUserRel.setCommercePriceListUserRelId(
				existingCommercePriceListUserRel.
					getCommercePriceListUserRelId());

			importedCommercePriceListUserRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedCommercePriceListUserRel);
		}

		portletDataContext.importClassedModel(
			commercePriceListUserRel, importedCommercePriceListUserRel);
	}

	@Override
	protected StagedModelRepository<CommercePriceListUserRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<CommercePriceListUserRel> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<CommercePriceListUserRel>
		_stagedModelRepository;

}