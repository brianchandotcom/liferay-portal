<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/common/init.jsp" %>

<%@ page import="org.apache.struts.taglib.tiles.ComponentConstants" %>
<%@ page import="org.apache.struts.tiles.ComponentContext" %>

<%
StringBundler sb = (StringBundler)request.getAttribute(WebKeys.LAYOUT_CONTENT);

if ((sb != null) && (themeDisplay.isFacebook() || themeDisplay.isStateExclusive())) {
	sb.writeTo(out);
}
else {
	ComponentContext componentContext = (ComponentContext)request.getAttribute(ComponentConstants.COMPONENT_CONTEXT);

	boolean tilesPopUp = false;

	if (componentContext != null) {
		tilesPopUp = GetterUtil.getBoolean(componentContext.getAttribute("pop_up"));
	}

	if (tilesPopUp || themeDisplay.isStatePopUp() || themeDisplay.isWidget()) {
%>

		<liferay-theme:include page="portal_pop_up.jsp" />

<%
	}
	else {
%>

		<liferay-theme:include page="portal_normal.jsp" />

<%
	}
}

request.removeAttribute(WebKeys.LAYOUT_CONTENT);

PortalMessages.clear(request);
SessionMessages.clear(request);
SessionErrors.clear(request);
%>