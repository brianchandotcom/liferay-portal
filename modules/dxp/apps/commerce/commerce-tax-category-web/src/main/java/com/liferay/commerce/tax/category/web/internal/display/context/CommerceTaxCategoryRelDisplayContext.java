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

package com.liferay.commerce.tax.category.web.internal.display.context;

import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CommerceTaxCategoryRelService;
import com.liferay.commerce.service.CommerceTaxCategoryService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceTaxCategoryRelDisplayContext {

	public CommerceTaxCategoryRelDisplayContext(
		HttpServletRequest httpServletRequest,
		CommerceTaxCategoryService commerceTaxCategoryService,
		CommerceTaxCategoryRelService commerceTaxCategoryRelService) {

		_httpServletRequest = httpServletRequest;
		_commerceTaxCategoryService = commerceTaxCategoryService;
		_commerceTaxCategoryRelService = commerceTaxCategoryRelService;
	}

	public List<CommerceTaxCategory> getCommerceTaxCategories(long groupId) {
		return _commerceTaxCategoryService.getCommerceTaxCategories(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public CommerceTaxCategoryRel getCommerceTaxCategoryRel()
		throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			_httpServletRequest, "cpDefinitionId");

		if (cpDefinitionId == 0) {
			return null;
		}

		return _commerceTaxCategoryRelService.fetchCommerceTaxCategoryRel(
			CPDefinition.class.getName(), cpDefinitionId);
	}

	private final CommerceTaxCategoryRelService _commerceTaxCategoryRelService;
	private final CommerceTaxCategoryService _commerceTaxCategoryService;
	private final HttpServletRequest _httpServletRequest;

}