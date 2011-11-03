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

<%@ include file="/html/portlet/user_statistics/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 5, portletURL, null, null);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Map<String, SocialActivityCounter> activityCounters = (Map<String, SocialActivityCounter>)row.getObject();

SocialActivityCounter contributionActivityCounter = activityCounters.get(SocialActivityCounterConstants.NAME_CONTRIBUTION);

if (contributionActivityCounter == null) {
	contributionActivityCounter = new SocialActivityCounterImpl();

	contributionActivityCounter.setName(SocialActivityCounterConstants.NAME_CONTRIBUTION);
}

SocialActivityCounter participationActivityCounter = activityCounters.get(SocialActivityCounterConstants.NAME_PARTICIPATION);

if (participationActivityCounter == null) {
	participationActivityCounter = new SocialActivityCounterImpl();

	participationActivityCounter.setName(SocialActivityCounterConstants.NAME_PARTICIPATION);
}

activityCounters.remove(SocialActivityCounterConstants.NAME_CONTRIBUTION);
activityCounters.remove(SocialActivityCounterConstants.NAME_PARTICIPATION);
%>

<liferay-ui:user-display
	userId="<%= GetterUtil.getLong(row.getPrimaryKey()) %>"
	userName=""
>
	<c:if test="<%= userDisplay != null %>">
		<div class="user-rank">
			<span><liferay-ui:message key="rank" />:</span> <%= searchContainer.getStart() + row.getPos() + 1 %>
		</div>

		<div class="contribution-score">
			<span><liferay-ui:message key='contribution-score' />:</span> <%= contributionActivityCounter.getCurrentValue() %>
		</div>

		<div class="participation-score">
			<span><liferay-ui:message key='participation-score' />:</span> <%= participationActivityCounter.getCurrentValue() %>
		</div>
	</c:if>
</liferay-ui:user-display>

<c:if test="<%= displayAdditionalCounters %>">
	<div class="separator"><!-- --></div>

	<%
	for (SocialActivityCounter activityCounter : activityCounters.values()) {
	%>

		<div class="social.counter.<%= activityCounter.getName() %>">
			<span><liferay-ui:message key='<%= "social.counter." + activityCounter.getName() %>' />:</span> <%= activityCounter.getCurrentValue() %>
		</div>

	<%
	}
	%>

</c:if>