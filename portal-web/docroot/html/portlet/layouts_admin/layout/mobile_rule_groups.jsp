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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

Group group = (Group)request.getAttribute("edit_pages.jsp-group");
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");
long selPlid = ((Long)request.getAttribute("edit_pages.jsp-selPlid")).longValue();

long companyId = selLayout.getCompanyId();
long groupId = selLayout.getGroupId();

String className = Layout.class.getName();
long classPK = selLayout.getPlid();
%>

<%@ include file="/html/portlet/layouts_admin/mobile_device_rules_manage.jspf" %>