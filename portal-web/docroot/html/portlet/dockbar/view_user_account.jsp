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

<%
String useDialog = StringPool.BLANK;

if (PropsValues.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP) {
	useDialog = StringPool.SPACE + "use-dialog";
}
%>

<liferay-util:buffer var="userName">
	<c:if test="<%= themeDisplay.isImpersonated() %>">
		<b class="alert-icon icon-warning-sign"></b>
	</c:if>

	<img alt="<liferay-ui:message key="manage-my-account" />" src="<%= HtmlUtil.escape(user.getPortraitURL(themeDisplay)) %>" />

	<span class="user-full-name">
		<%= HtmlUtil.escape(user.getFullName()) %>
	</span>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<aui:nav-item anchorCssClass="user-avatar-link" cssClass='<%= themeDisplay.isImpersonated() ? "user-avatar impersonating-user" : "user-avatar" %>' dropdown="<%= true %>" id="userAvatar" label="<%= userName %>">
			<c:choose>
				<c:when test="<%= themeDisplay.isImpersonated() %>">

					<%
					String impersonatingUserLabel = "you-are-impersonating-the-guest-user";

					if (themeDisplay.isSignedIn()) {
						impersonatingUserLabel = LanguageUtil.format(pageContext, "you-are-impersonating-x", new Object[] {HtmlUtil.escape(user.getFullName())});
					}
					%>

					<div class="alert alert-info"><%= impersonatingUserLabel %></div>

					<liferay-util:buffer var="leaveImpersonationLabel">
						<liferay-ui:message key="be-yourself-again" /> (<%= HtmlUtil.escape(realUser.getFullName()) %>)
					</liferay-util:buffer>

					<aui:nav-item href="<%= PortalUtil.getLayoutURL(layout, themeDisplay, false) %>" label="<%= leaveImpersonationLabel %>" />

					<%
					Locale realUserLocale = realUser.getLocale();
					Locale userLocale = user.getLocale();
					%>

					<c:if test="<%= !realUserLocale.equals(userLocale) %>">

						<%
						String doAsUserLanguageId = null;
						String changeLanguageMessage = null;

						if (locale.getLanguage().equals(realUserLocale.getLanguage()) && locale.getCountry().equals(realUserLocale.getCountry())) {
							doAsUserLanguageId = userLocale.getLanguage() + "_" + userLocale.getCountry();
							changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-x's-preferred-language-(x)", new String[] {HtmlUtil.escape(user.getFullName()), userLocale.getDisplayLanguage(realUserLocale)});
						}
						else {
							doAsUserLanguageId = realUserLocale.getLanguage() + "_" + realUserLocale.getCountry();
							changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-your-preferred-language-(x)", realUserLocale.getDisplayLanguage(realUserLocale));
						}
						%>

						<aui:nav-item cssClass="current-user-language" href='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsUserLanguageId", doAsUserLanguageId) %>' label="<%= changeLanguageMessage %>" />
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="<%= Validator.isNotNull(themeDisplay.getURLMyAccount()) %>">

						<%
						String myAccountURL = themeDisplay.getURLMyAccount().toString();

						myAccountURL = HttpUtil.setParameter(myAccountURL, "controlPanelCategory", PortletCategoryKeys.MY);
						%>

						<aui:nav-item anchorCssClass='<%= themeDisplay.isImpersonated() ? "" : useDialog %>' href="<%= myAccountURL %>" label="my-account" title="manage-my-account" />
					</c:if>

					<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
						<aui:nav-item cssClass="sign-out" href="<%= themeDisplay.getURLSignOut() %>" label="sign-out" />
					</c:if>
				</c:otherwise>
			</c:choose>
		</aui:nav-item>
	</c:when>
	<c:otherwise>
		<aui:nav-item cssClass="sign-in" href="<%= themeDisplay.getURLSignIn() %>" label="sign-in" />
	</c:otherwise>
</c:choose>