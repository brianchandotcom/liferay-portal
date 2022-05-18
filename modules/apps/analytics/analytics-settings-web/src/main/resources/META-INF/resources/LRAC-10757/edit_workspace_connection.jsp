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

<div class="wizard-mode">
	<portlet:actionURL name="/analytics_settings/edit_workspace_connection" var="editWorkspaceConnectionURL" />

	<ol class="multi-step-indicator-label-top multi-step-nav multi-step-nav-collapse-sm sheet-lg">
		<li class="active multi-step-item multi-step-item-expand">
			<div class="multi-step-divider"></div>

			<div class="multi-step-indicator">
				<div class="multi-step-indicator-label">
					<liferay-ui:message key="connect" />
				</div>

				<a class="multi-step-icon" data-multi-step-icon="1" href="#1"></a>
			</div>
		</li>
		<li class="multi-step-item multi-step-item-expand">
			<div class="multi-step-divider"></div>

			<div class="multi-step-indicator">
				<div class="multi-step-indicator-label">
					<liferay-ui:message key="property" />
				</div>

				<a class="multi-step-icon" data-multi-step-icon="2" href="#1"></a>
			</div>
		</li>
		<li class="multi-step-item multi-step-item-expand">
			<div class="multi-step-divider"></div>

			<div class="multi-step-indicator">
				<div class="multi-step-indicator-label">
					<liferay-ui:message key="people" />
				</div>

				<a class="multi-step-icon" data-multi-step-icon="3" href="#1"></a>
			</div>
		</li>
		<li class="multi-step-item">
			<div class="multi-step-divider"></div>

			<div class="multi-step-indicator">
				<div class="multi-step-indicator-label">
					<liferay-ui:message key="people-data" />
				</div>

				<a class="multi-step-icon" data-multi-step-icon="4" href="#1"></a>
			</div>
		</li>
	</ol>

	<clay:sheet>
		<h2 class="m-0">
			<liferay-ui:message key="connect-analytics-cloud" />
		</h2>

		<p class="mb-2 mt-3 small text-secondary">
			<liferay-ui:message key="use-the-token-genereted-in-your-analytics-cloud-to-connect-this-workspace" />
		</p>

		<aui:form action="<%= editWorkspaceConnectionURL %>" data-senna-off="true" method="post" name="fm" onSubmit='<%= liferayPortletResponse.getNamespace() + "confirmation(event);" %>'>
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<c:if test="<%= workspaceConnectionDisplayContext.isConnected() %>">
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="disconnect" />
			</c:if>

			<aui:fieldset>
				<c:if test="<%= !workspaceConnectionDisplayContext.isConnected() %>">
					<aui:input autocomplete="off" label="analytics-cloud-token" name="token" oninput='<%= liferayPortletResponse.getNamespace() + "validateTokenButton();" %>' placeholder="paste-token-here" value="<%= workspaceConnectionDisplayContext.getToken() %>" wrapperCssClass="mb-1" />

					<div class="small text-secondary">
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

				<aui:button-row cssClass="mt-3">
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
</div>

<aui:script>
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

	<c:if test='<%= SessionErrors.contains(renderRequest, "unableToNotifyAnalyticsCloud") %>'>
		Liferay.Util.openToast({
			message: '<liferay-ui:message key="unable-to-notify-analytics-cloud" />',
			title: Liferay.Language.get('warning'),
			toastProps: {
				autoClose: 5000,
			},
			type: 'warning',
		});
	</c:if>
</aui:script>