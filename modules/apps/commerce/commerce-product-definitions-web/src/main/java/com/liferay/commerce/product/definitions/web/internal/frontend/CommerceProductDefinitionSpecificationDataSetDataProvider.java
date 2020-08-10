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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.frontend.CommerceDataSetDataProvider;
import com.liferay.commerce.frontend.Filter;
import com.liferay.commerce.frontend.Pagination;
import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.definitions.web.internal.model.ProductSpecification;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_DEFINITION_SPECIFICATIONS,
	service = CommerceDataSetDataProvider.class
)
public class CommerceProductDefinitionSpecificationDataSetDataProvider
	implements CommerceDataSetDataProvider<ProductSpecification> {

	@Override
	public int countItems(HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		return _cpDefinitionSpecificationOptionValueService.
			getCPDefinitionSpecificationOptionValuesCount(cpDefinitionId);
	}

	@Override
	public List<ProductSpecification> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<ProductSpecification> productSpecifications = new ArrayList<>();

		String languageId = LocaleUtil.toLanguageId(
			_portal.getLocale(httpServletRequest));

		long cpDefinitionId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionId");

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				_cpDefinitionSpecificationOptionValueService.
					getCPDefinitionSpecificationOptionValues(
						cpDefinitionId, pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			CPSpecificationOption cpSpecificationOption =
				cpDefinitionSpecificationOptionValue.getCPSpecificationOption();

			productSpecifications.add(
				new ProductSpecification(
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId(),
					cpSpecificationOption.getTitle(languageId),
					cpDefinitionSpecificationOptionValue.getValue(languageId),
					_getCPOptionCategoryTitle(
						cpDefinitionSpecificationOptionValue, languageId),
					cpDefinitionSpecificationOptionValue.getPriority()));
		}

		return productSpecifications;
	}

	private String _getCPOptionCategoryTitle(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue,
			String languageId)
		throws PortalException {

		long cpOptionCategoryId =
			cpDefinitionSpecificationOptionValue.getCPOptionCategoryId();

		if (cpOptionCategoryId ==
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			return StringPool.BLANK;
		}

		try {
			CPOptionCategory cpOptionCategory =
				_cpOptionCategoryService.getCPOptionCategory(
					cpOptionCategoryId);

			return cpOptionCategory.getTitle(languageId);
		}
		catch (PrincipalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductDefinitionSpecificationDataSetDataProvider.class);

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;

	@Reference
	private CPOptionCategoryService _cpOptionCategoryService;

	@Reference
	private Portal _portal;

}