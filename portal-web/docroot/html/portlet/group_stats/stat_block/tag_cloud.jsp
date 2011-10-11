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

<%@ include file="/html/portlet/group_stats/init.jsp" %>

<%
String selectedTag = ParamUtil.getString(request, "tag");
List<SocialActivityStatsEntry> stats = (List<SocialActivityStatsEntry>)request.getAttribute("group-statistics:stats-entries");

int maxValue = 0;
int minValue = Integer.MAX_VALUE;

for (int i=0; i < stats.size(); i++) {
	SocialActivityStatsEntry entry = stats.get(i);

	maxValue = Math.max(maxValue, entry.getCurrentValue());
	minValue = Math.min(minValue, entry.getCurrentValue());
}

double multiplier = 1;

if (maxValue != minValue) {
	multiplier = (double)5 / (maxValue - minValue);
}
%>
<ul class="tag-items tag-cloud">
<%
for (int i=0; i < stats.size(); i++) {
	SocialActivityStatsEntry entry = stats.get(i);

	int popularity = (int)(1 + ((maxValue - (maxValue - (entry.getCurrentValue() - minValue))) * multiplier));
%>
	<li class="tag-popularity-<%=popularity %>">
		<span>
			<%
				if (entry.getStatName().equals(selectedTag)) {
					portletURL.setParameter("tag", "");
			%>
				<a class="tag-selected" href="<%=portletURL %>">
			<%
				}
				else {
					portletURL.setParameter("tag", entry.getStatName());
			%>
				<a href="<%=portletURL %>">
			<%
				}
			%>
			<strong><%=entry.getStatName() %></strong></a></span>
	</li>
<%
}
%>
</ul><br style="clear: both;" />