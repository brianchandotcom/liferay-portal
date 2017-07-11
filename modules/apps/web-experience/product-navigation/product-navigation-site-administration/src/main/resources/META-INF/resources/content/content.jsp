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
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);
PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);
%>

<liferay-application-list:panel-category panelCategory="<%= panelCategory %>" showBody="<%= false %>">

	<%
	Group curSite = themeDisplay.getSiteGroup();

	List<Layout> scopeLayouts = LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId());
	%>

	<c:choose>
		<c:when test="<%= scopeLayouts.isEmpty() %>">
			<liferay-application-list:panel-category-body panelCategory="<%= panelCategory %>" />
		</c:when>
		<c:otherwise>

			<%
			PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

			List<PanelApp> panelApps = panelAppRegistry.getPanelApps(panelCategory, themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroup());
			%>

			<c:if test="<%= !panelApps.isEmpty() %>">
				<ul class="nav nav-equal-height nav-nested">
					<li>
						<div class="scope-selector">

							<%
							Group curScopeGroup = themeDisplay.getScopeGroup();
							%>

							<span class="scope-name">
								<c:choose>
									<c:when test="<%= curScopeGroup.isLayout() %>">
										<%= curScopeGroup.getDescriptiveName(locale) %> (<liferay-ui:message key="scope" />)
									</c:when>
									<c:otherwise>
										<liferay-ui:message key="default-scope" />
									</c:otherwise>
								</c:choose>
							</span>
							<span class="nav-equal-height-heading-field">
								<div class="dropdown">
									<a aria-expanded="false" class="dropdown-toggle icon-monospaced" data-toggle="dropdown" href="javascript:;">
										<aui:icon image="cog" markupView="lexicon" />
									</a>

									<%
									Map<String, Object> data = new HashMap<String, Object>();

									String portletId = themeDisplay.getPpid();

									if (Validator.isNull(portletId) || !panelCategoryHelper.containsPortlet(portletId, PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, curSite)) {
										portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, curSite);
									}

									PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, curSite, portletId, 0, 0, PortletRequest.RENDER_PHASE);
									%>

									<ul class="dropdown-menu dropdown-menu-center">
										<li class="<%= (curScopeGroup.getGroupId() == curSite.getGroupId()) ? "active" : StringPool.BLANK %>">
											<a class="truncate-text" href="<%= portletURL.toString() %>">
												<liferay-ui:message key="default-scope" />
											</a>
										</li>

										<%
										for (Layout curScopeLayout : scopeLayouts) {
											Group scopeGroup = curScopeLayout.getScopeGroup();

											if (Validator.isNull(portletId) || !panelCategoryHelper.containsPortlet(portletId, PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, scopeGroup)) {
												portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION_CONTENT, permissionChecker, scopeGroup);
											}

											portletURL = PortalUtil.getControlPanelPortletURL(request, scopeGroup, portletId, 0, 0, PortletRequest.RENDER_PHASE);
										%>

											<li class="<%= (curScopeGroup.getGroupId() == scopeGroup.getGroupId()) ? "active" : StringPool.BLANK %>">
												<a class="truncate-text" href="<%= portletURL.toString() %>">
													<liferay-ui:message key="<%= HtmlUtil.escape(curScopeLayout.getName(locale)) %>" />
												</a>
											</li>

										<%
										}
										%>

									</ul>
							</div>

						<liferay-application-list:panel-category-body panelCategory="<%= panelCategory %>" />
					</li>
				</ul>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-application-list:panel-category>