<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DDMStructure ddmStructure = (DDMStructure)row.getObject();
%>

<c:choose>
	<c:when test="<%= ArrayUtil.contains(journalDisplayContext.getAddMenuFavItems(), ddmStructure.getStructureId()) %>">
		<portlet:actionURL name="/journal/remove_menu_fav_item" var="removeAddMenuFavItemURL">
			<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
			<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
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
					<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructure.getStructureId()) %>" />
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