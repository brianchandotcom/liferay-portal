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
String host = PortalUtil.getHost(request);

String sitemapUrl = PortalUtil.getPortalURL(request) + themeDisplay.getPathContext() + "/sitemap.xml";

LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

if (!host.equals(layoutSet.getVirtualHostname())) {
	sitemapUrl += "?groupId=" + layoutsAdminDisplayContext.getLiveGroupId() + "&privateLayout=" + layoutsAdminDisplayContext.isPrivateLayout();
}
%>

<liferay-ui:error-marker key="errorSection" value="siteMap" />

<liferay-util:buffer var="linkContent">
	<aui:a href="http://www.sitemaps.org" target="_blank">http://www.sitemaps.org</aui:a>
</liferay-util:buffer>

<liferay-ui:message key="the-sitemap-protocol-notifies-search-engines-of-the-structure-of-the-website" /> <liferay-ui:message arguments="<%= linkContent %>" key="see-x-for-more-information" translateArguments="<%= false %>" />

<br /><br />

<%= LanguageUtil.format(request, "send-sitemap-information-to-preview", new Object[] {"<a target=\"_blank\" href=\"" + HtmlUtil.escapeAttribute(sitemapUrl) + "\">", "</a>"}, false) %>

<ul>
	<li>
		<aui:a href='<%= "http://www.google.com/webmasters/sitemaps/ping?sitemap=" + HtmlUtil.escapeURL(sitemapUrl) %>' target="_blank">Google</aui:a>
	</li>
	<li>
		<aui:a href='<%= "https://siteexplorer.search.yahoo.com/submit/ping?sitemap=" + HtmlUtil.escapeURL(sitemapUrl) %>' target="_blank">Yahoo!</aui:a> (<liferay-ui:message key="requires-log-in" />)
	</li>
</ul>