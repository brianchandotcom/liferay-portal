<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

	<%
	GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);
	%>

	<liferay-ui:search-form
		page="/html/portlet/users_admin/group_search.jsp"
		searchContainer="<%= searchContainer %>"
		showAddButton="<%= false %>"
	/>

	<%
	GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap groupParams = new LinkedHashMap();

	groupParams.put("site", Boolean.TRUE);

	if (!permissionChecker.isCompanyAdmin()) {
		groupParams.put("usersGroups", new Long(user.getUserId()));
		//groupParams.put("active", Boolean.TRUE);
	}

	int total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams);

	searchContainer.setTotal(total);

	List results = GroupLocalServiceUtil.search(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);
	%>

	<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

		<%
		NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

		PKParser pkParser = new PKParser(nslse.getMessage());

		long groupId = pkParser.getLong("groupId");

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		%>

		<liferay-ui:message arguments="<%= group.getDescriptiveName() %>" key="site-x-does-not-have-any-private-pages" />
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

	<%
	List<String> headerNames = new ArrayList<String>();

	headerNames.add("name");
	headerNames.add("type");
	headerNames.add("members");

	if (PropsValues.LIVE_USERS_ENABLED) {
		headerNames.add("online-now");
	}

	headerNames.add("active");
	headerNames.add("pending-requests");
	headerNames.add("tags");

	headerNames.add(StringPool.BLANK);

	searchContainer.setHeaderNames(headerNames);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Group group = (Group)results.get(i);

		group = group.toEscapedModel();

		ResultRow row = new ResultRow(group, group.getGroupId(), i);

		LiferayPortletURL rowURL = ((LiferayPortletResponse)renderResponse).createRenderURL(PortletKeys.SITE_SETTINGS);

		rowURL.setDoAsGroupId(group.getGroupId());

		rowURL.setParameter("redirect", currentURL);

		// Name

		StringBundler sb = new StringBundler();

		sb.append("<a href=\"");
		sb.append(rowURL.toString());
		sb.append("\">");
		sb.append(HtmlUtil.escape(group.getDescriptiveName()));
		sb.append("</a>");

		if (group.isOrganization()) {
			Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getOrganizationId());

			sb.append("<br />");
			sb.append(LanguageUtil.format(pageContext, "belongs-to-an-organization-of-type-x", LanguageUtil.get(pageContext, organization.getType())));
		}

		row.addText(sb.toString());

		// Type

		row.addText(LanguageUtil.get(pageContext, group.getTypeLabel()), rowURL);

		// Members

		sb = new StringBundler();

		LinkedHashMap userParams = new LinkedHashMap();

		userParams.put("usersGroups", new Long(group.getGroupId()));

		int usersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams);

		if (usersCount > 0) {
			sb.append("<div class=\"user-count\">");
			sb.append(LanguageUtil.format(pageContext, usersCount > 1 ? "x-users" : "x-user", usersCount));
			sb.append("</div>");
		}

		LinkedHashMap organizationParams = new LinkedHashMap();

		organizationParams.put("organizationsGroups", new Long(group.getGroupId()));

		int organizationsCount = OrganizationLocalServiceUtil.searchCount(company.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchTerms.getKeywords(), null, null, null, organizationParams);

		if (group.isOrganization()) {
			organizationsCount += 1;
		}
		if (organizationsCount > 0) {
			sb.append("<div class=\"organization-count\">");
			sb.append(LanguageUtil.format(pageContext, organizationsCount > 1 ? "x-organizations" : "x-organization", organizationsCount));
			sb.append("</div>");
		}

		LinkedHashMap userGroupParams = new LinkedHashMap();

		userGroupParams.put("userGroupsGroups", new Long(group.getGroupId()));

		int userGroupsCount = UserGroupLocalServiceUtil.searchCount(company.getCompanyId(), null, null, userGroupParams);

		if (userGroupsCount > 0) {
			sb.append("<div class=\"user-group-count\">");
			sb.append(LanguageUtil.format(pageContext, userGroupsCount > 1 ? "x-user-groups" : "x-user-group", userGroupsCount));
			sb.append("</div>");
		}

		row.addText((sb.length() > 0) ? sb.toString() : "0");

		// Online Now

		if (PropsValues.LIVE_USERS_ENABLED) {
			int onlineCount = LiveUsers.getGroupUsersCount(company.getCompanyId(), group.getGroupId());

			row.addText(String.valueOf(onlineCount));
		}

		// Active

		row.addText(LanguageUtil.get(pageContext, (group.isActive() ? "yes" : "no")));

		// Restricted number of petitions

		if ((group.getType() == GroupConstants.TYPE_SITE_RESTRICTED) && permissionChecker.isGroupAdmin(group.getGroupId())) {
			int pendingRequests = MembershipRequestLocalServiceUtil.searchCount(group.getGroupId(), MembershipRequestConstants.STATUS_PENDING);

			row.addText(String.valueOf(pendingRequests));
		}
		else {
			row.addText(StringPool.BLANK);
		}
	%>

		<liferay-util:buffer var="assetTagsSummary">
			<liferay-ui:asset-tags-summary
				className="<%= Group.class.getName() %>"
				classPK="<%= group.getGroupId() %>"
			/>
		</liferay-util:buffer>

	<%

		// Tags

		row.addText(assetTagsSummary);

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/sites_admin/site_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>