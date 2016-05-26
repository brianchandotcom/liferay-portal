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
String tabs1 = ParamUtil.getString(request, "tabs1", "categories");

boolean showSearch = ParamUtil.getBoolean(request, "showSearch");

PortletURL viewURL = renderResponse.createRenderURL();

viewURL.setParameter("mvcRenderCommandName", "/shopping/view");
%>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">

		<%
		PortletURL categoriesURL = PortletURLUtil.clone(viewURL, renderResponse);

		categoriesURL.setParameter("tabs1", "categories");
		%>

		<aui:nav-item href="<%= categoriesURL.toString() %>" label="categories" selected='<%= tabs1.equals("categories") %>' />

		<%
		PortletURL cartURL = PortletURLUtil.clone(viewURL, renderResponse);

		cartURL.setParameter("tabs1", "cart");
		%>

		<aui:nav-item href="<%= cartURL.toString() %>" label="cart" selected='<%= tabs1.equals("cart") %>' />

		<c:if test="<%= !user.isDefaultUser() %>">

			<%
			PortletURL ordersURL = PortletURLUtil.clone(viewURL, renderResponse);

			ordersURL.setParameter("tabs1", "orders");
			%>

			<aui:nav-item href="<%= ordersURL.toString() %>" label="orders" selected='<%= tabs1.equals("orders") %>' />
		</c:if>

		<c:if test="<%= ShoppingPermission.contains(permissionChecker, scopeGroupId, ActionKeys.MANAGE_COUPONS) %>">

			<%
			PortletURL couponsURL = PortletURLUtil.clone(viewURL, renderResponse);

			couponsURL.setParameter("tabs1", "coupons");
			%>

			<aui:nav-item href="<%= couponsURL.toString() %>" label="coupons" selected='<%= tabs1.equals("coupons") %>' />
		</c:if>
	</aui:nav>

	<c:if test="<%= showSearch %>">

		<%
		PortletURL searchURL = PortletURLUtil.clone(viewURL, renderResponse);

		searchURL.setParameter("tabs1", tabs1);
		%>

		<aui:nav-bar-search>
			<aui:form action="<%= searchURL.toString() %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>