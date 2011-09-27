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

MDRRuleGroup ruleGroup = (MDRRuleGroup) renderRequest.getAttribute(MDRPortletConstants.MDR_RULE_GROUP);
MDRRule rule = (MDRRule) renderRequest.getAttribute(MDRPortletConstants.MDR_RULE);
String type = (String) renderRequest.getAttribute(MDRPortletConstants.TYPE);
String editorJSP = (String) renderRequest.getAttribute(MDRPortletConstants.EDITOR_JSP);

boolean isAdd = Validator.isNull(rule);

long ruleGroupId = ruleGroup.getRuleGroupId();
long ruleId = 0;

if (!isAdd) {
	ruleId = rule.getRuleId();
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter(
	"struts_action", "/mobile_device_rules_admin/edit_rule");
portletURL.setParameter(
	MDRPortletConstants.MDR_RULE_ID, String.valueOf(ruleId));
portletURL.setParameter(
	MDRPortletConstants.MDR_RULE_GROUP_ID, String.valueOf(ruleGroupId));
portletURL.setParameter("redirect", redirect);

request.setAttribute("view_rule_actions.jsp-portletURL", portletURL);
%>

<c:if test='<%=isAdd%>'>
    <liferay-ui:header
        title="add-rule"
        backURL="<%= redirect %>"
    />
</c:if>
<c:if test='<%=!isAdd%>'>
    <liferay-ui:header
        title="edit-rule"
        backURL="<%= redirect %>"
    />
</c:if>

<portlet:actionURL var="editRuleURL">
	<portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule" />
    <portlet:param name="redirect" value="<%= redirect %>" />
</portlet:actionURL>

<aui:form action="<%= editRuleURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveRule();" %>'>

    <aui:input name="<%= Constants.CMD %>" type="hidden" />
    <aui:input name="<%= MDRPortletConstants.MDR_RULE_GROUP_ID %>" type="hidden" value="<%= ruleGroupId %>" />
    <aui:input name="<%= MDRPortletConstants.MDR_RULE_ID %>" type="hidden" value="<%= ruleId %>" />

    <liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="rule-group-does-not-exist" />
    <liferay-ui:error exception="<%= NoSuchRuleException.class %>" message="rule-does-not-exist" />
	<liferay-ui:error exception="<%= UnknownRuleHandlerException.class %>" message="please-select-a-rule-type" />

	<aui:model-context bean="<%= rule %>" model="<%= MDRRule.class %>" />

	<aui:fieldset>
        <aui:input name="name" />
        <aui:input name="description" />
        <aui:select name="type" changesContext="<%= true %>" onChange='<%= renderResponse.getNamespace() + "changeType();" %>'>
            <aui:option label="select-a-type" selected="<%= StringPool.BLANK.equals(type) %>" disabled="<%=true%>"/>
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
            <c:if test="<%=Validator.isNotNull(editorJSP)%>">
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
    <portlet:param name="struts_action" value="/mobile_device_rules_admin/edit_rule_editor" />
</portlet:resourceURL>

<aui:script>
	Liferay.provide(
		window,
        '<portlet:namespace />changeType',
        function() {
            var A = AUI();

            var type = document.<portlet:namespace />fm.<portlet:namespace /><%= MDRPortletConstants.TYPE %>.value;

            A.io(
                '<%= editorURL.toString() %>',
                {
                    data: {
                        <%= MDRPortletConstants.TYPE %>: type,
                        <%= MDRPortletConstants.MDR_RULE_ID %>: <%= ruleId %>
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

	function <portlet:namespace />saveRule() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (isAdd) ? Constants.ADD : Constants.EDIT %>";
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>