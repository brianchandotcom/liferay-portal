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

<%@ include file="/sites/init.jsp" %>

<%
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);

Group group = themeDisplay.getScopeGroup();
%>

<div class="toolbar">
	<div class="toolbar-group-field">
		<a class="icon-angle-left icon-monospaced" href="javascript:;" id="<portlet:namespace />allSitesLink"></a>
	</div>
	<div class="toolbar-group-content">
		<aui:a href="<%= group.getDisplayURL(themeDisplay) %>" label="<%= group.getDescriptiveName(locale) %>" />
	</div>

	<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

		<%
		String stagingGroupURL = null;

		if (group.hasStagingGroup()) {
			Group stagingGroup = StagingUtil.getStagingGroup(group.getGroupId());

			if (stagingGroup != null) {
				LayoutSet layoutSet = themeDisplay.getLayoutSet();

				if (layoutSet.isPrivateLayout()) {
					layoutSet = stagingGroup.getPrivateLayoutSet();
				}
				else {
					layoutSet = stagingGroup.getPublicLayoutSet();
				}

				if (layoutSet.getPageCount() > 0) {
					stagingGroupURL = PortalUtil.getGroupFriendlyURL(layoutSet, themeDisplay);
				}
			}
		}
		%>

		<div class="<%= stagingGroupURL == null ? "active" : StringPool.BLANK %> toolbar-group-field">
			<aui:a cssClass="icon-fb-radio icon-monospaced" href="<%= stagingGroupURL %>" title="staging" />
		</div>

		<%
		String liveGroupURL = null;

		if (group.isStagingGroup()) {
			Group liveGroup = StagingUtil.getLiveGroup(group.getGroupId());

			if (liveGroup != null) {
				LayoutSet layoutSet = themeDisplay.getLayoutSet();

				if (layoutSet.isPrivateLayout()) {
					layoutSet = liveGroup.getPrivateLayoutSet();
				}
				else {
					layoutSet = liveGroup.getPublicLayoutSet();
				}

				if (layoutSet.getPageCount() > 0) {
					liveGroupURL = PortalUtil.getGroupFriendlyURL(layoutSet, themeDisplay);
				}
			}
		}
		%>

		<div class="<%= liveGroupURL == null ? "active" : StringPool.BLANK %> toolbar-group-field">
			<aui:a cssClass="icon-circle-blank icon-monospaced" href="<%= liveGroupURL %>" title="live" />
		</div>
	</c:if>
</div>

<liferay-application-list:panel panelCategory="<%= panelCategory %>" />

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />allSitesLink').on(
		'click',
		function(event) {
			$('#<portlet:namespace /><%= PanelCategoryKeys.SITES_ALL_SITES %>TabLink').tab('show');
		}
	);
</aui:script>