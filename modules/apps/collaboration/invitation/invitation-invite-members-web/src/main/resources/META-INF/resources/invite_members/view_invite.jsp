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

			<label><liferay-ui:message key="members-to-invite" /><liferay-ui:icon-help message="to-add,-click-members-on-the-top-list" /></label>

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

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" value="send-invitations" />
		</aui:button-row>
	</aui:form>
</div>

<aui:script use="aui-base,datasource-io,datatype-number,liferay-so-invite-members">
	new Liferay.InviteMembers(
		{
			form: {
				method: 'POST',
				node: A.one(document.<portlet:namespace />fm)
			},
			namespace: '<portlet:namespace />',
			rootNode: '#<portlet:namespace/>inviteMembersContainer'
		}
	);

	var inviteMembersContainer = A.one('#<portlet:namespace />inviteMembersContainer');

	var invitedMembersList = inviteMembersContainer.one('#<portlet:namespace />invitedMembersList');
	var membersList = inviteMembersContainer.one('#<portlet:namespace />membersList');

	var pageDelta = 50;

	var createDataSource = function(url) {
		return new A.DataSource.IO(
			{
				ioConfig: {
					method: "post"
				},
				on: {
					request: function(event) {
						var data = event.request;

						event.cfg.data = {
							<portlet:namespace />end: data.<portlet:namespace />end || pageDelta,
							<portlet:namespace />keywords: data.<portlet:namespace />keywords || '',
							<portlet:namespace />start: data.<portlet:namespace />start || 0
						}
					}
				},
				source: url
			}
		);
	}

	var inviteMembersList = new Liferay.InviteMembersList(
		{
			inputNode: '#<portlet:namespace />inviteMembersContainer #<portlet:namespace />inviteUserSearch',
			listNode: '#<portlet:namespace />inviteMembersContainer #<portlet:namespace />membersList',
			minQueryLength: 0,
			requestTemplate: function(query) {
				return {
					<portlet:namespace />end: pageDelta,
					<portlet:namespace />keywords: query,
					<portlet:namespace />start: 0
				}
			},
			resultTextLocator: function(response) {
				var result = '';

				if (typeof response.toString != 'undefined') {
					result = response.toString();
				}
				else if (typeof response.responseText != 'undefined') {
					result = response.responseText;
				}

				return result;
			},
			source: createDataSource('<portlet:resourceURL id="getAvailableUsers" />')
		}
	);

	var renderResults = function(responseData) {
		var count = responseData.count;
		var options = responseData.options;
		var results = responseData.users;

		var buffer = [];

		if (results.length == 0) {
			if (options.start == 0) {
				buffer.push(
					'<small class="text-capitalize text-muted"><liferay-ui:message key="there-are-no-users-to-invite" /></small>'
				);
			}
		}
		else {
			buffer.push(
				A.Array.map(
					results,
					function(result) {
						var userTemplate =
							'<div class="{cssClass}" data-userId="{userId}">' +
								'<span class="name">{userFullName}</span>'+
								'<span class="email">{userEmailAddress}</span>' +
							'</div>';

						var invited = invitedMembersList.one('[data-userId="' + result.userId + '"]');

						return A.Lang.sub(
							userTemplate,
							{
								cssClass: result.hasPendingMemberRequest ? "pending-member-request user" : (invited ? "invited user" : "user"),
								userEmailAddress: result.userEmailAddress,
								userFullName: result.userFullName,
								userId: result.userId
							}
						);
					}
				).join('')
			);

			if (count > results.length) {
				buffer.push(
					'<div class="more-results">' +
						'<a href="javascript:;" data-end="' + options.end + '"><liferay-ui:message key="view-more" unicode="<%= true %>" /></a>' +
					'</div>'
				);
			}
		}

		return buffer;
	}

	inviteMembersList.on(
		'results',
		function(event) {
			var responseData = A.JSON.parse(event.data.responseText);

			membersList.html(renderResults(responseData).join(''));
		}
	);

	membersList.delegate(
		'click',
		function(event) {
			var node = event.currentTarget;

			var start = A.DataType.Number.parse(node.getAttribute('data-end'));

			var end = start + pageDelta;

			var inviteUserSearch = inviteMembersContainer.one('#<portlet:namespace />inviteUserSearch');

			A.io.request(
				'<portlet:resourceURL id="getAvailableUsers" />',
				{
					after: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							var moreResults = membersList.one('.more-results');

							moreResults.remove();

							membersList.append(renderResults(responseData).join(''));
						}
					},
					data: {
						<portlet:namespace />end: end,
						<portlet:namespace />keywords: inviteUserSearch.get('value'),
						<portlet:namespace />start: start
					},
					dataType: 'json'
				}
			);
		},
		'.more-results a'
	);

	inviteMembersList.sendRequest();
</aui:script>