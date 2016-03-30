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
Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);
%>

<c:choose>
	<c:when test="<%= group.isUser() %>">
		<liferay-ui:message key="this-application-will-only-function-when-placed-on-a-site-page" />
	</c:when>
	<c:when test="<%= GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="inviteURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/invite_members/view_invite.jsp" />
		</portlet:renderURL>

		<aui:a cssClass="btn btn-default" href="javascript:;" id="inviteMembersButton" label="invite-members-to-this-site" />

		<aui:script position="inline" use="aui-base">
			var inviteMembersButton = A.one('#<portlet:namespace />inviteMembersButton');

			inviteMembersButton.on(
				'click',
				function() {
					Liferay.Util.openWindow(
						{
							dialog: {
								cssClass: 'so-portlet-invite-members',
								destroyOnHide: true,
								width: 700
							},
							dialogIframe: {
								bodyCssClass: 'dialog-with-footer'
							},
							title: '<%= portletDisplay.getTitle() %>',
							uri: '<%= HtmlUtil.escapeJS(inviteURL) %>'
						}
					);
				}
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script use="aui-base">
			var portlet = A.one('#p_p_id<portlet:namespace />');

			if (portlet) {
				portlet.hide();
			}
		</aui:script>
	</c:otherwise>
</c:choose>