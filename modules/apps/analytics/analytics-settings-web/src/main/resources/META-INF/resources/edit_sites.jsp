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

boolean connected = false;

if (!Validator.isBlank(analyticsConfiguration.token())) {
	connected = true;
}

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM);
String entriesNavigation = ParamUtil.getString(request, "entriesNavigation");
String orderByCol = ParamUtil.getString(request, "orderByCol", "site-name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = renderResponse.createRenderURL();

navigationPortletURL.setParameter("tabs1", "synced-sites");

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

GroupDisplayContext groupDisplayContext = new GroupDisplayContext("/analytics_settings/edit_synced_sites", renderRequest, renderResponse);
%>

<portlet:actionURL name="/analytics_settings/edit_synced_sites" var="editSyncedSitesURL" />

<div class="container-fluid-1280 mt-4 portlet-analytics-settings sheet sheet-lg">
	<h2 class="autofit-row">
		<span class="autofit-col autofit-col-expand">
			<liferay-ui:message key="choose-sites-to-sync" />
		</span>
	</h2>

	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="selectGroups"
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
				orderColumns='<%= new String[] {"site-name", "site-friendly-url"} %>'
				portletURL="<%= sortURL %>"
			/>
		</liferay-frontend:management-bar-filters>
	</liferay-frontend:management-bar>

	<aui:form action="<%= editSyncedSitesURL %>" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:search-container
			id="selectGroups"
			searchContainer="<%= groupDisplayContext.getGroupSearch() %>"
			var="groupSearchContainer"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.model.Group"
				escapedModel="<%= true %>"
				keyProperty="groupId"
				modelVar="group"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="site-name"
					value="<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="site-friendly-url"
					value="<%= HtmlUtil.escape(group.getFriendlyURL()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>

		<aui:button-row>
			<aui:button disabled="<%= !connected %>" type="submit" value="save-and-sync" />
		</aui:button-row>
	</aui:form>
</div>