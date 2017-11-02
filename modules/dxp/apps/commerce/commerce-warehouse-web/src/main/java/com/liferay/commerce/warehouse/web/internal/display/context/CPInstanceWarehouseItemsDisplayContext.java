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

package com.liferay.commerce.warehouse.web.internal.display.context;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPInstanceScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.service.CommerceWarehouseItemService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CPInstanceWarehouseItemsDisplayContext
	extends CommerceWarehouseItemsDisplayContext<CPInstance> {

	public CPInstanceWarehouseItemsDisplayContext(
		CommerceWarehouseItemService commerceWarehouseItemService,
		CPInstanceService cpInstanceService, ItemSelector itemSelector,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(commerceWarehouseItemService, itemSelector, httpServletRequest);

		_cpInstanceService = cpInstanceService;
		_portal = portal;
	}

	@Override
	public String getBackURL() throws PortalException {
		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		String lifecycle = (String)renderRequest.getAttribute(
			LiferayPortletRequest.LIFECYCLE_PHASE);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			renderRequest, CPPortletKeys.CP_DEFINITIONS, lifecycle);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");

		CPInstance cpInstance = getModel();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS);

		return portletURL.toString();
	}

	@Override
	public CPInstance getModel() throws PortalException {
		if (_cpInstance == null) {
			long cpInstanceId = ParamUtil.getLong(
				cpRequestHelper.getRenderRequest(), "cpInstanceId");

			_cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);
		}

		return _cpInstance;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");

		CPInstance cpInstance = getModel();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(cpInstance.getCPInstanceId()));

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPInstanceScreenNavigationConstants.CATEGORY_KEY_DETAILS);

		portletURL.setParameter("screenNavigationEntryKey", "warehouses");

		return portletURL;
	}

	@Override
	public String getTitle() throws PortalException {
		CPInstance cpInstance = getModel();

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();

		return cpDefinition.getTitle(themeDisplay.getLanguageId()) + " - " +
			cpInstance.getSku();
	}

	@Override
	public boolean isShowAddButton() throws PortalException {
		CPInstance cpInstance = getModel();

		return CPDefinitionPermission.contains(
			cpRequestHelper.getPermissionChecker(),
			cpInstance.getCPDefinitionId(), ActionKeys.UPDATE);
	}

	private CPInstance _cpInstance;
	private final CPInstanceService _cpInstanceService;
	private final Portal _portal;

}