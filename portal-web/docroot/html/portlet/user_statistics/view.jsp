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

<%@ include file="/html/portlet/user_statistics/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

List<String> rankingNamesList = new ArrayList<String>();

if (rankByParticipation) {
	rankingNamesList.add(SocialActivityCounterConstants.NAME_PARTICIPATION);
}

if (rankByContribution) {
	rankingNamesList.add(SocialActivityCounterConstants.NAME_CONTRIBUTION);
}

String[] rankingNames = rankingNamesList.toArray(new String[rankingNamesList.size()]);

if (!rankingNamesList.isEmpty()) {
	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 5, portletURL, null, null);

	int total = SocialActivityCounterLocalServiceUtil.getUserActivityCountersCount(scopeGroupId, rankingNames);

	searchContainer.setTotal(total);

	List<String> selectedNamesList = new ArrayList<String>();

	selectedNamesList.add(SocialActivityCounterConstants.NAME_CONTRIBUTION);
	selectedNamesList.add(SocialActivityCounterConstants.NAME_PARTICIPATION);

	if (displayAdditionalActivityCounters) {
		for (int displayActivityCounterNameIndex : displayActivityCounterNameIndexes) {
			selectedNamesList.add(PrefsParamUtil.getString(preferences, request, "displayActivityCounterName" + displayActivityCounterNameIndex));
		}
	}

	String[] selectedNames = selectedNamesList.toArray(new String[selectedNamesList.size()]);

	List<Tuple> results = SocialActivityCounterLocalServiceUtil.getUserActivityCounters(scopeGroupId, rankingNames, selectedNames, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Tuple tuple = results.get(i);

		ResultRow row = new ResultRow((Map<String, SocialActivityCounter>)tuple.getObject(1), (Long)tuple.getObject(0), i);

		// User display

		row.addJSP("/html/portlet/user_statistics/user_display.jsp", application, request, response);

		// Add result row

		resultRows.add(row);
	}

	String rankingNamesMessage = LanguageUtil.format(pageContext, rankingNames[0], StringPool.BLANK);

	for (int i = 1; i < rankingNames.length; i++) {
		rankingNamesMessage = LanguageUtil.format(pageContext, "x-and-y", new Object[] {rankingNamesMessage, rankingNames[i]});
	}
	%>

	<c:if test="<%= showHeaderText %>">
		<div class="top-users">
			<c:if test="<%= total > 0 %>">
				<liferay-ui:message arguments="<%= total %>" key="top-users-out-of-x" /><aui:spacer /><%= LanguageUtil.format(pageContext, "ranking-is-based-on-x", rankingNamesMessage) %><br />
			</c:if>
		</div>
	</c:if>

	<c:if test="<%= total == 0 %>">
		<liferay-ui:message key="there-are-no-active-users-for-this-period" />
	</c:if>

	<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />

	<c:if test="<%= results.size() > 0 %>">
		<div class="taglib-search-iterator-page-iterator-bottom" id="<portlet:namespace />searchTopUsers">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="article" />
		</div>
	</c:if>

	<aui:script use="aui-io-plugin-deprecated">
		var searchTopUsers = A.one('#<portlet:namespace />searchTopUsers');

		if (searchTopUsers) {
			var parent = searchTopUsers.ancestor();

			parent.plug(
				A.Plugin.IO,
				{
					autoLoad: false
				}
			);

			searchTopUsers.all('a').on(
				'click',
				function(event) {
					event.preventDefault();

					var uri = event.currentTarget.get('href').replace(/p_p_state=normal/i, 'p_p_state=exclusive');

					parent.io.set('uri', uri);
					parent.io.start();
				}
			);
		}
	</aui:script>

<%
}
else {
%>

	<div class="alert alert-info portlet-configuration">
		<a href="<%= portletDisplay.getURLConfiguration() %>" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
			<liferay-ui:message key="please-configure-this-portlet-and-select-at-least-one-ranking-criteria" />
		</a>
	</div>

<%
}
%>