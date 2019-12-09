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

String[] syncedGroupIds = new String[0];
String token = "";
boolean connected = false;

if (analyticsConfiguration != null) {
	syncedGroupIds = analyticsConfiguration.syncedGroupIds();
	token = analyticsConfiguration.token();
	connected = !Validator.isBlank(token);
}
%>

<portlet:actionURL name="/analytics/edit_workspace_connection" var="editWorkspaceConnectionURL" />

<liferay-portlet:renderURL varImpl="selectContactsURL">
	<portlet:param name="configurationScreenKey" value="synced-contacts" />
	<portlet:param name="mvcRenderCommandName" value="/view_configuration_screen" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL varImpl="selectSitesURL">
	<portlet:param name="configurationScreenKey" value="synced-sites" />
	<portlet:param name="mvcRenderCommandName" value="/view_configuration_screen" />
</liferay-portlet:renderURL>

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="connect-analytics-cloud" />
		</span>
	</h2>

	<aui:form action="<%= editWorkspaceConnectionURL %>" data-senna-off="true" method="post" name="fm" onSubmit="confirmation(event)">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<aui:fieldset>
			<aui:input label="analytics-cloud-token" name="token" placeholder="paste-token-here" value="<%= token %>" />

			<div class="form-text">
				<liferay-ui:message key="analytics-cloud-token-help" />
			</div>

			<c:choose>
				<c:when test="<%= connected %>">
					<aui:input name="disconnect" type="hidden" value="true" />

					<aui:button-row>
						<aui:button primary="false" type="submit" value="disconnect" />
					</aui:button-row>
				</c:when>
				<c:otherwise>
					<aui:button-row>
						<aui:button type="submit" value="connect" />
					</aui:button-row>
				</c:otherwise>
			</c:choose>
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
				<liferay-ui:message arguments="<%= ArrayUtil.getLength(syncedGroupIds) %>" key="total-sites-selected-x" />
			</strong>
		</small>

		<aui:button-row>
			<a href="<%= selectSitesURL.toString() %>">
				<aui:button disabled="<%= !connected %>" primary="<%= true %>" value="select-sites" />
			</a>
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
				<liferay-ui:message arguments="<%= 0 %>" key="total-contacts-selected-x" />
			</strong>
		</small>

		<aui:button-row>
			<a href="<%= selectContactsURL.toString() %>">
				<aui:button disabled="<%= !connected %>" primary="<%= true %>" value="select-contacts" />
			</a>
		</aui:button-row>
	</aui:fieldset>
</div>

<script>
	function confirmation(event) {
		<c:if test="<%=connected%>">
			if (!confirm("<liferay-ui:message key="are-you-sure-you-want-to-disconnect-your-analytics-cloud-workspace-from-this-dxp-instance" />")) {
				event.preventDefault();
			}
		</c:if>
	}
</script>