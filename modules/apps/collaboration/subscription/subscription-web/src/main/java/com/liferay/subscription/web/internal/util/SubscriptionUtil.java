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

package com.liferay.subscription.web.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alejandro Tardín
 */
public class SubscriptionUtil {

	public static PortletURL getManageSubscriptionsURL(
			HttpServletRequest request)
		throws PortalException, WindowStateException {

		PortletURL manageSubscriptionsURL = PortletProviderUtil.getPortletURL(
			request, Subscription.class.getName(),
			PortletProvider.Action.MANAGE);

		if (manageSubscriptionsURL != null) {
			manageSubscriptionsURL.setWindowState(LiferayWindowState.MAXIMIZED);
		}

		return manageSubscriptionsURL;
	}

	public static String getTitle(Locale locale, Subscription subscription)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				subscription.getClassName());

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			subscription.getClassPK());

		return assetRenderer.getTitle(locale);
	}

}