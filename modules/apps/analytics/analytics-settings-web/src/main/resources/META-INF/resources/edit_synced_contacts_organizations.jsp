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

boolean syncAllContacts = analyticsConfiguration.syncAllContacts();
Set<String> syncedOrganizationIds = SetUtil.fromArray(analyticsConfiguration.syncedOrganizationIds());
Set<String> syncedUserGroupIds = SetUtil.fromArray(analyticsConfiguration.syncedUserGroupIds());

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<div id="breadcrumb">
	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= true %>"
		showLayout="<%= true %>"
		showPortletBreadcrumb="<%= true %>"
	/>
</div>

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="select-contacts-by-organizations" />
		</span>
	</h2>

	<portlet:actionURL name="/analytics/edit_synced_contacts" var="editSyncedContactsURL" />

	<aui:form action="<%= editSyncedContactsURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<liferay-ui:search-container
			curParam="inheritedOrganizationsCur"
			headerNames="name"
			iteratorURL="<%= currentURLObj %>"
			rowChecker="<%= new OrganizationChecker(renderResponse, syncedOrganizationIds) %>"
			total="<%= OrganizationServiceUtil.getOrganizationsCount(themeDisplay.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) %>"
		>
			<liferay-ui:search-container-results
				results="<%= OrganizationServiceUtil.getOrganizations(themeDisplay.getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, searchContainer.getStart(), searchContainer.getEnd()) %>"
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