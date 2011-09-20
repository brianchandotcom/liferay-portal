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

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 5, portletURL, null, null);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SocialActivityUserStats socialActivityUserStats = (SocialActivityUserStats)row.getObject();

KeyValuePair contribution = socialActivityUserStats.getStat(SocialActivityConstants.STAT_CONTRIBUTION);
KeyValuePair participation = socialActivityUserStats.getStat(SocialActivityConstants.STAT_PARTICIPATION);

if (contribution == null) {
	contribution = new KeyValuePair(SocialActivityConstants.STAT_CONTRIBUTION, "0");
}

if (participation == null) {
	participation = new KeyValuePair(SocialActivityConstants.STAT_PARTICIPATION, "0");
}

socialActivityUserStats.removeStat(contribution);

socialActivityUserStats.removeStat(participation);
%>

<liferay-ui:user-display
	userId="<%= socialActivityUserStats.getUserId() %>"
	userName=""
>
	<c:if test="<%= userDisplay != null %>">
		<div class="user-rank">
			<span><liferay-ui:message key="rank" />:</span> <%= searchContainer.getStart() +row.getPos() + 1 %>
		</div>

		<div class="contribution-score">
			<span><liferay-ui:message key='contribution-score' />:</span> <%= contribution.getValue() %>
		</div>

		<div class="participation-score">
			<span><liferay-ui:message key='participation-score' />:</span> <%= participation.getValue() %>
		</div>

	</c:if>
</liferay-ui:user-display>

<%
if (displayAdditionalCounters) {
%>
<div style="height: 4px; border-top: 1px dotted;"></div>

<% for (KeyValuePair stat : socialActivityUserStats.getStats()) { %>

<div class="social.counter.<%=stat.getKey() %>">
	<span><liferay-ui:message key='<%="social.counter." + stat.getKey() %>' />:</span> <%= stat.getValue() %>
</div>

<% }
}
%>