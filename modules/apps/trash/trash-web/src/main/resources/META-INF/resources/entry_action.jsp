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

TrashEntry trashEntry = null;

if (row != null) {
	trashEntry = (TrashEntry)row.getObject();
}
else {
	trashEntry = (TrashEntry)request.getAttribute(TrashWebKeys.TRASH_ENTRY);
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>

	<%
	TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(trashEntry.getClassName());
	%>

	<c:choose>
		<c:when test="<%= trashHandler.isRestorable(trashEntry.getClassPK()) && !trashHandler.isInTrashContainer(trashEntry.getClassPK()) %>">
			<portlet:actionURL name="restoreEntries" var="restoreEntryURL">
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="trashEntryId" value="<%= String.valueOf(trashEntry.getEntryId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="restore"
				url="<%= restoreEntryURL.toString() %>"
			/>
		</c:when>
		<c:when test="<%= !trashHandler.isRestorable(trashEntry.getClassPK()) && trashHandler.isMovable() %>">
			<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/view_container_model.jsp" />
				<portlet:param name="classNameId" value="<%= String.valueOf(trashEntry.getClassNameId()) %>" />
				<portlet:param name="classPK" value="<%= String.valueOf(trashEntry.getClassPK()) %>" />
				<portlet:param name="containerModelClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(trashHandler.getContainerModelClassName(trashEntry.getClassPK()))) %>" />
			</portlet:renderURL>

			<%
			String taglibOnClick = liferayPortletResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
			%>

			<liferay-ui:icon
				message="restore"
				onClick="<%= taglibOnClick %>"
				url="javascript:;"
			/>
		</c:when>
	</c:choose>

	<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="trashEntryId" value="<%= String.valueOf(trashEntry.getEntryId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteEntryURL %>"
	/>
</liferay-ui:icon-menu>