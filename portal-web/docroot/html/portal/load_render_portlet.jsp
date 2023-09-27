<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<%
Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

portletDisplay.setId(portlet.getPortletId());
portletDisplay.setNamespace(PortalUtil.getPortletNamespace(portlet.getPortletId()));

String url = PortletURLUtil.getRefreshURL(request, themeDisplay, false);

Map<String, String[]> parameters = PortletURLUtil.getRefreshURLParameters(request);
%>

<div class="loading-animation" id="p_p_id<%= portletDisplay.getNamespace() %>">
	<div id="p_p_id<%= portletDisplay.getNamespace() %>-defaultScreen"></div>
</div>

<aui:script use="aui-base">
	var ns = '<%= portletDisplay.getNamespace() %>';

	Liferay.Portlet.addHTML(
		{
			data: <%= JSONFactoryUtil.looseSerializeDeep(parameters) %>,
			onComplete: function(portlet, portletId) {
				portlet.refreshURL = '<%= HtmlUtil.escapeJS(url) %>';
			},
			placeHolder: A.one('#p_p_id' + ns),
			url: '<%= HtmlUtil.escapeJS(url) %>'
		}
	);
</aui:script>