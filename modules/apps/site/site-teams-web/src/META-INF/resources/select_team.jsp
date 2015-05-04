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
String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectTeam");
%>

<liferay-ui:header
	title="teams"
/>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcPath" value="/select_team.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="eventName" value="<%= eventName %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL.toString() %>" cssClass="form-search" method="get" name="selectTeamFm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />

	<liferay-ui:search-container
		searchContainer="<%= new TeamSearch(renderRequest, portletURL) %>"
	>

		<%
		TeamDisplayTerms searchTerms = (TeamDisplayTerms)searchContainer.getSearchTerms();

		portletURL.setParameter(searchContainer.getCurParam(), String.valueOf(searchContainer.getCur()));

		total = TeamLocalServiceUtil.searchCount(groupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>());

		searchContainer.setTotal(total);
		%>

		<liferay-ui:input-search name="<%= searchTerms.NAME %>" />

		<div class="separator"><!-- --></div>

		<liferay-ui:search-container-results
			results="<%= TeamLocalServiceUtil.search(groupId, searchTerms.getName(), searchTerms.getDescription(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.TeamModel"
			keyProperty="teamId"
			modelVar="curTeam"
			rowVar="row"
		>
			<liferay-ui:search-container-column-text
				name="name"
				value="<%= HtmlUtil.escape(curTeam.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(curTeam.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("teamdescription", curTeam.getDescription());
				data.put("teamid", curTeam.getTeamId());
				data.put("teamname", curTeam.getName());
				data.put("teamsearchcontainername", "teams");

				boolean disabled = false;

				Group group = GroupLocalServiceUtil.getGroup(groupId);

				long[] defaultTeamIds = StringUtil.split(group.getTypeSettingsProperties().getProperty("defaultTeamIds"), 0L);

				for (long defaultTeamId : defaultTeamIds) {
					Team team = TeamLocalServiceUtil.getTeam(defaultTeamId);

					if (team.getTeamId() == curTeam.getTeamId()) {
						disabled = true;

						break;
					}
				}
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" disabled="<%= disabled %>" value="choose" />
			</liferay-ui:search-container-column-text>

		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var Util = Liferay.Util;

	var openingLiferay = Util.getOpener().Liferay;

	openingLiferay.fire(
		'<portlet:namespace />enableRemovedTeams',
		{
			selectors: A.all('.selector-button:disabled')
		}
	);

	Util.selectEntityHandler('#<portlet:namespace />selectTeamFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>