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

		<%
		boolean include = GetterUtil.getBoolean(layoutTypeSettings.getProperty("sitemap-include"), true);
		String changeFrequency = layoutTypeSettings.getProperty("sitemap-changefreq", "daily");
		String sitemapPriority = layoutTypeSettings.getProperty("sitemap-priority", StringPool.BLANK);
		%>

		<aui:select label="include" name="TypeSettingsProperties--sitemap-include--">
			<aui:option label="yes" selected="<%= include %>" value="1" />
			<aui:option label="no" selected="<%= !include %>" value="0" />
		</aui:select>

		<aui:input helpMessage="(0.0 - 1.0)" label="page-priority" name="TypeSettingsProperties--sitemap-priority--" size="3" type="text" value="<%= sitemapPriority %>" />

		<aui:select label="change-frequency" name="TypeSettingsProperties--sitemap-changefreq--">
			<aui:option selected='<%= changeFrequency.equals("always") %>' label="always" />
			<aui:option selected='<%= changeFrequency.equals("hourly") %>' label="hourly" />
			<aui:option selected='<%= changeFrequency.equals("daily") %>' label="daily" />
			<aui:option selected='<%= changeFrequency.equals("weekly") %>' label="weekly" />
			<aui:option selected='<%= changeFrequency.equals("monthly") %>' label="monthly" />
			<aui:option selected='<%= changeFrequency.equals("yearly") %>' label="yearly" />
			<aui:option selected='<%= changeFrequency.equals("never") %>' label="never" />
		</aui:select>
	</aui:fieldset>
</c:if>

<aui:fieldset>

	<%
	boolean showAlternateLinks = GetterUtil.getBoolean(layoutTypeSettings.getProperty("show-alternate-links"), true);
	%>

	<h3><liferay-ui:message key="alternate-links" /></h3>

	<aui:input helpMessage="show-alternate-links-help" label="show-alternate-links" name="TypeSettingsProperties--show-alternate-links--" type="checkbox" value="<%= showAlternateLinks %>" />
</aui:fieldset>