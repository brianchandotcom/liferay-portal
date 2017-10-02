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

<%@ include file="/html/portal/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);
%>

<aui:form action='<%= themeDisplay.getPathMain() + "/portal/update_language" %>' method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<aui:select label="" name="languageId">

		<%
		for (Locale curLocale : LanguageUtil.getAvailableLocales()) {
		%>

			<aui:option
				label="<%= curLocale.getDisplayName(curLocale) %>"
				lang="<%= LocaleUtil.toW3cLanguageId(curLocale) %>"
				selected="<%= (locale.getLanguage().equals(curLocale.getLanguage()) && locale.getCountry().equals(curLocale.getCountry())) %>"
				value="<%= LocaleUtil.toLanguageId(curLocale) %>"
			/>

		<%
		}
		%>

	</aui:select>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" value="save" />
	</aui:button-row>
</aui:form>