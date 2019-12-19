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
		showCurrentGroup="<%= true %>"
		showGuestGroup="<%= true %>"
		showLayout="<%= true %>"
		showPortletBreadcrumb="<%= true %>"
	/>
</div>

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="select-contacts-by-user-groups" />
		</span>
	</h2>

	<portlet:actionURL name="/analytics/edit_synced_contacts" var="editSyncedContactsURL" />

	<aui:form action="<%= editSyncedContactsURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<liferay-ui:search-container
			curParam="inheritedUserGroupsCur"
			headerNames="name"
			iteratorURL="<%= currentURLObj %>"
			rowChecker="<%= new UserGroupChecker(renderResponse, syncedUserGroupIds) %>"
			total="<%= UserGroupServiceUtil.getUserGroupsCount(themeDisplay.getCompanyId(), null) %>"
		>
			<liferay-ui:search-container-results
				results="<%= UserGroupServiceUtil.getUserGroups(themeDisplay.getCompanyId(), null, searchContainer.getStart(), searchContainer.getEnd()) %>"
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

		<aui:button-row>
			<aui:button type="submit" value="save" />
		</aui:button-row>
	</aui:form>
</div>