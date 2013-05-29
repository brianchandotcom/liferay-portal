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

<%@ include file="/html/portlet/mobile_device_rules/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

MDRAction action = (MDRAction)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION);

long actionId = BeanParamUtil.getLong(action, request, "actionId");

String editorJSP = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_EDITOR_JSP);
String type = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_TYPE);

MDRRuleGroupInstance ruleGroupInstance = (MDRRuleGroupInstance)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE);
MDRRuleGroup ruleGroup = (MDRRuleGroup)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP);

String title = null;

if (action == null) {
	title = LanguageUtil.format(pageContext, "new-action-for-x", ruleGroup.getName(locale), false);
}
else {
	StringBundler sb = new StringBundler(5);

	sb.append(action.getName(locale));
	sb.append(StringPool.SPACE);
	sb.append(StringPool.OPEN_PARENTHESIS);
	sb.append(ruleGroup.getName(locale));
	sb.append(StringPool.CLOSE_PARENTHESIS);

	title = sb.toString();
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= (action == null) %>"
	title="<%= title %>"
/>

<c:if test="<%= action == null %>">
	<div class="alert alert-info">
		<liferay-ui:message key="action-help" />
	</div>
</c:if>

<portlet:actionURL var="editActionURL">
	<portlet:param name="struts_action" value="/mobile_device_rules/edit_action" />
</portlet:actionURL>

<aui:form action="<%= editActionURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (action == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="actionId" type="hidden" value="<%= actionId %>" />
	<aui:input name="ruleGroupInstanceId" type="hidden" value="<%= ruleGroupInstance.getRuleGroupInstanceId() %>" />

	<liferay-ui:error exception="<%= ActionTypeException.class %>" message="please-select-a-valid-action-type" />
	<liferay-ui:error exception="<%= NoSuchActionException.class %>" message="action-does-not-exist" />
	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist" />
	<liferay-ui:error exception="<%= NoSuchRuleGroupInstanceException.class %>" message="rule-group-instance-does-not-exist" />

	<aui:model-context bean="<%= action %>" model="<%= MDRAction.class %>" />

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<aui:select changesContext="<%= true %>" name="type" onChange='<%= renderResponse.getNamespace() + "changeType();" %>'>
			<aui:option disabled="<%= true %>" label="select-an-action-type" selected="<%= Validator.isNull(type) %>" />

			<%
			for (ActionHandler actionHandler : ActionHandlerManagerUtil.getActionHandlers()) {
	   		%>

				<aui:option label="<%= actionHandler.getType() %>" selected="<%= type.equals(actionHandler.getType()) %>" />

			<%
			}
			%>

		</aui:select>

		<div id="<%= renderResponse.getNamespace() %>typeSettings">
			<c:if test="<%= Validator.isNotNull(editorJSP) %>">
				<liferay-util:include page="<%= editorJSP %>" />
			</c:if>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />changeDisplay',
		function() {
			var A = AUI();

			A.io.request(
				<portlet:resourceURL var="siteURLLayoutsURL">
					<portlet:param name="struts_action" value="/mobile_device_rules/site_url_layouts" />
				</portlet:resourceURL>

				'<%= siteURLLayoutsURL.toString() %>',
				{
					data: {
						actionGroupId: document.<portlet:namespace />fm.<portlet:namespace />groupId.value,
						actionPlid: document.<portlet:namespace />fm.<portlet:namespace />actionPlid.value
					},
					on: {
						success: function(id, obj) {
							var layouts = A.one('#<portlet:namespace />layouts');

							if (layouts) {
								layouts.html(this.get('responseData'));
							}
						}
					}
				}
			);
		},
		['aui-io']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />changeType',
		function() {
			var A = AUI();

			A.io.request(
				<portlet:resourceURL var="editorURL">
					<portlet:param name="struts_action" value="/mobile_device_rules/edit_action_editor" />
				</portlet:resourceURL>

				'<%= editorURL.toString() %>',
				{
					data: {
						type: document.<portlet:namespace />fm.<portlet:namespace />type.value,
						<%= "actionId" %>: <%= actionId %>
					},
					on: {
						success: function(id, obj) {
							var typeSettings = A.one('#<portlet:namespace />typeSettings');

							if (typeSettings) {
								typeSettings.html(this.get('responseData'));
							}
						}
					}
				}
			);
		},
		['aui-io']
	);
</aui:script>