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
String redirect = ParamUtil.getString(request, "redirect");

MDRRuleGroupInstance ruleGroupInstance =  null;
MDRRuleGroup ruleGroup = null;
String className = StringPool.BLANK;
long classPK = 0;

if (resourceRequest != null) {
	ruleGroupInstance = (MDRRuleGroupInstance) resourceRequest.getAttribute(MDRPortletConstants.MDR_RULE_GROUP_INSTANCE);
	ruleGroup = (MDRRuleGroup) resourceRequest.getAttribute(MDRPortletConstants.MDR_RULE_GROUP);
	className = ParamUtil.getString(resourceRequest, MDRPortletConstants.CLASS_NAME);
	classPK = ParamUtil.getLong(resourceRequest, MDRPortletConstants.CLASS_PK);
}
else if (renderRequest != null) {
	ruleGroupInstance = (MDRRuleGroupInstance) renderRequest.getAttribute(MDRPortletConstants.MDR_RULE_GROUP_INSTANCE);
	ruleGroup = (MDRRuleGroup) renderRequest.getAttribute(MDRPortletConstants.MDR_RULE_GROUP);
	className = ParamUtil.getString(renderRequest, MDRPortletConstants.CLASS_NAME);
	classPK = ParamUtil.getLong(renderRequest, MDRPortletConstants.CLASS_PK);
}

if (ruleGroupInstance == null) {
	ruleGroupInstance = MDRRuleGroupInstanceLocalServiceUtil.fetchRuleGroupInstance(className, classPK, ruleGroup.getRuleGroupId());
}

boolean isAdd = Validator.isNull(ruleGroupInstance);

long ruleGroupInstanceId = 0;
long ruleGroupId = 0;

if (!isAdd) {
	ruleGroupInstanceId = ruleGroupInstance.getRuleGroupInstanceId();
	ruleGroupId = ruleGroupInstance.getRuleGroupId();
	className = ruleGroupInstance.getClassName();
	classPK = ruleGroupInstance.getClassPK();
}
else {
	if (ruleGroup != null) {
		ruleGroupId =  ruleGroup.getRuleGroupId();
	}
}

PortletURL portletURL = null;

if (renderResponse != null) {
	portletURL = renderResponse.createRenderURL();
}
if (resourceResponse != null) {
	portletURL = resourceResponse.createRenderURL();
}

portletURL.setParameter("struts_action", "/mobile_device_rules_admin/edit_rule_group_instance");
portletURL.setParameter(MDRPortletConstants.MDR_RULE_GROUP_INSTANCE_ID, String.valueOf(ruleGroupInstanceId));
portletURL.setParameter(MDRPortletConstants.MDR_RULE_GROUP_ID, String.valueOf(ruleGroupId));
portletURL.setParameter(MDRPortletConstants.CLASS_NAME, className);
portletURL.setParameter(MDRPortletConstants.CLASS_PK, String.valueOf(classPK));
portletURL.setParameter("redirect", redirect);

request.setAttribute("view_rule_group_instance_actions.jsp-portletURL", portletURL);
%>

<liferay-portlet:renderURL varImpl="viewURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_group"/>
	<portlet:param name="<%= MDRPortletConstants.MDR_RULE_GROUP_ID%>" value="<%= String.valueOf(ruleGroupId) %>"/>
	<portlet:param name="redirect" value="<%= portletURL.toString() %>"/>
</liferay-portlet:renderURL>

</br>

<h5><%= LanguageUtil.get(pageContext, "rule-group") %></h5>

<div class="float-container lfr-current-device-rule-group device-rule-group-details">
	<div class="selected-device-rule-group device-rule-group-title"><%= ruleGroup.getName(locale) %></div>

	<dl class="device-rule-group-fields">
		<dt>
			<liferay-ui:message key="description" />
		</dt>
		<dd>
			<%= ruleGroup.getDescription(locale) %>
		</dd>
	</dl>

	<liferay-ui:icon
		image="edit"
		label="<%= true %>"
		message="edit-rule-group"
		url='<%= viewURL.toString() %>'
	/>
</div>

<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist" />
<liferay-ui:error exception="<%= NoSuchRuleGroupInstanceException.class %>" message="rule-group-instance-does-not-exist" />

<aui:model-context bean="<%= ruleGroupInstance %>" model="<%= MDRRuleGroupInstance.class %>" />

<aui:fieldset>
	<aui:input name="priority" />
</aui:fieldset>

<c:if test="<%= !isAdd %>">

	<h5><%= LanguageUtil.get(pageContext, "actions") %></h5>

	<liferay-portlet:renderURL varImpl="addURL">
		<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_action"/>
		<portlet:param name="<%= MDRPortletConstants.MDR_RULE_GROUP_INSTANCE_ID %>" value="<%= String.valueOf(ruleGroupInstanceId) %>" />
		<portlet:param name="redirect" value="<%= portletURL.toString() %>"/>
	</liferay-portlet:renderURL>

	<liferay-util:include page="/html/portlet/mobile_device_rules_admin/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value="view"/>
		<liferay-util:param name="addURL" value="<%=addURL.toString()%>"/>
	</liferay-util:include>

	<liferay-ui:search-container
		curParam="cur1"
		delta="<%= 5 %>"
		deltaConfigurable="<%= false %>"
		emptyResultsMessage="no-actions-are-configured-for-this-rule-group-instance"
		headerNames="name,description,type"
		iteratorURL="<%= portletURL %>"
	>

		<liferay-ui:search-container-results
			results="<%= MDRActionLocalServiceUtil.getActions(ruleGroupInstanceId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			total="<%= MDRActionLocalServiceUtil.getActionsCount(ruleGroupInstanceId) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.mobiledevicerules.model.MDRAction"
			escapedModel="<%= true %>"
			keyProperty="actionId"
			modelVar="action"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_action"/>
				<portlet:param name="redirect" value="<%= portletURL.toString() %>"/>
				<portlet:param name="<%= MDRPortletConstants.MDR_ACTION_ID %>" value="<%= String.valueOf(action.getActionId()) %>"/>
			</liferay-portlet:renderURL>

			<%@ include file="/html/portlet/mobile_device_rules_admin/device_action_columns.jspf" %>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator type="more"/>
	</liferay-ui:search-container>
</c:if>

<aui:button-row>
	<aui:button type="submit" value="save" />
	<aui:button href="<%= redirect %>" value="cancel" />
</aui:button-row>