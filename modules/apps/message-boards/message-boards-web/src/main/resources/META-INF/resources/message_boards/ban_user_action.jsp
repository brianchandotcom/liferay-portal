<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MBBan ban = (MBBan)row.getObject();
%>

<c:if test="<%= MBResourcePermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER) %>">
	<liferay-ui:icon-menu
		direction="left-side"
		icon="<%= StringPool.BLANK %>"
		markupView="lexicon"
		message="actions"
		showWhenSingleIcon="<%= true %>"
	>
		<portlet:actionURL name="/message_boards/ban_user" var="unbanUserURL">
			<portlet:param name="<%= Constants.CMD %>" value="unban" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="banUserId" value="<%= String.valueOf(ban.getBanUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="unban-this-user"
			url="<%= unbanUserURL %>"
		/>
	</liferay-ui:icon-menu>
</c:if>