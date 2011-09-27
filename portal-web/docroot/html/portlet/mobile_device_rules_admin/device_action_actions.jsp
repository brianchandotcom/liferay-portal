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

<%@ include file="/html/portlet/mobile_device_rules_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MDRAction action = (MDRAction) row.getObject();

PortletURL portletURL = (PortletURL) request.getAttribute("view_rule_group_instance_actions.jsp-portletURL");
%>

<liferay-ui:icon-menu>

    <c:if test="<%= MDRRuleGroupInstancePermissionUtil.contains(permissionChecker, action.getRuleGroupInstanceId(), ActionKeys.UPDATE) %>">
        <portlet:renderURL var="editActionURL">
            <portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_action" />
            <portlet:param name="<%= MDRPortletConstants.MDR_ACTION_ID %>" value="<%= Long.toString(action.getActionId()) %>" />
            <portlet:param name="redirect" value="<%= portletURL.toString() %>" />
        </portlet:renderURL>

        <liferay-ui:icon image="edit" url="<%= editActionURL.toString() %>"/>
    </c:if>

    <c:if test="<%= MDRRuleGroupInstancePermissionUtil.contains(permissionChecker, action.getRuleGroupInstanceId(), ActionKeys.UPDATE) %>">
        <portlet:actionURL var="deleteURL">
            <portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_action" />
            <portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
            <portlet:param name="<%= MDRPortletConstants.MDR_ACTION_ID %>" value="<%= Long.toString(action.getActionId()) %>" />
            <portlet:param name="redirect" value="<%= portletURL.toString() %>" />
        </portlet:actionURL>

        <liferay-ui:icon image="delete" url="<%= deleteURL.toString() %>"/>
    </c:if>

</liferay-ui:icon-menu>