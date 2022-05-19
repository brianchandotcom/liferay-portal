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

<%@ include file="/LRAC-10757/init.jsp" %>

<%
QADisplayContext qaDisplayContext = (QADisplayContext)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_DISPLAY_CONTEXT);
%>

<div class="wizard-mode">
	<portlet:actionURL name="/analytics_settings/edit_workspace_connection" var="editWorkspaceConnectionURL" />

	<ol class="multi-step-indicator-label-top multi-step-nav multi-step-nav-collapse-sm sheet-lg">
		<li class="complete multi-step-item multi-step-item-expand">
			<div class="multi-step-divider"></div>

			<div class="multi-step-indicator">
				<div class="multi-step-indicator-label">
					<liferay-ui:message key="connect" />
				</div>

				<a class="multi-step-icon" data-multi-step-icon="1" href="#1"></a>
			</div>
		</li>
		<li class="active multi-step-item multi-step-item-expand">
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
			<liferay-ui:message key="property-assignment" />
		</h2>

		<c:choose>
			<c:when test="<%= qaDisplayContext.isWizardMode() %>">
				<p class="mb-2 mt-3">Test success, we are in Wizard Mode.</p>
			</c:when>
			<c:otherwise>
				<p class="mb-2 mt-3">Test FAIL, we are NOT in Wizard Mode.</p>
			</c:otherwise>
		</c:choose>
	</clay:sheet>
</div>