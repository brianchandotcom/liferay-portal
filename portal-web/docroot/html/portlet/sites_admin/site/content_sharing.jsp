<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

int contentSharingWithChildrenEnabledCompany = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);

int contentSharingWithChildrenEnabledGroup = PropertiesParamUtil.getInteger(groupTypeSettings, request, "contentSharingWithChildrenEnabled", SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE);
%>

<aui:fieldset>
	<aui:select label="allow-subsites-to-display-content-from-this-site" name="contentSharingWithChildrenEnabled">
		<aui:option label='<%= (contentSharingWithChildrenEnabledCompany == SitesUtil.CONTENT_SHARING_WITH_CHILDREN_ENABLED_BY_DEFAULT) ? "default-value-enabled" : "default-value-disabled" %>' selected="<%= contentSharingWithChildrenEnabledGroup == SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE %>" value="<%= SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE %>" />
		<aui:option label="enabled" selected="<%= contentSharingWithChildrenEnabledGroup == SitesUtil.CONTENT_SHARING_WITH_CHILDREN_ENABLED %>" value="<%= SitesUtil.CONTENT_SHARING_WITH_CHILDREN_ENABLED %>" />
		<aui:option label="disabled" selected="<%= contentSharingWithChildrenEnabledGroup == SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DISABLED %>" value="<%= SitesUtil.CONTENT_SHARING_WITH_CHILDREN_DISABLED %>" />
	</aui:select>
</aui:fieldset>