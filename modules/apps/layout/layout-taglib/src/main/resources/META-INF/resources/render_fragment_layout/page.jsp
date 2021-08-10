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
Map<String, Object> fieldValues = (Map<String, Object>)request.getAttribute("liferay-layout:render-fragment-layout:fieldValues");
LayoutStructure layoutStructure = (LayoutStructure)request.getAttribute("liferay-layout:render-fragment-layout:layoutStructure");
String mainItemId = (String)request.getAttribute("liferay-layout:render-fragment-layout:mainItemId");
String mode = (String)request.getAttribute("liferay-layout:render-fragment-layout:mode");
boolean showPreview = GetterUtil.getBoolean(request.getAttribute("liferay-layout:render-fragment-layout:showPreview"));
%>

<liferay-util:dynamic-include key="com.liferay.layout,taglib#/render_fragment_layout/page.jsp#pre" />

<%
try {
	request.setAttribute(WebKeys.SHOW_PORTLET_TOPPER, Boolean.TRUE);
%>

	<liferay-util:buffer
		var="content"
	>
		<liferay-layout:render-layout-structure
			fieldValues="<%= fieldValues %>"
			layoutStructure="<%= layoutStructure %>"
			mainItemId="<%= mainItemId %>"
			mode="<%= mode %>"
			showPreview="<%= showPreview %>"
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

<liferay-util:dynamic-include key="com.liferay.layout,taglib#/render_fragment_layout/page.jsp#post" />