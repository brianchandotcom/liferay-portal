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

package com.liferay.commerce.currency.internal.exportimport.staged.model.repository;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
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
	property = "model.class.name=com.liferay.commerce.currency.model.CommerceCurrency",
	service = StagedModelRepository.class
)
public class CommerceCurrencyStagedModelRepository
	extends BaseStagedModelRepository<CommerceCurrency> {

	@Override
	public CommerceCurrency addStagedModel(
			PortletDataContext portletDataContext,
			CommerceCurrency commerceCurrency)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceCurrency);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(commerceCurrency.getUuid());
		}

		return _commerceCurrencyLocalService.addCommerceCurrency(
			commerceCurrency.getCode(), commerceCurrency.getNameMap(),
			commerceCurrency.getRate(), commerceCurrency.getRoundingType(),
			commerceCurrency.isPrimary(), commerceCurrency.getPriority(),
			commerceCurrency.isActive(), serviceContext);
	}

	@Override
	public void deleteStagedModel(CommerceCurrency commerceCurrency)
		throws PortalException {

		_commerceCurrencyLocalService.deleteCommerceCurrency(commerceCurrency);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		CommerceCurrency commerceCurrency = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (commerceCurrency != null) {
			deleteStagedModel(commerceCurrency);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_commerceCurrencyLocalService.deleteCommerceCurrencies(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public CommerceCurrency fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _commerceCurrencyLocalService.
			fetchCommerceCurrencyByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<CommerceCurrency> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _commerceCurrencyLocalService.
			getCommerceCurrenciesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<CommerceCurrency>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _commerceCurrencyLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public CommerceCurrency saveStagedModel(CommerceCurrency commerceCurrency)
		throws PortalException {

		return _commerceCurrencyLocalService.updateCommerceCurrency(
			commerceCurrency);
	}

	@Override
	public CommerceCurrency updateStagedModel(
			PortletDataContext portletDataContext,
			CommerceCurrency commerceCurrency)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			commerceCurrency);

		return _commerceCurrencyLocalService.updateCommerceCurrency(
			commerceCurrency.getCommerceCurrencyId(),
			commerceCurrency.getCode(), commerceCurrency.getNameMap(),
			commerceCurrency.getRate(), commerceCurrency.getRoundingType(),
			commerceCurrency.isPrimary(), commerceCurrency.getPriority(),
			commerceCurrency.isActive(), serviceContext);
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

}