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

<%@ include file="/dynamic_include/init.jsp" %>

<%
Locale userLocale = user.getLocale();
%>

<liferay-util:buffer
	var="alertMessage"
>
	<div dir="<%= LanguageUtil.get(userLocale, "lang.dir") %>">
		<div class="d-block">
			<%= LanguageUtil.format(userLocale, "this-page-is-displayed-in-x", locale.getDisplayName(userLocale)) %>
		</div>

		<c:if test="<%= LanguageUtil.isAvailableLocale(themeDisplay.getSiteGroupId(), user.getLocale()) %>">
			<aui:a cssClass="d-block" href='<%= themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + URLCodec.encodeURL(themeDisplay.getURLCurrent()) + "&languageId=" + user.getLanguageId() + "&persistState=false&showUserLocaleOptionsMessage=false" %>'>
				<%= LanguageUtil.format(userLocale, "display-the-page-in-x", userLocale.getDisplayName(userLocale)) %>
			</aui:a>
		</c:if>
	</div>

	<div dir="<%= LanguageUtil.get(request, "lang.dir") %>">
		<aui:a cssClass="d-block" href='<%= themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + URLCodec.encodeURL(themeDisplay.getURLCurrent()) + "&languageId=" + themeDisplay.getLanguageId() + "&showUserLocaleOptionsMessage=false" %>'>
			<%= LanguageUtil.format(locale, "set-x-as-your-preferred-language", locale.getDisplayName(locale)) %>
		</aui:a>
	</div>
</liferay-util:buffer>

<aui:script>
	Liferay.Util.openToast({
		message: '<%= HtmlUtil.escapeJS(alertMessage) %>',
		onClose: function (data) {
			if (data.event) {
				Liferay.Util.Session.set(
					'com.liferay.portal.user.locale.options.web_ignoreUserLocaleOptions',
					true,
					{
						useHttpSession: true,
					}
				);
			}
		},
		type: 'info',
	});
</aui:script>