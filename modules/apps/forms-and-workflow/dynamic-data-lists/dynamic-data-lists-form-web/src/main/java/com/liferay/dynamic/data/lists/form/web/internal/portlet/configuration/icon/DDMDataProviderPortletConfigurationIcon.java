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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.configuration.icon;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN},
	service = PortletConfigurationIcon.class
)
public class DDMDataProviderPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		String languageId = getLanguageId(portletRequest);

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(languageId);

		return LanguageUtil.get(resourceBundle, getMessageKey());
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String portletId = PortletProviderUtil.getPortletId(
			DDMDataProviderInstance.class.getName(),
			PortletProvider.Action.EDIT);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, portletId, PortletRequest.RENDER_PHASE);

		try {
			portletURL.setWindowState(LiferayWindowState.NORMAL);
			portletURL.setParameter("redirect", getRedirectURL(portletRequest));
		}
		catch (WindowStateException wse) {
			_log.error(wse);
		}

		return portletURL.toString();
	}

	@Override
	public double getWeight() {
		return 103;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return true;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	@Override
	public boolean isUseDialog() {
		return false;
	}

	protected String getLanguageId(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getLanguageId();
	}

	protected String getMessageKey() {
		return "data-providers";
	}

	protected String getRedirectURL(PortletRequest portletRequest) {
		PortletURL redirectURL = PortletURLFactoryUtil.create(
			portletRequest, DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
			PortletRequest.RENDER_PHASE);

		return redirectURL.toString();
	}

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.dynamic.data.mapping.data.provider.web)",
		unbind = "-"
	)
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMDataProviderPortletConfigurationIcon.class);

	private ResourceBundleLoader _resourceBundleLoader;

}