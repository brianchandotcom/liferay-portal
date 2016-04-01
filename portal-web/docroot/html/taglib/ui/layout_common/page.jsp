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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean includeStaticPortlets = GetterUtil.getBoolean(request.getAttribute("liferay-ui:layout-common:includeStaticPortlets"));
boolean includeWebServerDisplayNode = GetterUtil.getBoolean(request.getAttribute("liferay-ui:layout-common:includeWebServerDisplayNode"));
%>

<c:if test="<%= includeStaticPortlets %>">

	<%
	for (String portletId : PropsValues.LAYOUT_STATIC_PORTLETS_ALL) {
		if (PortletLocalServiceUtil.hasPortlet(company.getCompanyId(), portletId)) {
	%>

			<liferay-portlet:runtime portletName="<%= portletId %>" />

	<%
		}
	}
	%>

</c:if>

<c:if test="<%= includeWebServerDisplayNode %>">
	<div class="alert alert-info">
		<liferay-ui:message key="node" />: <%= StringUtil.toLowerCase(PortalUtil.getComputerName()) + StringPool.COLON + PortalUtil.getPortalLocalPort(false) %>
	</div>
</c:if>

<form action="#" id="hrefFm" method="post" name="hrefFm">
	<span></span>
</form>