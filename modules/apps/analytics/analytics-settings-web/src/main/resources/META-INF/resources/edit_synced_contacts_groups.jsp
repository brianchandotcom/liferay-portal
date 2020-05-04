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
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/view_configuration_screen");
portletURL.setParameter("configurationScreenKey", "synced-contacts");

String redirect = portletURL.toString();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", redirect));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "select-contacts"), redirect);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "sync-by-user-groups"), currentURL);
%>

<portlet:actionURL name="/analytics/edit_synced_contacts" var="editSyncedContactsURL" />

<div class="container-fluid container-fluid-max-xl">
	<clay:row>
		<clay:col
			size="12"
		>
			<div id="breadcrumb">
				<liferay-ui:breadcrumb
					showCurrentGroup="<%= false %>"
					showGuestGroup="<%= false %>"
					showLayout="<%= false %>"
					showPortletBreadcrumb="<%= true %>"
				/>
			</div>
		</clay:col>
	</clay:row>
</div>

<div class="sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="select-contacts-by-user-groups" />
		</span>
	</h2>

	<hr />

	<div class="autofit-row form-text">
		<span class="autofit-col autofit-col-expand pb-3">
			<liferay-ui:message key="select-contacts-by-user-groups-help" />
		</span>
	</div>

	<%
	UserGroupDisplayContext userGroupDisplayContext = new UserGroupDisplayContext(renderRequest, renderResponse);
	%>

	<clay:management-toolbar
		displayContext="<%= new UserGroupManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, userGroupDisplayContext) %>"
	/>

	<aui:form action="<%= editSyncedContactsURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="update_synced_groups" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

		<liferay-ui:search-container
			id="selectUserGroups"
			searchContainer="<%= userGroupDisplayContext.getUserGroupSearch() %>"
			var="userGroupSearchContainer"
		>
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
			<aui:button href="<%= redirect %>" type="cancel" value="cancel" />
		</aui:button-row>
	</aui:form>
</div>