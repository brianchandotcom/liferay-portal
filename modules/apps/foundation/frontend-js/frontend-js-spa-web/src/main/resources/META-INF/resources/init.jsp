<%--
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
--%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.frontend.js.spa.web.internal.constants.SPAWebKeys" %><%@
page import="com.liferay.frontend.js.spa.web.internal.servlet.taglib.util.SPAUtil" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %>

<liferay-theme:defineObjects />

<%
SPAUtil spaUtil = (SPAUtil)request.getAttribute(SPAWebKeys.SPA_UTIL);
%>

<aui:script position="inline" require="frontend-js-spa-web/liferay/init.es">
	Liferay.SPA = Liferay.SPA || {};

	Liferay.SPA.cacheExpirationTime = <%= spaUtil.getCacheExpirationTime(themeDisplay.getCompanyId()) %>;
	Liferay.SPA.clearScreensCache = <%= spaUtil.isClearScreensCache(request, session) %>;
	Liferay.SPA.excludedPaths = <%= spaUtil.getExcludedPaths() %>;
	Liferay.SPA.loginRedirect = '<%= spaUtil.getLoginRedirect(request) %>';
	Liferay.SPA.requestTimeout = <%= spaUtil.getRequestTimeout() %>;
	Liferay.SPA.userNotification = {
		message: '<%= LanguageUtil.get(spaUtil.getLanguageResourceBundle(themeDisplay.getLocale()), "it-looks-like-this-is-taking-longer-than-expected") %>',
		timeout: <%= spaUtil.getUserNotificationTimeout() %>,
		title: '<%= LanguageUtil.get(spaUtil.getLanguageResourceBundle(themeDisplay.getLocale()), "oops") %>'
	};

	frontendJsSpaWebLiferayInitEs.default.init(
		function(app) {
			app.setPortletsBlacklist(<%= spaUtil.getPortletsBlacklist(themeDisplay) %>);
			app.setValidStatusCodes(<%= spaUtil.getValidStatusCodes() %>);
		}
	);
</aui:script>