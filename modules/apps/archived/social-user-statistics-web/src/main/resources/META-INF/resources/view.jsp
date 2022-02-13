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
%>

<c:choose>
	<c:when test="<%= !rankingNamesList.isEmpty() %>">

		<%
		SearchContainer<Tuple> searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 5, portletURL, null, null);

		List<String> selectedNamesList =
			new ArrayList<String>() {
				{
					add(SocialActivityCounterConstants.NAME_CONTRIBUTION);
					add(SocialActivityCounterConstants.NAME_PARTICIPATION);
				}
			};

		if (socialUserStatisticsPortletInstanceConfiguration.displayAdditionalActivityCounters()) {
			String[] displayActivityCounterName = socialUserStatisticsPortletInstanceConfiguration.displayActivityCounterName();

			for (int displayActivityCounterNameIndex = 0; displayActivityCounterNameIndex < displayActivityCounterName.length; displayActivityCounterNameIndex++) {
				selectedNamesList.add(displayActivityCounterName[displayActivityCounterNameIndex]);
			}
		}

		String[] selectedNames = selectedNamesList.toArray(new String[0]);
		long socialActivityScopeGroupId = scopeGroupId;

		searchContainer.setResultsAndTotal(() -> SocialActivityCounterLocalServiceUtil.getUserActivityCounters(socialActivityScopeGroupId, rankingNames, selectedNames, searchContainer.getStart(), searchContainer.getEnd()), SocialActivityCounterLocalServiceUtil.getUserActivityCountersCount(socialActivityScopeGroupId, rankingNames));

		List<com.liferay.portal.kernel.dao.search.ResultRow> resultRows = searchContainer.getResultRows();

		List<Tuple> results = searchContainer.getResults();

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
				<c:if test="<%= searchContainer.getTotal() > 0 %>">
					<liferay-ui:message arguments="<%= searchContainer.getTotal() %>" key="top-users-out-of-x" translateArguments="<%= false %>" /> <liferay-ui:message arguments="<%= rankingNamesMessage %>" key="ranking-is-based-on-x" translateArguments="<%= false %>" /><br />
				</c:if>
			</div>
		</c:if>

		<c:if test="<%= searchContainer.getTotal() == 0 %>">
			<liferay-ui:message key="there-are-no-active-users-for-this-period" />
		</c:if>

		<liferay-ui:search-iterator
			paginate="<%= false %>"
			searchContainer="<%= searchContainer %>"
		/>

		<c:if test="<%= !results.isEmpty() %>">
			<div class="taglib-search-iterator-page-iterator-bottom" id="<portlet:namespace />searchTopUsers">
				<liferay-ui:search-paginator
					searchContainer="<%= searchContainer %>"
					type="article"
				/>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<div class="alert alert-info portlet-configuration">
			<a href="<%= HtmlUtil.escapeHREF(portletDisplay.getURLConfiguration()) %>" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
				<liferay-ui:message key="please-configure-this-widget-and-select-at-least-one-ranking-criteria" />
			</a>
		</div>
	</c:otherwise>
</c:choose>