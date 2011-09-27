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

MDRRule rule = (MDRRule)row.getObject();

PortletURL portletURL = (PortletURL) request.getAttribute("view_rule_group_rules.jsp-portletURL");
%>

<liferay-ui:icon-menu>
    <c:if test="<%= MDRRuleGroupPermissionUtil.contains(permissionChecker, rule.getRuleGroupId(), ActionKeys.UPDATE) %>">
        <liferay-portlet:renderURL varImpl="editURL">
            <portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule" />
            <portlet:param name="redirect" value="<%= portletURL.toString() %>" />
            <portlet:param name="<%= MDRPortletConstants.MDR_RULE_ID %>" value="<%= String.valueOf(rule.getRuleId()) %>" />
        </liferay-portlet:renderURL>

        <liferay-ui:icon image="edit" url="<%= editURL.toString() %>"/>
    </c:if>

    <c:if test="<%= MDRRuleGroupPermissionUtil.contains(permissionChecker, rule.getRuleGroupId(), ActionKeys.UPDATE) %>">
        <portlet:actionURL var="deleteURL">
            <portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule" />
            <portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
            <portlet:param name="<%= MDRPortletConstants.MDR_RULE_ID %>" value="<%= String.valueOf(rule.getRuleId()) %>" />
            <portlet:param name="redirect" value="<%= portletURL.toString() %>" />
        </portlet:actionURL>

        <liferay-ui:icon image="delete" url="<%= deleteURL.toString() %>"/>
    </c:if>
</liferay-ui:icon-menu>