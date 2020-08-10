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

package com.liferay.headless.commerce.admin.site.setting.internal.util.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.site.setting.internal.mapper.v1_0.DTOMapper;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
@Component(immediate = true, service = TaxCategoryHelper.class)
public class TaxCategoryHelper {

	public void deleteTaxCategory(Long id) throws PortalException {
		_cpTaxCategoryService.deleteCPTaxCategory(id);
	}

	public Page<TaxCategory> getTaxCategories(
			Long companyId, Pagination pagination)
		throws PortalException {

		List<CPTaxCategory> cpTaxCategories =
			_cpTaxCategoryService.getCPTaxCategories(
				companyId, pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int count = _cpTaxCategoryService.getCPTaxCategoriesCount(companyId);

		List<TaxCategory> taxCategories = new ArrayList<>();

		for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
			taxCategories.add(_dtoMapper.modelToDTO(cpTaxCategory));
		}

		return Page.of(taxCategories, pagination, count);
	}

	public TaxCategory getTaxCategory(Long id) throws PortalException {
		return _dtoMapper.modelToDTO(
			_cpTaxCategoryService.getCPTaxCategory(id));
	}

	public CPTaxCategory updateTaxCategory(Long id, TaxCategory taxCategory)
		throws PortalException {

		CPTaxCategory cpTaxCategory = _cpTaxCategoryService.getCPTaxCategory(
			id);

		return _cpTaxCategoryService.updateCPTaxCategory(
			cpTaxCategory.getCPTaxCategoryId(),
			LanguageUtils.getLocalizedMap(taxCategory.getName()),
			LanguageUtils.getLocalizedMap(taxCategory.getDescription()));
	}

	public TaxCategory upsertTaxCategory(
			Long groupId, TaxCategory taxCategory, User user)
		throws PortalException {

		try {
			CPTaxCategory cpTaxCategory = updateTaxCategory(
				taxCategory.getId(), taxCategory);

			return _dtoMapper.modelToDTO(cpTaxCategory);
		}
		catch (NoSuchCPTaxCategoryException nscptce) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find taxCategory with ID: " +
						taxCategory.getId());
			}
		}

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			groupId, new long[0], user, true);

		CPTaxCategory cpTaxCategory = _cpTaxCategoryService.addCPTaxCategory(
			LanguageUtils.getLocalizedMap(taxCategory.getName()),
			LanguageUtils.getLocalizedMap(taxCategory.getDescription()),
			serviceContext);

		return _dtoMapper.modelToDTO(cpTaxCategory);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TaxCategoryHelper.class);

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

	@Reference
	private DTOMapper _dtoMapper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}