<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.LARFileException" %>
<%@ page import="com.liferay.portal.LARTypeException" %>
<%@ page import="com.liferay.portal.LayoutImportException" %>
<%@ page import="com.liferay.portal.NoSuchLayoutException" %>
<%@ page import="com.liferay.portal.NoSuchPortletItemException" %>
<%@ page import="com.liferay.portal.NoSuchResourceException" %>
<%@ page import="com.liferay.portal.PortletIdException" %>
<%@ page import="com.liferay.portal.PortletItemNameException" %>
<%@ page import="com.liferay.portal.ResourcePrimKeyException" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataException" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandler" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerControl" %>
<%@ page import="com.liferay.portal.kernel.lar.PortletDataHandlerKeys" %>
<%@ page import="com.liferay.portal.kernel.lar.UserIdStrategy" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletModeFactory" %>
<%@ page import="com.liferay.portal.service.permission.RolePermissionUtil" %>
<%@ page import="com.liferay.portlet.PortletQNameUtil" %>
<%@ page import="com.liferay.portlet.portletconfiguration.util.PublicRenderParameterConfiguration" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.RoleSearch" %>
<%@ page import="com.liferay.portlet.rolesadmin.search.RoleSearchTerms" %>
<%@ page import="com.liferay.portlet.rolesadmin.util.RolesAdminUtil" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.OrganizationSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserGroupSearchTerms" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserSearch" %>
<%@ page import="com.liferay.portlet.usersadmin.search.UserSearchTerms" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletResource);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:if test='<%= themeDisplay.isStatePopUp() && SessionMessages.contains(renderRequest, portletName + ".doConfigure") %>'>
	<aui:script use="aui-base">
		if (window.parent) {
			var curPortletBoundaryId = '#p_p_id_<%= portletResource %>_';

			var data;

			<c:if test="<%= !selPortlet.isAjaxable() %>">
				data = {
					portletAjaxable: false
				};
			</c:if>

			Liferay.Util.getOpener().Liferay.Portlet.refresh(curPortletBoundaryId, data);
		}
	</aui:script>
</c:if>