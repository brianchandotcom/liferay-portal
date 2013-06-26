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
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

long groupId = selLayout.getGroupId();
String className = Layout.class.getName();
long classPK = selLayout.getPlid();
%>

<%@ include file="/html/portlet/layouts_admin/layout/mobile_device_rules_header.jspf" %>

<%
String rootNodeName = (String)request.getAttribute("edit_pages.jsp-rootNodeName");

PortletURL redirectURL = (PortletURL)request.getAttribute("edit_pages.jsp-redirectURL");

int mdrRuleGroupInstancesCount = MDRRuleGroupInstanceServiceUtil.getRuleGroupInstancesCount(className, classPK);
%>

<aui:input checked="<%= mdrRuleGroupInstancesCount == 0 %>" disabled="<%= mdrRuleGroupInstancesCount > 0 %>" id="inheritRuleGroupInstances" label='<%= LanguageUtil.format(pageContext, "use-the-same-mobile-device-rule-groups-of-the-x-x", new String[] {HtmlUtil.escape(rootNodeName), redirectURL.toString() + "#tab=mobileDeviceRules"}) %>' name="inheritRuleGroupInstances" type="radio" value="<%= true %>" />

<aui:input checked="<%= mdrRuleGroupInstancesCount > 0 %>" id="uniqueRuleGroupInstances" label="define-specific-mobile-rule-groups-for-this-page" name="inheritRuleGroupInstances" type="radio" value="<%= false %>" />

<div class="<%= (mdrRuleGroupInstancesCount == 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />inheritRuleGroupInstancesContainer">
	<div class="alert alert-info">
		<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(rootNodeName), redirectURL.toString()} %>" key="mobile-device-rule-groups-will-be-inhertited-from-x-x" />
	</div>
</div>

<div class="<%= (mdrRuleGroupInstancesCount > 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />uniqueRuleGroupInstancesContainer">
	<%@ include file="/html/portlet/layouts_admin/layout/mobile_device_rules_toolbar.jspf" %>

	<%@ include file="/html/portlet/layouts_admin/layout/mobile_device_rules_rule_group_instances.jspf" %>
</div>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />inheritRuleGroupInstances', '<portlet:namespace />inheritRuleGroupInstancesContainer', '<portlet:namespace />uniqueRuleGroupInstancesContainer');
	Liferay.Util.toggleRadio('<portlet:namespace />uniqueRuleGroupInstances', '<portlet:namespace />uniqueRuleGroupInstancesContainer', '<portlet:namespace />inheritRuleGroupInstancesContainer');
</aui:script>