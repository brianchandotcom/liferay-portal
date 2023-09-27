<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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