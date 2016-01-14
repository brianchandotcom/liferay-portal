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

<liferay-util:buffer var="rootNodeNameLink">

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

<aui:input checked="<%= mdrRuleGroupInstancesCount == 0 %>" disabled="<%= mdrRuleGroupInstancesCount > 0 %>" id="inheritRuleGroupInstances" label='<%= LanguageUtil.format(request, "use-the-same-mobile-device-rules-of-the-x", rootNodeNameLink, false) %>' name="inheritRuleGroupInstances" type="radio" value="<%= true %>" />

<aui:input checked="<%= mdrRuleGroupInstancesCount > 0 %>" id="uniqueRuleGroupInstances" label="define-specific-mobile-device-rules-for-this-page" name="inheritRuleGroupInstances" type="radio" value="<%= false %>" />

<div class="<%= (mdrRuleGroupInstancesCount == 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />inheritRuleGroupInstancesContainer">
	<div class="alert alert-info">
		<liferay-ui:message arguments="<%= rootNodeNameLink %>" key="mobile-device-rules-are-inhertited-from-x" translateArguments="<%= false %>" />
	</div>
</div>

<div class="<%= (mdrRuleGroupInstancesCount > 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />uniqueRuleGroupInstancesContainer">
	<liferay-util:include page="/layout/mobile_device_rules_rule_group_instances.jsp" servletContext="<%= application %>">
		<liferay-util:param name="groupId" value="<%= String.valueOf(selLayout.getGroupId()) %>" />
		<liferay-util:param name="className" value="<%= className %>" />
		<liferay-util:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	</liferay-util:include>
</div>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />inheritRuleGroupInstances', '<portlet:namespace />inheritRuleGroupInstancesContainer', '<portlet:namespace />uniqueRuleGroupInstancesContainer');
	Liferay.Util.toggleRadio('<portlet:namespace />uniqueRuleGroupInstances', '<portlet:namespace />uniqueRuleGroupInstancesContainer', '<portlet:namespace />inheritRuleGroupInstancesContainer');
</aui:script>