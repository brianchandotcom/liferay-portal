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
		<portlet:actionURL name="removeAddMenuFavItem" var="removeAddMenuFavItemURL">
			<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
			<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			icon="star"
			linkCssClass="icon-monospaced text-default"
			markupView="lexicon"
			message="remove-favorite"
			url="<%= removeAddMenuFavItemURL %>"
		/>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= journalDisplayContext.getAddMenuFavItemsLength() < journalWebConfiguration.maxAddMenuItems() %>">
				<portlet:actionURL name="addAddMenuFavItem" var="addAddMenuFavItemURL">
					<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
					<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					icon="star-o"
					linkCssClass="icon-monospaced text-default"
					markupView="lexicon"
					message="add-favorite"
					url="<%= addAddMenuFavItemURL %>"
				/>
			</c:when>
			<c:otherwise>
				<liferay-ui:icon
					cssClass="icon-monospaced text-muted"
					icon="star-o"
					markupView="lexicon"
					message="add-favorite"
				/>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>