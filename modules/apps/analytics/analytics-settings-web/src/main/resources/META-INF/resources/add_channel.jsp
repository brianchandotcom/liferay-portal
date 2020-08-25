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
String orderByCol = ParamUtil.getString(request, "orderByCol", "site-name");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

PortletURL navigationPortletURL = renderResponse.createRenderURL();

navigationPortletURL.setParameter("mvcRenderCommandName", "/analytics_settings/add_channel");
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

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "synced-sites"), redirect);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "add-new-property"), currentURL);
%>

<portlet:actionURL name="/analytics_settings/add_channel" var="addChannelURL" />

<div class="container-fluid container-fluid-max-xl">
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

<aui:form action="<%= addChannelURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<div class="portlet-analytics-settings sheet sheet-lg">
		<h2 class="autofit-row">
			<liferay-ui:message key="new-property" />
		</h2>

		<p class="mt-3 text-secondary">
			<liferay-ui:message key="select-new-property-organization-type-and-sites-to-sync.sites-can-only-be-assigned-to-a-single-property-at-a-time" />
		</p>

		<div class="mb-5 mt-4 radio-buttons">
			<label class="d-block mb-3">
				<aui:input checked="<%= true %>" label="combined-property" name="channelType" type="radio" value="combined" />

				<small class="text-secondary">
					<liferay-ui:message key="all-selected-sites-will-be-combined-in-to-a-single-property" />
				</small>
			</label>

			<label class="d-block mb-3">
				<aui:input label="multiple-properties" name="channelType" type="radio" value="multiple" />

				<small class="text-secondary">
					<liferay-ui:message key="each-site-selected-will-become-its-own-property" />
				</small>
			</label>
		</div>

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

		<%
		GroupDisplayContext groupDisplayContext = new GroupDisplayContext("/analytics_settings/add_channel", renderRequest, renderResponse);
		%>

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

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="assigned-property"
					value="<%= HtmlUtil.escape(groupDisplayContext.getChannelName(group.getGroupId())) %>"
				/>

				<%
				List<Group> childrenGroups = group.getChildren(true);
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smaller table-cell-ws-nowrap"
					name="child-sites"
					value="<%= String.valueOf(childrenGroups.size()) %>"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
				searchResultCssClass="show-quick-actions-on-hover table table-autofit"
			/>
		</liferay-ui:search-container>

		<div class="text-right">
			<aui:button-row>
				<aui:button href="<%= redirect %>" value="cancel" />

				<aui:button disabled="<%= true %>" id="add-channel-button" type="submit" value="done" />
			</aui:button-row>
		</div>
	</div>
</aui:form>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />selectGroups'
	);

	function <portlet:namespace />handleSubmitButton(selectedItems) {
		var button = document.getElementById(
			'<portlet:namespace />add-channel-button'
		);

		if (selectedItems.isEmpty()) {
			button.classList.add('disabled');
			button.setAttribute('disabled', 'disabled');
		}
		else {
			button.classList.remove('disabled');
			button.removeAttribute('disabled');
		}
	}

	searchContainer.on('rowToggled', function(event) {
		return <portlet:namespace />handleSubmitButton(
			event.elements.allSelectedElements
		);
	});

	Liferay.componentReady('<portlet:namespace />selectGroups').then(function(
		searchContainer
	) {
		return <portlet:namespace />handleSubmitButton(
			searchContainer.select.getAllSelectedElements()
		);
	});
</aui:script>