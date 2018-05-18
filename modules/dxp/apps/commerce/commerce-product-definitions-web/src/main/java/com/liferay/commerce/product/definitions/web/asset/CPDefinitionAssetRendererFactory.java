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

package com.liferay.commerce.product.definitions.web.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.product.service.permission.CPPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
	service = AssetRendererFactory.class
)
public class CPDefinitionAssetRendererFactory
	extends BaseAssetRendererFactory<CPDefinition> {

	public static final String ASSET_RENDERER_FACTORY_GROUP =
		"ASSET_RENDERER_FACTORY_GROUP";

	public static final String TYPE = "product";

	public CPDefinitionAssetRendererFactory() {
		setClassName(CPDefinition.class.getName());
		setLinkable(true);
		setPortletId(CPPortletKeys.CP_DEFINITIONS);
		setSearchable(true);
	}

	@Override
	public AssetRenderer<CPDefinition> getAssetRenderer(long classPK, int type)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			classPK);

		CPDefinitionAssetRenderer cpDefinitionAssetRenderer =
			new CPDefinitionAssetRenderer(cpDefinition);

		cpDefinitionAssetRenderer.setAssetRendererType(type);
		cpDefinitionAssetRenderer.setServletContext(_servletContext);

		return cpDefinitionAssetRenderer;
	}

	@Override
	public String getClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return "product";
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public PortletURL getURLAdd(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, long classTypeId) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			liferayPortletRequest, getGroup(liferayPortletRequest),
			CPPortletKeys.CP_DEFINITIONS, 0, 0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
		LiferayPortletResponse liferayPortletResponse,
		WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
			liferayPortletResponse.createLiferayPortletURL(
				CPPortletKeys.CP_DEFINITIONS, PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}

	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, long groupId, long classTypeId)
		throws Exception {

		return CPPermission.contains(
			permissionChecker, groupId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_DEFINITION);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws Exception {

		return CPDefinitionPermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Override
	protected Group getGroup(LiferayPortletRequest liferayPortletRequest) {
		return (Group)liferayPortletRequest.getAttribute(
			ASSET_RENDERER_FACTORY_GROUP);
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.definitions.web)"
	)
	private ServletContext _servletContext;

}