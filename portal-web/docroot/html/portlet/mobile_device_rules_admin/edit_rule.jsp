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
String backURL = ParamUtil.getString(request, "backURL");

MDRRuleGroup ruleGroup = (MDRRuleGroup)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_GROUP);
MDRRule rule = (MDRRule)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE);
String type = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_TYPE);
String editorJSP = (String)renderRequest.getAttribute(WebKeys.MOBILE_DEVICE_RULES_RULE_EDITOR_JSP);

String title = StringPool.BLANK;
long ruleId = 0;
long ruleGroupId = ruleGroup.getRuleGroupId();

if (ruleGroup != null) {
	title = LanguageUtil.format(pageContext, "new-rule-for-x", ruleGroup.getName(locale), false);

	if (rule != null) {
		ruleId = rule.getRuleId();

		title = rule.getName(locale) + " (" + ruleGroup.getName(locale) + ")";
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/mobile_device_rules_admin/edit_rule");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("ruleGroupId", String.valueOf(ruleGroupId));
portletURL.setParameter("ruleId", String.valueOf(ruleId));

request.setAttribute("view_rule_actions.jsp-portletURL", portletURL);
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= false %>"
	title='<%= title %>'
/>

<portlet:actionURL var="editRuleURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editRuleURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (rule == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="ruleGroupId" type="hidden" value="<%= ruleGroupId %>" />
	<aui:input name="ruleId" type="hidden" value="<%= ruleId %>" />

	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist" />
	<liferay-ui:error exception="<%= NoSuchRuleException.class %>" message="rule-does-not-exist" />
	<liferay-ui:error exception="<%= UnknownRuleHandlerException.class %>" message="please-select-a-rule-type" />

	<aui:model-context bean="<%= rule %>" model="<%= MDRRule.class %>" />

	<aui:fieldset>
		<aui:input name="name" />
		<aui:input name="description" />

		<aui:select name="type" changesContext="<%= true %>" onChange='<%= renderResponse.getNamespace() + "changeType();" %>'>
			<aui:option label="select-a-type" selected="<%= StringPool.BLANK.equals(type) %>" disabled="<%= true %>" />

			<%
			for (String ruleHandlerType : RuleGroupProcessorUtil.getRuleHandlerTypes()) {
				boolean selected = ruleHandlerType.equals(type);
			%>
				<aui:option label="<%= ruleHandlerType %>" selected="<%= selected %>" />
			<%
			}
	   		%>
		</aui:select>

		<div id="<%= renderResponse.getNamespace() %>typeSettings">
			<c:if test="<%=Validator.isNotNull(editorJSP) %>">
				<c:import url="<%= editorJSP %>" />
			</c:if>
		</div>
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
		<aui:button href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<portlet:resourceURL var="editorURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_editor" />
</portlet:resourceURL>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />changeType',
		function() {
			var A = AUI();

			var typeNode = A.one('#<portlet:namespace /><%= MDRPortletConstants.TYPE %>');

			A.io(
				'<%= editorURL.toString() %>',
				{
					data: {
						<%= MDRPortletConstants.MDR_RULE_ID %>: <%= ruleId %>,
						<%= MDRPortletConstants.TYPE %>: typeNode.val()
					},
					method: AUI.defaults.io.method,
					on: {
						complete: function <portlet:namespace />displayForm(id, obj) {
							var typeSettingsNode = A.one('#<portlet:namespace />typeSettings');

							if (typeSettingsNode) {
								typeSettingsNode.setContent(obj.responseText);
							}
						}
					}
				}
			)
		},
		['aui-base']
	);
</aui:script>