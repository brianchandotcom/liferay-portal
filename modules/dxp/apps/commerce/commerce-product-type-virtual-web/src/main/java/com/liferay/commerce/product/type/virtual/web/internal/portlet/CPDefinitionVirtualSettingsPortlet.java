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

package com.liferay.commerce.product.type.virtual.web.internal.portlet;

import com.liferay.commerce.product.type.virtual.web.internal.CPDefinitionVirtualSettingItemSelectorHelper;
import com.liferay.commerce.product.type.virtual.web.internal.constants.CPDefinitionVirtualSettingPortletKeys;
import com.liferay.commerce.product.type.virtual.web.internal.constants.CPDefinitionVirtualSettingWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-commerce-product-type-virtual",
		"com.liferay.portlet.display-category=prova",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.preferences-unique-per-layout=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Virtual Settings",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/edit_definition_virtual_setting.jsp",
		"javax.portlet.name=" + CPDefinitionVirtualSettingPortletKeys.COMMERCE_PRODUCT_DEFINITION_VIRTUAL_SETTINGS,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {CPDefinitionVirtualSettingsPortlet.class, Portlet.class}
)
public class CPDefinitionVirtualSettingsPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			CPDefinitionVirtualSettingWebKeys.
				DEFINITION_VIRTUAL_SETTING_ITEM_SELECTOR_HELPER,
			_cpDefinitionVirtualSettingItemSelectorHelper);

		super.render(renderRequest, renderResponse);
	}

	@Reference
	private CPDefinitionVirtualSettingItemSelectorHelper
		_cpDefinitionVirtualSettingItemSelectorHelper;

}