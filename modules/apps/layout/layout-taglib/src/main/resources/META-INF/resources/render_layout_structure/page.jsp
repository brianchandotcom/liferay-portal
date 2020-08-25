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

<%@ include file="/render_layout_structure/init.jsp" %>

<%
RenderLayoutStructureDisplayContext renderLayoutStructureDisplayContext = (RenderLayoutStructureDisplayContext)request.getAttribute(RenderLayoutStructureDisplayContext.class.getName());

request.setAttribute("render_layout_structure.jsp-childrenItemIds", renderLayoutStructureDisplayContext.getMainChildrenItemIds());
%>

<liferay-util:include page="/render_layout_structure/render_layout_structure.jsp" servletContext="<%= application %>" />