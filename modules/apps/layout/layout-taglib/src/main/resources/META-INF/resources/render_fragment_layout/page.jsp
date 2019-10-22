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
JSONArray structureJSONArray = (JSONArray)request.getAttribute("liferay-layout:render-fragment-layout:structureJSONArray");
%>

<c:if test="<%= structureJSONArray != null %>">
	<div class="layout-content portlet-layout" id="main-content" role="main">

		<%
		try {
			request.setAttribute(WebKeys.SHOW_PORTLET_TOPPER, Boolean.TRUE);

			request.setAttribute("render_layout_data_structure.jsp-structureJSONArray", structureJSONArray);
		%>

			<liferay-util:include page="/render_fragment_layout/render_layout_data_structure.jsp" servletContext="<%= application %>" />

		<%
		}
		finally {
			request.removeAttribute(WebKeys.SHOW_PORTLET_TOPPER);
		}
		%>

	</div>
</c:if>