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

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

Locale snippetLocale = ((Summary)objArray[1]).getLocale();

String languageId = LocaleUtil.toLanguageId(snippetLocale);
%>

<c:if test="<%= languageId.length() > 0 %>">
	<liferay-ui:icon image='<%= "../language/" + languageId %>' message='<%= LanguageUtil.format(request, "this-result-comes-from-the-x-version-of-this-content", snippetLocale.getDisplayLanguage(locale), false) %>' />
</c:if>