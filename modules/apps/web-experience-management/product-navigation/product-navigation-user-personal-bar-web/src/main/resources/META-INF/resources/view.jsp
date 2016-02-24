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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<liferay-util:buffer var="userName">
			<c:if test="<%= themeDisplay.isImpersonated() %>">
				<aui:icon image="asterisk" markupView="lexicon" />
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

			<%
			int notificationsCount = GetterUtil.getInteger(request.getAttribute(ProductNavigationUserPersonalBarWebKeys.NOTIFICATIONS_COUNT));
			%>

			<c:if test="<%= notificationsCount > 0 %>">
				<span class="panel-notifications-count sticker sticker-right sticker-rounded sticker-sm sticker-warning"><%= notificationsCount %></span>
			</c:if>
		</liferay-util:buffer>

		<%
		String displayStyle = ParamUtil.getString(request, "displayStyle", "button");
		%>

		<c:choose>
			<c:when test='<%= Validator.equals(displayStyle, "button") %>'>
				<span class="user-avatar-link">
					<a class="text-default" data-qa-id="openUserMenu" href="javascript:;" id="<portlet:namespace />sidenavUserToggle">
						<%= userName %>
					</a>
				</span>

				<aui:script sandbox="<%= true %>">
					var sidenavUserToggle = $('#<portlet:namespace />sidenavUserToggle');

					sidenavUserToggle.on(
						'click',
						function(event) {
							Liferay.fire('ProductMenu:openUserMenu');
						}
					);
				</aui:script>
			</c:when>
			<c:otherwise>
				<aui:nav-item anchorCssClass="user-avatar-link" cssClass='<%= "portlet-user-personal-bar " + (themeDisplay.isImpersonated() ? "user-avatar impersonating-user" : "user-avatar") %>' dropdown="<%= true %>" id="userAvatar" label="<%= userName %>" toggleTouch="<%= false %>">
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
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>

		<%
		Map<String, Object> anchorData = new HashMap<String, Object>();

		anchorData.put("redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request)));
		%>

		<span class="sign-in text-default" role="presentation">
			<aui:a cssClass="sign-in text-default" data="<%= anchorData %>" href="<%= themeDisplay.getURLSignIn() %>" iconCssClass="icon-user" label="sign-in" />
		</span>
	</c:otherwise>
</c:choose>