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
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

MDRRuleGroup ruleGroup = (MDRRuleGroup)renderRequest.getAttribute(MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP);

long ruleGroupId = BeanParamUtil.getLong(ruleGroup, request, "ruleGroupId");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(((ruleGroup == null) ? LanguageUtil.get(request, "new-device-family") : ruleGroup.getName(locale)));
%>

<portlet:actionURL name="/mobile_device_rules/edit_rule_group" var="editRuleGroupURL">
	<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_rule_group" />
</portlet:actionURL>

<aui:form action="<%= editRuleGroupURL %>" cssClass="container-fluid-1280" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (ruleGroup == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="backURL" type="hidden" value="<%= backURL %>" />
	<aui:input name="ruleGroupId" type="hidden" value="<%= ruleGroupId %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />

	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="device-family-does-not-exist" />

	<c:if test="<%= ruleGroup == null %>">
		<div class="alert alert-info">
			<liferay-ui:message key="device-family-help" />
		</div>
	</c:if>

	<aui:model-context bean="<%= ruleGroup %>" model="<%= MDRRuleGroup.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="name" />

			<aui:input name="description" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>