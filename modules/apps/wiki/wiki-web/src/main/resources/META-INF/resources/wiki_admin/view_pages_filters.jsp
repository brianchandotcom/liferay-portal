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

<%@ include file="/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

String navigation = ParamUtil.getString(request, "navigation", "all-pages");

String keywords = ParamUtil.getString(request, "keywords");

String orderByCol = GetterUtil.getString((String)request.getAttribute("view_pages.jsp-orderByCol"));
String orderByType = GetterUtil.getString((String)request.getAttribute("view_pages.jsp-orderByType"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/wiki/view_pages");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("navigation", navigation);

Map<String, String> orderColumns = new HashMap<String, String>();

orderColumns.put("modifiedDate", "modified-date");
orderColumns.put("title", "title");

WikiURLHelper wikiURLHelper = new WikiURLHelper(wikiRequestHelper, renderResponse, wikiGroupServiceConfiguration);
%>

<c:if test="<%= Validator.isNull(keywords) %>">
	<liferay-frontend:management-bar-navigation
		navigationKeys='<%= new String[] {"all-pages", "draft-pages", "frontpage", "orphan-pages", "recent-changes"} %>'
		portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
	/>

	<c:if test='<%= navigation.equals("all-pages") %>'>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns="<%= orderColumns %>"
			portletURL="<%= portletURL %>"
		/>
	</c:if>
</c:if>

<li>
	<aui:form action="<%= wikiURLHelper.getSearchURL() %>" method="get" name="searchFm">
		<liferay-portlet:renderURLParams portletURL="<%= wikiURLHelper.getSearchURL() %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />

		<liferay-ui:input-search
			id="keywords1"
			markupView="lexicon"
		/>
	</aui:form>
</li>