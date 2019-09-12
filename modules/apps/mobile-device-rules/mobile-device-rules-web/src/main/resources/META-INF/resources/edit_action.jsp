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

MDRAction action = (MDRAction)renderRequest.getAttribute(MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION);

long actionId = BeanParamUtil.getLong(action, request, "actionId");

String editorJSP = (String)renderRequest.getAttribute(MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_EDITOR_JSP);
String type = (String)renderRequest.getAttribute(MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_ACTION_TYPE);

MDRRuleGroupInstance ruleGroupInstance = (MDRRuleGroupInstance)renderRequest.getAttribute(MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP_INSTANCE);
%>

<portlet:actionURL name="/mobile_device_rules/edit_action" var="editActionURL">
	<portlet:param name="mvcRenderCommandName" value="/mobile_device_rules/edit_action" />
</portlet:actionURL>

<aui:form action="<%= editActionURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (action == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="actionId" type="hidden" value="<%= actionId %>" />
	<aui:input name="ruleGroupInstanceId" type="hidden" value="<%= ruleGroupInstance.getRuleGroupInstanceId() %>" />

	<liferay-ui:error exception="<%= ActionTypeException.class %>" message="please-select-a-valid-action-type" />
	<liferay-ui:error exception="<%= NoSuchActionException.class %>" message="action-does-not-exist" />
	<liferay-ui:error exception="<%= NoSuchRuleGroupException.class %>" message="device-family-does-not-exist" />
	<liferay-ui:error exception="<%= NoSuchRuleGroupInstanceException.class %>" message="device-rule-does-not-exist" />

	<aui:model-context bean="<%= action %>" model="<%= MDRAction.class %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<c:if test="<%= action == null %>">
						<p class="text-default">
							<liferay-ui:message key="action-help" />
						</p>
					</c:if>

					<aui:input name="name" placeholder="name" />

					<aui:input name="description" placeholder="description" />

					<aui:select changesContext="<%= true %>" name="type" onChange='<%= renderResponse.getNamespace() + "changeType();" %>' required="<%= true %>" showEmptyOption="<%= true %>">

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
							<liferay-util:include page="<%= editorJSP %>" servletContext="<%= application %>" />
						</c:if>
					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</div>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" value="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />changeDisplay() {
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/mobile_device_rules/site_url_layouts" var="siteURLLayoutsURL" />

		var form = document.<portlet:namespace />fm;

		var formData = new FormData();

		var actionGroupId = Liferay.Util.getFormElement(form, 'groupId');
		var actionPlid = Liferay.Util.getFormElement(form, 'actionPlid');

		if (actionGroupId && actionPlid) {
			formData.append(
				'<portlet:namespace />actionGroupId',
				actionGroupId.value
			);
			formData.append('<portlet:namespace />actionPlid', actionPlid.value);
		}

		Liferay.Util.fetch(
			'<%= HtmlUtil.escapeJS(siteURLLayoutsURL.toString()) %>',
			{
				body: formData,
				method: 'POST'
			}
		)
			.then(function(response) {
				return response.text();
			})
			.then(function(response) {
				var layouts = document.getElementById(
					'<portlet:namespace />layouts'
				);

				if (layouts) {
					layouts.innerHTML = response;
				}
			});
	}

	function <portlet:namespace />changeType() {
		<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/mobile_device_rules/edit_action" var="editorURL">
			<portlet:param name="ajax" value="<%= Boolean.TRUE.toString() %>" />
		</liferay-portlet:resourceURL>

		var form = document.<portlet:namespace />fm;

		var formData = new FormData();

		var type = Liferay.Util.getFormElement(form, 'type');

		if (type) {
			formData.append('<portlet:namespace />type', type.value);
		}

		formData.append('<portlet:namespace /><%= actionId %>', '<%= actionId %>');

		Liferay.Util.fetch('<%= HtmlUtil.escapeJS(editorURL.toString()) %>', {
			data: formData,
			method: 'POST'
		})
			.then(function(response) {
				return response.text();
			})
			.then(function(response) {
				var typeSettings = document.getElementById(
					'<portlet:namespace />typeSettings'
				);

				if (typeSettings) {
					typeSettings.innerHTML = response;
				}
			});
	}
</aui:script>