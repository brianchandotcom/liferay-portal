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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/view");

pageContext.setAttribute("portletURL", portletURL);
%>

<liferay-ui:success key="membership_request_sent" message="your-request-was-sent-you-will-receive-a-reply-by-email" />

<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-util:include page="/html/portlet/sites_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value="view-all" />
	</liferay-util:include>

	<liferay-ui:search-container
		searchContainer="<%= new GroupSearch(renderRequest, portletURL) %>"
	>

		<%
		GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

		LinkedHashMap groupParams = new LinkedHashMap();

		groupParams.put("site", Boolean.TRUE);

		if (!permissionChecker.isCompanyAdmin()) {
			groupParams.put("usersGroups", new Long(user.getUserId()));
			//groupParams.put("active", Boolean.TRUE);
		}
		%>

		<liferay-ui:search-container-results
			results="<%= GroupLocalServiceUtil.search(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
			total="<%= GroupLocalServiceUtil.searchCount(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams) %>"
		/>

		<liferay-ui:search-form
			page="/html/portlet/users_admin/group_search.jsp"
			searchContainer="<%= searchContainer %>"
			showAddButton="<%= false %>"
		/>

		<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

			<%
			NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

			PKParser pkParser = new PKParser(nslse.getMessage());

			long groupId = pkParser.getLong("groupId");

			Group group = GroupLocalServiceUtil.getGroup(groupId);
			%>

			<liferay-ui:message arguments="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>" key="site-x-does-not-have-any-private-pages" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= RequiredGroupException.class %>">

			<%
			RequiredGroupException rge = (RequiredGroupException)errorException;

			long groupId = GetterUtil.getLong(rge.getMessage());

			Group group = GroupLocalServiceUtil.getGroup(groupId);
			%>

			<c:choose>
				<c:when test="<%= PortalUtil.isSystemGroup(group.getName()) %>">
					<liferay-ui:message key="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
				</c:otherwise>
			</c:choose>
		</liferay-ui:error>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.Group"
			escapedModel="<%= true %>"
			keyProperty="groupId"
			modelVar="group"
			rowIdProperty="friendlyURL"
			rowVar="row"
		>
			<liferay-portlet:renderURL doAsGroupId="<%= group.getGroupId() %>" portletName="<%= PortletKeys.SITE_SETTINGS %>" varImpl="rowURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</liferay-portlet:renderURL>

			<%
			if (!GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.UPDATE)) {
				rowURL = null;
			}
			%>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				href="<%= rowURL %>"
				name="name"
				orderable="<%= true %>"
			>

				<%
				buffer.append(HtmlUtil.escape(group.getDescriptiveName(locale)));

				if (group.isOrganization()) {
					Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());

					buffer.append("<br />");
					buffer.append(LanguageUtil.format(pageContext, "belongs-to-an-organization-of-type-x", LanguageUtil.get(pageContext, organization.getType())));
				}
				else {
					boolean organizationUser = false;

					LinkedHashMap organizationParams = new LinkedHashMap();

					organizationParams.put("organizationsGroups", new Long(group.getGroupId()));

					List<Organization> organizationsGroups = OrganizationLocalServiceUtil.search(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getKeywords(), null, null, null, organizationParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

					List<String> names = new ArrayList<String>();

					for (Organization organization : organizationsGroups) {
						for (long userOrganizationId : user.getOrganizationIds()) {
							if (userOrganizationId == organization.getOrganizationId()) {
								names.add(organization.getName());

								organizationUser = true;
							}
						}
					}

					row.setParameter("organizationUser", organizationUser);

					boolean userGroupUser = false;

					LinkedHashMap userGroupParams = new LinkedHashMap();

					userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));

					List<UserGroup> userGroupsGroups = UserGroupLocalServiceUtil.search(company.getCompanyId(), null, null, userGroupParams, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

					for (UserGroup userGroup : userGroupsGroups) {
						for (long userGroupId : user.getUserGroupIds()) {
							if (userGroupId == userGroup.getUserGroupId()) {
								names.add(userGroup.getName());

								userGroupUser = true;
							}
						}
					}

					row.setParameter("userGroupUser", userGroupUser);

					String message = StringPool.BLANK;

					if (organizationUser || userGroupUser) {
						StringBundler namesSB = new StringBundler();

						for (int j = 0; j < (names.size() - 1); j++) {
							namesSB.append(names.get(j));

							if (j < (names.size() - 2)) {
								namesSB.append(", ");
							}
						}

						if (names.size() == 1) {
							message = LanguageUtil.format(pageContext, "you-are-a-member-of-x-because-you-belong-to-x", new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), names.get(0)});
						}
						else {
							message = LanguageUtil.format(pageContext, "you-are-a-member-of-x-because-you-belong-to-x-and-x", new Object[] {HtmlUtil.escape(group.getDescriptiveName(locale)), namesSB, names.get(names.size() - 1)});
						}
			%>

						<liferay-util:buffer var="iconHelp">
							<liferay-ui:icon-help message="<%= message %>" />
						</liferay-util:buffer>

			<%
						buffer.append(iconHelp);
					}
				}
			%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="type"
				value="<%= LanguageUtil.get(pageContext, group.getTypeLabel()) %>"
			/>

			<liferay-ui:search-container-column-text
				buffer="buffer"
				name="members"
			>

				<%
				LinkedHashMap userParams = new LinkedHashMap();

				userParams.put("usersGroups", new Long(group.getGroupId()));

				int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams);

				if (usersCount > 0) {
					buffer.append("<div class=\"user-count\">");
					buffer.append(LanguageUtil.format(pageContext, usersCount > 1 ? "x-users" : "x-user", usersCount));
					buffer.append("</div>");
				}

				LinkedHashMap organizationParams = new LinkedHashMap();

				organizationParams.put("organizationsGroups", new Long(group.getGroupId()));

				int organizationsCount = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getKeywords(), null, null, null, organizationParams);

				if (group.isOrganization()) {
					organizationsCount += 1;
				}
				if (organizationsCount > 0) {
					buffer.append("<div class=\"organization-count\">");
					buffer.append(LanguageUtil.format(pageContext, organizationsCount > 1 ? "x-organizations" : "x-organization", organizationsCount));
					buffer.append("</div>");
				}

				LinkedHashMap userGroupParams = new LinkedHashMap();

				userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));

				int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), null, null, userGroupParams);

				if (userGroupsCount > 0) {
					buffer.append("<div class=\"user-group-count\">");
					buffer.append(LanguageUtil.format(pageContext, userGroupsCount > 1 ? "x-user-groups" : "x-user-group", userGroupsCount));
					buffer.append("</div>");
				}

				if (buffer.length() == 0) {
					buffer.append("0");
				}
				%>

			</liferay-ui:search-container-column-text>

			<c:if test="<%= PropsValues.LIVE_USERS_ENABLED %>">
				<liferay-ui:search-container-column-text
					name="online-now"
					value='<%= LanguageUtil.get(pageContext, (group.isActive() ? "yes" : "no")) %>'
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				name="active"
				value="<%= String.valueOf(LiveUsers.getGroupUsersCount(company.getCompanyId(), group.getGroupId())) %>"
			/>

			<c:if test="<%= permissionChecker.isGroupAdmin(themeDisplay.getScopeGroupId()) %>">
				<liferay-ui:search-container-column-text
					name="pending-requests"
					value="<%= (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) ? String.valueOf(MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING)) : StringPool.BLANK %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				name="tags"
			>
				<liferay-ui:asset-tags-summary
					className="<%= Group.class.getName() %>"
					classPK="<%= group.getGroupId() %>"
				/>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				path="/html/portlet/sites_admin/site_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>