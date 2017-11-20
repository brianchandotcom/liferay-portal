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

package com.liferay.commerce.price.list.qualification.type.internal.exportimport.staged.model.repository;

import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.service.CommercePriceListUserRelLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel",
	service = StagedModelRepository.class
)
public class CommercePriceListUserRelStagedModelRepository
	extends BaseStagedModelRepository<CommercePriceListUserRel> {

	@Override
	public CommercePriceListUserRel addStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceListUserRel commercePriceListUserRel)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commercePriceListUserRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commercePriceListUserRel.getUuid());
		}

		return
			_commercePriceListUserRelLocalService.addCommercePriceListUserRel(
				commercePriceListUserRel.
					getCommercePriceListQualificationTypeRelId(),
				commercePriceListUserRel.getClassName(),
				commercePriceListUserRel.getClassPK(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			CommercePriceListUserRel commercePriceListUserRel)
		throws PortalException {

		_commercePriceListUserRelLocalService.deleteCommercePriceListUserRel(
			commercePriceListUserRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommercePriceListUserRel commercePriceListUserRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (className != null) {
			deleteStagedModel(commercePriceListUserRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public CommercePriceListUserRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commercePriceListUserRelLocalService.
			fetchCommercePriceListUserRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommercePriceListUserRel> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commercePriceListUserRelLocalService.
			getCommercePriceListUserRelsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator
					<CommercePriceListUserRel>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commercePriceListUserRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public CommercePriceListUserRel saveStagedModel(
			CommercePriceListUserRel commercePriceListUserRel)
		throws PortalException {

		return _commercePriceListUserRelLocalService.
			updateCommercePriceListUserRel(commercePriceListUserRel);
	}

	@Override
	public CommercePriceListUserRel updateStagedModel(
			PortletDataContext portletDataContext,
			CommercePriceListUserRel commercePriceListUserRel)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commercePriceListUserRel);

		return _commercePriceListUserRelLocalService.
			updateCommercePriceListUserRel(
				commercePriceListUserRel.getCommercePriceListUserRelId(),
				commercePriceListUserRel.
					getCommercePriceListQualificationTypeRelId(),
				serviceContext);
	}

	@Reference
	private CommercePriceListUserRelLocalService
		_commercePriceListUserRelLocalService;

}