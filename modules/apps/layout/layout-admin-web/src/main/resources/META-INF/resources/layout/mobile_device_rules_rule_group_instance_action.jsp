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
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = currentURL;

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

MDRRuleGroupInstance mdrRuleGroupInstance = (MDRRuleGroupInstance)row.getObject();

MDRRuleGroup mdrRuleGroup = MDRRuleGroupLocalServiceUtil.getMDRRuleGroup(mdrRuleGroupInstance.getRuleGroupId());
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= MDRRuleGroupInstancePermission.contains(permissionChecker, mdrRuleGroupInstance.getRuleGroupInstanceId(), ActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL portletName="<%= PortletKeys.MOBILE_DEVICE_RULES %>" varImpl="viewRuleGroupInstanceActionsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/view_actions" />
			<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
			<portlet:param name="redirect" value='<%= HttpUtil.setParameter(redirect, liferayPortletResponse.getNamespace() + "historyKey", "mobileDeviceRules") %>' />
			<portlet:param name="ruleGroupInstanceId" value="<%= String.valueOf(mdrRuleGroupInstance.getRuleGroupInstanceId()) %>" />
		</liferay-portlet:renderURL>

		<%
		String taglibActionHandler = liferayPortletResponse.getNamespace() + "mobileDeviceActionHandler('" + viewRuleGroupInstanceActionsURL.toString() + "');";
		%>

		<liferay-ui:icon message="manage-actions" onClick="<%= taglibActionHandler %>" url="javascript:;" />
	</c:if>

	<c:if test="<%= MDRRuleGroupInstancePermission.contains(permissionChecker, mdrRuleGroupInstance.getRuleGroupInstanceId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= MDRRuleGroupInstance.class.getName() %>"
			modelResourceDescription="<%= mdrRuleGroup.getName(locale) %>"
			resourcePrimKey="<%= String.valueOf(mdrRuleGroupInstance.getRuleGroupInstanceId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon message="permissions" url="<%= permissionsURL %>" useDialog="<%= true %>" />
	</c:if>

	<c:if test="<%= MDRRuleGroupInstancePermission.contains(permissionChecker, mdrRuleGroupInstance.getRuleGroupInstanceId(), ActionKeys.DELETE) %>">
		<liferay-ui:icon-delete url='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteRuleGroupInstance(" + mdrRuleGroupInstance.getRuleGroupInstanceId() + ");" %>' />
	</c:if>
</liferay-ui:icon-menu>