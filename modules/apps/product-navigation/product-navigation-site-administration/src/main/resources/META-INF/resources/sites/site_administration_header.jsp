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
SiteAdministrationPanelCategoryDisplayContext siteAdministrationPanelCategoryDisplayContext = new SiteAdministrationPanelCategoryDisplayContext(liferayPortletRequest, liferayPortletResponse, null);

Group group = siteAdministrationPanelCategoryDisplayContext.getGroup();
PanelCategory panelCategory = siteAdministrationPanelCategoryDisplayContext.getPanelCategory();

PortletURL portletURL = PortletURLFactoryUtil.create(request, ProductNavigationProductMenuPortletKeys.PRODUCT_NAVIGATION_PRODUCT_MENU, RenderRequest.RENDER_PHASE);

portletURL.setParameter("mvcPath", "/portlet/pages_tree.jsp");
portletURL.setParameter("selPpid", portletDisplay.getId());
portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
%>

<c:choose>
	<c:when test="<%= group != null %>">

		<%
		int childPanelCategoriesSize = GetterUtil.getInteger(request.getAttribute("product_menu.jsp-childPanelCategoriesSize"));
		%>

		<c:choose>
			<c:when test="<%= childPanelCategoriesSize > 1 %>">
				<a aria-controls="<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" aria-expanded="<%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() %>" class="panel-toggler <%= (group != null) ? "collapse-icon collapse-icon-middle " : StringPool.BLANK %> <%= siteAdministrationPanelCategoryDisplayContext.isCollapsedPanel() ? StringPool.BLANK : "collapsed" %> site-administration-toggler" data-parent="#<portlet:namespace />Accordion" data-qa-id="productMenuSiteAdministrationPanelCategory" data-toggle="liferay-collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Collapse" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelCategory.getKey()) %>Toggler" <%= (group != null) ? "role=\"button\"" : StringPool.BLANK %>>
					<%@ include file="/sites/site_administration_header_no_collapsible.jspf" %>

					<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

					<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
				</a>
			</c:when>
			<c:otherwise>
				<span class="collapse-icon pr-2" data-qa-id="productMenuSiteAdministrationPanelCategory">
					<%@ include file="/sites/site_administration_header_no_collapsible.jspf" %>
				</span>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="<%= siteAdministrationPanelCategoryDisplayContext.isShowSiteSelector() %>">
		<div class="collapsed panel-toggler">
			<span class="site-name">
				<liferay-ui:message key="choose-a-site" />
			</span>
		</div>
	</c:when>
</c:choose>