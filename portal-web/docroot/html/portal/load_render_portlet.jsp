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

<%@ include file="/html/portal/init.jsp" %>

<%
Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

portletDisplay.setId(portlet.getPortletId());
portletDisplay.setNamespace(PortalUtil.getPortletNamespace(portlet.getPortletId()));

String url = PortletURLUtil.getRefreshURL(request, themeDisplay);
%>

<div class="loading-animation" id="p_p_id<%= portletDisplay.getNamespace() %>">
	<div id="p_p_id<%= portletDisplay.getNamespace() %>-defaultScreen"></div>
</div>

<aui:script use="aui-base">
	var ns = '<%= portletDisplay.getNamespace() %>';

	Liferay.Portlet.addHTML(
		{
			onComplete: function(portlet, portletId) {
				portlet.refreshURL = '<%= HtmlUtil.escapeJS(url) %>';
			},
			placeHolder: A.one('#p_p_id' + ns),
			url: '<%= HtmlUtil.escapeJS(url) %>'
		}
	);
</aui:script>