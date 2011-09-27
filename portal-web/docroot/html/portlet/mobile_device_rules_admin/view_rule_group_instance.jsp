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
PortletURL portletURL = renderResponse.createRenderURL();

long classPK = ParamUtil.getLong(request, "classPK");
String className = ParamUtil.getString(request, "className");

portletURL.setParameter("struts_action", "/mobile_device_rules_admin/view_rule_group_instance");
portletURL.setParameter("classPK", String.valueOf(classPK));
portletURL.setParameter("className", className);

request.setAttribute("view.jsp-portletURL", portletURL);

pageContext.setAttribute("portletURL", portletURL);

String portletURLString = portletURL.toString();

Layout selLayout = null;
LayoutSet selLayoutSet = null;

if (className.equals(LayoutSet.class.getName())) {
	selLayoutSet = LayoutSetLocalServiceUtil.getLayoutSet(classPK);
}
else if (className.equals(Layout.class.getName())) {
	selLayout = LayoutLocalServiceUtil.getLayout(classPK);
}

OrderByComparator orderByComparator = new RuleGroupInstancePriorityComparator();
%>

<liferay-ui:header
	title="manage-rule-group-instances"
/>

<liferay-portlet:renderURL varImpl="addURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_group_instance"/>
	<portlet:param name="<%= MDRPortletConstants.CLASS_NAME %>" value="<%= className %>"/>
	<portlet:param name="<%= MDRPortletConstants.CLASS_PK %>" value="<%= String.valueOf(classPK) %>"/>
	<portlet:param name="redirect" value="<%= currentURL %>"/>
</liferay-portlet:renderURL>

<liferay-util:include page="/html/portlet/mobile_device_rules_admin/toolbar.jsp">
	<liferay-util:param name="toolbarItem" value="view"/>
	<liferay-util:param name="addURL" value="<%=addURL.toString()%>"/>
</liferay-util:include>

<liferay-ui:search-container
	curParam="cur1"
	delta="<%= 20 %>"
	deltaConfigurable="<%= false %>"
	emptyResultsMessage="no-rule-group-instances-are-configured"
	headerNames="name,description,priority"
	iteratorURL="<%= portletURL %>"
>

	<liferay-ui:search-container-results
		results="<%= MDRRuleGroupInstanceServiceUtil.getRuleGroupInstances(className, classPK, searchContainer.getStart(), searchContainer.getEnd(), orderByComparator) %>"
		total="<%= MDRRuleGroupInstanceServiceUtil.getRuleGroupInstancesCount(className, classPK) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance"
		escapedModel="<%= true %>"
		keyProperty="ruleGroupInstanceId"
		modelVar="ruleGroupInstance"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_group_instance"/>
			<portlet:param name="redirect" value="<%= portletURL.toString() %>"/>
			<portlet:param name="<%= MDRPortletConstants.MDR_RULE_GROUP_INSTANCE_ID %>" value="<%= String.valueOf(ruleGroupInstance.getRuleGroupInstanceId()) %>"/>
			<portlet:param name="<%= MDRPortletConstants.CLASS_NAME %>" value="<%= className %>"/>
			<portlet:param name="<%= MDRPortletConstants.CLASS_PK %>" value="<%= String.valueOf(classPK) %>"/>
		</liferay-portlet:renderURL>

		<%@ include file="/html/portlet/mobile_device_rules_admin/device_rule_group_instance_columns.jspf" %>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator type="more"/>
</liferay-ui:search-container>
