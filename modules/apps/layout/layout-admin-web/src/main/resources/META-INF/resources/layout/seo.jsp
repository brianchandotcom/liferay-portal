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
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

UnicodeProperties layoutTypeSettings = null;

if (selLayout != null) {
	layoutTypeSettings = selLayout.getTypeSettingsProperties();
}
%>

<liferay-ui:error-marker key="errorSection" value="seo" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<aui:input label="html-title" name="title" />

<h4><liferay-ui:message key="meta-tags" /></h4>

<aui:input id="descriptionSEO" name="description" />

<aui:input name="keywords" />

<aui:input name="robots" />

<c:if test="<%= PortalUtil.isLayoutSitemapable(selLayout) %>">
	<h4><liferay-ui:message key="sitemap" /></h4>

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

	<aui:input helpMessage="(0.0 - 1.0)" label="page-priority" name="TypeSettingsProperties--sitemap-priority--" size="3" type="text" value="<%= sitemapPriority %>">
		<aui:validator name="number" />
		<aui:validator errorMessage="please-enter-a-valid-page-priority" name="range">[0,1]</aui:validator>
	</aui:input>

	<%
	String siteMapChangeFrequency = layoutTypeSettings.getProperty("sitemap-changefreq", PropsValues.SITES_SITEMAP_DEFAULT_CHANGE_FREQUENCY);
	%>

	<aui:select label="change-frequency" name="TypeSettingsProperties--sitemap-changefreq--" value="<%= siteMapChangeFrequency %>">
		<aui:option label="always" />
		<aui:option label="hourly" />
		<aui:option label="daily" />
		<aui:option label="weekly" />
		<aui:option label="monthly" />
		<aui:option label="yearly" />
		<aui:option label="never" />
	</aui:select>
</c:if>