/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.click.to.chat.web.internal.portal.settings.configuration.admin.display;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfigurationUtil;
import com.liferay.click.to.chat.web.internal.constants.ClickToChatWebKeys;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.settings.configuration.admin.display.SiteSettingsConfigurationScreenContributor;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = SiteSettingsConfigurationScreenContributor.class)
public class ClickToChatSiteSettingsConfigurationScreenContributor
	implements SiteSettingsConfigurationScreenContributor {

	@Override
	public String getCategoryKey() {
		return "click-to-chat";
	}

	@Override
	public String getJspPath() {
		return "/site_settings/click_to_chat.jsp";
	}

	@Override
	public String getKey() {
		return "site-configuration-click-to-chat";
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, "click-to-chat");
	}

	@Override
	public String getSaveMVCActionCommandName() {
		return "/click_to_chat/save_site_configuration";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void setAttributes(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ClickToChatConfiguration clickToChatConfiguration =
			ClickToChatConfigurationUtil.getClickToChatConfiguration(
				themeDisplay.getCompanyId(), themeDisplay.getSiteGroupId());

		httpServletRequest.setAttribute(
			ClickToChatConfiguration.class.getName(),
			ClickToChatConfigurationUtil.getClickToChatConfiguration(
				themeDisplay.getCompanyId(), 0));

		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ACCOUNT_ID,
			clickToChatConfiguration.chatProviderAccountId());
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_ID,
			clickToChatConfiguration.chatProviderId());
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_KEY_ID,
			clickToChatConfiguration.chatProviderKeyId());
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_CHAT_PROVIDER_SECRET_KEY,
			clickToChatConfiguration.chatProviderSecretKey());
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_ENABLED,
			clickToChatConfiguration.enabled());
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_GUEST_USERS_ALLOWED,
			clickToChatConfiguration.guestUsersAllowed());
		httpServletRequest.setAttribute(
			ClickToChatWebKeys.CLICK_TO_CHAT_HIDE_IN_CONTROL_PANEL,
			clickToChatConfiguration.hideInControlPanel());
	}

	@Reference
	private Language _language;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.click.to.chat.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}