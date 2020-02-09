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
JSONObject dataJSONObject = (JSONObject)request.getAttribute("liferay-layout:render-fragment-layout:dataJSONObject");
%>

<c:if test="<%= dataJSONObject != null %>">
	<div class="layout-content portlet-layout" id="main-content" role="main">

		<%
		try {
			request.setAttribute(WebKeys.SHOW_PORTLET_TOPPER, Boolean.TRUE);

			RenderFragmentLayoutDisplayContext renderFragmentLayoutDisplayContext = new RenderFragmentLayoutDisplayContext(request, response);

			request.setAttribute("render_layout_data_structure.jsp-renderFragmentLayoutDisplayContext", renderFragmentLayoutDisplayContext);
		%>

			<%= renderFragmentLayoutDisplayContext.getPortletPaths() %>

			<c:choose>
				<c:when test="<%= LayoutDataConverter.isLatestVersion(dataJSONObject) %>">

					<%
					LayoutStructure layoutStructure = LayoutStructure.of(dataJSONObject.toString());

					request.setAttribute("render_react_editor_layout_data_structure.jsp-layoutStructure", layoutStructure);

					LayoutStructureItem layoutStructureItem = layoutStructure.getMainLayoutStructureItem();

					request.setAttribute("render_react_editor_layout_data_structure.jsp-childrenItemIds", layoutStructureItem.getChildrenItemIds());
					%>

					<liferay-util:include page="/render_fragment_layout/render_react_editor_layout_data_structure.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>

					<%
					request.setAttribute("render_layout_data_structure.jsp-dataJSONObject", dataJSONObject);
					%>

					<liferay-util:include page="/render_fragment_layout/render_layout_data_structure.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>

		<%
		}
		finally {
			request.removeAttribute(WebKeys.SHOW_PORTLET_TOPPER);
		}
		%>

	</div>
</c:if>