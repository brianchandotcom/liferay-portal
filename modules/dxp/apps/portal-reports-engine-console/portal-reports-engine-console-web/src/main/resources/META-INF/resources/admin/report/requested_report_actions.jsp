<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	Date now = new Date();
	%>

	<c:if test="<%= entry.isRepeating() && ((entry.getEndDate() == null) || now.before(entry.getEndDate())) %>">
		<c:if test="<%= EntryPermissionChecker.contains(permissionChecker, entry.getEntryId(), ActionKeys.DELETE) %>">
			<portlet:renderURL var="searchRequestURL">
				<portlet:param name="mvcPath" value="/admin/view.jsp" />
				<portlet:param name="tabs1" value="reports" />
			</portlet:renderURL>

			<portlet:actionURL name="unscheduleReportRequest" var="unscheduleURL">
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

		<portlet:actionURL name="archiveRequest" var="deleteURL">
			<portlet:param name="redirect" value="<%= searchRequestURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>