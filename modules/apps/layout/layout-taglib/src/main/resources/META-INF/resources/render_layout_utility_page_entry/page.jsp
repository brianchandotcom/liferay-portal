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

<%@ include file="/render_layout_utility_page_entry/init.jsp" %>

<%
LayoutStructure layoutStructure = (LayoutStructure)request.getAttribute("liferay-layout:render-layout-utility-page-entry:layoutStructure");

LayoutUtilityPageEntry layoutUtilityPageEntry = (LayoutUtilityPageEntry)request.getAttribute("liferay-layout:render-layout-utility-page-entry:layoutUtilityPageEntry");

RenderLayoutUtilityPageEntryDisplayContext renderLayoutUtilityPageEntryDisplayContext = new RenderLayoutUtilityPageEntryDisplayContext(layoutUtilityPageEntry);
%>

<c:if test="<%= layoutStructure != null %>">
	<link data-senna-track="temporary" href="<%= renderLayoutUtilityPageEntryDisplayContext.getHref() %>" rel="stylesheet" type="text/css" />

	<%
	try {
		request.setAttribute(WebKeys.SHOW_PORTLET_TOPPER, Boolean.FALSE);
	%>

		<liferay-util:buffer
			var="content"
		>
			<liferay-layout:render-layout-structure
				layoutStructure="<%= layoutStructure %>"
			/>
		</liferay-util:buffer>

		<%
		LayoutAdaptiveMediaProcessor layoutAdaptiveMediaProcessor = ServletContextUtil.getLayoutAdaptiveMediaProcessor();
		%>

		<%= layoutAdaptiveMediaProcessor.processAdaptiveMediaContent(content) %>

	<%
	}
	finally {
		request.removeAttribute(WebKeys.SHOW_PORTLET_TOPPER);
	}
	%>

</c:if>