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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
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

		if (Objects.equals(
				_clickToChatConfiguration.siteSettingsStrategy(),
				"always-inherit")) {

			if (!_clickToChatConfiguration.enabled()) {
				return;
			}

			if (!_clickToChatConfiguration.guestUsersAllowed() &&
				!themeDisplay.isSignedIn()) {

				return;
			}

			if (Validator.isNull(
					_clickToChatConfiguration.chatProviderAccountId()) ||
				Validator.isNull(_clickToChatConfiguration.chatProviderId())) {

				return;
			}

			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ACCOUNT_ID,
				_clickToChatConfiguration.chatProviderAccountId());
			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ID,
				_clickToChatConfiguration.chatProviderId());
		}
		else if (Objects.equals(
					_clickToChatConfiguration.siteSettingsStrategy(),
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

			String clickToChatProviderAccountId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatProviderAccountId");
			String clickToChatProviderId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatProviderId");

			if (Validator.isNull(clickToChatProviderAccountId) ||
				Validator.isNull(clickToChatProviderId)) {

				return;
			}

			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ACCOUNT_ID,
				clickToChatProviderAccountId);
			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ID,
				clickToChatProviderId);
		}
		else if (Objects.equals(
					_clickToChatConfiguration.siteSettingsStrategy(),
					"inherit-or-override")) {

			UnicodeProperties typeSettingsUnicodeProperties =
				group.getTypeSettingsProperties();

			String clickToChatEnabled =
				typeSettingsUnicodeProperties.getProperty("clickToChatEnabled");

			if (clickToChatEnabled == null) {
				if (!_clickToChatConfiguration.enabled()) {
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
				if (!_clickToChatConfiguration.guestUsersAllowed() &&
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

			String clickToChatProviderAccountId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatProviderAccountId",
					_clickToChatConfiguration.chatProviderAccountId());
			String clickToChatProviderId =
				typeSettingsUnicodeProperties.getProperty(
					"clickToChatProviderId",
					_clickToChatConfiguration.chatProviderId());

			if (Validator.isNull(clickToChatProviderAccountId) ||
				Validator.isNull(clickToChatProviderId)) {

				return;
			}

			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ACCOUNT_ID,
				clickToChatProviderAccountId);
			httpServletRequest.setAttribute(
				ClickToChatWebKeys.CLICK_TO_CHAT_PROVIDER_ID,
				clickToChatProviderId);
		}

		super.include(httpServletRequest, httpServletResponse, key);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_clickToChatConfiguration = ConfigurableUtil.createConfigurable(
			ClickToChatConfiguration.class, properties);
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

	private volatile ClickToChatConfiguration _clickToChatConfiguration;

}