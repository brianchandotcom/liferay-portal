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

<%
String redirect = currentURL;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AccountUserDisplay accountUserDisplay = (AccountUserDisplay)row.getObject();

long accountUserId = accountUserDisplay.getUserId();

User accountUser = UserLocalServiceUtil.getUser(accountUserId);
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= !PropsValues.PORTAL_JAAS_ENABLE && PropsValues.PORTAL_IMPERSONATION_ENABLE && (accountUserId != user.getUserId()) && !themeDisplay.isImpersonated() && UserPermissionUtil.contains(permissionChecker, accountUserId, ActionKeys.IMPERSONATE) %>">
		<liferay-security:doAsURL
			doAsUserId="<%= accountUserId %>"
			var="impersonateUserURL"
		/>

		<liferay-ui:icon
			message="impersonate-user"
			target="_blank"
			url="<%= impersonateUserURL %>"
		/>
	</c:if>

	<c:if test="<%= UserPermissionUtil.contains(permissionChecker, accountUserId, ActionKeys.DELETE) %>">
		<c:if test="<%= !accountUser.isActive() %>">
			<portlet:actionURL name="/account_admin/edit_account_user" var="restoreUserURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="accountEntriesNavigation" value='<%= ParamUtil.getString(request, "accountEntriesNavigation") %>' />
				<portlet:param name="accountUserIds" value="<%= String.valueOf(accountUserId) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="activate"
				url="<%= restoreUserURL %>"
			/>
		</c:if>

		<portlet:actionURL name="/account_admin/edit_account_user" var="deleteUserURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= accountUser.isActive() ? Constants.DEACTIVATE : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="accountEntriesNavigation" value='<%= ParamUtil.getString(request, "accountEntriesNavigation") %>' />
			<portlet:param name="accountUserIds" value="<%= String.valueOf(accountUserId) %>" />
		</portlet:actionURL>

		<c:if test="<%= accountUserId != user.getUserId() %>">
			<c:choose>
				<c:when test="<%= accountUser.isActive() %>">
					<liferay-ui:icon-deactivate
						url="<%= deleteUserURL %>"
					/>
				</c:when>
				<c:when test="<%= !accountUser.isActive() && PropsValues.USERS_DELETE %>">
					<liferay-ui:icon-delete
						url="<%= deleteUserURL %>"
					/>
				</c:when>
			</c:choose>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>