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
PortletURL portletURL = renderResponse.createRenderURL();

List<String> rankingNamesList = new ArrayList<String>();

if (socialUserStatisticsPortletInstanceConfiguration.rankByParticipation()) {
	rankingNamesList.add(SocialActivityCounterConstants.NAME_PARTICIPATION);
}

if (socialUserStatisticsPortletInstanceConfiguration.rankByContribution()) {
	rankingNamesList.add(SocialActivityCounterConstants.NAME_CONTRIBUTION);
}

String[] rankingNames = rankingNamesList.toArray(new String[0]);

if (!rankingNamesList.isEmpty()) {
	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 5, portletURL, null, null);

	int total = SocialActivityCounterLocalServiceUtil.getUserActivityCountersCount(scopeGroupId, rankingNames);

	searchContainer.setTotal(total);

	List<String> selectedNamesList = new ArrayList<String>();

	selectedNamesList.add(SocialActivityCounterConstants.NAME_CONTRIBUTION);
	selectedNamesList.add(SocialActivityCounterConstants.NAME_PARTICIPATION);

	if (socialUserStatisticsPortletInstanceConfiguration.displayAdditionalActivityCounters()) {
		int displayActivityCounterNameCount = socialUserStatisticsPortletInstanceConfiguration.displayActivityCounterName().length;

		for (int displayActivityCounterNameIndex = 0; displayActivityCounterNameIndex < displayActivityCounterNameCount; displayActivityCounterNameIndex++) {
			selectedNamesList.add(socialUserStatisticsPortletInstanceConfiguration.displayActivityCounterName()[displayActivityCounterNameIndex]);
		}
	}

	String[] selectedNames = selectedNamesList.toArray(new String[0]);

	List<Tuple> results = SocialActivityCounterLocalServiceUtil.getUserActivityCounters(scopeGroupId, rankingNames, selectedNames, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Tuple tuple = results.get(i);

		ResultRow row = new ResultRow((Map<String, SocialActivityCounter>)tuple.getObject(1), (Long)tuple.getObject(0), i);

		// User display

		row.addJSP("/user_display.jsp", application, request, response);

		// Add result row

		resultRows.add(row);
	}

	String rankingNamesMessage = LanguageUtil.format(request, rankingNames[0], StringPool.BLANK, false);

	for (int i = 1; i < rankingNames.length; i++) {
		rankingNamesMessage = LanguageUtil.format(request, "x-and-y", new Object[] {rankingNamesMessage, rankingNames[i]});
	}
%>

	<c:if test="<%= socialUserStatisticsPortletInstanceConfiguration.showHeaderText() %>">
		<div class="top-users">
			<c:if test="<%= total > 0 %>">
				<liferay-ui:message arguments="<%= total %>" key="top-users-out-of-x" translateArguments="<%= false %>" /> <liferay-ui:message arguments="<%= rankingNamesMessage %>" key="ranking-is-based-on-x" translateArguments="<%= false %>" /><br />
			</c:if>
		</div>
	</c:if>

	<c:if test="<%= total == 0 %>">
		<liferay-ui:message key="there-are-no-active-users-for-this-period" />
	</c:if>

	<liferay-ui:search-iterator
		paginate="<%= false %>"
		searchContainer="<%= searchContainer %>"
	/>

	<c:if test="<%= results.size() > 0 %>">
		<div class="taglib-search-iterator-page-iterator-bottom" id="<portlet:namespace />searchTopUsers">
			<liferay-ui:search-paginator
				searchContainer="<%= searchContainer %>"
				type="article"
			/>
		</div>
	</c:if>

<%
}
else {
%>

	<div class="alert alert-info portlet-configuration">
		<a href="<%= HtmlUtil.escapeHREF(portletDisplay.getURLConfiguration()) %>" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
			<liferay-ui:message key="please-configure-this-widget-and-select-at-least-one-ranking-criteria" />
		</a>
	</div>

<%
}
%>