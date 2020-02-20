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

<div class="container-fluid-1280">
	<div class="statistics-panel">
		<h3><liferay-ui:message key="overview" /></h3>

		<dl>
			<dt>
				<liferay-ui:message key="categories" />:
			</dt>
			<dd>
				<%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %>
			</dd>
			<dt>
				<c:choose>
					<c:when test="<%= MBStatsUserLocalServiceUtil.getMessageCountByGroupId(scopeGroupId) == 1 %>">
						<liferay-ui:message key="post" />:
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="posts" />:
					</c:otherwise>
				</c:choose>
			</dt>
			<dd>
				<%= numberFormat.format(MBStatsUserLocalServiceUtil.getMessageCountByGroupId(scopeGroupId)) %>
			</dd>
			<dt>
				<liferay-ui:message key="participants" />:
			</dt>
			<dd>
				<%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %>
			</dd>
		</dl>
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
</div>

<%
PortalUtil.setPageSubtitle(LanguageUtil.get(request, "statistics"), request);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format("statistics", TextFormatter.O)), portletURL.toString());
%>