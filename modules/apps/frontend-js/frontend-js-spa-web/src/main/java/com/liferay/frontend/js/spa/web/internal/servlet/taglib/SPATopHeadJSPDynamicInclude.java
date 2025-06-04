/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.spa.web.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.esm.ESImportUtil;
import com.liferay.frontend.js.spa.web.internal.servlet.taglib.helper.SPAHelper;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.JSFragment;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(service = DynamicInclude.class)
public class SPATopHeadJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group group = themeDisplay.getScopeGroup();

		SPAHelper spaHelper = new SPAHelper(
			_configurationProvider, _jsonFactory, _portal, _portletLocalService,
			group.getGroupId());

		if (!spaHelper.isEnabled()) {
			return;
		}

		JSONArray excludedPathsJSONArray =
			spaHelper.getExcludedPathsJSONArray();

		String currentURL = _portal.getCurrentURL(httpServletRequest);

		for (Object excludedPath : excludedPathsJSONArray) {
			if (currentURL.equals(excludedPath)) {
				return;
			}
		}

		JSONObject configJSONObject = JSONUtil.put(
			"cacheExpirationTime", spaHelper.getCacheExpirationTime()
		).put(
			"clearScreensCache",
			spaHelper.isClearScreensCache(
				httpServletRequest, httpServletRequest.getSession())
		).put(
			"debugEnabled", spaHelper.isDebugEnabled()
		).put(
			"excludedPaths", excludedPathsJSONArray
		).put(
			"excludedTargetPortlets",
			spaHelper.getExcludedTargetPortletsJSONArray()
		).put(
			"loginRedirect",
			HtmlUtil.escapeJS(spaHelper.getLoginRedirect(httpServletRequest))
		).put(
			"navigationExceptionSelectors",
			spaHelper.getNavigationExceptionSelectors()
		).put(
			"portletsBlacklist",
			spaHelper.getPortletsBlacklistJSONArray(themeDisplay)
		).put(
			"preloadCSS", spaHelper.isPreloadCSS()
		).put(
			"requestTimeout", spaHelper.getRequestTimeout()
		).put(
			"userNotification",
			JSONUtil.put(
				"message",
				_language.get(
					spaHelper.getLanguageResourceBundle(
						"frontend-js-spa-web", themeDisplay.getLocale()),
					"it-looks-like-this-is-taking-longer-than-expected")
			).put(
				"timeout", spaHelper.getUserNotificationTimeout()
			).put(
				"title",
				_language.get(
					spaHelper.getLanguageResourceBundle(
						"frontend-js-spa-web", themeDisplay.getLocale()),
					"oops")
			)
		).put(
			"validStatusCodes", spaHelper.getValidStatusCodesJSONArray()
		);

		ScriptData initScriptData = new ScriptData();

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		initScriptData.append(
			null,
			new JSFragment(
				"init(" + configJSONObject.toString() + ");",
				Arrays.asList(
					ESImportUtil.getESImport(
						absolutePortalURLBuilder,
						"{init} from frontend-js-spa-web"))));

		initScriptData.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_head.jsp#post");
	}

	@Override
	protected String getJspPath() {
		return null;
	}

	@Override
	protected Log getLog() {
		return null;
	}

	@Override
	protected ServletContext getServletContext() {
		return null;
	}

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

}