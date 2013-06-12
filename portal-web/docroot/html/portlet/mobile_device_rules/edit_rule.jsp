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
String backURL = ParamUtil.getString(request, "backURL");

MDRRule rule = (MDRRule)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE);

long ruleId = BeanParamUtil.getLong(rule, request, "ruleId");

String type = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_TYPE);
String editorJSP = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_EDITOR_JSP);

MDRRuleGroup ruleGroup = (MDRRuleGroup)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP);

long ruleGroupId = BeanParamUtil.getLong(ruleGroup, request, "ruleGroupId");

String title = StringPool.BLANK;

if (ruleGroup != null) {
	title = LanguageUtil.format(pageContext, "new-rule-for-x", ruleGroup.getName(locale), false);

	if (rule != null) {
		title = rule.getName(locale) + " (" + ruleGroup.getName(locale) + ")";
	}
}
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= false %>"
	title="<%= title %>"
/>

<c:if test="<%= rule == null %>">
	<div class="alert alert-info">
		<liferay-ui:message key="rule-help" />
	</div>
</c:if>

<portlet:actionURL var="editRuleURL">
	<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule" />
</portlet:actionURL>

<aui:form action="<%= editRuleURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (rule == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="ruleGroupId" type="hidden" value="<%= ruleGroupId %>" />
	<aui:input name="ruleId" type="hidden" value="<%= ruleId %>" />

	<liferay-ui:error exception="<%= NoSuchRuleException.class %>" message="rule-does-not-exist" />
	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist" />
	<liferay-ui:error exception="<%= UnknownRuleHandlerException.class %>" message="please-select-a-rule-type" />

	<aui:model-context bean="<%= rule %>" model="<%= MDRRule.class %>" />

	<c:if test='<%= !PluginPackageUtil.isInstalled("wurfl-web") %>'>
		<div class="alert alert-block">
			<liferay-ui:message key="there-is-no-device-recognition-provider-installed" />
		</div>
	</c:if>

	<aui:fieldset>
		<aui:input name="name" />

		<aui:input name="description" />

		<aui:select changesContext="<%= true %>" name="type" showEmptyOption="<%= true %>">

			<%
			for (String ruleHandlerType : RuleGroupProcessorUtil.getRuleHandlerTypes()) {
			%>

				<aui:option label="<%= ruleHandlerType %>" selected="<%= type.equals(ruleHandlerType) %>" />

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

<aui:script use="aui-io">
	var typeNode = A.one('#<portlet:namespace />type');
	var typeSettings = A.one('#<portlet:namespace />typeSettings');

	typeNode.on(
		'change',
		function(event) {
			A.io.request(
				<portlet:resourceURL var="editorURL">
					<portlet:param name="struts_action" value="/mobile_device_rules/edit_rule_editor" />
				</portlet:resourceURL>

				'<%= editorURL.toString() %>',
				{
					data: {
						ruleId: <%= ruleId %>,
						type: typeNode.val()
					},
					on: {
						success: function(event, id, obj) {
							var response = this.get('responseData');

							if (typeSettings) {
								typeSettings.html(response);
							}
						}
					}
				}
			);
		}
	);
</aui:script>