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
String redirect = ParamUtil.getString(request, "redirect");
long groupId = ParamUtil.getLong(request, "groupId");

Group group = GroupLocalServiceUtil.getGroup(groupId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/sites_admin/select_team");
portletURL.setParameter("groupId", String.valueOf(groupId));

pageContext.setAttribute("portletURL", portletURL);
%>

<liferay-ui:header
	title="teams"
/>

<aui:form action="<%= portletURL.toString() %>" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<%
		TeamSearch searchContainer = new TeamSearch(renderRequest, portletURL);
	%>

	<liferay-ui:search-form
		page="/html/portlet/sites_admin/team_search.jsp"
		searchContainer="<%= searchContainer %>"
	/>

	<%
		TeamSearchTerms searchTerms = (TeamSearchTerms)searchContainer.getSearchTerms();

		int total = TeamLocalServiceUtil.searchCount(groupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>());

		searchContainer.setTotal(total);

		List results = TeamLocalServiceUtil.search(groupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

		searchContainer.setResults(results);

		portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));
	%>

	<div class="separator"><!-- --></div>

	<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Team team = (Team)results.get(i);

			team = team.toEscapedModel();

			ResultRow row = new ResultRow(team, team.getTeamId(), i);

			StringBundler sb = new StringBundler(14);

			sb.append("javascript:opener.");
			sb.append(renderResponse.getNamespace());
			sb.append("selectTeam('");
			sb.append(team.getTeamId());
			sb.append("', '");
			sb.append(UnicodeFormatter.toString(team.getName()));
			sb.append("', '");
			sb.append("groupTeams");
			sb.append("', '");
			sb.append(UnicodeFormatter.toString(team.getDescription()));
			sb.append("', '");
			sb.append(group.getGroupId());
			sb.append("');");
			sb.append("window.close();");

			String rowHREF = sb.toString();

			// Name

			row.addText(team.getName(), rowHREF);

			// Description

			row.addText(team.getDescription(), rowHREF);

			// Add result row

			resultRows.add(row);
		}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>