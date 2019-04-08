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

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.web.internal.AssignableScopes;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2AuthorizePortletDisplayContext
	extends BaseOAuth2PortletDisplayContext {

	public OAuth2AuthorizePortletDisplayContext(
		ThemeDisplay themeDisplay, DLURLHelper dlURLHelper) {

		super.themeDisplay = themeDisplay;
		super.dlURLHelper = dlURLHelper;
	}

	public AssignableScopes getAssignableScopes() {
		return _assignableScopes;
	}

	@Override
	public OAuth2Application getOAuth2Application() {
		return _oAuth2Application;
	}

	public Map<String, String> getOAuth2Parameters() {
		return _oAuth2Parameters;
	}

	public boolean hasCreateTokenApplicationPermission(
		OAuth2Application oAuth2Application) {

		return hasPermission(
			oAuth2Application, OAuth2ProviderActionKeys.ACTION_CREATE_TOKEN);
	}

	public void setAssignableScopes(AssignableScopes assignableScopes) {
		_assignableScopes = assignableScopes;
	}

	public void setOAuth2Application(OAuth2Application oAuth2Application) {
		_oAuth2Application = oAuth2Application;
	}

	public void setOAuth2Parameters(Map<String, String> oAuth2Parameters) {
		_oAuth2Parameters = oAuth2Parameters;
	}

	private AssignableScopes _assignableScopes;
	private OAuth2Application _oAuth2Application;
	private Map<String, String> _oAuth2Parameters = new HashMap<>();

}