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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CPDefinitionWarehouseItemsDisplayContext
	extends CommerceWarehouseItemsDisplayContext<CPDefinition> {

	public CPDefinitionWarehouseItemsDisplayContext(
		CommerceWarehouseItemService commerceWarehouseItemService,
		CPDefinitionService cpDefinitionService,
		ServletContext cpDefinitionServletContext,
		CPTypeServicesTracker cpTypeServicesTracker, ItemSelector itemSelector,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(commerceWarehouseItemService, itemSelector, httpServletRequest);

		_cpDefinitionService = cpDefinitionService;
		_cpDefinitionServletContext = cpDefinitionServletContext;
		_cpTypeServicesTracker = cpTypeServicesTracker;
		_portal = portal;
	}

	@Override
	public String getBackURL() {
		RenderRequest renderRequest = cpRequestHelper.getRenderRequest();

		String lifecycle = (String)renderRequest.getAttribute(
			LiferayPortletRequest.LIFECYCLE_PHASE);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			renderRequest, CPPortletKeys.CP_DEFINITIONS, lifecycle);

		return portletURL.toString();
	}

	public ServletContext getCPDefinitionServletContext() {
		return _cpDefinitionServletContext;
	}

	public CPType getCPType() throws PortalException {
		CPDefinition cpDefinition = getModel();

		return _cpTypeServicesTracker.getCPType(
			cpDefinition.getProductTypeName());
	}

	@Override
	public CPDefinition getModel() throws PortalException {
		if (_cpDefinition == null) {
			long cpDefinitionId = ParamUtil.getLong(
				cpRequestHelper.getRenderRequest(), "cpDefinitionId");

			_cpDefinition = _cpDefinitionService.getCPDefinition(
				cpDefinitionId);
		}

		return _cpDefinition;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		CPDefinition cpDefinition = getModel();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCPDefinitionWarehouseItems");

		return portletURL;
	}

	@Override
	public String getTitle() throws PortalException {
		CPDefinition cpDefinition = getModel();

		ThemeDisplay themeDisplay = cpRequestHelper.getThemeDisplay();

		return cpDefinition.getTitle(themeDisplay.getLanguageId());
	}

	@Override
	public boolean isShowAddButton() throws PortalException {
		return CPDefinitionPermission.contains(
			cpRequestHelper.getPermissionChecker(), getModel(),
			ActionKeys.UPDATE);
	}

	private CPDefinition _cpDefinition;
	private final CPDefinitionService _cpDefinitionService;
	private final ServletContext _cpDefinitionServletContext;
	private final CPTypeServicesTracker _cpTypeServicesTracker;
	private final Portal _portal;

}