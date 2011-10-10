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

MDRRuleGroupInstance ruleGroupInstance = (MDRRuleGroupInstance)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE);
MDRAction action = (MDRAction)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION);
String type = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_TYPE);
String editorJSP = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_EDITOR_JSP);

boolean isAdd = Validator.isNull(action);

long actionId = 0;

if (!isAdd) {
	actionId = action.getActionId();
}

Collection<ActionHandler> actionHandlers = ActionHandlerManagerUtil.getActionHandlers();
%>

<c:if test='<%= isAdd %>'>
	<liferay-ui:header
		title="add-action"
		backURL="<%= redirect %>"
	/>
</c:if>
<c:if test='<%= !isAdd %>'>
	<liferay-ui:header
		title="edit-action"
		backURL="<%= redirect %>"
	/>
</c:if>

<portlet:actionURL var="editActionURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_action" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editActionURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= isAdd ? Constants.ADD : Constants.UPDATE %>" />

	<aui:input name="actionId" type="hidden" value="<%= actionId %>" />
	<aui:input name="ruleGroupInstanceId" type="hidden" value="<%= ruleGroupInstance.getRuleGroupInstanceId() %>" />

	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="unable-to-edit-a-non-existing-device-rule-group" />
	<liferay-ui:error exception="<%= NoSuchRuleGroupInstanceException.class %>" message="unable-to-edit-a-non-existing-device-rule" />
	<liferay-ui:error exception="<%= NoSuchActionException.class %>" message="unable-to-edit-a-non-existing-device-action" />

	<aui:model-context bean="<%= action %>" model="<%= MDRAction.class %>" />

	<aui:fieldset>
		<aui:input name="name" />
		<aui:input name="description" />
		<aui:select name="type" changesContext="<%= true %>" onChange='<%= renderResponse.getNamespace() + "changeType();" %>'>
			<aui:option label="select-an-action-type" selected="<%= StringPool.BLANK.equals(type) %>" disabled="<%= true %>" />
			<%
			for (ActionHandler actionHandler : actionHandlers) {
				boolean selected = actionHandler.getType().equals(type);
	   		%>
				<aui:option label="<%= actionHandler.getType() %>" selected="<%= selected %>" />
			<%
			}
			%>
		</aui:select>
		<div id="<%= renderResponse.getNamespace() %>typeSettings">
			<c:if test="<%=Validator.isNotNull(editorJSP) %>">
				<c:import url="<%=editorJSP%>" />
			</c:if>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<portlet:resourceURL var="editorURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_action_editor" />
</portlet:resourceURL>

<aui:script>

	Liferay.provide(
		window,
		'<portlet:namespace />changeType',
		function() {
			var A = AUI();

			var type = document.<portlet:namespace />fm.<portlet:namespace />type.value;

			A.io(
				'<%= editorURL.toString() %>',
				{
					data: {
						type: type,
						<%= MDRPortletConstants.MDR_ACTION_ID %>: <%= actionId %>
					},
					method: AUI.defaults.io.method,
					on: {
						complete: <portlet:namespace />displayForm
					}
				}
			)
		},
		['aui-base']
	);

	function <portlet:namespace />displayForm(id, obj) {
		document.getElementById('<portlet:namespace />typeSettings').innerHTML=obj.responseText;
	}
</aui:script>

<portlet:resourceURL var="layoutsURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/view_group_layouts" />
</portlet:resourceURL>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />changeDisplay',
		function() {
			var A = AUI();

			var groupId = document.<portlet:namespace />fm.<portlet:namespace />groupId.value;
			var layoutId = document.<portlet:namespace />fm.<portlet:namespace />originalLayoutId.value;

			A.io(
				'<%= layoutsURL.toString() %>',
				{
					data: {
						actionGroupId: groupId,
						actionLayoutId: layoutId
					},
					method: AUI.defaults.io.method,
					on: {
						complete: <portlet:namespace />displayLayouts
					}
				}
			)
		},
		['aui-base']
	);

	function <portlet:namespace />displayLayouts(id, obj) {
		document.getElementById('<portlet:namespace />layouts').innerHTML=obj.responseText;
	};
</aui:script>