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
String redirect = ParamUtil.getString(request, "redirect");

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
String entriesNavigation = ParamUtil.getString(request, "entriesNavigation");
String orderByCol = ParamUtil.getString(request, "orderByCol", "organization-name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = renderResponse.createRenderURL();

navigationPortletURL.setParameter("mvcRenderCommandName", "/analytics_settings/edit_synced_contacts_organizations");
navigationPortletURL.setParameter("redirect", redirect);

if (delta > 0) {
	navigationPortletURL.setParameter("delta", String.valueOf(delta));
}

PortletURL sortURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);

sortURL.setParameter("entriesNavigation", entriesNavigation);

navigationPortletURL.setParameter("orderBycol", orderByCol);
navigationPortletURL.setParameter("orderByType", orderByType);

PortletURL portletURL = PortletURLUtil.clone(navigationPortletURL, liferayPortletResponse);

portletURL.setParameter("entriesNavigation", entriesNavigation);

PortletURL displayStyleURL = PortletURLUtil.clone(portletURL, liferayPortletResponse);

if (cur > 0) {
	displayStyleURL.setParameter("cur", String.valueOf(cur));
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", redirect));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "select-contacts"), redirect);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "sync-by-organizations"), currentURL);
%>

<portlet:actionURL name="/analytics_settings/edit_synced_contacts" var="editSyncedContactsURL" />

<div class="container-fluid-1280">
	<div class="col-12">
		<div id="breadcrumb">
			<liferay-ui:breadcrumb
				showCurrentGroup="<%= false %>"
				showGuestGroup="<%= false %>"
				showLayout="<%= false %>"
				showPortletBreadcrumb="<%= true %>"
			/>
		</div>
	</div>
</div>

<div class="container-fluid-1280">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="select-contacts-by-organizations" />
		</span>
	</h2>

	<hr />

	<div class="autofit-row form-text">
		<span class="autofit-col autofit-col-expand pb-3">
			<liferay-ui:message key="select-contacts-by-organizations-help" />
		</span>
	</div>

	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="selectOrganizations"
	>
		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= displayStyleURL %>"
				selectedDisplayStyle="list"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all"} %>'
				navigationParam="entriesNavigation"
				portletURL="<%= navigationPortletURL %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"organization-name"} %>'
				portletURL="<%= sortURL %>"
			/>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>

	<%
	OrganizationDisplayContext organizationDisplayContext = new OrganizationDisplayContext(renderRequest, renderResponse);
	%>

	<aui:form action="<%= editSyncedContactsURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="update_synced_organizations" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

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
			/>
		</liferay-ui:search-container>

		<aui:button-row>
			<aui:button type="submit" value="save" />
			<aui:button href="<%= redirect %>" type="cancel" value="cancel" />
		</aui:button-row>
	</aui:form>
</div>