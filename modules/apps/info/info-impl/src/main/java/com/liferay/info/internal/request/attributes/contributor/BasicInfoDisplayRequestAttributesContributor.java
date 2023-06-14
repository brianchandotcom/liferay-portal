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

package com.liferay.info.internal.request.attributes.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.request.attributes.contributor.InfoDisplayRequestAttributesContributor;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayRenderRequest;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.RenderRequestFactory;
import com.liferay.portlet.RenderResponseFactory;

import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = InfoDisplayRequestAttributesContributor.class)
public class BasicInfoDisplayRequestAttributesContributor
	implements InfoDisplayRequestAttributesContributor {

	@Override
	public void addAttributes(HttpServletRequest httpServletRequest) {
		AssetRendererFactory<?> assetRendererFactory = _getAssetRendererFactory(
			httpServletRequest);

		PortletRequest portletRequest =
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		if ((portletRequest == null) && (assetRendererFactory != null)) {
			Portlet portlet = _portletLocalService.getPortletById(
				assetRendererFactory.getPortletId());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			try {
				InvokerPortlet invokerPortlet =
					PortletInstanceFactoryUtil.create(
						portlet, httpServletRequest.getServletContext());

				PortletConfig portletConfig = PortletConfigFactoryUtil.create(
					portlet, httpServletRequest.getServletContext());

				LiferayRenderRequest liferayRenderRequest =
					RenderRequestFactory.create(
						httpServletRequest, portlet, invokerPortlet,
						portletConfig.getPortletContext(), WindowState.NORMAL,
						PortletMode.VIEW,
						PortletPreferencesFactoryUtil.fromDefaultXML(
							portlet.getDefaultPreferences()),
						themeDisplay.getPlid());

				httpServletRequest.setAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST, liferayRenderRequest);
				httpServletRequest.setAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE,
					RenderResponseFactory.create(
						themeDisplay.getResponse(), liferayRenderRequest));
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private AssetRendererFactory<?> _getAssetRendererFactory(
		HttpServletRequest httpServletRequest) {

		InfoItemDetails infoItemDetails =
			(InfoItemDetails)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_DETAILS);

		if (infoItemDetails == null) {
			return null;
		}

		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(infoItemDetails.getClassName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BasicInfoDisplayRequestAttributesContributor.class);

	@Reference
	private PortletLocalService _portletLocalService;

}