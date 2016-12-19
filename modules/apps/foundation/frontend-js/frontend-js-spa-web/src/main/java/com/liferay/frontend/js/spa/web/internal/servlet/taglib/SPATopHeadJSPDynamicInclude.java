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

import com.liferay.frontend.js.spa.web.internal.servlet.taglib.util.SPAUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

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
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(26);

		sb.append("var $ = AUI.$;var _ = AUI._;\n");
		sb.append("Liferay.SPA = Liferay.SPA || {};\n");
		sb.append("Liferay.SPA.cacheExpirationTime = ");
		sb.append(_spaUtil.getCacheExpirationTime(themeDisplay.getCompanyId()));
		sb.append(";\nLiferay.SPA.clearScreensCache = ");
		sb.append(_spaUtil.isClearScreensCache(request, request.getSession()));
		sb.append(";\nLiferay.SPA.excludedPaths = ");
		sb.append(_spaUtil.getExcludedPaths());
		sb.append(";\nLiferay.SPA.loginRedirect = '");
		sb.append(_html.escapeJS(_spaUtil.getLoginRedirect(request)));
		sb.append("';\nLiferay.SPA.navigationExceptionSelectors = '");
		sb.append(_spaUtil.getNavigationExceptionSelectors());
		sb.append("';\nLiferay.SPA.requestTimeout = ");
		sb.append(_spaUtil.getRequestTimeout());
		sb.append(";\nLiferay.SPA.userNotification = {\nmessage: '");
		sb.append(
			_language.get(
				_spaUtil.getLanguageResourceBundle(themeDisplay.getLocale()),
				"it-looks-like-this-is-taking-longer-than-expected"));
		sb.append("',\ntimeout: ");
		sb.append(_spaUtil.getUserNotificationTimeout());
		sb.append(",\ntitle: '");
		sb.append(
			_language.get(
				_spaUtil.getLanguageResourceBundle(themeDisplay.getLocale()),
				"oops"));
		sb.append("'};\nfrontendJsSpaWebLiferayInitEs.default.init(\n");
		sb.append("function(app) {\napp.setPortletsBlacklist(");
		sb.append(_spaUtil.getPortletsBlacklist(themeDisplay));
		sb.append(");\napp.setValidStatusCodes(");
		sb.append(_spaUtil.getValidStatusCodes());
		sb.append(");\n}\n);");

		ScriptData scriptData = new ScriptData();

		scriptData.append(
			null, sb, "frontend-js-spa-web/liferay/init.es",
			ScriptData.ModulesType.ES6);

		scriptData.writeTo(response.getWriter());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		boolean singlePageApplicationEnabled = GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.JAVASCRIPT_SINGLE_PAGE_APPLICATION_ENABLED));

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

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setSPAUtil(SPAUtil spaUtil) {
		_spaUtil = spaUtil;
	}

	protected void unsetSPAUtil(SPAUtil spaUtil) {
		_spaUtil = null;
	}

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	private SPAUtil _spaUtil;

}