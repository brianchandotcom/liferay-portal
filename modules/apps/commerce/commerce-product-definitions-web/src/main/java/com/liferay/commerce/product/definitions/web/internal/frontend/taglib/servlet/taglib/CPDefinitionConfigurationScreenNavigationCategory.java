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

package com.liferay.commerce.product.definitions.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.product.definitions.web.internal.display.context.CPDefinitionConfigurationDisplayContext;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.portlet.action.ActionHelper;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPMeasurementUnitLocalService;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.servlet.taglib.ui.constants.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.commerce.service.CPDAvailabilityEstimateService;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.commerce.service.CommerceAvailabilityEstimateService;
import com.liferay.commerce.stock.activity.CommerceLowStockActivityRegistry;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=120",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CPDefinitionConfigurationScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<CPDefinition> {

	@Override
	public String getCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_CONFIGURATION;
	}

	@Override
	public String getEntryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_CONFIGURATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_CONFIGURATION);
	}

	@Override
	public String getScreenNavigationKey() {
		return CPDefinitionScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_CP_DEFINITION_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CPDefinition cpDefinition) {
		if (cpDefinition == null) {
			return false;
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			return _commerceCatalogModelResourcePermission.contains(
				permissionChecker, cpDefinition.getCommerceCatalog(),
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CPDefinitionConfigurationDisplayContext
			cpDefinitionConfigurationDisplayContext =
				new CPDefinitionConfigurationDisplayContext(
					_actionHelper, httpServletRequest,
					_commerceAccountGroupRelService,
					_commerceAvailabilityEstimateService,
					_commerceCatalogService, _commerceChannelRelService,
					_commerceCurrencyLocalService,
					_commerceLowStockActivityRegistry,
					_cpdAvailabilityEstimateService,
					_cpDefinitionInventoryEngineRegistry,
					_cpDefinitionInventoryService, _cpDefinitionService,
					_cpMeasurementUnitLocalService, _cpTaxCategoryService,
					_cpFriendlyURL, _itemSelector);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			cpDefinitionConfigurationDisplayContext);

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/edit_cp_definition_configuration.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionConfigurationScreenNavigationCategory.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

	@Reference
	private CommerceAvailabilityEstimateService
		_commerceAvailabilityEstimateService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceCatalog)"
	)
	private ModelResourcePermission<CommerceCatalog>
		_commerceCatalogModelResourcePermission;

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommerceLowStockActivityRegistry _commerceLowStockActivityRegistry;

	@Reference
	private CPDAvailabilityEstimateService _cpdAvailabilityEstimateService;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private CPMeasurementUnitLocalService _cpMeasurementUnitLocalService;

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.definitions.web)"
	)
	private ServletContext _setServletContext;

}