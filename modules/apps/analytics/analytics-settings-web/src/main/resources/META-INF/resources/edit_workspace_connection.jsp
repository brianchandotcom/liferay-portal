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
AnalyticsUsersManager analyticsUsersManager = (AnalyticsUsersManager)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_USERS_MANAGER);

boolean connected = false;
String[] syncedGroupIds = new String[0];
String token = "";
long totalContactsSelected = 0;

if (analyticsConfiguration != null) {
	syncedGroupIds = analyticsConfiguration.syncedGroupIds();
	token = analyticsConfiguration.token();

	if (!Validator.isBlank(token)) {
		connected = true;
	}

	if (analyticsConfiguration.syncAllContacts()) {
		totalContactsSelected = analyticsUsersManager.getCompanyUsersCount(themeDisplay.getCompanyId());
	}
	else {
		String[] syncedOrganizationIds = analyticsConfiguration.syncedOrganizationIds();

		long[] syncedOrganizationIdsLong = new long[syncedOrganizationIds.length];

		for (int i = 0; i < syncedOrganizationIds.length; i++) {
			syncedOrganizationIdsLong[i] = GetterUtil.getLong(syncedOrganizationIds[i]);
		}

		String[] syncedUserGroupIds = analyticsConfiguration.syncedUserGroupIds();

		long[] syncedUserGroupIdsLong = new long[syncedUserGroupIds.length];

		for (int i = 0; i < syncedUserGroupIds.length; i++) {
			syncedUserGroupIdsLong[i] = GetterUtil.getLong(syncedUserGroupIds[i]);
		}

		totalContactsSelected = analyticsUsersManager.getOrganizationsAndUserGroupsUsersCount(syncedOrganizationIdsLong, syncedUserGroupIdsLong);
	}
}
%>

<portlet:actionURL name="/analytics/edit_workspace_connection" var="editWorkspaceConnectionURL" />

<div class="sheet sheet-lg">
	<c:if test="<%= AnalyticsSettingsUtil.isAnalyticsEnabledWithOAuth(themeDisplay.getCompanyId()) %>">
		<aui:alert type="warning">
			<div class="mb-2">
				<liferay-ui:message key="this-dxp-instance-is-already-connected-with-oauth" />

				<strong>
					<a href="https://help.liferay.com/hc/articles/360038812191-Setup-a-DXP-Data-Source-using-Token-based-Connection">
						<liferay-ui:message key="read-about-using-the-new-token-based-connection-here" />
					</a>
				</strong>
			</div>

			<div>
				<liferay-ui:message key="you-will-be-able-to-upgrade-to-the-new-token-based-connection" />
			</div>
		</aui:alert>
	</c:if>

	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="connect-analytics-cloud" />
		</span>
	</h2>

	<aui:form action="<%= editWorkspaceConnectionURL %>" data-senna-off="true" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "confirmation(event);" %>'>
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<c:if test="<%= connected %>">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="disconnect" />
		</c:if>

		<aui:fieldset>
			<c:if test="<%= !connected %>">
				<aui:input autocomplete="off" label="analytics-cloud-token" name="token" oninput='<%= renderResponse.getNamespace() + "validateTokenButton();" %>' placeholder="paste-token-here" value="<%= token %>" wrapperCssClass="mb-1" />

				<div class="form-text">
					<liferay-ui:message key="analytics-cloud-token-help" />
				</div>
			</c:if>

			<c:if test="<%= connected %>">
				<label class="control-label d-block mb-2">
					<liferay-ui:message key="analytics-cloud-token" />
				</label>

				<label class="control-label d-block">
					<liferay-ui:message key="your-dxp-instance-is-connected-to-analytics-cloud" />
				</label>
			</c:if>

			<aui:button-row cssClass="mt-2">
				<c:if test="<%= connected %>">
					<a class="btn btn-primary mr-2" href="<%= analyticsConfiguration.liferayAnalyticsURL() %>" target="_blank">
						<span class="lfr-btn-label"><liferay-ui:message key="go-to-workspace" /></span>

						<liferay-ui:icon
							icon="shortcut"
							markupView="lexicon"
						/>
					</a>
				</c:if>

				<aui:button id="tokenButton" primary="<%= connected ? false : true %>" type="submit" value='<%= connected ? "disconnect" : "connect" %>' />
			</aui:button-row>
		</aui:fieldset>
	</aui:form>

	<aui:fieldset>
		<label class="control-label">
			<liferay-ui:message key="synced-contacts" />
		</label>

		<div class="form-text">
			<liferay-ui:message key="synced-contacts-help" />
		</div>

		<small>
			<strong>
				<liferay-ui:message arguments="<%= totalContactsSelected %>" key="total-contacts-selected-x" />
			</strong>
		</small>

		<aui:button-row>
			<liferay-portlet:renderURL varImpl="selectContactsURL">
				<portlet:param name="mvcRenderCommandName" value="/view_configuration_screen" />
				<portlet:param name="configurationScreenKey" value="synced-contacts" />
			</liferay-portlet:renderURL>

			<aui:button disabled="<%= !connected %>" href="<%= selectContactsURL.toString() %>" primary="<%= true %>" value="select-contacts" />
		</aui:button-row>
	</aui:fieldset>

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
			<liferay-portlet:renderURL varImpl="selectSitesURL">
				<portlet:param name="mvcRenderCommandName" value="/view_configuration_screen" />
				<portlet:param name="configurationScreenKey" value="synced-sites" />
			</liferay-portlet:renderURL>

			<aui:button disabled="<%= !connected %>" href="<%= selectSitesURL.toString() %>" primary="<%= true %>" value="select-sites" />
		</aui:button-row>
	</aui:fieldset>
</div>

<script>
	function <portlet:namespace />confirmation(event) {
		<c:if test="<%= connected %>">
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

	<c:if test="<%= !connected %>">
		<portlet:namespace />validateTokenButton();
	</c:if>
</script>