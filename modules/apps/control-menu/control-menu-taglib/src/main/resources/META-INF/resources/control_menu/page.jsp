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

<%@ include file="/control_menu/init.jsp" %>

<%
List<ControlMenuCategory> controlMenuCategories = (List<ControlMenuCategory>)request.getAttribute("liferay-control-menu:control-menu:control-menu-categories");
ControlMenuEntryRegistry controlMenuEntryRegistry = (ControlMenuEntryRegistry)request.getAttribute("liferay-control-menu:control-menu:control-menu-entry-registry");
%>

<c:if test="<%= !controlMenuCategories.isEmpty() %>">
	<div class="control-menu control-menu-level-1" data-qa-id="controlMenu" id="<portlet:namespace/>ControlMenu">
		<div class="container-fluid-1280">
			<ul class="control-menu-level-1-nav control-menu-nav" data-namespace="<portlet:namespace />" data-qa-id="header" id="<portlet:namespace />controlMenu">

				<%
				for (ControlMenuCategory controlMenuCategory : controlMenuCategories) {
				%>

					<li class="control-menu-nav-item <%= controlMenuCategory.getKey() %>-controls-group">
						<ul class="control-menu-nav">

							<%
							List<ControlMenuEntry> controlMenuEntries = controlMenuEntryRegistry.getControlMenuEntries(controlMenuCategory, request);

							for (ControlMenuEntry controlMenuEntry : controlMenuEntries) {
								if (controlMenuEntry.includeIcon(request, new PipingServletResponse(pageContext))) {
									continue;
								}
							%>

								<liferay-ui:icon
									data="<%= controlMenuEntry.getData(request) %>"
									icon="<%= controlMenuEntry.getIconCssClass(request) %>"
									label="<%= false %>"
									linkCssClass='<%= "control-menu-icon " + controlMenuEntry.getLinkCssClass(request) %>'
									markupView="lexicon"
									message="<%= controlMenuEntry.getLabel(locale) %>"
									url="<%= controlMenuEntry.getURL(request) %>"
								/>

							<%
							}
							%>

						</ul>
					</li>

				<%
				}
				%>

			</ul>
		</div>

		<div class="control-menu-body">

			<%
			for (ControlMenuCategory controlMenuCategory : controlMenuCategories) {
				List<ControlMenuEntry> controlMenuEntries = controlMenuEntryRegistry.getControlMenuEntries(controlMenuCategory, request);

				for (ControlMenuEntry controlMenuEntry : controlMenuEntries) {
					controlMenuEntry.includeBody(request, new PipingServletResponse(pageContext));
				}
			}
			%>

		</div>
	</div>

	<aui:script use="liferay-control-menu">
		Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');
	</aui:script>
</c:if>