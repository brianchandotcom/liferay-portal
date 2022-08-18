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

package com.liferay.friendly.url.web.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionClicks;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Couso
 */
@Component(immediate = true, service = DynamicInclude.class)
public class RedirectOptionsJSPDynamicInclude implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		User user = themeDisplay.getUser();

		if (user.isDefaultUser()) {
			return;
		}

		boolean showRedirectMessage = ParamUtil.getBoolean(
			httpServletRequest, "p_l_skip_redirect");

		if (!showRedirectMessage) {
			return;
		}

		boolean ignoreRedirectOptions = GetterUtil.getBoolean(
			SessionClicks.get(
				httpServletRequest.getSession(),
				"com.liferay.friendly.url.web_ignoreRedirectOptions",
				Boolean.FALSE.toString()));

		if (ignoreRedirectOptions) {
			return;
		}

		boolean showRedirectOptionsMessage = ParamUtil.getBoolean(
			httpServletRequest, "showRedirectOptionsMessage", true);

		if (!showRedirectOptionsMessage) {
			return;
		}

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.fetchRedirectEntry(
				themeDisplay.getScopeGroupId(),
				_normalizeFriendlyURL(
					originalHttpServletRequest.getRequestURI()),
				false);

		if (redirectEntry == null) {
			redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
				themeDisplay.getScopeGroupId(),
				_normalizeFriendlyURL(
					themeDisplay.getLayoutFriendlyURL(
						themeDisplay.getLayout())),
				true);
		}

		if (redirectEntry == null) {
			return;
		}

		ScriptData scriptData = new ScriptData();

		String initModuleName = _npmResolver.resolveModuleName(
			"@liferay/friendly-url-web/index");

		String redirectURL = StringBundler.concat(
			redirectEntry.getDestinationURL(), "?redirect=",
			URLCodec.encodeURL(themeDisplay.getURLCurrent()),
			"&showRedirectOptionsMessage=false");

		scriptData.append(
			null,
			"RedirectOptions.default({redirectURL: \"" + redirectURL + "\"})",
			initModuleName + " as RedirectOptions", ScriptData.ModulesType.ES6);

		scriptData.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void register(
		DynamicInclude.DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#pre");
	}

	private String _normalizeFriendlyURL(String friendlyURL) {
		if (Validator.isNotNull(friendlyURL) &&
			friendlyURL.startsWith(StringPool.SLASH)) {

			return friendlyURL.substring(1);
		}

		return friendlyURL;
	}

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Portal _portal;

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

}