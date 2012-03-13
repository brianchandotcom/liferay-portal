<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
String tabs3 = (String)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-tabs3");

portletResource = (String)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-portletResource");
String modelResource = (String)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-modelResource");
Group group = (Group)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-group");
long groupId = (Long)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-groupId");
Resource resource = (Resource)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-resource");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-portletURL");

String userIds = ParamUtil.getString(request, "userIds");
long[] userIdsArray = StringUtil.split(userIds, 0L);
int userIdsPos = ParamUtil.getInteger(request, "userIdsPos");
%>

<aui:input name="userIds" type="hidden" value="<%= userIds %>" />
<aui:input name="userIdsPos" type="hidden" value="<%= userIdsPos %>" />
<aui:input name="userIdsPosValue" type="hidden" />
<aui:input name="userIdActionIds" type="hidden" />

<c:choose>
	<c:when test="<%= userIdsArray.length == 0 %>">
		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:search-container
			id="usersSearchContainer"
			rowChecker="<%= new RowChecker(renderResponse) %>"
			searchContainer="<%= new UserSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/users_admin/user_search.jsp"
			/>

			<%
			UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

			LinkedHashMap userParams = new LinkedHashMap();

			if (tabs3.equals("current")) {
				userParams.put("permission", new Long(resource.getResourceId()));
			}
			else if (tabs3.equals("available")) {
				if (group.isOrganization()) {
					userParams.put("usersOrgs", new Long(group.getOrganizationId()));
				}
				else if (group.isRegularSite()) {
					userParams.put("usersGroups", new Long(groupId));
				}
			}
			%>

			<liferay-ui:search-container-results>
				<%@ include file="/html/portlet/users_admin/user_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.User"
				escapedModel="<%= true %>"
				keyProperty="userId"
				modelVar="user2"
			>
				<liferay-ui:search-container-column-text
					name="name"
					property="fullName"
				/>

				<liferay-ui:search-container-column-text
					name="screen-name"
					orderable="<%= true %>"
					property="screenName"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="permissions"
				>

					<%
					List permissions = PermissionLocalServiceUtil.getUserPermissions(user2.getUserId(), resource.getResourceId());

					List actions = ResourceActionsUtil.getActions(permissions);
					List actionsNames = ResourceActionsUtil.getActionsNames(pageContext, actions);

					buffer.append(StringUtil.merge(actionsNames, ", "));
					%>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<div class="separator"><!-- --></div>

			<aui:button onClick='<%= renderResponse.getNamespace() + "updateUserPermissions();" %>' value="update-permissions" />

			<br /><br />

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:otherwise>

		<%
		User user2 = UserLocalServiceUtil.getUserById(userIdsArray[userIdsPos]);
		%>

		<liferay-ui:header
			localizeTitle="<%= false %>"
			title="<%= user2.getFullName() %>"
		/>

		<%
		List permissions = PermissionLocalServiceUtil.getUserPermissions(user2.getUserId(), resource.getResourceId());

		List actions1 = ResourceActionsUtil.getResourceActions(portletResource, modelResource);
		List actions2 = ResourceActionsUtil.getActions(permissions);

		String leftTitle = "what-he-can-do";
		String rightTitle = "what-he-cant-do";

		if (user2.isFemale()) {
			leftTitle = "what-she-can-do";
			rightTitle = "what-she-cant-do";
		}

		// Left list

		List leftList = new ArrayList();

		for (int i = 0; i < actions2.size(); i++) {
			String actionId = (String)actions2.get(i);

			leftList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
		}

		leftList = ListUtil.sort(leftList, new KeyValuePairComparator(false, true));

		// Right list

		List rightList = new ArrayList();

		for (int i = 0; i < actions1.size(); i++) {
			String actionId = (String)actions1.get(i);

			if (!actions2.contains(actionId)) {
				rightList.add(new KeyValuePair(actionId, ResourceActionsUtil.getAction(pageContext, actionId)));
			}
		}

		rightList = ListUtil.sort(rightList, new KeyValuePairComparator(false, true));
		%>

		<div class="assign-permissions">
			<liferay-ui:input-move-boxes
				leftBoxName="current_actions"
				leftList="<%= leftList %>"
				leftTitle="<%= leftTitle %>"
				rightBoxName="available_actions"
				rightList="<%= rightList %>"
				rightTitle="<%= rightTitle %>"
			/>

			<aui:button-row>

				<%
				String taglibPreviousOnClick = renderResponse.getNamespace() + "saveUserPermissions(" + (userIdsPos - 1) + ", '" + userIdsArray[userIdsPos] + "');";
				String taglibNextOnClick = renderResponse.getNamespace() + "saveUserPermissions(" + (userIdsPos + 1) + ", '" + userIdsArray[userIdsPos] + "');";
				String taglibFinishedOnClick = renderResponse.getNamespace() + "saveUserPermissions(-1, '"+ userIdsArray[userIdsPos] + "');";
				%>

				<aui:button cssClass="previous" disabled="<%= userIdsPos <= 0 %>" onClick='<%= taglibPreviousOnClick %>' value="previous" />

				<aui:button cssClass="next" disabled="<%= userIdsPos + 1 >= userIdsArray.length %>" onClick='<%= taglibNextOnClick %>' value="next" />

				<aui:button cssClass="finished" onClick="<%= taglibFinishedOnClick %>" value="finished" />
			</aui:button-row>
		</div>
	</c:otherwise>
</c:choose>