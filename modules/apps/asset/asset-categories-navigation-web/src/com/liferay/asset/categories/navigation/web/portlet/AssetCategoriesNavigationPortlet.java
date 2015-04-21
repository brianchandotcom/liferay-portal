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

package com.liferay.asset.categories.navigation.web.portlet;

import com.liferay.asset.categories.navigation.web.configuration.AssetCategoriesNavigationPortletInstanceConfiguration;
import com.liferay.asset.categories.navigation.web.upgrade.AssetCategoriesNavigationWebUpgrade;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-asset-categories-navigation",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.icon=/icons/asset_categories_navigation.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Categories Navigation",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class AssetCategoriesNavigationPortlet extends MVCPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		try {
			AssetCategoriesNavigationPortletInstanceConfiguration
				assetCategoriesNavigationPortletInstanceConfiguration =
					_settingsFactory.getSettings(
						AssetCategoriesNavigationPortletInstanceConfiguration.
							class,
						new PortletInstanceSettingsLocator(
							themeDisplay.getLayout(), portletDisplay.getId()));

			renderRequest.setAttribute(
				AssetCategoriesNavigationPortletInstanceConfiguration.
					class.getName(),
				assetCategoriesNavigationPortletInstanceConfiguration);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		super.doView(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	protected void setAssetCategoriesNavigationUpgrade(
		AssetCategoriesNavigationWebUpgrade
			assetCategoriesNavigationWebUpgrade) {
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private SettingsFactory _settingsFactory;

}