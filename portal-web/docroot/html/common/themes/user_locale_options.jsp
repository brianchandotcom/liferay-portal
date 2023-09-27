<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/common/themes/init.jsp" %>

<%
String currentURL = PortalUtil.getCurrentURL(request);
%>

<c:if test="<%= !locale.equals(user.getLocale()) %>">

	<%
	Locale userLocale = user.getLocale();
	%>

	<div dir="<%= LanguageUtil.get(userLocale, "lang.dir") %>">
		<div class="d-block">
			<button aria-label="<%= LanguageUtil.get(request, "close") %>" class="close" id="ignoreUserLocaleOptions" type="button">&times;</button>

			<%= LanguageUtil.format(userLocale, "this-page-is-displayed-in-x", locale.getDisplayName(userLocale)) %>
		</div>

		<c:if test="<%= LanguageUtil.isAvailableLocale(themeDisplay.getSiteGroupId(), user.getLocale()) %>">
			<aui:a cssClass="d-block" href='<%= themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + URLCodec.encodeURL(currentURL) + "&languageId=" + user.getLanguageId() + "&persistState=false&showUserLocaleOptionsMessage=false" %>'>
				<%= LanguageUtil.format(userLocale, "display-the-page-in-x", userLocale.getDisplayName(userLocale)) %>
			</aui:a>
		</c:if>
	</div>

	<div dir="<%= LanguageUtil.get(request, "lang.dir") %>">
		<aui:a cssClass="d-block" href='<%= themeDisplay.getPathMain() + "/portal/update_language?p_l_id=" + themeDisplay.getPlid() + "&redirect=" + URLCodec.encodeURL(currentURL) + "&languageId=" + themeDisplay.getLanguageId() + "&showUserLocaleOptionsMessage=false" %>'>
			<%= LanguageUtil.format(locale, "set-x-as-your-preferred-language", locale.getDisplayName(locale)) %>
		</aui:a>
	</div>

	<aui:script use="aui-base,liferay-store">
		var ignoreUserLocaleOptionsNode = A.one('#ignoreUserLocaleOptions');

		ignoreUserLocaleOptionsNode.on(
			'click',
			function() {
				Liferay.Util.Session.set('ignoreUserLocaleOptions', true, {
					useHttpSession: true
				});
			}
		);
	</aui:script>
</c:if>