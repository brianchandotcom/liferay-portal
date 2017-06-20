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

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.servlet.HttpHeaders" %><%@
page import="com.liferay.portal.kernel.util.ContentTypes" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %>

<%@ page import="java.util.Locale" %>

<%
response.setHeader(HttpHeaders.CONTENT_TYPE, ContentTypes.TEXT_JAVASCRIPT);

String languageId = LanguageUtil.getLanguageId(request);

Locale locale = LocaleUtil.fromLanguageId(languageId);
%>

AUI.add(
	'portal-available-languages',
	function(A) {
		var available = {};

		var direction = {};

		<%
		for (Locale curLocale : LanguageUtil.getAvailableLocales()) {
			String selLanguageId = LocaleUtil.toLanguageId(curLocale);
		%>

			available['<%= selLanguageId %>'] = '<%= curLocale.getDisplayName(locale) %>';
			direction['<%= selLanguageId %>'] = '<%= LanguageUtil.get(curLocale, "lang.dir") %>';

		<%
		}
		%>

		Liferay.Language.available = available;
		Liferay.Language.direction = direction;
	},
	'',
	{
		requires: ['liferay-language']
	}
);