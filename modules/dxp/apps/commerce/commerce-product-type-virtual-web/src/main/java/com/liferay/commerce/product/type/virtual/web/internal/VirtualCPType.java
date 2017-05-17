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

package com.liferay.commerce.product.type.virtual.web.internal;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.CPType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.type.display.order:Integer=15",
		"commerce.product.type.name=virtual"
	},
	service = CPType.class
)
public class VirtualCPType implements CPType {

	@Override
	public String getCPDefinitionEditUrl(long cpDefinitionId, HttpServletRequest httpServletRequest) throws PortalException{

		ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
				httpServletRequest, themeDisplay.getScopeGroup(),
				CPDefinition.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
				"mvcRenderCommandName", "editProductDefinitionVirtualSetting");

		portletURL.setParameter(
				"cpDefinitionId", String.valueOf(cpDefinitionId));

		return portletURL.toString();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "virtual");
	}

	@Override
	public String getName() {
		return "virtual";
	}

}