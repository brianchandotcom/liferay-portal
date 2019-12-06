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
List<UserGroup> userGroups = UserGroupServiceUtil.getUserGroups(themeDisplay.getCompanyId());

List<Organization> organizations = OrganizationServiceUtil.getOrganizations(themeDisplay.getCompanyId(), -1);

AnalyticsConfiguration analyticsConfiguration = (AnalyticsConfiguration)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);

boolean syncAllContacts = analyticsConfiguration.syncAllContacts();

Set<String> syncedUserGroupIds = SetUtil.fromArray(analyticsConfiguration.syncedUserGroupIds());

Set<String> syncedOrganizationIds = SetUtil.fromArray(analyticsConfiguration.syncedOrganizationIds());
%>

<portlet:actionURL name="/analytics/edit_synced_contacts" var="editSyncedContactsURL" />

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="contact-data" />
		</span>
	</h2>

	<aui:form action="<%= editSyncedContactsURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<aui:fieldset>
			<label class="control-label">
				<liferay-ui:message key="sync-all-contacts" />
			</label>

			<div class="form-text">
				<liferay-ui:message key="sync-all-contacts-help" />
			</div>

			<label class="mb-4 mt-3 toggle-switch">
				<input class="toggle-switch-check" name="<portlet:namespace />syncAllContacts" type="checkbox" <%= syncAllContacts ? "checked" : "" %> />

				<span aria-hidden="true" class="toggle-switch-bar">
					<span class="toggle-switch-handle" />
				</span>
				<span class="toggle-switch-text toggle-switch-text-right">
					<liferay-ui:message arguments="<%= UserServiceUtil.getCompanyUsersCount(themeDisplay.getCompanyId()) %>" key="sync-all-x-contacts" />
				</span>
			</label>
		</aui:fieldset>

		<aui:fieldset>
			<label class="control-label">
				<liferay-ui:message key="sync-by-user-groups-and-organizations" />
			</label>

			<div class="form-text">
				<liferay-ui:message key="sync-by-user-groups-and-organizations-help" />
			</div>

			<ul class="list-group mt-4">
				<li class="list-group-item list-group-item-flex">
					<div class="autofit-col">
						<div class="sticker sticker-light sticker-rounded">
							<liferay-ui:icon
								icon="user"
								markupView="lexicon"
							/>
						</div>
					</div>

					<div class="autofit-col">
						<h4 class="list-group-title">
							<liferay-ui:message key="sync-by-user-groups" />
						</h4>

						<p class="list-group-subtext">
							<liferay-ui:message arguments='<%= syncAllContacts ? "All" : syncedUserGroupIds.size() %>' key="x-user-groups-selected" />
						</p>
					</div>
				</li>
				<li class="list-group-item list-group-item-flex">
					<div class="autofit-col">
						<div class="sticker sticker-light sticker-rounded">
							<liferay-ui:icon
								icon="organizations"
								markupView="lexicon"
							/>
						</div>
					</div>

					<div class="autofit-col">
						<h4 class="list-group-title">
							<liferay-ui:message key="sync-by-organizations" />
						</h4>

						<p class="list-group-subtext">
							<liferay-ui:message arguments='<%= syncAllContacts ? "All" : syncedOrganizationIds.size() %>' key="x-organizations-selected" />
						</p>
					</div>
				</li>
			</ul>
		</aui:fieldset>

		<liferay-ui:search-container
			curParam="inheritedUserGroupsCur"
			headerNames="name"
			rowChecker="<%= new UserGroupChecker(renderResponse, syncedUserGroupIds) %>"
			total="<%= userGroups.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= userGroups %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.UserGroup"
				escapedModel="<%= true %>"
				keyProperty="userGroupId"
				modelVar="userGroup"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="user-group-name"
					value="<%= HtmlUtil.escape(userGroup.getName()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchResultCssClass="show-quick-actions-on-hover table table-autofit"
			/>
		</liferay-ui:search-container>

		<liferay-ui:search-container
			curParam="inheritedOrganizationsCur"
			headerNames="name"
			rowChecker="<%= new OrganizationChecker(renderResponse, syncedOrganizationIds) %>"
			total="<%= organizations.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= organizations %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Organization"
				escapedModel="<%= true %>"
				keyProperty="organizationId"
				modelVar="organization"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="organization-name"
					value="<%= HtmlUtil.escape(organization.getName()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchResultCssClass="show-quick-actions-on-hover table table-autofit"
			/>
		</liferay-ui:search-container>

		<aui:button-row>
			<aui:button type="submit" value="save" />
		</aui:button-row>
	</aui:form>
</div>