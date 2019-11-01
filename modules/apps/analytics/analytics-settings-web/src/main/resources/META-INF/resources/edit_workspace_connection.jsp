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
AnalyticsConfiguration analyticsConfiguration = (AnalyticsConfiguration)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);
%>

<portlet:actionURL name="/analytics/edit_workspace_connection" var="editWorkspaceConnectionURL" />

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="connect-analytics-cloud" />
		</span>
	</h2>

	<aui:form action="<%= editWorkspaceConnectionURL %>" data-senna-off="true" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<aui:fieldset>
			<aui:input label="analytics-cloud-token" name="token" placeholder="paste-token-here" value='<%= (analyticsConfiguration != null) ? analyticsConfiguration.token() : "" %>' />

			<div class="form-text">
				<liferay-ui:message key="analytics-cloud-token-help" />
			</div>

			<aui:button-row>
				<aui:button type="submit" value="connect" />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>

	<aui:fieldset>
		<label class="control-label">
			<liferay-ui:message key="synced-sites" />
		</label>

		<div class="form-text">
			<liferay-ui:message key="synced-sites-help" />
		</div>

		<small>
			<strong>
				<liferay-ui:message key="total-sites-selected" />
				<%= 0 %>
			</strong>
		</small>

		<aui:button-row>
			<aui:button disabled="true" primary="true" type="button" value="select-sites" />
		</aui:button-row>
	</aui:fieldset>

	<aui:fieldset>
		<label class="control-label">
			<liferay-ui:message key="synced-contacts" />
		</label>

		<div class="form-text">
			<liferay-ui:message key="synced-contacts-help" />
		</div>

		<small>
			<strong>
				<liferay-ui:message key="total-contacts-selected" />
				<%= 0 %>
			</strong>
		</small>

		<aui:button-row>
			<aui:button disabled="true" primary="true" type="button" value="select-contacts" />
		</aui:button-row>
	</aui:fieldset>
</div>