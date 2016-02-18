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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<c:if test="<%= layout != null %>">

			<%
			Group group = layout.getGroup();

			boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
			boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);
			%>

			<c:if test="<%= !group.isControlPanel() && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
				<div class="add-content-menu" data-qa-id="addPanelBody" id="<portlet:namespace />addPanelContainer">

					<%
					boolean stateMaximized = ParamUtil.getBoolean(request, "stateMaximized");

					LayoutTypeController layoutTypeController = layoutTypePortlet.getLayoutTypeController();

					boolean hasAddApplicationsPermission = !stateMaximized && layout.isTypePortlet() && !layout.isLayoutPrototypeLinkActive() && !layoutTypeController.isFullPageDisplayable() && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission));

					boolean hasAddContentPermission = hasAddApplicationsPermission && !group.isLayoutPrototype();

					String selectedTab = GetterUtil.getString(SessionClicks.get(request, "com.liferay.product.navigation.control.menu.web_addPanelTab", "content"));
					%>

					<div aria-multiselectable="true" class="panel-group" id="<portlet:namespace />Accordion" role="tablist">
						<c:if test="<%= hasAddApplicationsPermission %>">
							<div class="panel">
								<div class="panel-heading" id="<portlet:namespace />addApplicationHeading" role="tab">
									<div class="panel-title">
										<div aria-controls="#<portlet:namespace />addApplicationCollapse" aria-expanded="<%= selectedTab.equals("applications") %>" class="collapse-icon collapse-icon-middle panel-toggler <%= selectedTab.equals("applications") ? StringPool.BLANK : "collapsed" %>" class="collapsed" data-parent="#<portlet:namespace />Accordion" data-toggle="collapse" href="#<portlet:namespace />addApplicationCollapse" role="button">
											<span class="category-name"><liferay-ui:message key="applications" /></span>

											<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

											<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
										</div>
									</div>
								</div>

								<div aria-expanded="false" aria-labelledby="<portlet:namespace />addApplicationHeading" class="collapse panel-collapse <%= selectedTab.equals("applications") ? "in" : StringPool.BLANK %>" id="<portlet:namespace />addApplicationCollapse" role="tabpanel">
									<div class="panel-body">
										<liferay-util:include page="/add_application.jsp" servletContext="<%= application %>" />
									</div>
								</div>
							</div>
						</c:if>

						<c:if test="<%= hasAddContentPermission %>">
							<div class="panel">
								<div class="panel-heading" id="<portlet:namespace />addContentHeading" role="tab">
									<div class="panel-title">
										<div aria-controls="#<portlet:namespace />addContentCollapse" aria-expanded="<%= selectedTab.equals("content") %>" class="collapse-icon collapse-icon-middle panel-toggler <%= selectedTab.equals("content") ? StringPool.BLANK : "collapsed" %>" class="collapsed" data-parent="#<portlet:namespace />Accordion" data-toggle="collapse" href="#<portlet:namespace />addContentCollapse" role="button">
											<span class="category-name"><liferay-ui:message key="content" /></span>

											<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

											<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
										</div>
									</div>
								</div>

								<div aria-expanded="false" aria-labelledby="<portlet:namespace />addContentHeading" class="collapse panel-collapse <%= selectedTab.equals("content") ? "in" : StringPool.BLANK %>" id="<portlet:namespace />addContentCollapse" role="tabpanel">
									<div class="panel-body">
										<liferay-util:include page="/add_content.jsp" servletContext="<%= application %>" />
									</div>
								</div>
							</div>
						</c:if>
					</div>

					<span class="added-message hide" id="<portlet:namespace />addedMessage">
						<span class="alert-success message">
							<aui:icon image="check" markupView="lexicon" /> <span id="<portlet:namespace />portletName"></span> <liferay-ui:message key="added" />

							<a class="content-link" href="javascript:;" id="<portlet:namespace />contentLink"><liferay-ui:message key="skip-to-content" /></a>
						</span>
					</span>
				</div>
			</c:if>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-sign-in-to-continue" />
	</c:otherwise>
</c:choose>