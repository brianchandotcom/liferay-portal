<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/portal/init.jsp" %>

<portlet:defineObjects />

<%
String portletTitle = HtmlUtil.escape(PortalUtil.getPortletTitle(renderResponse));

if (portletTitle == null) {
	portletTitle = LanguageUtil.get(request, "portlet");
}

String key = "is-temporarily-unavailable";

Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

if (portlet != null) {
	key = StringBundler.concat(key, StringPool.OPEN_BRACKET, portlet.getPortletId(), StringPool.CLOSE_BRACKET);
}
%>

<div class="alert alert-danger">
	<liferay-ui:message arguments="<%= portletTitle %>" key="<%= key %>" translateArguments="<%= false %>" />
</div>