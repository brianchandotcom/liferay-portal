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

<div class="container-fluid main-content-body" id="<portlet:namespace />inviteMembersContainer">
	<portlet:actionURL name="sendInvites" var="sendInvitesURL" />

	<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/invite_members/view_invite.jsp" />
	</portlet:renderURL>

	<aui:form action="<%= sendInvitesURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirectURL %>" />
		<aui:input name="groupId" type="hidden" value="<%= themeDisplay.getScopeGroupId() %>" />
		<aui:input name="receiverUserIds" type="hidden" value="" />
		<aui:input name="receiverEmailAddresses" type="hidden" value="" />
		<aui:input name="invitedRoleId" type="hidden" value="" />
		<aui:input name="invitedTeamId" type="hidden" value="" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<label><liferay-ui:message key="find-members" /></label>

				<small class="text-capitalize text-muted">
					<liferay-ui:icon
						cssClass="footnote"
						icon="check"
						label="<%= true %>"
						markupView="lexicon"
						message="previous-invitation-was-sent"
					/>
				</small>

				<aui:input id="inviteUserSearch" label="" name="userName" placeholder="search" />

				<div class="search" id="<portlet:namespace />membersList"></div>

				<label><liferay-ui:message key="email-addresses-to-send-invite" /><liferay-ui:icon-help message="to-add,-click-members-on-the-top-list" /></label>

				<div class="user-invited" id="<portlet:namespace />invitedMembersList"></div>

				<div class="button-holder controls">
					<aui:input label="invite-by-email" name="emailAddress" />

					<aui:button name="emailButton" value="add-email-address" />
				</div>

				<label><liferay-ui:message key="email-addresses-to-send-invite" /></label>

				<div class="email-invited" id="<portlet:namespace />invitedEmailList"></div>

				<%
				List<Role> roles = RoleLocalServiceUtil.search(layout.getCompanyId(), null, null, new Integer[] {RoleConstants.TYPE_SITE}, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new RoleNameComparator(false));

				roles = UsersAdminUtil.filterGroupRoles(permissionChecker, group.getGroupId(), roles);
				%>

				<c:if test="<%= !roles.isEmpty() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_USER_ROLES) %>">
					<div class="invite-to">
						<aui:select label="invite-to-role" name="roleId">
							<aui:option value="0" />

							<%
							for (Role role : roles) {
							%>

								<aui:option label="<%= HtmlUtil.escape(role.getTitle(locale)) %>" value="<%= role.getRoleId() %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</c:if>

				<%
				List<Team> teams = TeamLocalServiceUtil.getGroupTeams(group.getGroupId());
				%>

				<c:if test="<%= !teams.isEmpty() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_TEAMS) %>">
					<div class="invite-to">
						<aui:select label="invite-to-team" name="teamId">
							<aui:option value="0" />

							<%
							for (Team team : teams) {
							%>

								<aui:option label="<%= HtmlUtil.escape(team.getName()) %>" value="<%= team.getTeamId() %>" />

							<%
							}
							%>

						</aui:select>
					</div>
				</c:if>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="send-invitations" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script use="liferay-portlet-invite-members">
	var portletInviteMembers = new Liferay.Portlet.InviteMembers(
		{
			availableUsersURL: '<portlet:resourceURL id="getAvailableUsers" />',
			form: {
				method: 'POST',
				node: A.one(document.<portlet:namespace />fm)
			},
			namespace: '<portlet:namespace />',
			rootNode: '#<portlet:namespace />inviteMembersContainer'
		}
	);

	var destroyInstance = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			portletInviteMembers.destroy();

			Liferay.detach('destroyPortlet', destroyInstance);
		}
	};

	Liferay.on('destroyPortlet', destroyInstance);
</aui:script>