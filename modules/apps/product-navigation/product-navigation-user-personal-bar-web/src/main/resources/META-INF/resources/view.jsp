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

<%@ include file="/init.jsp" %>

<liferay-util:buffer var="userName">
	<c:if test="<%= themeDisplay.isImpersonated() %>">
		<b class="alert-icon icon-warning-sign"></b>
	</c:if>

	<span class="user-avatar-image">
		<liferay-ui:user-portrait
			imageCssClass="user-icon-lg"
			userId="<%= user.getUserId() %>"
		/>
	</span>

	<span class="user-full-name">
		<%= HtmlUtil.escape(user.getFullName()) %>
	</span>
</liferay-util:buffer>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<aui:nav-item anchorCssClass="user-avatar-link" cssClass='<%= "portlet-user-personal-bar " + (themeDisplay.isImpersonated() ? "user-avatar impersonating-user" : "user-avatar") %>' dropdown="<%= true %>" id="userAvatar" label="<%= userName %>" toggleTouch="<%= false %>">
			<c:if test="<%= themeDisplay.isImpersonated() %>">

				<%
				String impersonatingUserLabel = "you-are-impersonating-the-guest-user";

				if (themeDisplay.isSignedIn()) {
					impersonatingUserLabel = LanguageUtil.format(request, "you-are-impersonating-x", HtmlUtil.escape(user.getFullName()), false);
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
						changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-x's-preferred-language-(x)", new String[] {HtmlUtil.escape(user.getFullName()), userLocale.getDisplayLanguage(realUserLocale)}, false);
					}
					else {
						doAsUserLanguageId = realUserLocale.getLanguage() + "_" + realUserLocale.getCountry();
						changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-your-preferred-language-(x)", realUserLocale.getDisplayLanguage(realUserLocale), false);
					}
					%>

					<aui:nav-item cssClass="current-user-language" href='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsUserLanguageId", doAsUserLanguageId) %>' label="<%= changeLanguageMessage %>" />
				</c:if>
			</c:if>
			<c:if test="<%= (user.isSetupComplete() || themeDisplay.isImpersonated()) && (themeDisplay.getURLMyAccount() != null) %>">

				<%
				List<Group> mySiteGroups = user.getMySiteGroups(new String[] {User.class.getName()}, QueryUtil.ALL_POS);

				for (Group mySiteGroup : mySiteGroups) {
				%>

					<c:if test="<%= mySiteGroup.getPublicLayoutsPageCount() > 0 %>">
						<li class="my-sites-menu public-site">
							<a href="<%= HtmlUtil.escapeHREF(mySiteGroup.getDisplayURL(themeDisplay, false)) %>" role="menuitem">
								<span class="my-profile"><liferay-ui:message key="my-profile" /></span>

								<span class="badge site-type"><liferay-ui:message key="public" /></span>
							</a>
						</li>
					</c:if>

					<c:if test="<%= mySiteGroup.getPrivateLayoutsPageCount() > 0 %>">
						<li class="my-sites-menu private-site">
							<a href="<%= HtmlUtil.escapeHREF(mySiteGroup.getDisplayURL(themeDisplay, true)) %>" role="menuitem">
								<span class="my-dashboard"><liferay-ui:message key="my-dashboard" /></span>

								<span class="badge site-type"><liferay-ui:message key="private" /></span>
							</a>
						</li>
					</c:if>

				<%
				}
				%>

				<%
				String myAccountURL = themeDisplay.getURLMyAccount().toString();
				%>

				<aui:nav-item href="<%= myAccountURL %>" iconCssClass="icon-user" label="my-account" title="my-account" />
			</c:if>

			<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
				<aui:nav-item cssClass="sign-out" href="<%= themeDisplay.getURLSignOut() %>" iconCssClass="icon-off" label="sign-out" />
			</c:if>
		</aui:nav-item>
	</c:when>
	<c:otherwise>

		<%
		Map<String, String> anchorData = new HashMap<String, String>();

		anchorData.put("redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request)));
		%>

		<aui:nav-item anchorData="<%= anchorData %>" cssClass="sign-in" href="<%= themeDisplay.getURLSignIn() %>" iconCssClass="icon-user" label="sign-in" />
	</c:otherwise>
</c:choose>