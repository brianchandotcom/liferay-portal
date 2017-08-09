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

package com.liferay.commerce.internal.exportimport.staged.model.repository;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryLocalService;
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
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.commerce.model.CommerceCountry",
	service = StagedModelRepository.class
)
public class CommerceCountryStagedModelRepository
	extends BaseStagedModelRepository<CommerceCountry> {

	@Override
	public CommerceCountry addStagedModel(
			PortletDataContext portletDataContext,
			CommerceCountry commerceCountry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceCountry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commerceCountry.getUuid());
		}

		return _commerceCountryLocalService.addCommerceCountry(
			commerceCountry.getNameMap(), commerceCountry.isBillingAllowed(),
			commerceCountry.isShippingAllowed(),
			commerceCountry.getTwoLettersISOCode(),
			commerceCountry.getThreeLettersISOCode(),
			commerceCountry.getNumericISOCode(),
			commerceCountry.isSubjectToVAT(), commerceCountry.getPriority(),
			commerceCountry.isActive(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CommerceCountry commerceCountry)
		throws PortalException {

		_commerceCountryLocalService.deleteCommerceCountry(commerceCountry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommerceCountry commerceCountry = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (commerceCountry != null) {
			deleteStagedModel(commerceCountry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_commerceCountryLocalService.deleteCommerceCountries(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CommerceCountry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commerceCountryLocalService.
			fetchCommerceCountryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommerceCountry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commerceCountryLocalService.
			getCommerceCountriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CommerceCountry>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commerceCountryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CommerceCountry saveStagedModel(CommerceCountry commerceCountry)
		throws PortalException {

		return _commerceCountryLocalService.updateCommerceCountry(
			commerceCountry);
	}

	@Override
	public CommerceCountry updateStagedModel(
			PortletDataContext portletDataContext,
			CommerceCountry commerceCountry)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceCountry);

		return _commerceCountryLocalService.updateCommerceCountry(
			commerceCountry.getCommerceCountryId(),
			commerceCountry.getNameMap(), commerceCountry.isBillingAllowed(),
			commerceCountry.isShippingAllowed(),
			commerceCountry.getTwoLettersISOCode(),
			commerceCountry.getThreeLettersISOCode(),
			commerceCountry.getNumericISOCode(),
			commerceCountry.isSubjectToVAT(), commerceCountry.getPriority(),
			commerceCountry.isActive(), serviceContext);
	}

	@Reference
	private CommerceCountryLocalService _commerceCountryLocalService;

}