<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/portlet/init.jsp" %>

<%
String productMenuState = SessionClicks.get(request, "com.liferay.product.navigation.product.menu.web_productMenuState", "closed");
String pagesTreeState = SessionClicks.get(request, "com.liferay.product.navigation.product.menu.web_pagesTreeState", "closed");
%>

<div class="lfr-product-menu-sidebar lfr-applications-menu" id="productMenuSidebar">

	<div class="sidebar-body">
		<c:choose>
			<c:when test='<%= Objects.equals(productMenuState, "open") && (!Objects.equals(pagesTreeState, "open") || productMenuDisplayContext.isLayoutsTreeDisabled() || !productMenuDisplayContext.isShowLayoutsTree()) %>'>
				<liferay-util:include page="/portlet/product_menu.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= Objects.equals(productMenuState, "open") && Objects.equals(pagesTreeState, "open") %>'>
				<div class="pages-tree">
					<liferay-util:include page="/portlet/pages_tree.jsp" servletContext="<%= application %>">
						<liferay-util:param name="redirect" value="<%= themeDisplay.getURLCurrent() %>" />
					</liferay-util:include>
				</div>
			</c:when>
		</c:choose>
	</div>
</div>