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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		Group group = null;

		if (layout != null) {
			group = layout.getGroup();
		}

		boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
		boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);
		%>

		<c:if test="<%= !themeDisplay.isStateMaximized() && (layout != null) && (layout.isTypePortlet() || layout.isTypePanel()) && !layout.isLayoutPrototypeLinkActive() && !group.isControlPanel() && (!group.hasStagingGroup() || group.isStagingGroup()) && (GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_LAYOUT) || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
			<div class="add-content-menu" id="<portlet:namespace />addPanelContainer">
				<button class="close pull-right" id="closePanel" type="button">&#x00D7;</button>

				<%
				String selectedTab = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_tab", "content"));
				%>

				<liferay-ui:tabs
					names="content,applications,page"
					refresh="<%= false %>"
					value="<%= selectedTab %>"
				>
					<liferay-ui:section>
						<liferay-util:include page="/html/portlet/dockbar/add_content.jsp" />
					</liferay-ui:section>

					<liferay-ui:section>
						<liferay-util:include page="/html/portlet/dockbar/add_application.jsp" />
					</liferay-ui:section>

					<liferay-ui:section>
						<liferay-util:include page="/html/portlet/dockbar/add_page.jsp" />
					</liferay-ui:section>
				</liferay-ui:tabs>
			</div>

			<aui:script use="liferay-dockbar-add-content,liferay-dockbar-add-page">
				new Liferay.Dockbar.AddContent(
					{
						namespace: '<portlet:namespace />'
					}
				);

				var results = [];

				var templateList = A.one('#<portlet:namespace />templateList');
				var templates = templateList.all('.lfr-page-template');

				templates.each(
					function(item, index, collection) {
						results.push(
							{
								node: item,
								search: item.attr('data-search')
							}
						);
					}
				);

				new Liferay.Dockbar.AddPage(
					{
						entries: templates,
						inputNode: A.one('#<portlet:namespace />searchTemplates'),
						minQueryLength: 0,
						queryDelay: 100,
						resultFilters: 'phraseMatch',
						resultTextLocator: 'search',
						source: results
					}
				);
			</aui:script>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-sign-in-to-continue" />
	</c:otherwise>
</c:choose>