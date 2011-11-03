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

Map<String, SocialActivityCounter> counters = (Map<String, SocialActivityCounter>)row.getObject();

SocialActivityCounter contribution = counters.get(SocialActivityCounterConstants.NAME_CONTRIBUTION);
SocialActivityCounter participation = counters.get(SocialActivityCounterConstants.NAME_PARTICIPATION);

if (contribution == null) {
	contribution = new SocialActivityCounterImpl();
	contribution.setName(SocialActivityCounterConstants.NAME_CONTRIBUTION);
}

if (participation == null) {
	participation = new SocialActivityCounterImpl();
	participation.setName(SocialActivityCounterConstants.NAME_PARTICIPATION);
}

counters.remove(SocialActivityCounterConstants.NAME_CONTRIBUTION);
counters.remove(SocialActivityCounterConstants.NAME_PARTICIPATION);
%>

<liferay-ui:user-display
	userId="<%= GetterUtil.getLong(row.getPrimaryKey()) %>"
	userName=""
>
	<c:if test="<%= userDisplay != null %>">
		<div class="user-rank">
			<span><liferay-ui:message key="rank" />:</span> <%= searchContainer.getStart() +row.getPos() + 1 %>
		</div>

		<div class="contribution-score">
			<span><liferay-ui:message key='contribution-score' />:</span> <%= contribution.getCurrentValue() %>
		</div>

		<div class="participation-score">
			<span><liferay-ui:message key='participation-score' />:</span> <%= participation.getCurrentValue() %>
		</div>
	</c:if>
</liferay-ui:user-display>

<%
if (displayAdditionalCounters) {
%>

<div class="separator"><!-- --></div>

<% for (SocialActivityCounter counter : counters.values()) { %>

	<div class="social.counter.<%= counter.getName() %>">
		<span><liferay-ui:message key='<%= "social.counter." + counter.getName() %>' />:</span> <%= counter.getCurrentValue() %>
	</div>

<% }
}
%>