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
String tabs1 = ParamUtil.getString(request, "tabs1", "my-sites");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/my_sites/view");
portletURL.setParameter("tabs1", tabs1);

pageContext.setAttribute("portletURL", portletURL);
%>

<liferay-ui:success key="membership_request_sent" message="your-request-was-sent-you-will-receive-a-reply-by-email" />

<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:tabs
		names="my-sites,available-sites"
		url="<%= portletURL.toString() %>"
	/>

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

	if (tabs1.equals("my-sites")) {
		groupParams.put("usersGroups", new Long(user.getUserId()));
		groupParams.put("active", Boolean.TRUE);
	}
	else if (tabs1.equals("available-sites")) {
		List types = new ArrayList();

		types.add(new Integer(GroupConstants.TYPE_SITE_OPEN));
		types.add(new Integer(GroupConstants.TYPE_SITE_RESTRICTED));

		groupParams.put("types", types);
		groupParams.put("active", Boolean.TRUE);
	}

	int total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams);

	searchContainer.setTotal(total);

	List results = GroupLocalServiceUtil.search(company.getCompanyId(), classNameIds, searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

	searchContainer.setResults(results);
	%>

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
	headerNames.add("members");

	if (PropsValues.LIVE_USERS_ENABLED && tabs1.equals("my-sites")) {
		headerNames.add("online-now");
	}

	headerNames.add("tags");
	headerNames.add(StringPool.BLANK);

	searchContainer.setHeaderNames(headerNames);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Group group = (Group)results.get(i);

		group = group.toEscapedModel();

		ResultRow row = new ResultRow(new Object[] {group, tabs1}, group.getGroupId(), i);

		PortletURL rowURL = null;

		if (group.getPublicLayoutsPageCount() > 0) {
			rowURL = renderResponse.createActionURL();

			rowURL.setWindowState(WindowState.NORMAL);

			rowURL.setParameter("struts_action", "/sites_admin/page");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("groupId", String.valueOf(group.getGroupId()));
			rowURL.setParameter("privateLayout", Boolean.FALSE.toString());
		}
		else if (tabs1.equals("my-sites") && (group.getPrivateLayoutsPageCount() > 0)) {
			rowURL = renderResponse.createActionURL();

			rowURL.setWindowState(WindowState.NORMAL);

			rowURL.setParameter("struts_action", "/sites_admin/page");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("groupId", String.valueOf(group.getGroupId()));
			rowURL.setParameter("privateLayout", Boolean.TRUE.toString());
		}

		// Name

		StringBundler sb = new StringBundler();

		if (rowURL != null) {
			sb.append("<a href=\"");
			sb.append(rowURL.toString());
			sb.append("\" target=\"_blank\"><strong>");
			sb.append(HtmlUtil.escape(group.getDescriptiveName()));
			sb.append("</strong></a>");
		}
		else {
			sb.append("<strong>");
			sb.append(HtmlUtil.escape(group.getDescriptiveName()));
			sb.append("</strong>");
		}

		if (!tabs1.equals("my-sites") && Validator.isNotNull(group.getDescription())) {
			sb.append("<br /><em>");
			sb.append(group.getDescription());
			sb.append("</em>");
		}

		row.addText(sb.toString());

		// Members

		LinkedHashMap userParams = new LinkedHashMap();

		userParams.put("usersGroups", new Long(group.getGroupId()));

		int membersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams);

		row.addText(String.valueOf(membersCount));

		// Online Now

		if (tabs1.equals("my-sites") && PropsValues.LIVE_USERS_ENABLED) {
			int onlineCount = LiveUsers.getGroupUsersCount(company.getCompanyId(), group.getGroupId());

			row.addText(String.valueOf(onlineCount));
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

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/my_sites/site_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>