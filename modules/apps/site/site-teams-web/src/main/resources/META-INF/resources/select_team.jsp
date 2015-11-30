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
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectTeam");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_team.jsp");
portletURL.setParameter("eventName", eventName);

TeamSearch teamSearch = new TeamSearch(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse));

TeamDisplayTerms searchTerms = (TeamDisplayTerms)teamSearch.getSearchTerms();

portletURL.setParameter(teamSearch.getCurParam(), String.valueOf(teamSearch.getCur()));

int teamsCount = TeamLocalServiceUtil.searchCount(scopeGroupId, searchTerms.getKeywords(), searchTerms.getDescription(), new LinkedHashMap<String, Object>());

teamSearch.setTotal(teamsCount);
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="teams" selected="<%= true %>" />
	</aui:nav>

	<aui:nav-bar-search>
		<aui:form action="<%= portletURL %>" name="searchFm">
			<liferay-ui:input-search markupView="lexicon" />
		</aui:form>
	</aui:nav-bar-search>
</aui:nav-bar>

<c:if test="<%= teamsCount > 0 %>">
	<liferay-frontend:management-bar>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-filters>
				<liferay-frontend:management-bar-navigation
					navigationKeys='<%= new String[] {"all"} %>'
					portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
				/>
			</liferay-frontend:management-bar-filters>

			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>
</c:if>

<aui:form cssClass="container-fluid-1280" name="selectTeamFm">
	<liferay-ui:search-container
		searchContainer="<%= teamSearch %>"
	>

		<liferay-ui:search-container-results
			results="<%= TeamLocalServiceUtil.search(scopeGroupId, searchTerms.getKeywords(), searchTerms.getDescription(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.TeamModel"
			keyProperty="teamId"
			modelVar="curTeam"
			rowVar="row"
		>
			<liferay-ui:search-container-column-text
				name="name"
			>

				<%
				boolean disabled = false;

				Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

				long[] defaultTeamIds = StringUtil.split(group.getTypeSettingsProperties().getProperty("defaultTeamIds"), 0L);

				for (long defaultTeamId : defaultTeamIds) {
					Team team = TeamLocalServiceUtil.getTeam(defaultTeamId);

					if (team.getTeamId() == curTeam.getTeamId()) {
						disabled = true;

						break;
					}
				}
				%>

				<c:choose>
					<c:when test="<%= !disabled %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("teamdescription", curTeam.getDescription());
						data.put("teamid", curTeam.getTeamId());
						data.put("teamname", curTeam.getName());
						data.put("teamsearchcontainername", "teams");
						%>

						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(curTeam.getName()) %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(curTeam.getName()) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(curTeam.getDescription()) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
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