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

<%@ include file="/render_fragment_layout/init.jsp" %>

<%
LayoutStructure layoutStructure = (LayoutStructure)request.getAttribute("liferay-layout:render-fragment-layout:layoutStructure");
String mainItemId = (String)request.getAttribute("liferay-layout:render-fragment-layout:mainItemId");

RenderFragmentLayoutDisplayContext renderFragmentLayoutDisplayContext = (RenderFragmentLayoutDisplayContext)request.getAttribute("liferay-layout:render-fragment-layout:renderFragmentLayoutDisplayContext");
%>

<liferay-util:html-top>
	<%= renderFragmentLayoutDisplayContext.getPortletHeaderPaths() %>
</liferay-util:html-top>

<%
try {
	request.setAttribute(WebKeys.SHOW_PORTLET_TOPPER, Boolean.TRUE);

	LayoutStructureItem layoutStructureItem = layoutStructure.getLayoutStructureItem(mainItemId);

	request.setAttribute("render_layout_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
%>

	<liferay-util:include page="/render_fragment_layout/render_layout_structure.jsp" servletContext="<%= application %>" />

<%
}
finally {
	request.removeAttribute(WebKeys.SHOW_PORTLET_TOPPER);
}
%>

<liferay-util:html-bottom>
	<%= renderFragmentLayoutDisplayContext.getPortletFooterPaths() %>
</liferay-util:html-bottom>