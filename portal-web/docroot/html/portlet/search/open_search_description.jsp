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

<%@ include file="/html/common/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

PortletURLImpl searchURL = new PortletURLImpl(request, PortletKeys.SEARCH, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

searchURL.setEscapeXml(true);

searchURL.setParameter("mvcPath", "/html/portlet/search/search.jsp");
searchURL.setParameter("groupId", String.valueOf(groupId));

response.setContentType(ContentTypes.TEXT_XML_UTF8);
%>

<OpenSearchDescription xmlns="http://a9.com/-/spec/opensearch/1.1/">
	<ShortName><%= LanguageUtil.format(request, "x-search", HtmlUtil.escape(company.getName()), false) %></ShortName>
	<Description><%= LanguageUtil.format(request, "x-search-provider", HtmlUtil.escape(company.getName()), false) %></Description>
	<Url template="<%= searchURL.toString() %>&amp;keywords={searchTerms}" type="text/html" />
	<Url template="<%= themeDisplay.getPortalURL() %><%= PortalUtil.getPathMain() %>/search/open_search?keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=atom" type="application/atom+xml" />
	<Url template="<%= themeDisplay.getPortalURL() %><%= PortalUtil.getPathMain() %>/search/open_search?keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=rss" type="application/rss+xml" />
</OpenSearchDescription>