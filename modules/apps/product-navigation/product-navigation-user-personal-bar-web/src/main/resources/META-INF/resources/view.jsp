<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<span class="user-avatar-link">
			<liferay-product-navigation:personal-menu
				size="lg"
				user="<%= user %>"
			/>

			<%
			int notificationsCount = GetterUtil.getInteger(request.getAttribute(ProductNavigationUserPersonalBarWebKeys.NOTIFICATIONS_COUNT));
			%>

			<c:if test="<%= notificationsCount > 0 %>">

				<%
				String notificaitonsPortletId = PortletProviderUtil.getPortletId(UserNotificationEvent.class.getName(), PortletProvider.Action.VIEW);

				String notificationsURL = PersonalApplicationURLUtil.getPersonalApplicationURL(request, notificaitonsPortletId);
				%>

				<a aria-label="<%= notificationsCount + StringPool.SPACE + LanguageUtil.get(request, "new-notification") %>" class="panel-notifications-count" data-qa-id="notificationsCount" href="<%= (notificationsURL != null) ? notificationsURL : null %>" title="<%= notificationsCount + StringPool.SPACE + LanguageUtil.get(request, "new-notification") %>">
					<clay:badge
						displayType="danger"
						label="<%= String.valueOf(notificationsCount) %>"
					/>
				</a>
			</c:if>
		</span>
	</c:when>
	<c:when test="<%= themeDisplay.isShowSignInIcon() %>">
		<span class="sign-in text-default" role="presentation">
			<clay:button
				additionalProps='<%=
					HashMapBuilder.<String, Object>put(
						"redirect", PortalUtil.isLoginRedirectRequired(request)
					).put(
						"signInURL", themeDisplay.getURLSignIn()
					).build()
				%>'
				cssClass="sign-in text-default"
				displayType="unstyled"
				icon="user"
				label="sign-in"
				propsTransformer="{signInButtonPropsTransformer} from product-navigation-user-personal-bar-web"
				small="<%= true %>"
			/>
		</span>
	</c:when>
</c:choose>