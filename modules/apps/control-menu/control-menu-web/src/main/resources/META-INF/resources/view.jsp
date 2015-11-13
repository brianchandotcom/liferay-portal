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

Group group = null;

if (layout != null) {
	group = layout.getGroup();
}
%>

<c:if test="<%= !layout.isTypeControlPanel() && !group.isControlPanel() && !controlMenuCategories.isEmpty() %>">
	<div class="control-menu">
		<c:if test="<%= (user.isSetupComplete() || themeDisplay.isImpersonated()) && themeDisplay.isShowStagingIcon() %>">
			<div class="control-menu-level-2">
				<div class="container-fluid-1280">

					<%
					String renderPortletBoundary = GetterUtil.getString(request.getAttribute(WebKeys.RENDER_PORTLET_BOUNDARY));

					request.setAttribute(WebKeys.RENDER_PORTLET_BOUNDARY, Boolean.FALSE.toString());
					%>

					<liferay-portlet:runtime portletName="<%= PortletKeys.STAGING_BAR %>" />

					<%
					request.setAttribute(WebKeys.RENDER_PORTLET_BOUNDARY, renderPortletBoundary);
					%>

				</div>
			</div>
		</c:if>

		<div class="control-menu-level-1">
			<div class="container-fluid-1280">
				<ul class="control-menu-nav" data-namespace="<portlet:namespace />" id="<portlet:namespace />controlMenu">

					<%
					for (ControlMenuCategory controlMenuCategory : controlMenuCategories) {
						List<ControlMenuEntry> controlMenuEntries = controlMenuEntryRegistry.getControlMenuEntries(controlMenuCategory, request);

						for (ControlMenuEntry controlMenuEntry : controlMenuEntries) {
							if (controlMenuEntry.include(request, new PipingServletResponse(pageContext))) {
								continue;
							}
					%>

							<li>
								<aui:icon
									cssClass='<%= "control-menu-icon " + controlMenuEntry.getLinkCssClass(request) %>'
									image="<%= controlMenuEntry.getIconCssClass(request) %>"
									markupView="lexicon"
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
</c:if>

<aui:script position="inline" use="liferay-control-menu">
	Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');
</aui:script>