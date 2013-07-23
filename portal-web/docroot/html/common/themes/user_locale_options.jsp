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

<%@ include file="/html/common/init.jsp" %>

<%
Locale curLocale = themeDisplay.getLocale();
Locale userLocale = user.getLocale();
%>

<c:if test="<%= user.getLocale() != curLocale %>">
	<button class="close" type="button">&times;</button>

	<%
	String currentURL = PortalUtil.getCurrentURL(request);

	PortletURL displayPreferredLanguageURL = new PortletURLImpl(request, PortletKeys.LANGUAGE, plid, PortletRequest.ACTION_PHASE);

	displayPreferredLanguageURL.setParameter("struts_action", "/language/view");
	displayPreferredLanguageURL.setParameter("redirect", currentURL);
	displayPreferredLanguageURL.setParameter("languageId", user.getLanguageId());
	displayPreferredLanguageURL.setParameter("persistState", Boolean.FALSE.toString());
	displayPreferredLanguageURL.setPortletMode(PortletMode.VIEW);
	displayPreferredLanguageURL.setWindowState(WindowState.NORMAL);

	String displayPreferredLanguageURLString = displayPreferredLanguageURL.toString();

	displayPreferredLanguageURLString = HttpUtil.addParameter(displayPreferredLanguageURLString, "showUserLocaleOptionsMessage", false);
	%>

	<liferay-util:buffer var="displayPreferredLanguage">
		<aui:a href="<%= displayPreferredLanguageURLString %>">
			<%= LanguageUtil.format(userLocale, "display-the-page-in-x", userLocale.getDisplayName(userLocale)) %>
		</aui:a>
	</liferay-util:buffer>

	<%
	PortletURL changePreferredLanguageURL = new PortletURLImpl(request, PortletKeys.LANGUAGE, plid, PortletRequest.ACTION_PHASE);

	changePreferredLanguageURL.setParameter("struts_action", "/language/view");
	changePreferredLanguageURL.setParameter("redirect", currentURL);
	changePreferredLanguageURL.setParameter("languageId", themeDisplay.getLanguageId());
	changePreferredLanguageURL.setPortletMode(PortletMode.VIEW);
	changePreferredLanguageURL.setWindowState(WindowState.NORMAL);

	String changePreferredLanguageURLString = changePreferredLanguageURL.toString();

	changePreferredLanguageURLString = HttpUtil.addParameter(changePreferredLanguageURLString, "showUserLocaleOptionsMessage", false);

	String currentLanguageDisplayName = curLocale.getDisplayName(userLocale);
	%>

	<liferay-util:buffer var="changePreferredLanguage">
		<aui:a href="<%= changePreferredLanguageURLString %>">
			<%= LanguageUtil.format(userLocale, "set-x-as-your-preferred-language", currentLanguageDisplayName) %>
		</aui:a>
	</liferay-util:buffer>

	<%= LanguageUtil.format(userLocale, "this-page-is-being-displayed-in-x", new Object[] {currentLanguageDisplayName, displayPreferredLanguage, changePreferredLanguage}) %>
</c:if>