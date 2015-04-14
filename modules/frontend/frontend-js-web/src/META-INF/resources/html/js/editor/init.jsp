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

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.JavaConstants" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %>

<%@ page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletResponse" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-theme:defineObjects />

<%
PortletRequest portletRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);

PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

String namespace = StringPool.BLANK;

boolean auiFormUseNamespace = GetterUtil.getBoolean((String)request.getAttribute("aui:form:useNamespace"), true);

if ((portletResponse != null) && auiFormUseNamespace) {
	namespace = GetterUtil.getString(request.getAttribute("aui:form:portletNamespace"), portletResponse.getNamespace());
}

String currentURL = null;

if ((portletRequest != null) && (portletResponse != null)) {
	PortletURL currentURLObj = PortletURLUtil.getCurrent(PortalUtil.getLiferayPortletRequest(portletRequest), PortalUtil.getLiferayPortletResponse(portletResponse));

	currentURL = currentURLObj.toString();
}
else {
	currentURL = PortalUtil.getCurrentURL(request);
}
%>