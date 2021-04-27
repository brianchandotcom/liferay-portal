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
import com.liferay.click.to.chat.web.internal.constants.ClickToChatWebKeys;
import com.liferay.frontend.taglib.form.navigator.BaseJSPFormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorEntry;
import com.liferay.frontend.taglib.form.navigator.constants.FormNavigatorConstants;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration",
	immediate = true, property = "form.navigator.entry.order:Integer=30",
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

		String chatProviderAccountId = null;
		String chatProviderId = null;
		boolean enabled = false;
		boolean guestUsersAllowed = false;

		ClickToChatConfiguration clickToChatConfiguration = null;

		try {
			clickToChatConfiguration =
				_configurationProvider.getCompanyConfiguration(
					ClickToChatConfiguration.class,
					CompanyThreadLocal.getCompanyId());
		}
		catch (PortalException portalException) {
			ReflectionUtil.throwException(portalException);
		}

		httpServletRequest.setAttribute(
			ClickToChatConfiguration.class.getName(), clickToChatConfiguration);

		UnicodeProperties typeSettingsUnicodeProperties =
			_getTypeSettingsUnicodeProperties(httpServletRequest);

		if (Objects.equals(
				clickToChatConfiguration.siteSettingsStrategy(),
				"always-inherit")) {

			chatProviderAccountId =
				clickToChatConfiguration.chatProviderAccountId();
			chatProviderId = clickToChatConfiguration.chatProviderId();
			enabled = clickToChatConfiguration.enabled();
			guestUsersAllowed = clickToChatConfiguration.guestUsersAllowed();
		}
		else if (Objects.equals(
					clickToChatConfiguration.siteSettingsStrategy(),
					"always-override")) {

			chatProviderAccountId = typeSettingsUnicodeProperties.getProperty(
				"clickToChatChatProviderAccountId");
			chatProviderId = typeSettingsUnicodeProperties.getProperty(
				"clickToChatChatProviderId");
			enabled = GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatEnabled"));
			guestUsersAllowed = GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatGuestUsersAllowed"));
		}
		else if (Objects.equals(
					clickToChatConfiguration.siteSettingsStrategy(),
					"inherit-or-override")) {

			chatProviderAccountId = typeSettingsUnicodeProperties.getProperty(
				"clickToChatChatProviderAccountId");
			chatProviderId = typeSettingsUnicodeProperties.getProperty(
				"clickToChatChatProviderId");

			String clickToChatEnabled =
				typeSettingsUnicodeProperties.getProperty("clickToChatEnabled");

			enabled = (clickToChatEnabled != null) ?
				GetterUtil.getBoolean(clickToChatEnabled) :
					clickToChatConfiguration.enabled();

			String clickToChatGuestUsersAllowed =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatGuestUsersAllowed");

			guestUsersAllowed = (clickToChatGuestUsersAllowed != null) ?
				GetterUtil.getBoolean(clickToChatGuestUsersAllowed) :
					clickToChatConfiguration.guestUsersAllowed();
		}

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ACCOUNT_ID,
			chatProviderAccountId);
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID, chatProviderId);
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_ENABLED, enabled);
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_GUEST_USERS_ALLOWED,
			guestUsersAllowed);

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

	@Override
	protected String getJspPath() {
		return "/sites_admin/click_to_chat.jsp";
	}

	private UnicodeProperties _getTypeSettingsUnicodeProperties(
		HttpServletRequest httpServletRequest) {

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

		return typeSettingsUnicodeProperties;
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}