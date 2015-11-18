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

<%@ include file="/html/common/themes/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);

Locale userLocale = user.getLocale();
%>

<c:if test="<%= !locale.equals(user.getLocale()) %>">
	<button class="close" id="ignoreUserLocaleOptions" type="button">&times;</button>

	<liferay-ui:message arguments="<%= locale.getDisplayName(locale) %>" key="this-page-is-displayed-in-x" />

	<c:if test="<%= LanguageUtil.isAvailableLocale(userLocale) %>">

		<%
		String displayPreferredLanguageURLString = themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + currentURL + "&languageId=" + user.getLanguageId() + "&persistState=false&showUserLocaleOptionsMessage=false";
		%>

		<aui:a href="<%= displayPreferredLanguageURLString %>">
			<liferay-ui:message arguments="<%= userLocale.getDisplayName(locale) %>" key="display-the-page-in-x" />
		</aui:a>
	</c:if>

	<%
	String changePreferredLanguageURLString = themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + currentURL + "&languageId=" + themeDisplay.getLanguageId() + "&showUserLocaleOptionsMessage=false";
	%>

	<aui:a href="<%= changePreferredLanguageURLString %>">
		<liferay-ui:message arguments="<%= locale.getDisplayName(locale) %>" key="set-x-as-your-preferred-language" />
	</aui:a>

	<aui:script use="aui-base,liferay-store">
		var ignoreUserLocaleOptionsNode = A.one('#ignoreUserLocaleOptions');

		ignoreUserLocaleOptionsNode.on(
			'click',
			function() {
				Liferay.Store(
					{
						ignoreUserLocaleOptions: true,
						useHttpSession: true
					}
				);
			}
		);
	</aui:script>
</c:if>