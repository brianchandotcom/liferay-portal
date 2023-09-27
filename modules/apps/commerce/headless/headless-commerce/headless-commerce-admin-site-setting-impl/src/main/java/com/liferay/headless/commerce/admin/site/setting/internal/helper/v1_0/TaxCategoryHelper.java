/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.site.setting.internal.helper.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.site.setting.internal.mapper.v1_0.DTOMapper;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
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
@Component(service = TaxCategoryHelper.class)
public class TaxCategoryHelper {

	public TaxCategory addOrUpdateTaxCategory(
			Long groupId, TaxCategory taxCategory, User user)
		throws PortalException {

		try {
			CPTaxCategory cpTaxCategory = updateTaxCategory(
				taxCategory.getId(), taxCategory);

			return _dtoMapper.modelToDTO(cpTaxCategory);
		}
		catch (NoSuchCPTaxCategoryException noSuchCPTaxCategoryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to find taxCategory with ID: " +
						taxCategory.getId(),
					noSuchCPTaxCategoryException);
			}
		}

		CPTaxCategory cpTaxCategory = _cpTaxCategoryService.addCPTaxCategory(
			StringPool.BLANK,
			LanguageUtils.getLocalizedMap(taxCategory.getName()),
			LanguageUtils.getLocalizedMap(taxCategory.getDescription()),
			_serviceContextHelper.getServiceContext(
				groupId, new long[0], user, true));

		return _dtoMapper.modelToDTO(cpTaxCategory);
	}

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
			cpTaxCategory.getExternalReferenceCode(),
			cpTaxCategory.getCPTaxCategoryId(),
			LanguageUtils.getLocalizedMap(taxCategory.getName()),
			LanguageUtils.getLocalizedMap(taxCategory.getDescription()));
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