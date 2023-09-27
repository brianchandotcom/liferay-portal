<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Entry entry = (Entry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= EntryPermissionChecker.contains(permissionChecker, entry.getEntryId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= Entry.class.getName() %>"
			modelResourceDescription="<%= String.valueOf(entry.getEntryId()) %>"
			resourcePrimKey="<%= String.valueOf(entry.getEntryId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<%
	Date date = new Date();
	%>

	<c:if test="<%= entry.isRepeating() && ((entry.getEndDate() == null) || date.before(entry.getEndDate())) %>">
		<c:if test="<%= EntryPermissionChecker.contains(permissionChecker, entry.getEntryId(), ActionKeys.DELETE) %>">
			<portlet:renderURL var="searchRequestURL">
				<portlet:param name="mvcPath" value="/admin/view.jsp" />
				<portlet:param name="tabs1" value="reports" />
			</portlet:renderURL>

			<portlet:actionURL name="/reports_admin/unschedule_report_request" var="unscheduleURL">
				<portlet:param name="redirect" value="<%= searchRequestURL %>" />
				<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon
				message="unschedule"
				url="<%= unscheduleURL %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= EntryPermissionChecker.contains(permissionChecker, entry.getEntryId(), ActionKeys.DELETE) %>">
		<portlet:renderURL var="searchRequestURL">
			<portlet:param name="mvcPath" value="/admin/view.jsp" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="tabs1" value="reports" />
		</portlet:renderURL>

		<portlet:actionURL name="/reports_admin/archive_request" var="deleteURL">
			<portlet:param name="redirect" value="<%= searchRequestURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>