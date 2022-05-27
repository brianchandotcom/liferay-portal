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

package com.liferay.client.extension.internal;

import com.liferay.client.extension.constants.ClientExtensionConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.service.ClientExtensionEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.client.extension.ThemeClientExtensions;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ThemeClientExtensions.class)
public class ThemeClientExtensionsImpl implements ThemeClientExtensions {

	@Override
	public List<String> getThemeJSURLs(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<ClientExtensionEntry> clientExtensionEntries =
			_clientExtensionEntryLocalService.getClientExtensionEntries(
				themeDisplay.getCompanyId(),
				ClientExtensionConstants.TYPE_THEME_JS);

		for (ClientExtensionEntry clientExtensionEntry :
				clientExtensionEntries) {

			if (Objects.equals(
					_getThemeId(clientExtensionEntry),
					themeDisplay.getThemeId())) {

				String themeJSURLsString =
					clientExtensionEntry.getThemeJSURLs();

				return Arrays.asList(
					themeJSURLsString.split(StringPool.NEW_LINE));
			}
		}

		return Collections.singletonList(
			HtmlUtil.escape(
				_portal.getStaticResourceURL(
					httpServletRequest,
					themeDisplay.getPathThemeJavaScript() + "/main.js")));
	}

	private String _getThemeId(ClientExtensionEntry clientExtensionEntry) {
		try {
			Properties properties = PropertiesUtil.load(
				clientExtensionEntry.getProperties());

			return (String)properties.get("theme.id");
		}
		catch (IOException ioException) {
			return null;
		}
	}

	@Reference
	private ClientExtensionEntryLocalService _clientExtensionEntryLocalService;

	@Reference
	private Portal _portal;

}