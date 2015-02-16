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

<aui:form action="<%= formAction %>" method="post" name="<%= formName %>">
	<aui:select changesContext="<%= true %>" label="" name="<%= name %>" onChange='<%= "submitForm(document." + namespace + formName + ");" %>' title="language" useNamespace="<%= false %>">

		<%
		for (int i = 0; i < locales.length; i++) {
		%>

			<aui:option cssClass="taglib-language-option" label="<%= LocaleUtil.getLongDisplayName(locales[i], duplicateLanguages) %>" lang="<%= LocaleUtil.toW3cLanguageId(locales[i]) %>" selected="<%= (locale.getLanguage().equals(locales[i].getLanguage()) && locale.getCountry().equals(locales[i].getCountry())) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

		<%
		}
		%>

	</aui:select>
</aui:form>

<aui:script>

	<%
	for (int i = 0; i < locales.length; i++) {
	%>

		document.<%= namespace + formName %>.<%= namespace + name %>.options[<%= i %>].style.backgroundImage = 'url(<%= themeDisplay.getPathThemeImages() %>/language/<%= LocaleUtil.toLanguageId(locales[i]) %>.png)';

	<%
	}
	%>

</aui:script>