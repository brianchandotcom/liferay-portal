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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.constants.CPOptionCategoryConstants;
import com.liferay.commerce.product.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.item.selector.criterion.CPSpecificationOptionItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionSpecificationOptionValueDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionSpecificationOptionValueDisplayContext(
		ActionHelper actionHelper, HttpServletRequest httpServletRequest,
		CPOptionCategoryService cpOptionCategoryService,
		ItemSelector itemSelector) {

		super(actionHelper, httpServletRequest);

		_cpOptionCategoryService = cpOptionCategoryService;
		_itemSelector = itemSelector;
	}

	public CPDefinitionSpecificationOptionValue
			getCPDefinitionSpecificationOptionValue()
		throws PortalException {

		if (_cpDefinitionSpecificationOptionValue != null) {
			return _cpDefinitionSpecificationOptionValue;
		}

		_cpDefinitionSpecificationOptionValue =
			actionHelper.getCPDefinitionSpecificationOptionValue(
				cpRequestHelper.getRenderRequest());

		return _cpDefinitionSpecificationOptionValue;
	}

	public List<CPOptionCategory> getCPOptionCategories()
		throws PortalException {

		BaseModelSearchResult<CPOptionCategory>
			cpOptionCategoryBaseModelSearchResult =
				_cpOptionCategoryService.searchCPOptionCategories(
					cpRequestHelper.getCompanyId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

		return cpOptionCategoryBaseModelSearchResult.getBaseModels();
	}

	public String getCPOptionCategoryTitle(
			CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue)
		throws PortalException {

		long cpOptionCategoryId =
			cpDefinitionSpecificationOptionValue.getCPOptionCategoryId();

		if (cpOptionCategoryId ==
				CPOptionCategoryConstants.DEFAULT_CP_OPTION_CATEGORY_ID) {

			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			CPOptionCategory cpOptionCategory =
				_cpOptionCategoryService.getCPOptionCategory(
					cpOptionCategoryId);

			return cpOptionCategory.getTitle(themeDisplay.getLocale());
		}
		catch (PrincipalException principalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(principalException, principalException);
			}
		}

		return StringPool.BLANK;
	}

	public String getItemSelectorUrl() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				cpRequestHelper.getRenderRequest());

		CPSpecificationOptionItemSelectorCriterion
			cpSpecificationOptionItemSelectorCriterion =
				new CPSpecificationOptionItemSelectorCriterion();

		cpSpecificationOptionItemSelectorCriterion.
			setDesiredItemSelectorReturnTypes(
				Collections.<ItemSelectorReturnType>singletonList(
					new UUIDItemSelectorReturnType()));

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory,
			"productSpecificationOptionsSelectItem",
			cpSpecificationOptionItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"screenNavigationCategoryKey", getScreenNavigationCategoryKey());

		return portletURL;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long cpDefinitionSpecificationOptionValueId = 0;

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				getCPDefinitionSpecificationOptionValue();

		if (cpDefinitionSpecificationOptionValue != null) {
			cpDefinitionSpecificationOptionValueId =
				cpDefinitionSpecificationOptionValue.
					getCPDefinitionSpecificationOptionValueId();
		}

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(),
			CPDefinitionSpecificationOptionValue.class.getName(),
			cpDefinitionSpecificationOptionValueId, null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionSpecificationOptionValueDisplayContext.class);

	private CPDefinitionSpecificationOptionValue
		_cpDefinitionSpecificationOptionValue;
	private final CPOptionCategoryService _cpOptionCategoryService;
	private final ItemSelector _itemSelector;

}