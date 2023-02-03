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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure ddmStructure = (DDMStructure)row.getObject();
%>

<c:choose>
	<c:when test="<%= ArrayUtil.contains(journalDisplayContext.getAddMenuFavItems(), ddmStructure.getStructureKey()) %>">
		<portlet:actionURL name="/journal/remove_menu_fav_item" var="removeAddMenuFavItemURL">
			<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
			<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
		</portlet:actionURL>

		<clay:link
			aria-label='<%= LanguageUtil.get(request, "remove-favorite") %>'
			cssClass="icon-monospaced lfr-portal-tooltip text-default"
			href="<%= removeAddMenuFavItemURL %>"
			icon="star"
			title='<%= LanguageUtil.get(request, "remove-favorite") %>'
		/>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= journalDisplayContext.getAddMenuFavItemsLength() < journalWebConfiguration.maxAddMenuItems() %>">
				<portlet:actionURL name="/journal/add_menu_fav_item" var="addAddMenuFavItemURL">
					<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
					<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
				</portlet:actionURL>

				<clay:link
					aria-label='<%= LanguageUtil.get(request, "add-favorite") %>'
					cssClass="icon-monospaced lfr-portal-tooltip text-default"
					href="<%= addAddMenuFavItemURL %>"
					icon="star-o"
					title='<%= LanguageUtil.get(request, "add-favorite") %>'
				/>
			</c:when>
			<c:otherwise>
				<clay:icon
					aria-label='<%= LanguageUtil.get(request, "add-favorite") %>'
					cssClass="icon-monospaced lfr-portal-tooltip text-muted"
					symbol="star-o"
					title='<%= LanguageUtil.get(request, "add-favorite") %>'
				/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>