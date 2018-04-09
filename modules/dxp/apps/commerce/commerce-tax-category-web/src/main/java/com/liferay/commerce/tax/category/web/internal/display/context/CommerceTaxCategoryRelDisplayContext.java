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

import com.liferay.commerce.item.selector.criterion.CommerceTaxCategoryItemSelectorCriterion;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CommerceTaxCategoryRelService;
import com.liferay.commerce.service.CommerceTaxCategoryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceTaxCategoryRelDisplayContext {

	public CommerceTaxCategoryRelDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceTaxCategoryService commerceTaxCategoryService,
			CommerceTaxCategoryRelService commerceTaxCategoryRelService) {

		_commerceTaxCategoryService = commerceTaxCategoryService;
		_commerceTaxCategoryRelService = commerceTaxCategoryRelService;
		_httpServletRequest = httpServletRequest;
	}

	public List<CommerceTaxCategory> getCommerceTaxCategories(long groupId){
		return _commerceTaxCategoryService.getCommerceTaxCategories(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	public CommerceTaxCategoryRel getCommerceTaxCategoryRel() throws PortalException {

		long cpDefinitionId = ParamUtil.getLong(
			_httpServletRequest, "cpDefinitionId");

		if(cpDefinitionId == 0){
			return null;
		}

		return _commerceTaxCategoryRelService.fetchCommerceTaxCategoryRel(
			CPDefinition.class.getName(), cpDefinitionId);
	}


	private final HttpServletRequest _httpServletRequest;
	private final CommerceTaxCategoryService _commerceTaxCategoryService;
	private final CommerceTaxCategoryRelService _commerceTaxCategoryRelService;

	private CPDefinition _cpDefinition;
}