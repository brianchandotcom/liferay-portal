/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionTaxCategoryDisplayContext
	extends CPDefinitionsDisplayContext {

	public CPDefinitionTaxCategoryDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionHelper cpDefinitionHelper,
			CPDefinitionService cpDefinitionService, ItemSelector itemSelector,
			CPTaxCategoryService cpTaxCategoryService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest, cpDefinitionHelper,
			cpDefinitionService, itemSelector);

		_cpTaxCategoryService = cpTaxCategoryService;
	}

	public List<CPTaxCategory> getCPTaxCategories() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _cpTaxCategoryService.getCPTaxCategories(
			themeDisplay.getScopeGroupId());
	}

	private final CPTaxCategoryService _cpTaxCategoryService;

}