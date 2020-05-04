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
portletURL.setParameter("configurationScreenKey", "synced-sites");

String redirect = portletURL.toString();

String channelId = ParamUtil.getString(request, "channelId");
String channelName = ParamUtil.getString(request, "channelName");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL", redirect));

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "synced-sites"), redirect);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(resourceBundle, "edit-property"), currentURL);
%>

<portlet:actionURL name="/analytics/edit_channel" var="editChannelURL" />

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

<aui:form action="<%= editChannelURL %>" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="channelId" type="hidden" value="<%= channelId %>" />

	<div class="portlet-analytics-settings sheet sheet-lg">
		<h2 class="autofit-row">
			<liferay-ui:message arguments="<%= channelName %>" key="sites-to-sync-x" />
		</h2>

		<p class="mt-3 text-secondary">
			<liferay-ui:message key="sites-can-only-be-assigned-to-a-single-property-at-a-time" />
		</p>

		<%
		GroupDisplayContext groupDisplayContext = new GroupDisplayContext("/analytics_settings/edit_channel", renderRequest, renderResponse);
		%>

		<clay:management-toolbar
			displayContext="<%= new GroupManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, groupDisplayContext) %>"
		/>

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

				<aui:button type="submit" value="done" />
			</aui:button-row>
		</div>
	</div>
</aui:form>