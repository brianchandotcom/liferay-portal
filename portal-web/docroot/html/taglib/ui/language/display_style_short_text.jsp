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

<%@ include file="/html/taglib/ui/language/init.jsp" %>

<%
for (int i = 0; i < locales.length; i++) {
	String currentLanguageId = LocaleUtil.toLanguageId(locales[i]);

	if (!displayCurrentLocale && languageId.equals(currentLanguageId)) {
		continue;
	}

	String cssClassName = "taglib-language-list-text";

	if ((i + 1) == locales.length) {
		cssClassName += " last";
	}

	String localeDisplayName = LocaleUtil.getShortDisplayName(locales[i], duplicateLanguages);
%>

	<c:choose>
		<c:when test="<%= languageId.equals(currentLanguageId) %>">
			<span class="<%= cssClassName %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>"><%= localeDisplayName %></span>
		</c:when>
		<c:otherwise>
			<aui:a cssClass="<%= cssClassName %>" href="<%= HttpUtil.addParameter(formAction, namespace + name, currentLanguageId) %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>"><%= localeDisplayName %></aui:a>
		</c:otherwise>
	</c:choose>

<%
}
%>