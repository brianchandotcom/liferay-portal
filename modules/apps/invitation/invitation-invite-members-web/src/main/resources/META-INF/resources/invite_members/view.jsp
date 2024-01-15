<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);
%>

<c:choose>
	<c:when test="<%= group.isUser() %>">
		<liferay-ui:message key="this-application-will-only-function-when-placed-on-a-site-page" />
	</c:when>
	<c:when test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.UPDATE) %>">

		<%
		InviteMembersDisplayContext inviteMembersDisplayContext = new InviteMembersDisplayContext(renderRequest, renderResponse);
		%>

		<react:component
			module="{InviteMembers} from invitation-invite-members-web"
			props="<%= inviteMembersDisplayContext.getInviteMembersProps() %>"
		/>
	</c:when>
	<c:otherwise>
		<aui:script>
			var portlet = document.getElementById('p_p_id<portlet:namespace />');

			if (portlet) {
				portlet.classList.add('hide');
			}
		</aui:script>
	</c:otherwise>
</c:choose>