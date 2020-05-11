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

<%@ include file="/message_boards/init.jsp" %>

<%
String navigation = "statistics";

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view_statistics");
%>

<%@ include file="/message_boards_admin/nav.jspf" %>

<%
long categoryId = GetterUtil.getLong(request.getAttribute("view.jsp-categoryId"));

MBCategoryDisplay categoryDisplay = new MBCategoryDisplay(scopeGroupId, categoryId);
%>

<clay:container>
	<div class="statistics-panel">
		<h3><liferay-ui:message key="overview" /></h3>

		<clay:row>
			<clay:col
				md="4"
			>
				<div class="overview-container statistics-panel">
					<div class="sticker sticker-categories sticker-user-icon">
						<clay:icon
							symbol="categories"
						/>
					</div>

					<small class="text-uppercase"><liferay-ui:message key="categories" /></small>

					<p class="statistics-number"><%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %></p>
				</div>
			</clay:col>

			<clay:col
				md="4"
			>
				<div class="overview-container statistics-panel">
					<div class="sticker sticker-posts sticker-user-icon">
						<clay:icon
							symbol="message-boards"
						/>
					</div>

					<small class="text-uppercase"><liferay-ui:message key="posts" /></small>

					<p class="statistics-number"><%= numberFormat.format(MBStatsUserLocalServiceUtil.getMessageCountByGroupId(scopeGroupId)) %></p>
				</div>
			</clay:col>

			<clay:col
				md="4"
			>
				<div class="overview-container statistics-panel">
					<div class="sticker sticker-participants sticker-user-icon">
						<clay:icon
							symbol="users"
						/>
					</div>

					<small class="text-uppercase"><liferay-ui:message key="participants" /></small>

					<p class="statistics-number"><%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %></p>
				</div>
			</clay:col>
		</clay:row>
	</div>

	<div class="statistics-panel">
		<h3><liferay-ui:message key="top-posters" /></h3>

		<liferay-ui:search-container
			emptyResultsMessage="there-are-no-top-posters"
			iteratorURL="<%= portletURL %>"
			total="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId) %>"
		>
			<liferay-ui:search-container-results
				results="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupId(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.message.boards.model.MBStatsUser"
				keyProperty="statsUserId"
				modelVar="statsUser"
			>
				<%@ include file="/message_boards/top_posters_user_display.jspf" %>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="descriptive"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</clay:container>

<%
PortalUtil.setPageSubtitle(LanguageUtil.get(request, "statistics"), request);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format("statistics", TextFormatter.O)), portletURL.toString());
%>