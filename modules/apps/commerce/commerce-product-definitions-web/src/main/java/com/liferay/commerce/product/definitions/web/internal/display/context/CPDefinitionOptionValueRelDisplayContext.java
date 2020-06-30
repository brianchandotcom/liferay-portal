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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionOptionValueRelDisplayContext
	extends BaseCPDefinitionsDisplayContext {

	public CPDefinitionOptionValueRelDisplayContext(
		ActionHelper actionHelper,
		CommerceCatalogLocalService commerceCatalogLocalService,
		CommerceCurrencyLocalService commerceCurrencyLocalService,
		HttpServletRequest httpServletRequest) {

		super(actionHelper, httpServletRequest);

		_commerceCatalogLocalService = commerceCatalogLocalService;
		_commerceCurrencyLocalService = commerceCurrencyLocalService;
	}

	public CPInstance fetchCPInstance() throws PortalException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			getCPDefinitionOptionValueRel();

		if (cpDefinitionOptionValueRel == null) {
			return null;
		}

		return cpDefinitionOptionValueRel.fetchCPInstance();
	}

	public CommerceCurrency getCommerceCurrency() throws PortalException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			getCPDefinitionOptionValueRel();

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.fetchCommerceCatalogByGroupId(
				cpDefinitionOptionValueRel.getGroupId());

		return _commerceCurrencyLocalService.getCommerceCurrency(
			commerceCatalog.getCompanyId(),
			commerceCatalog.getCommerceCurrencyCode());
	}

	public CPDefinitionOptionRel getCPDefinitionOptionRel()
		throws PortalException {

		if (_cpDefinitionOptionRel != null) {
			return _cpDefinitionOptionRel;
		}

		_cpDefinitionOptionRel = actionHelper.getCPDefinitionOptionRel(
			cpRequestHelper.getRenderRequest());

		return _cpDefinitionOptionRel;
	}

	public long getCPDefinitionOptionRelId() throws PortalException {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			getCPDefinitionOptionRel();

		if (cpDefinitionOptionRel == null) {
			return 0;
		}

		return cpDefinitionOptionRel.getCPDefinitionOptionRelId();
	}

	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel()
		throws PortalException {

		if (_cpDefinitionOptionValueRel != null) {
			return _cpDefinitionOptionValueRel;
		}

		_cpDefinitionOptionValueRel =
			actionHelper.getCPDefinitionOptionValueRel(
				cpRequestHelper.getRenderRequest());

		return _cpDefinitionOptionValueRel;
	}

	public long getCPDefinitionOptionValueRelId() throws PortalException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			getCPDefinitionOptionValueRel();

		if (cpDefinitionOptionValueRel == null) {
			return 0;
		}

		return cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId();
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_OPTIONS;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(),
			CPDefinitionOptionValueRel.class.getName(),
			getCPDefinitionOptionValueRelId(), null);
	}

	private final CommerceCatalogLocalService _commerceCatalogLocalService;
	private final CommerceCurrencyLocalService _commerceCurrencyLocalService;
	private CPDefinitionOptionRel _cpDefinitionOptionRel;
	private CPDefinitionOptionValueRel _cpDefinitionOptionValueRel;

}