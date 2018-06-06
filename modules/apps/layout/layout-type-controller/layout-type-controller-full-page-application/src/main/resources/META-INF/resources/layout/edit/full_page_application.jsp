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

<%@ include file="/layout/edit/init.jsp" %>

<%
String selectedPortletId = StringPool.BLANK;

if (selLayout != null) {
	selectedPortletId = GetterUtil.getString(selLayout.getTypeSettingsProperty("fullPageApplicationPortlet"));
}
%>

<aui:select label='<%= LanguageUtil.get(resourceBundle, "full-page-application") %>' name="TypeSettingsProperties--fullPageApplicationPortlet--">

	<%
	List<Portlet> portlets = (List<Portlet>)request.getAttribute(FullPageApplicationLayoutTypeControllerWebKeys.FULL_PAGE_APPLICATION_PORTLETS);

	for (Portlet portlet : portlets) {
	%>

		<aui:option label="<%= PortalUtil.getPortletLongTitle(portlet, application, locale) %>" selected="<%= Objects.equals(selectedPortletId, portlet.getPortletId()) %>" value="<%= portlet.getPortletId() %>" />

	<%
	}
	%>

</aui:select>