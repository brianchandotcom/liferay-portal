<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

UnicodeProperties layoutTypeSettings = null;

if (selLayout != null) {
	layoutTypeSettings = selLayout.getTypeSettingsProperties();
}
%>

<liferay-ui:error-marker key="errorSection" value="seo" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="meta-tags" /></h3>

<aui:fieldset>
	<aui:input name="description" />

	<aui:input name="keywords" />

	<aui:input name="robots" />
</aui:fieldset>

<c:if test="<%= PortalUtil.isLayoutSitemapable(selLayout) %>">
	<h3><liferay-ui:message key="robots" /></h3>

	<aui:fieldset>
		<liferay-ui:error exception="<%= SitemapChangeFrequencyException.class %>" message="please-select-a-valid-change-frequency" />
		<liferay-ui:error exception="<%= SitemapIncludeException.class %>" message="please-select-a-valid-include-value" />
		<liferay-ui:error exception="<%= SitemapPagePriorityException.class %>" message="please-enter-a-valid-page-priority" />

		<%
		boolean sitemapInclude = GetterUtil.getBoolean(layoutTypeSettings.getProperty("sitemap-include"), true);
		%>

		<aui:select label="include" name="TypeSettingsProperties--sitemap-include--">
			<aui:option label="yes" selected="<%= sitemapInclude %>" value="1" />
			<aui:option label="no" selected="<%= !sitemapInclude %>" value="0" />
		</aui:select>

		<%
		String sitemapPriority = layoutTypeSettings.getProperty("sitemap-priority", PropsValues.SITES_SITEMAP_DEFAULT_PRIORITY);
		%>

		<aui:input helpMessage="(0.0 - 1.0)" label="page-priority" name="TypeSettingsProperties--sitemap-priority--" size="3" type="text" value="<%= sitemapPriority %>" />

		<%
		String siteMapChangeFrequency = layoutTypeSettings.getProperty("sitemap-changefreq", PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);
		%>

		<aui:select label="change-frequency" name="TypeSettingsProperties--sitemap-changefreq--">
			<aui:option label="always" selected='<%= siteMapChangeFrequency.equals("always") %>' />
			<aui:option label="hourly" selected='<%= siteMapChangeFrequency.equals("hourly") %>' />
			<aui:option label="daily" selected='<%= siteMapChangeFrequency.equals("daily") %>' />
			<aui:option label="weekly" selected='<%= siteMapChangeFrequency.equals("weekly") %>' />
			<aui:option label="monthly" selected='<%= siteMapChangeFrequency.equals("monthly") %>' />
			<aui:option label="yearly" selected='<%= siteMapChangeFrequency.equals("yearly") %>' />
			<aui:option label="never" selected='<%= siteMapChangeFrequency.equals("never") %>' />
		</aui:select>
	</aui:fieldset>
</c:if>