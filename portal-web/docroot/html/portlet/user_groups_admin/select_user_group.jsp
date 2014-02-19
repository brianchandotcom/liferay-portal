<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/user_groups_admin/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectUserGroup");

User selUser = PortalUtil.getSelectedUser(request);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/user_groups_admin/select_user_group");

if (selUser != null) {
	portletURL.setParameter("p_u_i_d", String.valueOf(selUser.getUserId()));
}

portletURL.setParameter("eventName", eventName);
%>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectUserGroupFm">
	<liferay-ui:header
		title="user-groups"
	/>

	<liferay-ui:search-container
		searchContainer="<%= new UserGroupSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/user_groups_admin/user_group_search.jsp"
		/>

		<%
		UserGroupDisplayTerms searchTerms = (UserGroupDisplayTerms)searchContainer.getSearchTerms();
		%>

		<liferay-ui:search-container-results>

			<%
			if (filterManageableUserGroups) {
				List<UserGroup> userGroups = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, searchContainer.getOrderByComparator());

				userGroups = UsersAdminUtil.filterUserGroups(permissionChecker, userGroups);

				searchContainer.setTotal(userGroups.size());

				results = ListUtil.subList(userGroups, searchContainer.getStart(), searchContainer.getEnd());
			}
			else {
				total = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getKeywords(), null);

				searchContainer.setTotal(total);

				results = UserGroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getKeywords(), null, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
			}

			searchContainer.setResults(results);
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.UserGroup"
			escapedModel="<%= false %>"
			keyProperty="userGroupId"
			modelVar="userGroup"
		>
			<liferay-ui:search-container-column-text
				name="name"
				value="<%= HtmlUtil.escape(userGroup.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(userGroup.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-text>
				<c:if test="<%= (UserGroupMembershipPolicyUtil.isMembershipAllowed((selUser != null) ? selUser.getUserId() : 0, userGroup.getUserGroupId())) %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("usergroupid", userGroup.getUserGroupId());
					data.put("usergroupname", userGroup.getName());

					boolean disabled = false;

					for (UserGroup curUserGroup : selUser.getUserGroups()) {
						if (curUserGroup.getUserGroupId() == userGroup.getUserGroupId()) {
							disabled = true;

							break;
						}
					}
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= disabled %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	var openingLiferay = Util.getOpener().Liferay;

	openingLiferay.fire(
		'<portlet:namespace />enableRemovedUserGroups',
		{
			selectors: A.all('.selector-button:disabled')
		}
	);

	Util.selectEntityHandler('#<portlet:namespace />selectUserGroupFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>