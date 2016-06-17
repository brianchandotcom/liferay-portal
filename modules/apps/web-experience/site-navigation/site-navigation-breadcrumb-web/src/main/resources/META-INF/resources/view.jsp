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

<liferay-ui:breadcrumb
	ddmTemplateGroupId="<%= siteNavigationBreadcrumbDisplayContext.getDisplayStyleGroupId() %>"
	ddmTemplateKey="<%= siteNavigationBreadcrumbDisplayContext.getDDMTemplateKey() %>"
	showCurrentGroup="<%= siteNavigationBreadcrumbDisplayContext.isShowCurrentGroup() %>"
	showGuestGroup="<%= siteNavigationBreadcrumbDisplayContext.isShowGuestGroup() %>"
	showLayout="<%= siteNavigationBreadcrumbDisplayContext.isShowLayout() %>"
	showParentGroups="<%= siteNavigationBreadcrumbDisplayContext.isShowParentGroups() %>"
	showPortletBreadcrumb="<%= siteNavigationBreadcrumbDisplayContext.isShowPortletBreadcrumb() %>"
/>

<aui:form method="post" name="fmr">
	<aui:select label="" name="sampleDropdownr">
		<aui:option selected="true" value="">Select an Option</aui:option>
		<aui:option value="1">Option 1</aui:option>
		<aui:option value="2">Option 2</aui:option>
		<aui:option value="3">Option 3</aui:option>

		<aui:validator name="required" />
		<aui:validator name="min">2</aui:validator>
	</aui:select>

	<aui:button type="submit" value="Save"></aui:button>
</aui:form>