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
WorkspaceConnectionDisplayContext workspaceConnectionDisplayContext = (WorkspaceConnectionDisplayContext)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_DISPLAY_CONTEXT);
%>

<c:if test="<%= workspaceConnectionDisplayContext.isWizardMode() %>">
	<h1> Wizard Mode</h1>
</c:if>

<c:if test='<%= SessionErrors.contains(renderRequest, "unableToNotifyAnalyticsCloud") %>'>
	<aui:script>
		Liferay.Util.openToast({
			message: '<liferay-ui:message key="unable-to-notify-analytics-cloud" />',
			title: Liferay.Language.get('warning'),
			toastProps: {
				autoClose: 5000,
			},
			type: 'warning',
		});
	</aui:script>
</c:if>

<portlet:actionURL name="/analytics_settings/edit_workspace_connection" var="editWorkspaceConnectionURL" />

<clay:sheet>
	<h2>
		<liferay-ui:message key="connect-analytics-cloud" />
	</h2>

	<aui:form action="<%= editWorkspaceConnectionURL %>" data-senna-off="true" method="post" name="fm" onSubmit='<%= liferayPortletResponse.getNamespace() + "confirmation(event);" %>'>
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<c:if test="<%= workspaceConnectionDisplayContext.isConnected() %>">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="disconnect" />
		</c:if>

		<aui:fieldset>
			<c:if test="<%= !workspaceConnectionDisplayContext.isConnected() %>">
				<aui:input autocomplete="off" label="analytics-cloud-token" name="token" oninput='<%= liferayPortletResponse.getNamespace() + "validateTokenButton();" %>' placeholder="paste-token-here" value="<%= workspaceConnectionDisplayContext.getToken() %>" wrapperCssClass="mb-1" />

				<div class="form-text">
					<liferay-ui:message key="analytics-cloud-token-help" />
				</div>
			</c:if>

			<c:if test="<%= workspaceConnectionDisplayContext.isConnected() %>">
				<label class="control-label d-block mb-2">
					<liferay-ui:message key="analytics-cloud-token" />
				</label>

				<label class="control-label d-block">
					<liferay-ui:message key="your-dxp-instance-is-connected-to-analytics-cloud" />
				</label>
			</c:if>

			<aui:button-row cssClass="mt-2">
				<c:if test="<%= workspaceConnectionDisplayContext.isConnected() %>">
					<a class="btn btn-primary mr-2" href="<%= workspaceConnectionDisplayContext.getLiferayAnalyticsURL() %>" rel="noopener noreferrer" target="_blank">
						<liferay-ui:message key="go-to-workspace" />

						<span class="inline-item inline-item-after">
							<clay:icon
								symbol="shortcut"
							/>
						</span>
					</a>
				</c:if>

				<aui:button id="tokenButton" primary="<%= workspaceConnectionDisplayContext.isConnected() ? false : true %>" type="submit" value='<%= workspaceConnectionDisplayContext.isConnected() ? "disconnect" : "connect" %>' />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>
</clay:sheet>

<script>
	function <portlet:namespace />confirmation(event) {
		<c:if test="<%= workspaceConnectionDisplayContext.isConnected() %>">
			if (
				!confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-disconnect-your-analytics-cloud-workspace-from-this-dxp-instance" />'
				)
			) {
				event.preventDefault();
			}
		</c:if>
	}

	function <portlet:namespace />validateTokenButton() {
		var token = document.getElementById('<portlet:namespace />token');
		var tokenButton = document.getElementById(
			'<portlet:namespace />tokenButton'
		);

		var value = token.value;

		tokenButton.disabled = value.length === 0;
	}

	<c:if test="<%= !workspaceConnectionDisplayContext.isConnected() %>">
		<portlet:namespace />validateTokenButton();
	</c:if>
</script>