<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/render_fragment_layout/init.jsp" %>

<%
String mainItemId = (String)request.getAttribute("liferay-layout:render-fragment-layout:mainItemId");
String mode = (String)request.getAttribute("liferay-layout:render-fragment-layout:mode");
boolean showPreview = GetterUtil.getBoolean(request.getAttribute("liferay-layout:render-fragment-layout:showPreview"));
%>

<liferay-util:dynamic-include key="com.liferay.layout,taglib#/render_fragment_layout/page.jsp#pre" />

<%
try {
	request.setAttribute(WebKeys.SHOW_PORTLET_TOPPER, Boolean.FALSE);
%>

	<liferay-util:buffer
		var="content"
	>
		<liferay-layout:render-layout-structure
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