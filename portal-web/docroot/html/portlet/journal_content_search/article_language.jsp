<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal_content_search/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Locale snippetLocale = ((Summary)objArray[2]).getLocale();

String languageId = LocaleUtil.toLanguageId(snippetLocale);
%>

<c:if test="<%= languageId.length() > 0 %>">
	<liferay-ui:icon image='<%= "../language/" + languageId %>' message='<%= LanguageUtil.format(locale, "this-result-comes-from-the-x-version-of-this-content", snippetLocale.getDisplayLanguage(locale), false) %>' />
</c:if>