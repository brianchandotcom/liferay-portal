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
boolean contentSharingWithSiteAdministratorsEnabled = PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED);
int contentSharingWithChildrenEnabled = PrefsPropsUtil.getInteger(company.getCompanyId(), PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED);
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:input label="allow-site-administrators-to-display-content-from-other-sites-they-administer" name='<%= "settings--" + PropsKeys.SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED + "--" %>' type="checkbox" value="<%= contentSharingWithSiteAdministratorsEnabled %>" />

	<aui:select label="allow-subsites-to-display-content-from-parent-sites" name='<%= "settings--" + PropsKeys.SITES_CONTENT_SHARING_WITH_CHILDREN_ENABLED + "--" %>' value="<%= contentSharingWithChildrenEnabled %>">
		<aui:option label="enabled-by-default" value="<%= Sites.CONTENT_SHARING_WITH_CHILDREN_ENABLED_BY_DEFAULT %>" />
		<aui:option label="disabled-by-default" value="<%= Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED_BY_DEFAULT %>" />
		<aui:option label="disabled" value="<%= Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED %>" />
	</aui:select>
</aui:fieldset>