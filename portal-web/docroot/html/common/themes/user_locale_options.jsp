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
%>

<c:if test="<%= !locale.equals(user.getLocale()) %>">

	<%
	Locale userLocale = user.getLocale();

	String userLocaleLanguageDir = LanguageUtil.get(userLocale, "lang.dir");
	%>

	<div dir="<%= userLocaleLanguageDir %>">
		<div class="d-block">
			<button aria-label="<%= LanguageUtil.get(request, "close") %>" class="close" id="ignoreUserLocaleOptions" type="button">&times;</button>

			<%= LanguageUtil.format(userLocale, "this-page-is-displayed-in-x", locale.getDisplayName(userLocale)) %>
		</div>

		<c:if test="<%= LanguageUtil.isAvailableLocale(themeDisplay.getSiteGroupId(), user.getLocale()) %>">

			<%
			String displayPreferredLanguageURLString = themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + URLCodec.encodeURL(currentURL) + "&languageId=" + user.getLanguageId() + "&persistState=false&showUserLocaleOptionsMessage=false";
			%>

			<aui:a cssClass="d-block" href="<%= displayPreferredLanguageURLString %>">
				<%= LanguageUtil.format(userLocale, "display-the-page-in-x", userLocale.getDisplayName(userLocale)) %>
			</aui:a>
		</c:if>
	</div>

	<%
	String requestLanguageDir = LanguageUtil.get(request, "lang.dir");
	%>

	<div dir="<%= requestLanguageDir %>">

		<%
		String changePreferredLanguageURLString = themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + URLCodec.encodeURL(currentURL) + "&languageId=" + themeDisplay.getLanguageId() + "&showUserLocaleOptionsMessage=false";
		%>

		<aui:a cssClass="d-block" href="<%= changePreferredLanguageURLString %>">
			<%= LanguageUtil.format(locale, "set-x-as-your-preferred-language", locale.getDisplayName(locale)) %>
		</aui:a>
	</div>

	<aui:script use="aui-base,liferay-store">
		var ignoreUserLocaleOptionsNode = A.one('#ignoreUserLocaleOptions');

		ignoreUserLocaleOptionsNode.on(
			'click',
			function() {
				Liferay.Util.Session.set('ignoreUserLocaleOptions', true);
				Liferay.Util.Session.set('useHttpSession', true);
			}
		);
	</aui:script>
</c:if>