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

if (!Validator.isBlank(analyticsConfiguration.token())) {
	connected = true;
}

boolean syncAllContacts = analyticsConfiguration.syncAllContacts();
Set<String> syncedOrganizationIds = SetUtil.fromArray(analyticsConfiguration.syncedOrganizationIds());
Set<String> syncedUserGroupIds = SetUtil.fromArray(analyticsConfiguration.syncedUserGroupIds());
%>

<portlet:actionURL name="/analytics_settings/edit_synced_contacts" var="editSyncedContactsURL" />

<div class="container-fluid-1280 mt-4 portlet-analytics-settings sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="contact-data" />
		</span>
	</h2>

	<aui:form action="<%= editSyncedContactsURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<fieldset <%= connected ? "" : "disabled" %>>
			<label class="control-label">
				<liferay-ui:message key="sync-all-contacts" />
			</label>

			<div class="form-text">
				<liferay-ui:message key="sync-all-contacts-help" />
			</div>

			<div class="sync-all-contacts">

				<%
				String label = LanguageUtil.format(resourceBundle, "sync-all-x-contacts", analyticsUsersManager.getCompanyUsersCount(themeDisplay.getCompanyId()));
				%>

				<aui:input inlineField="<%= true %>" label="" labelOff="<%= label %>" labelOn="<%= label %>" name="syncAllContacts" type="toggle-switch" value="<%= syncAllContacts %>" />
			</div>
		</fieldset>

		<fieldset <%= connected ? "" : "disabled" %>>
			<label class="control-label">
				<liferay-ui:message key="sync-by-user-groups-and-organizations" />
			</label>

			<div class="form-text">
				<liferay-ui:message key="sync-by-user-groups-and-organizations-help" />
			</div>

			<ul class="list-group-sync">
				<li>
					<c:choose>
						<c:when test="<%= connected %>">
							<portlet:renderURL var="createUserGroupURL">
								<portlet:param name="mvcRenderCommandName" value="/analytics_settings/edit_synced_contacts_groups" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
							</portlet:renderURL>

							<a class="d-flex m-4 p-2 text-decoration-none" href=<%= createUserGroupURL %>>
						</c:when>
						<c:otherwise>
							<span class="contacts-link-disabled d-flex m-4 p-2">
						</c:otherwise>
					</c:choose>

						<div class="bg-light mr-3 sticker sticker-light sticker-rounded">
							<liferay-ui:icon
								icon="users"
								markupView="lexicon"
							/>
						</div>

						<div class="sync-options">
							<p class="list-group-title">
								<liferay-ui:message key="sync-by-user-groups" />
							</p>

							<small class="list-group-subtext">
								<liferay-ui:message arguments='<%= syncAllContacts ? "all" : syncedUserGroupIds.size() %>' key="x-user-groups-selected" />
							</small>
						</div>

					<c:choose>
						<c:when test="<%= connected %>">
							</a>
						</c:when>
						<c:otherwise>
							</span>
						</c:otherwise>
					</c:choose>
				</li>
				<li>
					<c:choose>
						<c:when test="<%= connected %>">
							<portlet:renderURL var="createOrganizationsURL">
								<portlet:param name="mvcRenderCommandName" value="/analytics_settings/edit_synced_contacts_organizations" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
							</portlet:renderURL>

							<a class="d-flex m-4 p-2 text-decoration-none" href=<%= createOrganizationsURL %>>
						</c:when>
						<c:otherwise>
							<span class="contacts-link-disabled d-flex m-4 p-2">
						</c:otherwise>
					</c:choose>

						<div class="bg-light mr-3 sticker sticker-light sticker-rounded">
							<liferay-ui:icon
								icon="organizations"
								markupView="lexicon"
							/>
						</div>

						<div class="sync-options">
							<p class="list-group-title">
								<liferay-ui:message key="sync-by-organizations" />
							</p>

							<small class="list-group-subtext">
								<liferay-ui:message arguments='<%= syncAllContacts ? "all" : syncedOrganizationIds.size() %>' key="x-organizations-selected" />
							</small>
						</div>

					<c:choose>
						<c:when test="<%= connected %>">
							</a>
						</c:when>
						<c:otherwise>
							</span>
						</c:otherwise>
					</c:choose>
				</li>
			</ul>
		</fieldset>

		<aui:button-row>
			<aui:button disabled="<%= !connected %>" type="submit" value="save" />
		</aui:button-row>
	</aui:form>
</div>