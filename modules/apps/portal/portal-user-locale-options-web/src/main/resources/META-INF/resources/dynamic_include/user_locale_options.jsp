<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<c:if test="<%= LanguageUtil.isAvailableLocale(themeDisplay.getSiteGroupId(), user.getLocale()) || (PortalUtil.isGroupControlPanelPath(themeDisplay.getURLCurrent()) && LanguageUtil.isAvailableLocale(userLocale)) %>">
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