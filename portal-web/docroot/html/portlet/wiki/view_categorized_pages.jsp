<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
long categoryId = GetterUtil.getLong(ParamUtil.getString(request, "categoryId"));
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/wiki/view_categorized_pages");
portletURL.setParameter("categoryId", String.valueOf(categoryId));
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-ui:categorization-filter assetType="pages" portletURL="<%= portletURL %>" />

<liferay-ui:header
	escapeXml="<%= false %>"
	localizeTitle="<%= false %>"
	title=''
/>

<liferay-util:include page="/html/portlet/wiki/page_iterator.jsp">
	<liferay-util:param name="type" value="categorized_pages" />
</liferay-util:include>