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
ChannelDisplayContext channelDisplayContext = new ChannelDisplayContext(renderRequest, renderResponse);

ChannelSearch channelSearch = channelDisplayContext.getChannelSearch();

boolean connected = false;

AnalyticsConfiguration analyticsConfiguration = (AnalyticsConfiguration)request.getAttribute(AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);

if (!Validator.isBlank(analyticsConfiguration.token())) {
	connected = true;
}

String keywords = ParamUtil.getString(request, "keywords");
%>

<clay:sheet
	cssClass="pb-2 portlet-analytics-settings"
>
	<h2>
		<liferay-ui:message key="sync-sites-to-property" />
	</h2>

	<p class="mt-3 text-secondary">
		<liferay-ui:message key="select-or-create-a-property-to-manage-synced-sites" />
	</p>

	<c:choose>
		<c:when test="<%= !connected %>">
			<liferay-ui:message key="your-dxp-instance-is-not-connected-to-analytics-cloud" />
		</c:when>
		<c:when test="<%= channelSearch == null %>">
			<div class="mt-4">
				<liferay-ui:message key="unable-to-retrieve-the-properties-from-analytics-cloud" />

				<div class="mt-4">
					<liferay-portlet:renderURL varImpl="selectSitesURL">
						<portlet:param name="mvcRenderCommandName" value="/view_configuration_screen" />
						<portlet:param name="configurationScreenKey" value="synced-sites" />
					</liferay-portlet:renderURL>

					<a class="btn btn-primary" href="<%= selectSitesURL.toString() %>">
						<span class="lfr-btn-label"><liferay-ui:message key="retry" /></span>
					</a>
				</div>
			</div>
		</c:when>
		<c:when test="<%= (channelSearch != null) && (channelSearch.getTotal() == 0) && Validator.isBlank(keywords) %>">
			<div class="mb-5 mt-5">
				<div class="empty-state-icon mb-4 mt-4"></div>

				<div class="text-center">
					<h2>
						<liferay-ui:message key="no-properties-found" />
					</h2>

					<p class="text-secondary">
						<liferay-ui:message key="create-a-new-property-to-get-started" />
					</p>

					<aui:button-row>
						<portlet:renderURL var="addNewChannelURL">
							<portlet:param name="mvcRenderCommandName" value="/analytics_settings/add_channel" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<aui:button href="<%= addNewChannelURL %>" primary="<%= true %>" value="new-property" />
					</aui:button-row>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<clay:management-toolbar
				displayContext="<%= new ChannelManagementToolbarDisplayContext(channelDisplayContext, request, liferayPortletRequest, liferayPortletResponse) %>"
				elementClasses="custom-management-toolbar"
			/>

			<liferay-ui:search-container
				id="selectChannels"
				searchContainer="<%= channelSearch %>"
				var="groupSearchContainer"
			>
				<liferay-ui:search-container-row
					className="com.liferay.analytics.settings.web.internal.model.Channel"
					escapedModel="<%= true %>"
					keyProperty="id"
					modelVar="channel"
				>
					<portlet:renderURL var="editChannelURL">
						<portlet:param name="mvcRenderCommandName" value="/analytics_settings/edit_channel" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="channelId" value="<%= String.valueOf (channel.getId()) %>" />
						<portlet:param name="channelName" value="<%= channel.getName() %>" />
					</portlet:renderURL>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						href="<%= editChannelURL %>"
						name="available-properties"
						truncate="<%= true %>"
					>
						<span class="lfr-portal-tooltip text-truncate-inline" title="<%= HtmlUtil.escape(channel.getName()) %>">
							<span class="text-truncate">
								<%= HtmlUtil.escape(channel.getName()) %>
							</span>
						</span>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchResultCssClass="show-quick-actions-on-hover table table-autofit"
				/>
			</liferay-ui:search-container>
		</c:otherwise>
	</c:choose>
</clay:sheet>