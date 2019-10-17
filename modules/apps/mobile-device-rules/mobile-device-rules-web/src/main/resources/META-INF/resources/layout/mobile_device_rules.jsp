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

<%@ include file="/init.jsp" %>

<%
long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);

Layout selLayout = LayoutLocalServiceUtil.fetchLayout(selPlid);

String className = Layout.class.getName();
long classPK = selLayout.getPlid();
%>

<%@ include file="/layout/mobile_device_rules_header.jspf" %>

<liferay-util:buffer
	var="rootNodeNameLink"
>

	<%
	PortletURL editLayoutSetURL = liferayPortletResponse.createRenderURL();

	editLayoutSetURL.setParameter("selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));
	editLayoutSetURL.setParameter("groupId", String.valueOf(selLayout.getGroupId()));

	Group group = GroupLocalServiceUtil.getGroup(selLayout.getGroupId());

	String rootNodeName = group.getLayoutRootNodeName(selLayout.isPrivateLayout(), locale);
	%>

	<aui:a href='<%= editLayoutSetURL.toString() + "#tab=mobileDeviceRules" %>'><%= HtmlUtil.escape(rootNodeName) %></aui:a>
</liferay-util:buffer>

<%
int mdrRuleGroupInstancesCount = MDRRuleGroupInstanceServiceUtil.getRuleGroupInstancesCount(className, classPK);
%>

<aui:input label='<%= LanguageUtil.format(resourceBundle, "use-the-same-mobile-device-rules-of-the-x", rootNodeNameLink, false) %>' name="inheritRuleGroupInstances" type="toggle-switch" value="<%= mdrRuleGroupInstancesCount == 0 %>" />

<div class="<%= (mdrRuleGroupInstancesCount == 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />inheritRuleGroupInstancesContainer">
	<p class="text-muted">
		<liferay-ui:message arguments="<%= rootNodeNameLink %>" key="mobile-device-rules-are-inhertited-from-x" translateArguments="<%= false %>" />
	</p>
</div>

<div class="<%= (mdrRuleGroupInstancesCount > 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />uniqueRuleGroupInstancesContainer">
	<p class="text-muted">
		<liferay-ui:message key="device-family" />
	</p>

	<liferay-util:include page="/layout/mobile_device_rules_rule_group_instances.jsp" servletContext="<%= application %>">
		<liferay-util:param name="groupId" value="<%= String.valueOf(selLayout.getGroupId()) %>" />
		<liferay-util:param name="className" value="<%= className %>" />
		<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	</liferay-util:include>
</div>

<aui:script>
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />inheritRuleGroupInstances',
		'<portlet:namespace />inheritRuleGroupInstancesContainer'
	);
	Liferay.Util.toggleBoxes(
		'<portlet:namespace />inheritRuleGroupInstances',
		'<portlet:namespace />uniqueRuleGroupInstancesContainer',
		true
	);
</aui:script>