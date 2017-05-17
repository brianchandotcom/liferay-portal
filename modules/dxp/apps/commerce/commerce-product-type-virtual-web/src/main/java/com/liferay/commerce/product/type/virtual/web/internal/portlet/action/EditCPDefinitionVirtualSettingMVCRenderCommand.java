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

package com.liferay.commerce.product.type.virtual.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.commerce.product.type.virtual.web.internal.CPDefinitionVirtualSettingItemSelectorHelper;
import com.liferay.commerce.product.type.virtual.web.internal.constants.CPDefinitionVirtualSettingWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editProductDefinitionVirtualSetting"
	},
	service = MVCRenderCommand.class
)
public class EditCPDefinitionVirtualSettingMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(
				"/edit_definition_virtual_setting.jsp");

		try {
			long cpDefinitionId = ParamUtil.getLong(
				renderRequest, "cpDefinitionId");

			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					fetchCPDefinitionVirtualSetting(cpDefinitionId);

			HttpServletRequest httpServletRequest =
				PortalUtil.getHttpServletRequest(renderRequest);
			HttpServletResponse httpServletResponse =
				PortalUtil.getHttpServletResponse(renderResponse);

			requestDispatcher.include(httpServletRequest, httpServletResponse);

			renderRequest.setAttribute(
				CPDefinitionVirtualSettingWebKeys.
					COMMERCE_PRODUCT_DEFINITION_VIRTUAL_SETTING,
				cpDefinitionVirtualSetting);

			renderRequest.setAttribute(
				CPDefinitionVirtualSettingWebKeys.
					DEFINITION_VIRTUAL_SETTING_ITEM_SELECTOR_HELPER,
				_cpDefinitionVirtualSettingItemSelectorHelper);

			renderRequest.setAttribute(
				"cpDefinitionServletContext", cpDefinitionServletContext);
		}
		catch (Exception e) {
			throw new PortletException(
				"Unable to include edit_definition_virtual_setting.jsp", e);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.definitions.web)"
	)
	protected ServletContext cpDefinitionServletContext;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.type.virtual.web)"
	)
	protected ServletContext servletContext;

	@Reference
	private CPDefinitionVirtualSettingItemSelectorHelper
		_cpDefinitionVirtualSettingItemSelectorHelper;

	@Reference
	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

}