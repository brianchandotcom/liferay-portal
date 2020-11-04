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

portletURL.setParameter("mvcRenderCommandName", "/configuration_admin/view_configuration_screen");

boolean includeSyncContactsFields = ParamUtil.getBoolean(request, "includeSyncContactsFields");

if (includeSyncContactsFields) {
	portletURL.setParameter("configurationScreenKey", "2-synced-contact-data");
}
else {
	portletURL.setParameter("configurationScreenKey", "2-synced-contacts");
}

String redirect = ParamUtil.getString(request, "redirect", portletURL.toString());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", redirect));

if (includeSyncContactsFields) {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "select-contact-data"), portletURL.toString());
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "select-contacts"), redirect);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "select-contacts"), portletURL.toString());
}

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "sync-by-organizations"), currentURL);
%>

<portlet:actionURL name="/analytics_settings/edit_synced_contacts" var="editSyncedContactsURL" />

<portlet:renderURL var="editSyncedContactsFieldsURL">
	<portlet:param name="mvcRenderCommandName" value="/analytics_settings/edit_synced_contacts_fields" />
</portlet:renderURL>

<clay:container-fluid>
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
</clay:container-fluid>

<clay:sheet>
	<h2>
		<liferay-ui:message key="select-contacts-by-organizations" />
	</h2>

	<hr />

	<div class="c-pb-3 form-text">
		<liferay-ui:message key="select-contacts-by-organizations-help" />
	</div>

	<%
	OrganizationDisplayContext organizationDisplayContext = new OrganizationDisplayContext(renderRequest, renderResponse);
	%>

	<clay:management-toolbar-v2
		displayContext="<%= new OrganizationManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, organizationDisplayContext) %>"
	/>

	<aui:form action="<%= includeSyncContactsFields ? editSyncedContactsFieldsURL : editSyncedContactsURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="update_synced_organizations" />
		<aui:input name="redirect" type="hidden" value="<%= includeSyncContactsFields ? currentURL : redirect %>" />
		<aui:input name="includeSyncContactsFields" type="hidden" value="<%= String.valueOf(includeSyncContactsFields) %>" />

		<liferay-ui:search-container
			id="selectOrganizations"
			searchContainer="<%= organizationDisplayContext.getOrganizationSearch() %>"
			var="organizationSearchContainer"
		>
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
			<aui:button type="submit" value='<%= includeSyncContactsFields ? "save-and-next" : "save" %>' />
			<aui:button href="<%= redirect %>" type="cancel" value="cancel" />
		</aui:button-row>
	</aui:form>
</clay:sheet>