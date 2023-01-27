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