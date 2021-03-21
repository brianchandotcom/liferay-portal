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

import aQute.bnd.component.annotations.Activate;
import aQute.bnd.component.annotations.Modified;

import com.liferay.click.to.chat.web.internal.configuration.ClickToChatConfiguration;
import com.liferay.click.to.chat.web.internal.configuration.GroupProviderTokenStrategy;
import com.liferay.click.to.chat.web.internal.constants.ClickToChatWebKeys;
import com.liferay.click.to.chat.web.internal.providers.ProviderDynamicInclude;
import com.liferay.click.to.chat.web.internal.providers.ProviderDynamicIncludeFactory;
import com.liferay.click.to.chat.web.internal.providers.ProviderOptions;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(
	configurationPid = ClickToChatConfiguration.ID, immediate = true,
	service = DynamicInclude.class
)
public class ClickToChatTopHeadDynamicInclude implements DynamicInclude {

	@Activate
	@Modified
	public void activate(Map<String, Object> properties) {
		_clickToChatConfiguration = ConfigurableUtil.createConfigurable(
			ClickToChatConfiguration.class, properties);
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group liveGroup = themeDisplay.getScopeGroup();

		if (liveGroup != null) {
			_typeSettingsUnicodeProperties =
				liveGroup.getTypeSettingsProperties();
		}
		else {
			_typeSettingsUnicodeProperties = new UnicodeProperties();
		}

		if (!LiferayWindowState.isPopUp(httpServletRequest)) {
			PrintWriter printWriter = httpServletResponse.getWriter();

			printWriter.println(_getContentToInclude(themeDisplay));
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	private String _determineProviderTokenForSite() {
		GroupProviderTokenStrategy strategy =
			_clickToChatConfiguration.groupProviderTokenStrategy();

		if (strategy != null) {
			if (strategy.equals(GroupProviderTokenStrategy.ALWAYS_INHERIT)) {
				return _clickToChatConfiguration.defaultAccountToken();
			}
			else if (strategy.equals(
						GroupProviderTokenStrategy.PROVIDE_OR_INHERIT)) {

				String groupProviderToken = GetterUtil.getString(
					_typeSettingsUnicodeProperties.getProperty(
						ClickToChatWebKeys.
							CLICK_TO_CHAT_GROUP_PROVIDER_ACCOUNT_TOKEN));

				if (Validator.isNotNull(groupProviderToken)) {
					return groupProviderToken;
				}

				return _clickToChatConfiguration.defaultAccountToken();
			}
			else if (strategy.equals(GroupProviderTokenStrategy.PROVIDE)) {
				return GetterUtil.getString(
					_typeSettingsUnicodeProperties.getProperty(
						ClickToChatWebKeys.
							CLICK_TO_CHAT_GROUP_PROVIDER_ACCOUNT_TOKEN));
			}
			else {
				throw new UnsupportedOperationException(
					"Unsupported strategy: " + strategy);
			}
		}
		else {
			throw new UnsupportedOperationException(
				"Unsupported strategy: " + strategy);
		}
	}

	private String _getContentToInclude(ThemeDisplay themeDisplay)
		throws IOException {

		if (!_clickToChatConfiguration.enabled()) {
			return "<!-- Click to Chat not enabled in system config -->";
		}
		else if (!_isEnabledInSite()) {
			return "<!-- Click to Chat not enabled for current site -->";
		}
		else if (_isSignedInUsersOnly() && !themeDisplay.isSignedIn()) {
			StringBundler sb = new StringBundler(2);

			sb.append("<!-- Click to Chat requires the user to be signed ");
			sb.append("in to be used in current site -->");

			return sb.toString();
		}
		else {
			String providerAccountToken = _determineProviderTokenForSite();

			if (Validator.isNull(providerAccountToken)) {
				return "<!-- Provider Account Token not setted in Click to " +
					"Chat -->";
			}

			ProviderOptions provider = _clickToChatConfiguration.provider();

			ProviderDynamicInclude providerDynamicInclude =
				ProviderDynamicIncludeFactory.create(
					provider, providerAccountToken, themeDisplay.getUser());

			if (providerDynamicInclude != null) {
				return providerDynamicInclude.getContentToInclude();
			}

			return "<!-- Provider not setted in Click to Chat -->";
		}
	}

	private boolean _isEnabledInSite() {
		Boolean enabledInGroup = GetterUtil.getBoolean(
			_typeSettingsUnicodeProperties.getProperty(
				ClickToChatWebKeys.CLICK_TO_CHAT_GROUP_ENABLED));

		if (enabledInGroup == Boolean.TRUE) {
			return true;
		}

		return false;
	}

	private boolean _isSignedInUsersOnly() {
		Boolean signedInUsersOnly = GetterUtil.getBoolean(
			_typeSettingsUnicodeProperties.getProperty(
				ClickToChatWebKeys.CLICK_TO_CHAT_SIGNED_IN_USERS_ONLY));

		if (signedInUsersOnly == Boolean.TRUE) {
			return true;
		}

		return false;
	}

	private volatile ClickToChatConfiguration _clickToChatConfiguration;
	private UnicodeProperties _typeSettingsUnicodeProperties;

}