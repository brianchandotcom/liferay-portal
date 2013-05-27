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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
LayoutSet selLayoutSet = ((LayoutSet)request.getAttribute("edit_pages.jsp-selLayoutSet"));

long groupId = selLayoutSet.getGroupId();
String className = LayoutSet.class.getName();
long classPK = selLayoutSet.getLayoutSetId();
%>

<%@ include file="/html/portlet/layouts_admin/layout/mobile_rule_groups_header.jspf" %>

<%@ include file="/html/portlet/layouts_admin/layout/mobile_rule_groups_toolbar.jspf" %>

<%@ include file="/html/portlet/layouts_admin/layout/mobile_rule_groups_rule_group_instances.jspf" %>