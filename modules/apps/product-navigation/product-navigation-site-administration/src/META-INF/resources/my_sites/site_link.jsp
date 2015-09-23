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

<%@ include file="/my_sites/init.jsp" %>

<%
boolean privateLayout = (Boolean)request.getAttribute("my_sites.jsp-privateLayout");
boolean selectedSite = (Boolean)request.getAttribute("my_sites.jsp-selectedSite");
Group siteGroup = (Group)request.getAttribute("my_sites.jsp-siteGroup");
String siteName = GetterUtil.getString(request.getAttribute("my_sites.jsp-siteGroup"), siteGroup.getDescriptiveName(locale));
boolean showPrivateLabel = (Boolean)request.getAttribute("my_sites.jsp-showPrivateLabel");
boolean showStagingLabel = (Boolean)request.getAttribute("my_sites.jsp-showStagingLabel");

String groupFriendlyURL = siteGroup.getFriendlyURL();

String groupDisplayURL = siteGroup.getDisplayURL(themeDisplay, privateLayout);

if (Validator.isNull(groupDisplayURL)) {
	PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

	String portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION, permissionChecker, siteGroup);

	PortletURL groupAdministrationURL = null;

	if (Validator.isNotNull(portletId)) {
		groupAdministrationURL = PortalUtil.getControlPanelPortletURL(request, siteGroup, portletId, 0, PortletRequest.RENDER_PHASE);

		if (groupAdministrationURL != null) {
			groupDisplayURL = groupAdministrationURL.toString();
		}
	}
}
%>

<liferay-util:buffer var="siteHtml">
	<c:if test="<%= showStagingLabel %>">
		<small class="pull-right"><liferay-ui:message key="staging" /></small>
	</c:if>

	<c:if test="<%= showPrivateLabel %>">
		<small class="pull-right"><liferay-ui:message key='<%= privateLayout ? "private" : "public" %>' /></small>
	</c:if>
</liferay-util:buffer>

<liferay-application-list:panel-app
	active="<%= selectedSite %>"
	id='<%= groupFriendlyURL.substring(1) + (privateLayout ? "Private" : "Public") + "SiteLink" %>'
	label="<%= HtmlUtil.escape(siteName) + siteHtml %>"
	url='<%= selectedSite ? "javascript:;" : groupDisplayURL %>'
/>

<c:if test="<%= selectedSite %>">
	<aui:script sandbox="<%= true %>">
		$('#<portlet:namespace /><%= groupFriendlyURL.substring(1) %><%= privateLayout ? "Private" : "Public" %>SiteLink').on(
			'click',
			function(event) {
				$('#<portlet:namespace /><%= PanelCategoryKeys.SITE_ADMINISTRATION %>TabLink').tab('show');
			}
		);
	</aui:script>
</c:if>