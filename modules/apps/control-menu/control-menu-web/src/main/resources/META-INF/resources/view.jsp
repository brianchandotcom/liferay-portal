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
List<ControlMenuCategory> controlMenuCategories = (List<ControlMenuCategory>)request.getAttribute(ControlMenuWebKeys.CONTROL_MENU_CATEGORIES);
ControlMenuEntryRegistry controlMenuEntryRegistry = (ControlMenuEntryRegistry)request.getAttribute(ControlMenuWebKeys.CONTROL_MENU_ENTRY_REGISTRY);
%>

<c:if test="<%= !controlMenuCategories.isEmpty() %>">
	<div class="control-menu" data-qa-id="controlMenu" id="<portlet:namespace/>ControlMenu">
		<div class="control-menu-level-1">
			<div class="container-fluid-1280">
				<ul class="control-menu-nav control-menu-nav-level-1" data-namespace="<portlet:namespace />" id="<portlet:namespace />controlMenu">

					<%
					for (ControlMenuCategory controlMenuCategory : controlMenuCategories) {
						List<ControlMenuEntry> controlMenuEntries = controlMenuEntryRegistry.getControlMenuEntries(controlMenuCategory, request);

						for (ControlMenuEntry controlMenuEntry : controlMenuEntries) {
							if (controlMenuEntry.include(request, new PipingServletResponse(pageContext))) {
								continue;
							}

							String controlMenuEntryCssClass = "";

							String controlMenuEntryKey = controlMenuEntry.getKey();

							if (controlMenuEntryKey.equals(ManageLayoutControlMenuEntry.class.getName())) {
								controlMenuEntryCssClass = "edit-layout-link";
							}
							else if (controlMenuEntryKey.equals(ToggleControlsControlMenuEntry.class.getName())) {
								controlMenuEntryCssClass = "edit-controls-toggle visible-xs";
							}
					%>

							<li class="<%= controlMenuEntryCssClass %>">
								<liferay-ui:icon
									cssClass='<%= "control-menu-icon " + controlMenuEntry.getLinkCssClass(request) %>'
									data="<%= controlMenuEntry.getData(request) %>"
									icon="<%= controlMenuEntry.getIconCssClass(request) %>"
									label="<%= false %>"
									linkCssClass='<%= "control-menu-icon " + controlMenuEntry.getLinkCssClass(request) %>'
									markupView="lexicon"
									message="<%= controlMenuEntry.getLabel(locale) %>"
									url="<%= controlMenuEntry.getURL(request) %>"
								/>
							</li>

					<%
						}
					}
					%>

				</ul>
			</div>
		</div>
	</div>

	<aui:script position="inline" use="liferay-control-menu">
		Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');
	</aui:script>
</c:if>