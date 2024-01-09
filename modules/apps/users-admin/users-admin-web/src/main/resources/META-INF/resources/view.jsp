<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String viewUsersRedirect = ParamUtil.getString(request, "viewUsersRedirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

String usersListView = (String)request.getAttribute("view.jsp-usersListView");

if (Validator.isNull(usersListView)) {
	usersListView = ParamUtil.get(request, "usersListView", UserConstants.LIST_VIEW_FLAT_USERS);
}

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setParameter(
	"screenNavigationCategoryKey", UserScreenNavigationEntryConstants.CATEGORY_KEY_USERS
).setParameter(
	"usersListView", usersListView
).setParameter(
	"viewUsersRedirect",
	() -> {
		if (Validator.isNull(viewUsersRedirect)) {
			return null;
		}

		return viewUsersRedirect;
	}
).buildPortletURL();

request.setAttribute("view.jsp-portletURL", portletURL);

request.setAttribute("view.jsp-usersListView", usersListView);

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<liferay-ui:error exception="<%= CompanyMaxUsersException.class %>" message="unable-to-activate-user-because-that-would-exceed-the-maximum-number-of-users-allowed" />

<c:choose>
	<c:when test="<%= portletName.equals(UsersAdminPortletKeys.MY_ORGANIZATIONS) %>">
		<liferay-util:include page="/view_flat_organizations.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test="<%= portletName.equals(UsersAdminPortletKeys.SERVICE_ACCOUNTS) %>">

		<%
		request.setAttribute("view.jsp-backURL", backURL);
		request.setAttribute("view.jsp-status", ParamUtil.getInteger(request, "status", WorkflowConstants.STATUS_APPROVED));
		request.setAttribute("view.jsp-usersListView", usersListView);
		request.setAttribute("view.jsp-viewUsersRedirect", viewUsersRedirect);
		%>

		<liferay-util:include page="/view_flat_users.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<c:if test="<%= !portletName.equals(UsersAdminPortletKeys.SERVICE_ACCOUNTS) && !usersListView.equals(UserConstants.LIST_VIEW_TREE) %>">
			<liferay-frontend:screen-navigation
				key="<%= UserScreenNavigationEntryConstants.SCREEN_NAVIGATION_KEY_USERS_AND_ORGANIZATIONS %>"
				portletURL="<%= currentURLObj %>"
			/>
		</c:if>
	</c:otherwise>
</c:choose>

<%
usersListView = (String)request.getAttribute("view.jsp-usersListView");

if (!usersListView.equals(UserConstants.LIST_VIEW_FLAT_USERS)) {
	portletDisplay.setShowExportImportIcon(true);
}
else {
	portletDisplay.setShowExportImportIcon(false);
}
%>