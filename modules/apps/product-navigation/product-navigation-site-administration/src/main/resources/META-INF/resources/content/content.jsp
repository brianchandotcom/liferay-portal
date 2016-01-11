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
			<ul class="nav nav-equal-height nav-nested">
				<li>
					<div class="nav-equal-height-heading">

						<%
						String scopeLabel = null;

						Group curScopeGroup = themeDisplay.getScopeGroup();

						if (curScopeGroup.isLayout()) {
							scopeLabel = StringUtil.shorten(curScopeGroup.getDescriptiveName(locale), 20);
						}
						else {
							scopeLabel = LanguageUtil.get(request, "default-scope");
						}
						%>

						<span><%= scopeLabel %></span>

						<span class="nav-equal-height-heading-field">
							<liferay-ui:icon-menu direction="down" icon="cog" markupView="lexicon" message="" showArrow="<%= false %>">

								<%
								Map<String, Object> data = new HashMap<String, Object>();

								data.put("navigation", Boolean.TRUE.toString());

								PortletURL portletURL = PortalUtil.getControlPanelPortletURL(request, curSite, themeDisplay.getPpid(), 0, 0, PortletRequest.RENDER_PHASE);
								%>

								<liferay-ui:icon
									data="<%= data %>"
									message="default-scope"
									url="<%= portletURL.toString() %>"
								/>

								<%
								for (Layout curScopeLayout : scopeLayouts) {
									Group scopeGroup = curScopeLayout.getScopeGroup();

									portletURL = PortalUtil.getControlPanelPortletURL(request, scopeGroup, themeDisplay.getPpid(), 0, 0, PortletRequest.RENDER_PHASE);
								%>

									<liferay-ui:icon
										data="<%= data %>"
										message="<%= HtmlUtil.escape(curScopeLayout.getName(locale)) %>"
										url="<%= portletURL.toString() %>"
									/>

								<%
								}
								%>

							</liferay-ui:icon-menu>
						</span>
					</div>

					<liferay-application-list:panel-category-body panelCategory="<%= panelCategory %>" />
				</li>
			</ul>
		</c:otherwise>
	</c:choose>
</liferay-application-list:panel-category>