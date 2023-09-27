<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

AccountUserDisplay accountUserDisplay = (AccountUserDisplay)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= AccountPermission.contains(permissionChecker, AccountPortletKeys.ACCOUNT_USERS_ADMIN, AccountActionKeys.ASSIGN_ACCOUNTS) || UserPermissionUtil.contains(permissionChecker, accountUserDisplay.getUserId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editUserURL">
			<portlet:param name="p_u_i_d" value="<%= String.valueOf(accountUserDisplay.getUserId()) %>" />
			<portlet:param name="mvcPath" value="/account_users_admin/edit_account_user.jsp" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editUserURL %>"
		/>
	</c:if>

	<c:if test="<%= !PropsValues.PORTAL_JAAS_ENABLE && PropsValues.PORTAL_IMPERSONATION_ENABLE && (accountUserDisplay.getUserId() != user.getUserId()) && !themeDisplay.isImpersonated() && UserPermissionUtil.contains(permissionChecker, accountUserDisplay.getUserId(), ActionKeys.IMPERSONATE) %>">
		<liferay-security:doAsURL
			doAsUserId="<%= accountUserDisplay.getUserId() %>"
			var="impersonateUserURL"
		/>

		<liferay-ui:icon
			message="impersonate-user"
			target="_blank"
			url="<%= impersonateUserURL %>"
		/>
	</c:if>

	<c:if test="<%= UserPermissionUtil.contains(permissionChecker, accountUserDisplay.getUserId(), ActionKeys.DELETE) %>">
		<c:if test="<%= accountUserDisplay.getStatus() == WorkflowConstants.STATUS_INACTIVE %>">
			<portlet:actionURL name="/account_admin/edit_account_users" var="restoreUserURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="accountEntriesNavigation" value='<%= ParamUtil.getString(request, "accountEntriesNavigation") %>' />
				<portlet:param name="accountUserIds" value="<%= String.valueOf(accountUserDisplay.getUserId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="activate"
				url="<%= restoreUserURL %>"
			/>
		</c:if>

		<portlet:actionURL name="/account_admin/edit_account_users" var="deleteUserURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= (accountUserDisplay.getStatus() == WorkflowConstants.STATUS_APPROVED) ? Constants.DEACTIVATE : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="accountEntriesNavigation" value='<%= ParamUtil.getString(request, "accountEntriesNavigation") %>' />
			<portlet:param name="accountUserIds" value="<%= String.valueOf(accountUserDisplay.getUserId()) %>" />
		</portlet:actionURL>

		<c:if test="<%= accountUserDisplay.getUserId() != user.getUserId() %>">
			<c:choose>
				<c:when test="<%= accountUserDisplay.getStatus() == WorkflowConstants.STATUS_APPROVED %>">
					<liferay-ui:icon-delete
						confirmation="are-you-sure-you-want-to-deactivate-this-user"
						message="deactivate"
						url="<%= deleteUserURL %>"
					/>
				</c:when>
				<c:when test="<%= (accountUserDisplay.getStatus() == WorkflowConstants.STATUS_INACTIVE) && PropsValues.USERS_DELETE %>">
					<liferay-ui:icon-delete
						confirmation="are-you-sure-you-want-to-delete-this-user"
						url="<%= deleteUserURL %>"
					/>
				</c:when>
			</c:choose>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>