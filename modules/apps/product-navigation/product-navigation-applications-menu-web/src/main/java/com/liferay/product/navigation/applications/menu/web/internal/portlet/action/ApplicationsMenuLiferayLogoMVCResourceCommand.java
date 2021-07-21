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

package com.liferay.product.navigation.applications.menu.web.internal.portlet.action;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.image.ImageToolImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.product.navigation.applications.menu.web.internal.constants.ProductNavigationApplicationsMenuPortletKeys;

import java.io.InputStream;

import java.net.URL;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ProductNavigationApplicationsMenuPortletKeys.PRODUCT_NAVIGATION_APPLICATIONS_MENU,
		"mvc.command.name=/applications_menu/liferay_logo"
	},
	service = MVCResourceCommand.class
)
public class ApplicationsMenuLiferayLogoMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		InputStream inputStream =
			_getApplicationsMenuDefaultLiferayLogoInputStream();

		if (inputStream == null) {
			return;
		}

		PortletResponseUtil.write(resourceResponse, inputStream);
	}

	private InputStream _getApplicationsMenuDefaultLiferayLogoInputStream() {
		ClassLoader classLoader = ImageToolImpl.class.getClassLoader();

		try {
			InputStream inputStream = null;

			String imageDefaultLiferayLogo =
				_getApplicationsMenuDefualtLiferayLogo();

			int index = imageDefaultLiferayLogo.indexOf(CharPool.SEMICOLON);

			if (index == -1) {
				inputStream = classLoader.getResourceAsStream(
					_getApplicationsMenuDefualtLiferayLogo());
			}
			else {
				String bundleIdString = imageDefaultLiferayLogo.substring(
					0, index);

				int bundleId = GetterUtil.getInteger(bundleIdString, -1);

				String name = imageDefaultLiferayLogo.substring(index + 1);

				if (bundleId < 0) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Fallback to portal class loader because of " +
								"invalid bundle ID " + bundleIdString);
					}

					inputStream = classLoader.getResourceAsStream(name);
				}
				else {
					Bundle bundle = _bundleContext.getBundle(bundleId);

					if (bundle != null) {
						URL url = bundle.getResource(name);

						inputStream = url.openStream();
					}
				}
			}

			if (inputStream == null) {
				if (_log.isDebugEnabled()) {
					_log.debug("Default Liferay logo is not available");
				}
			}

			return inputStream;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to configure the default Liferay logo: " +
						exception.getMessage());
			}
		}

		return null;
	}

	private String _getApplicationsMenuDefualtLiferayLogo() {
		return GetterUtil.getString(
			PropsUtil.get(PropsKeys.APPLICATIONS_MENU_DEFAULT_LIFERAY_LOGO),
			"com/liferay/portal/dependencies/liferay_logo.png");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ApplicationsMenuLiferayLogoMVCResourceCommand.class);

	private BundleContext _bundleContext;

}