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

package com.liferay.click.to.chat.web.internal.frontend.taglib.form.navigator;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.click.to.chat.web.internal.configuration.GroupProviderTokenStrategy;
import com.liferay.click.to.chat.web.internal.constants.ClickToChatWebKeys;
import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = ClickToChatConfiguration.ID, immediate = true,
	property = "form.navigator.entry.order:Integer=30",
	service = FormNavigatorEntry.class
)
public class ClickToChatFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<Object>
	implements FormNavigatorEntry<Object> {

	@Override
	public String getCategoryKey() {
		return FormNavigatorConstants.CATEGORY_KEY_SITES_ADVANCED;
	}

	@Override
	public String getFormNavigatorId() {
		return FormNavigatorConstants.FORM_NAVIGATOR_ID_SITES;
	}

	@Override
	public String getKey() {
		return "click-to-chat";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, getKey());
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Group liveGroup = (Group)httpServletRequest.getAttribute(
			"site.liveGroup");

		UnicodeProperties typeSettingsUnicodeProperties = null;

		if (liveGroup != null) {
			typeSettingsUnicodeProperties =
				liveGroup.getTypeSettingsProperties();
		}
		else {
			typeSettingsUnicodeProperties = new UnicodeProperties();
		}

		boolean systemSettingsEnabled = _clickToChatConfiguration.enabled();

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_SYSTEM_SETTINGS_ENABLED,
			systemSettingsEnabled);

		boolean clickToChatEnabled = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty(
				ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_ENABLED));

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_ENABLED, clickToChatEnabled);

		boolean clickToChatSignedInUsersOnly = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty(
				ClickToChatWebKeys.CLICK_TO_CHAT_SIGNED_IN_USERS_ONLY));

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_SIGNED_IN_USERS_ONLY,
			clickToChatSignedInUsersOnly);

		String clickToChatProviderAccountToken = GetterUtil.getString(
			typeSettingsUnicodeProperties.getProperty(
				ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_ACCOUNT_TOKEN));

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_ACCOUNT_TOKEN,
			clickToChatProviderAccountToken);

		GroupProviderTokenStrategy strategy =
			_clickToChatConfiguration.groupProviderTokenStrategy();

		if (strategy != null) {
			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_PROVIDER_TOKEN_STRATEGY,
				strategy.getValue());
		}

		super.include(httpServletRequest, httpServletResponse);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.click.to.chat.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_clickToChatConfiguration = ConfigurableUtil.createConfigurable(
			ClickToChatConfiguration.class, properties);
	}

	@Override
	protected String getJspPath() {
		return "/sites_admin/click_to_chat.jsp";
	}

	private ClickToChatConfiguration _clickToChatConfiguration;

}