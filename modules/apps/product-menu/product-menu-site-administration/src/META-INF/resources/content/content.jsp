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

<%@ include file="/content/init.jsp" %>

<%
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

boolean containsActivePortlet = panelCategoryHelper.containsPortlet(themeDisplay.getPpid(), panelCategory);

String panelPageCategoryId = "panel-manage-" + StringUtil.replace(panelCategory.getKey(), StringPool.PERIOD, StringPool.UNDERLINE);
%>

<a aria-expanded="false" class="collapse-icon <%= containsActivePortlet ? StringPool.BLANK : "collapsed" %> list-group-heading" data-toggle="collapse" href="#<%= panelPageCategoryId %>">
	<liferay-ui:message key="content" />
</a>

<div class="collapse <%= containsActivePortlet ? "in" : StringPool.BLANK %>" id="<%= panelPageCategoryId %>">
	<div class="list-group-item">

		<%
		List<Layout> scopeLayouts = new ArrayList<Layout>();

		Group curSite = themeDisplay.getSiteGroup();

		scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId(), false));
		scopeLayouts.addAll(LayoutLocalServiceUtil.getScopeGroupLayouts(curSite.getGroupId(), true));

		String scopeLabel = null;

		Group curScopeGroup = themeDisplay.getScopeGroup();

		if (curScopeGroup.isLayout()) {
			Layout scopeLayout = LayoutLocalServiceUtil.getLayout(curScopeGroup.getClassPK());

			scopeLabel = StringUtil.shorten(scopeLayout.getName(locale), 20);
		}
		else {
			scopeLabel = LanguageUtil.get(request, "default");
		}
		%>

		<c:if test="<%= !scopeLayouts.isEmpty() %>">
			<div class="lfr-title-scope-selector nobr">
				<liferay-ui:message key="scope" />:
				<liferay-ui:icon-menu direction="down" icon="" message="<%= scopeLabel %>">

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("navigation", Boolean.TRUE.toString());
					%>

					<liferay-ui:icon
						data="<%= data %>"
						iconCssClass="<%= curSite.getIconCssClass() %>"
						message="default"
						url='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", curSite.getGroupId()) %>'
					/>

					<%
					for (Layout curScopeLayout : scopeLayouts) {
						Group scopeGroup = curScopeLayout.getScopeGroup();
					%>

						<liferay-ui:icon
							data="<%= data %>"
							iconCssClass="<%= scopeGroup.getIconCssClass() %>"
							message="<%= HtmlUtil.escape(curScopeLayout.getName(locale)) %>"
							url='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsGroupId", scopeGroup.getGroupId()) %>'
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</div>
		</c:if>

		<ul aria-labelledby="<%= panelPageCategoryId %>" class="nav nav-equal-height" role="menu">

			<%
			for (PanelApp panelApp : panelAppRegistry.getPanelApps(panelCategory)) {
			%>

				<c:if test="<%= panelApp.hasAccessPermission(permissionChecker, themeDisplay.getScopeGroup()) %>">
					<liferay-application-list:panel-app
						panelApp="<%= panelApp %>"
						panelCategory="<%= panelCategory %>"
					/>
				</c:if>

			<%
			}
			%>

		</ul>
	</div>
</div>