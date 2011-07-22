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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-ui:header
	title='<%= "recent-changes" %>'
/>

<liferay-util:include page="/html/portlet/wiki/page_iterator.jsp">
	<liferay-util:param name="type" value="recent_changes" />
</liferay-util:include>

<br />

<liferay-ui:icon-list>
	<liferay-ui:icon
		image="rss"
		label="<%= true %>"
		message="Atom 1.0"
		target="_blank"
		url='<%= themeDisplay.getPathMain() + "/wiki/rss?p_l_id=" + plid + "&nodeId=" + node.getNodeId() + rssURLAtomParams %>'
	/>

	<liferay-ui:icon
		image="rss"
		label="<%= true %>"
		message="RSS 1.0"
		target="_blank"
		url='<%= themeDisplay.getPathMain() + "/wiki/rss?p_l_id=" + plid + "&nodeId=" + node.getNodeId() + rssURLRSS10Params %>'
	/>

	<liferay-ui:icon
		image="rss"
		label="<%= true %>"
		message="RSS 2.0"
		target="_blank"
		url='<%= themeDisplay.getPathMain() + "/wiki/rss?p_l_id=" + plid + "&nodeId=" + node.getNodeId() + rssURLRSS20Params %>'
	/>
</liferay-ui:icon-list>