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

<%@ include file="/html/portlet/users_stats/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

List<String> rankingCounters = new ArrayList<String>();

if (rankByParticipation) {
	rankingCounters.add(SocialActivityCounterConstants.NAME_PARTICIPATION);
}

if (rankByContribution) {
	rankingCounters.add(SocialActivityCounterConstants.NAME_CONTRIBUTION);
}

List<String> selectedCounters = new ArrayList<String>();

selectedCounters.add(SocialActivityCounterConstants.NAME_CONTRIBUTION);
selectedCounters.add(SocialActivityCounterConstants.NAME_PARTICIPATION);

if (displayAdditionalCounters) {
	for (int displayCounterIndex : displayCounterIndexes) {
		selectedCounters.add(PrefsParamUtil.getString(preferences, request, "displayCounter" + displayCounterIndex));
	}
}

if (rankingCounters.size() > 0) {

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 5, portletURL, null, null);

	int total = SocialActivityCounterLocalServiceUtil.getUserActivityCounters(scopeGroupId, rankingCounters.toArray(new String[rankingCounters.size()]));

	searchContainer.setTotal(total);

	List<Tuple> results = SocialActivityCounterLocalServiceUtil.getUserActivityCounters(scopeGroupId, rankingCounters.toArray(new String[rankingCounters.size()]), selectedCounters.toArray(new String[selectedCounters.size()]), searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Tuple tuple = results.get(i);

		ResultRow row = new ResultRow((Map<String, SocialActivityCounter>)tuple.getObject(1), (Long)tuple.getObject(0), i);

		// User display

		row.addJSP("/html/portlet/users_stats/user_display.jsp", application, request, response);

		// Add result row

		resultRows.add(row);
	}

	String rankingCountersMessage = LanguageUtil.format(pageContext, rankingCounters.get(0), StringPool.BLANK);

	if (rankingCounters.size() > 1) {
		for (int i = 1; i < rankingCounters.size(); i++) {
			rankingCountersMessage = LanguageUtil.format(pageContext, "x-and-y", new Object[] {rankingCountersMessage, rankingCounters.get(i)});
		}
	}

	String rankingMessage = LanguageUtil.format(pageContext,"ranking-is-based-on-x", rankingCountersMessage);

	%>

	<div>
		<%= LanguageUtil.format(pageContext, "top-users-out-of-x", String.valueOf(total)) %>
		&nbsp;
		<%=rankingMessage %>
	</div>

	<c:if test="<%= total > 0 %>">
		<br />
	</c:if>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />

	<c:if test="<%= results.size() > 0 %>">
		<div class="taglib-search-iterator-page-iterator-bottom" id="<portlet:namespace />searchTopUsers">
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="article" />
		</div>
	</c:if>

	<aui:script use="aui-io-plugin">
		var searchTopUsers = A.one('#<portlet:namespace />searchTopUsers');

		if (searchTopUsers) {
			var parent = searchTopUsers.get('parentNode');

			parent.plug(
				A.Plugin.IO,
				{
					autoLoad: false
				}
			);

			searchTopUsers.all('a').on(
				'click',
				function(event) {
					var uri = event.currentTarget.get('href').replace(/p_p_state=normal/i, 'p_p_state=exclusive');

					parent.io.set('uri', uri);
					parent.io.start();

					event.preventDefault();
				}
			);
		}
	</aui:script>

<%
}
else {
%>
	<div class="portlet-msg-error">
		<%=LanguageUtil.format(pageContext, "no-ranking-selected-please-select-one", false) %>
	</div>
<%
}
%>