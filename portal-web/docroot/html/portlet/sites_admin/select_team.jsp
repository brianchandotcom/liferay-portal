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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectTeam");
%>

<liferay-ui:header
	title="teams"
/>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/sites_admin/select_team" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="eventName" value="<%= eventName %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL.toString() %>" method="get" name="selectTeamFm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:search-container
		searchContainer="<%= new TeamSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-form
			page="/html/portlet/sites_admin/team_search.jsp"
		/>

		<div class="separator"><!-- --></div>

		<%
		TeamSearchTerms searchTerms = (TeamSearchTerms)searchContainer.getSearchTerms();

		portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));
		%>

		<liferay-ui:search-container-results
			results="<%= TeamLocalServiceUtil.search(groupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
			total="<%= TeamLocalServiceUtil.searchCount(groupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.TeamModel"
			keyProperty="teamId"
			modelVar="curTeam"
			rowVar="row"
		>
			<liferay-ui:search-container-column-text
				name="name"
				value="<%= curTeam.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= curTeam.getDescription() %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("teamdescription", HtmlUtil.escape(curTeam.getDescription()));
				data.put("teamid", curTeam.getTeamId());
				data.put("teamname", HtmlUtil.escape(curTeam.getName()));
				data.put("teamsearchcontainername", "teams");
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	A.one('#<portlet:namespace />selectTeamFm').delegate(
		'click',
		function(event) {
			var result = Util.getAttributes(event.currentTarget, 'data-');

			Util.getOpener().Liferay.fire('<%= HtmlUtil.escapeJS(eventName) %>', result);

			Util.getWindow().close();
		},
		'.selector-button input'
	);
</aui:script>