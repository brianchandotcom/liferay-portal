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

<%@ include file="/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

response.setContentType(ContentTypes.TEXT_XML_UTF8);
%>

<portlet:renderURL var="searchURL">
	<portlet:param name="mvcPath" value="/search.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
</portlet:renderURL>

<portlet:resourceURL id="getOpenSearchXML" var="openSearchResourceURL" />

<OpenSearchDescription xmlns="http://a9.com/-/spec/opensearch/1.1/">
	<ShortName><liferay-ui:message arguments="<%= HtmlUtil.escape(company.getName()) %>" key="x-search" translateArguments="<%= false %>" /></ShortName>

	<Description><liferay-ui:message arguments="<%= HtmlUtil.escape(company.getName()) %>" key="x-search-provider" translateArguments="<%= false %>" /></Description>

	<Url template="<%= HtmlUtil.escapeURL(searchURL.toString()) %>&amp;keywords={searchTerms}" type="text/html" />
	<Url template="<%= HtmlUtil.escapeURL(openSearchResourceURL.toString()) %>&amp;keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=atom" type="application/atom+xml" />
	<Url template="<%= HtmlUtil.escapeURL(openSearchResourceURL.toString()) %>&amp;keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=rss" type="application/rss+xml" />
</OpenSearchDescription>