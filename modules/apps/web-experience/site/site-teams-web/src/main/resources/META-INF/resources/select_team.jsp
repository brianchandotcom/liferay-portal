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
String displayStyle = portalPreferences.getValue(SiteTeamsPortletKeys.SITE_TEAMS, "display-style", "icon");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectTeam");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_team.jsp");
portletURL.setParameter("eventName", eventName);

TeamSearch teamSearch = new TeamSearch(renderRequest, PortletURLUtil.clone(portletURL, liferayPortletResponse));

TeamDisplayTerms searchTerms = (TeamDisplayTerms)teamSearch.getSearchTerms();

portletURL.setParameter(teamSearch.getCurParam(), String.valueOf(teamSearch.getCur()));

int teamsCount = TeamLocalServiceUtil.searchCount(scopeGroupId, searchTerms.getKeywords(), searchTerms.getDescription(), new LinkedHashMap<String, Object>());

teamSearch.setTotal(teamsCount);

List<NavigationItem> navigationItems = new ArrayList<>();

NavigationItem navigationItem = new NavigationItem();

navigationItem.setActive(true);
navigationItem.setHref(currentURL);
navigationItem.setLabel(LanguageUtil.get(request, "teams"));

navigationItems.add(navigationItem);
%>

<clay:navigation-bar
	items="<%= navigationItems %>"
/>

<liferay-frontend:management-bar
	disabled="<%= (teamsCount <= 0) && !searchTerms.isSearch() %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= teamSearch.getOrderByCol() %>"
				orderByType="<%= teamSearch.getOrderByType() %>"
				orderColumns='<%= new String[] {"name"} %>'
				portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			/>

			<c:if test="<%= (teamsCount > 0) || searchTerms.isSearch() %>">
				<li>
					<aui:form action="<%= portletURL %>" name="searchFm">
						<liferay-ui:input-search
							markupView="lexicon"
						/>
					</aui:form>
				</li>
			</c:if>
		</liferay-frontend:management-bar-filters>

		<liferay-portlet:actionURL name="changeDisplayStyle" varImpl="changeDisplayStyleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"icon", "descriptive", "list"} %>'
			portletURL="<%= changeDisplayStyleURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form cssClass="container-fluid-1280" name="selectTeamFm">
	<liferay-ui:search-container
		searchContainer="<%= teamSearch %>"
	>
		<liferay-ui:search-container-results
			results="<%= TeamLocalServiceUtil.search(scopeGroupId, searchTerms.getKeywords(), searchTerms.getDescription(), new LinkedHashMap<String, Object>(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.TeamModel"
			keyProperty="teamId"
			modelVar="curTeam"
			rowVar="row"
		>

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("entityid", curTeam.getTeamId());
			data.put("entityname", curTeam.getName());
			data.put("teamdescription", curTeam.getDescription());

			Group group = themeDisplay.getScopeGroup();

			long[] defaultTeamIds = StringUtil.split(group.getTypeSettingsProperties().getProperty("defaultTeamIds"), 0L);

			long[] teamIds = ParamUtil.getLongValues(request, "teamIds", defaultTeamIds);

			boolean disabled = ArrayUtil.contains(teamIds, curTeam.getTeamId());
			%>

			<c:choose>
				<c:when test='<%= displayStyle.equals("descriptive") %>'>
					<liferay-ui:search-container-column-icon
						icon="users"
						toggleRowChecker="<%= true %>"
					/>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<c:choose>
								<c:when test="<%= !disabled %>">
									<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
										<%= HtmlUtil.escape(curTeam.getName()) %>
									</aui:a>
								</c:when>
								<c:otherwise>
									<%= HtmlUtil.escape(curTeam.getName()) %>
								</c:otherwise>
							</c:choose>
						</h5>

						<h6 class="text-default">
							<span><%= HtmlUtil.escape(curTeam.getDescription()) %></span>
						</h6>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<liferay-frontend:icon-vertical-card
							cssClass='<%= disabled ? StringPool.BLANK : "selector-button" %>'
							data="<%= data %>"
							icon="users"
							resultRow="<%= row %>"
							subtitle="<%= curTeam.getDescription() %>"
							title="<%= curTeam.getName() %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= displayStyle.equals("list") %>'>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
					>
						<c:choose>
							<c:when test="<%= !disabled %>">
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
						cssClass="table-cell-content"
						name="description"
						value="<%= HtmlUtil.escape(curTeam.getDescription()) %>"
					/>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectTeamFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>