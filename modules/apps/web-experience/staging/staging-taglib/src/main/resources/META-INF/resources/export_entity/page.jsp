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

<%@ include file="/export_entity/init.jsp" %>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getScopeGroup(), ActionKeys.EXPORT_IMPORT_PORTLET_INFO) %>">

	<%
	PortletURL portletURL = PortletURLFactoryUtil.create(request, ExportImportPortletKeys.EXPORT_IMPORT, PortletRequest.ACTION_PHASE);

	portletURL.setParameter(ActionRequest.ACTION_NAME, "exportImportEntity");
	portletURL.setParameter("mvcRenderCommandName", "exportImportEntity");
	portletURL.setParameter("cmd", Constants.EXPORT);
	portletURL.setParameter("className", className);
	portletURL.setParameter("classPK", String.valueOf(classPK));
	%>

	<liferay-ui:icon
		message="export-entity"
		url="<%= portletURL.toString() %>"
	/>
</c:if>