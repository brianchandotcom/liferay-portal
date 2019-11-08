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

package com.liferay.frontend.js.spa.web.internal.servlet.taglib;

import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.js.spa.web.internal.servlet.taglib.util.SPAUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Bruno Basto
 */
@Component(immediate = true, service = DynamicInclude.class)
public class SPATopHeadJSPDynamicInclude extends BaseJSPDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		if (_spaUtil.isDisabled(httpServletRequest)) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Map<String, String> values = HashMapBuilder.put(
			"cacheExpirationTime",
			String.valueOf(
				_spaUtil.getCacheExpirationTime(themeDisplay.getCompanyId()))
		).put(
			"clearScreensCache",
			String.valueOf(
				_spaUtil.isClearScreensCache(
					httpServletRequest, httpServletRequest.getSession()))
		).put(
			"debugEnabled", String.valueOf(_spaUtil.isDebugEnabled())
		).put(
			"excludedPaths", _spaUtil.getExcludedPaths()
		).put(
			"loginRedirect",
			_html.escapeJS(_spaUtil.getLoginRedirect(httpServletRequest))
		).put(
			"message",
			_language.get(
				_spaUtil.getLanguageResourceBundle(
					"frontend-js-spa-web", themeDisplay.getLocale()),
				"it-looks-like-this-is-taking-longer-than-expected")
		).put(
			"navigationExceptionSelectors",
			_spaUtil.getNavigationExceptionSelectors()
		).put(
			"portletsBlacklist", _spaUtil.getPortletsBlacklist(themeDisplay)
		).put(
			"requestTimeout", String.valueOf(_spaUtil.getRequestTimeout())
		).put(
			"timeout", String.valueOf(_spaUtil.getUserNotificationTimeout())
		).put(
			"title",
			_language.get(
				_spaUtil.getLanguageResourceBundle(
					"frontend-js-spa-web", themeDisplay.getLocale()),
				"oops")
		).put(
			"validStatusCodes", _spaUtil.getValidStatusCodes()
		).build();

		ScriptData configScriptData = new ScriptData();

		configScriptData.append(
			null,
			StringUtil.replaceToStringBundler(
				_CONFIG_TMPL_CONTENT, StringPool.POUND, StringPool.POUND,
				values),
			null, null);

		configScriptData.writeTo(httpServletResponse.getWriter());

		String initModuleName = _npmResolver.resolveModuleName(
			"frontend-js-spa-web/liferay/init.es");

		ScriptData initScriptData = new ScriptData();

		initScriptData.append(
			null,
			StringUtil.replaceToStringBundler(
				_INIT_TMPL_CONTENT, StringPool.POUND, StringPool.POUND, values),
			initModuleName + " as frontendJsSpaWebLiferayInitEs",
			ScriptData.ModulesType.ES6);

		initScriptData.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		boolean singlePageApplicationEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.JAVASCRIPT_SINGLE_PAGE_APPLICATION_ENABLED));

		if (singlePageApplicationEnabled) {
			dynamicIncludeRegistry.register(
				"/html/common/themes/top_head.jsp#post");
		}
	}

	@Override
	protected String getJspPath() {
		return null;
	}

	@Override
	protected Log getLog() {
		return null;
	}

	private static final String _CONFIG_TMPL_CONTENT = StringUtil.read(
		SPATopHeadJSPDynamicInclude.class, "/META-INF/resources/config.tmpl");

	private static final String _INIT_TMPL_CONTENT = StringUtil.read(
		SPATopHeadJSPDynamicInclude.class, "/META-INF/resources/init.tmpl");

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private NPMResolver _npmResolver;

	@Reference
	private Props _props;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile SPAUtil _spaUtil;

}