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
String sitemapUrl = PortalUtil.getPortalURL(request) + themeDisplay.getPathContext() + "/sitemap.xml";

LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

TreeMap<String, String> virtualHostnames = layoutSet.getVirtualHostnames();

if (!virtualHostnames.containsKey(PortalUtil.getHost(request))) {
	sitemapUrl += "?groupId=" + layoutsAdminDisplayContext.getLiveGroupId() + "&privateLayout=" + layoutsAdminDisplayContext.isPrivateLayout();
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="siteMap"
/>

<liferay-util:buffer
	var="linkContent"
>
	<a href="http://www.sitemaps.org" target="_blank">
		http://www.sitemaps.org
	</a>
</liferay-util:buffer>

<div class="text-secondary">
	<p>
		<liferay-ui:message key="the-sitemap-protocol-notifies-search-engines-of-the-structure-of-the-website" /> <liferay-ui:message arguments="<%= linkContent %>" key="see-x-for-more-information" translateArguments="<%= false %>" />
	</p>

	<%
	String sitemapUrlLink = "<a target=\"_blank\" href=\"" + HtmlUtil.escapeAttribute(sitemapUrl) + "\">";
	%>

	<p>
		<liferay-ui:message arguments='<%= new Object[] {sitemapUrlLink, "</a>"} %>' key="send-sitemap-information-to-preview" translateArguments="<%= false %>" />
	</p>

	<ul class="list-unstyled">
		<li>
			<clay:link
				href='<%= "http://www.google.com/webmasters/sitemaps/ping?sitemap=" + HtmlUtil.escapeURL(sitemapUrl) %>'
				label="Google"
				target="_blank"
			/>
		</li>
		<li>
			<div class="d-flex">
				<clay:link
					cssClass="mr-2"
					href='<%= "https://siteexplorer.search.yahoo.com/submit/ping?sitemap=" + HtmlUtil.escapeURL(sitemapUrl) %>'
					label="Yahoo!"
					target="_blank"
				/>

				<liferay-ui:message key="requires-log-in" />
			</div>
		</li>
	</ul>
</div>