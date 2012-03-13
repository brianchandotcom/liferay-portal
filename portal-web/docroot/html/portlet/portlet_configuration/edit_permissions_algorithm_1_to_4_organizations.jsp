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
long groupId = (Long)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-groupId");
Resource resource = (Resource)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-resource");

PortletURL portletURL = (PortletURL)request.getAttribute("edit_permissions_algorithm_1_to_4.jsp-portletURL");

String organizationIds = ParamUtil.getString(request, "organizationIds");
long[] organizationIdsArray = StringUtil.split(organizationIds, 0L);
int organizationIdsPos = ParamUtil.getInteger(request, "organizationIdsPos");
%>

<aui:input name="organizationIds" type="hidden" value="<%= organizationIds %>" />
<aui:input name="organizationIdsPos" type="hidden" value="<%= organizationIdsPos %>" />
<aui:input name="organizationIdsPosValue" type="hidden" />
<aui:input name="organizationIdActionIds" type="hidden" />

<c:choose>
	<c:when test="<%= organizationIdsArray.length == 0 %>">
		<liferay-ui:tabs
			names="current,available"
			param="tabs3"
			url="<%= portletURL.toString() %>"
		/>

		<liferay-ui:search-container
			id="organizationsSearchContainer"
			rowChecker="<%= new RowChecker(renderResponse) %>"
			searchContainer="<%= new OrganizationSearch(renderRequest, portletURL) %>"
		>
			<liferay-ui:search-form
				page="/html/portlet/users_admin/organization_search.jsp"
			/>

			<%
			OrganizationSearchTerms searchTerms = (OrganizationSearchTerms)searchContainer.getSearchTerms();

			long parentOrganizationId = OrganizationConstants.ANY_PARENT_ORGANIZATION_ID;

			LinkedHashMap organizationParams = new LinkedHashMap();

			if (tabs3.equals("current")) {
				organizationParams.put("permissionsResourceId", new Long(resource.getResourceId()));
				organizationParams.put("permissionsGroupId", new Long(groupId));
			}
			%>

			<liferay-ui:search-container-results>
				<%@ include file="/html/portlet/users_admin/organization_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.portal.model.Organization"
				escapedModel="<%= true %>"
				keyProperty="organizationId"
				modelVar="organization"
			>
				<liferay-ui:search-container-column-text
					name="name"
					orderable="<%= true %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="parent-organization"
				>

					<%
					if (organization.getParentOrganizationId() > 0) {
						try {
							Organization parentOrganization = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());

							buffer.append(HtmlUtil.escape(parentOrganization.getName()));
						}
						catch (Exception e) {
						}
					}
					%>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					name="type"
					orderable="<%= true %>"
					value="<%= LanguageUtil.get(pageContext, organization.getType()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="city"
					value="<%= HtmlUtil.escape(organization.getAddress().getCity()) %>"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
					name="permissions"
				>

					<%

					//boolean organizationIntersection = false;

					List permissions = PermissionLocalServiceUtil.getGroupPermissions(organization.getGroup().getGroupId(), resource.getResourceId());

					/*if (permissions.isEmpty()) {
						permissions = PermissionLocalServiceUtil.getOrgGroupPermissions(organization.getOrganizationId(), groupId, resource.getResourceId());

						if (!permissions.isEmpty()) {
							organizationIntersection = true;
						}
					}*/

					List actions = ResourceActionsUtil.getActions(permissions);
					List actionsNames = ResourceActionsUtil.getActionsNames(pageContext, actions);

					buffer.append(StringUtil.merge(actionsNames, ", "));

					/*if (permissions.isEmpty()) {
						row.addText(StringPool.BLANK);
					}
					else {
						row.addText(LanguageUtil.get(pageContext, (organizationIntersection ? "yes" : "no")));
					}*/
					%>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<div class="separator"><!-- --></div>

			<aui:button onClick='<%= renderResponse.getNamespace() + "updateOrganizationPermissions();" %>' value="update-permissions" />

			<br /><br />

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</c:when>
	<c:otherwise>

		<%
		Organization organization = OrganizationLocalServiceUtil.getOrganization(organizationIdsArray[organizationIdsPos]);
		%>

		<liferay-ui:header
			localizeTitle="<%= false %>"
			title="<%= organization.getName() %>"
		/>

		<%
		List permissions = PermissionLocalServiceUtil.getGroupPermissions(organization.getGroup().getGroupId(), resource.getResourceId());

		List actions1 = ResourceActionsUtil.getResourceActions(portletResource, modelResource);
		List actions2 = ResourceActionsUtil.getActions(permissions);

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
				leftTitle="what-they-can-do"
				rightBoxName="available_actions"
				rightList="<%= rightList %>"
				rightTitle="what-they-cant-do"
			/>

			<aui:button-row>

				<%
				String taglibPreviousOnClick = renderResponse.getNamespace() + "saveOrganizationPermissions(" + (organizationIdsPos - 1) + ", '" + organizationIdsArray[organizationIdsPos] + "');";
				String taglibNextOnClick = renderResponse.getNamespace() + "saveOrganizationPermissions(" + (organizationIdsPos + 1) + ", '" + organizationIdsArray[organizationIdsPos] + "');";
				String taglibFinishedOnClick = renderResponse.getNamespace() + "saveOrganizationPermissions(-1, '"+ organizationIdsArray[organizationIdsPos] + "');";
				%>

				<aui:button cssClass="previous" disabled="<%= organizationIdsPos <= 0 %>" onClick='<%= taglibPreviousOnClick %>' value="previous" />

				<aui:button cssClass="next" disabled="<%= organizationIdsPos + 1 >= organizationIdsArray.length %>" onClick='<%= taglibNextOnClick %>' value="next" />

				<aui:button cssClass="finished" onClick="<%= taglibFinishedOnClick %>" value="finished" />
			</aui:button-row>
		</div>

		<%--<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="assign-permissions-only-to-users-that-are-also-members-of-the-current-site" />
			</td>
			<td>
				<select name="<portlet:namespace />organizationIntersection">
					<option <%= organizationIntersection ? "selected" : "" %> value="1"><liferay-ui:message key="yes" /></option>
					<option <%= !organizationIntersection ? "selected" : "" %> value="0"><liferay-ui:message key="no" /></option>
				</select>
			</td>
		</tr>
		</table>

		<br />--%>
	</c:otherwise>
</c:choose>