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

package com.liferay.click.to.chat.web.internal.servlet.taglib;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.click.to.chat.web.internal.constants.ClickToChatWebKeys;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	configurationPid = "com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration",
	immediate = true, service = DynamicInclude.class
)
public class ClickToChatTopHeadJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		if (group.isStagingGroup()) {
			return;
		}

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

		if (Objects.equals(
				clickToChatConfiguration.siteSettingsStrategy(),
				"always-inherit")) {

			if (!clickToChatConfiguration.enabled()) {
				return;
			}

			if (!clickToChatConfiguration.guestUsersAllowed() &&
				!themeDisplay.isSignedIn()) {

				return;
			}

			if (Validator.isNull(
					clickToChatConfiguration.chatProviderAccountId()) ||
				Validator.isNull(clickToChatConfiguration.chatProviderId())) {

				return;
			}

			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ACCOUNT_ID,
				clickToChatConfiguration.chatProviderAccountId());
			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID,
				clickToChatConfiguration.chatProviderId());
		}
		else if (Objects.equals(
					clickToChatConfiguration.siteSettingsStrategy(),
					"always-override")) {

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			if (!GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.getProperty(
						"clickToChatEnabled"))) {

				return;
			}

			if (!GetterUtil.getBoolean(
					typeSettingsUnicodeProperties.getProperty(
						"clickToChatGuestUsersAllowed")) &&
				!themeDisplay.isSignedIn()) {

				return;
			}

			String clickToChatChatProviderAccountId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatChatProviderAccountId");
			String clickToChatChatProviderId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatChatProviderId");

			if (Validator.isNull(clickToChatChatProviderAccountId) ||
				Validator.isNull(clickToChatChatProviderId)) {

				return;
			}

			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ACCOUNT_ID,
				clickToChatChatProviderAccountId);
			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID,
				clickToChatChatProviderId);
		}
		else if (Objects.equals(
					clickToChatConfiguration.siteSettingsStrategy(),
					"inherit-or-override")) {

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			String clickToChatEnabled =
				typeSettingsUnicodeProperties.getProperty("clickToChatEnabled");

			if (clickToChatEnabled == null) {
				if (!clickToChatConfiguration.enabled()) {
					return;
				}
			}
			else {
				if (!GetterUtil.getBoolean(clickToChatEnabled)) {
					return;
				}
			}

			String clickToChatGuestUsersAllowed =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatGuestUsersAllowed");

			if (clickToChatGuestUsersAllowed == null) {
				if (!clickToChatConfiguration.guestUsersAllowed() &&
					!themeDisplay.isSignedIn()) {

					return;
				}
			}
			else {
				if (!GetterUtil.getBoolean(clickToChatGuestUsersAllowed) &&
					!themeDisplay.isSignedIn()) {

					return;
				}
			}

			String clickToChatChatProviderAccountId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatChatProviderAccountId",
					clickToChatConfiguration.chatProviderAccountId());
			String clickToChatChatProviderId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatChatProviderId",
					clickToChatConfiguration.chatProviderId());

			if (Validator.isNull(clickToChatChatProviderAccountId) ||
				Validator.isNull(clickToChatChatProviderId)) {

				return;
			}

			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ACCOUNT_ID,
				clickToChatChatProviderAccountId);
			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID,
				clickToChatChatProviderId);
		}

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return "/dynamic_include/view.jsp";
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.click.to.chat.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ClickToChatTopHeadJSPDynamicInclude.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}