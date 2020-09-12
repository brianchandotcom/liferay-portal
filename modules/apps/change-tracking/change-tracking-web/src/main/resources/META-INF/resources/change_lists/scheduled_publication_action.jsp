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

<%@ include file="/change_lists/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

CTCollection ctCollection = (CTCollection)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CTCollectionPermission.contains(permissionChecker, ctCollection, CTActionKeys.PUBLISH) %>">
		<liferay-portlet:renderURL var="rescheduleURL">
			<portlet:param name="mvcRenderCommandName" value="/change_lists/schedule_publication" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="reschedule"
			url="<%= rescheduleURL %>"
		/>

		<liferay-portlet:actionURL name="/change_lists/unschedule_publication" var="unscheduleURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			message="unschedule"
			url="<%= unscheduleURL %>"
		/>

		<li aria-hidden="true" class="dropdown-divider" role="presentation"></li>
	</c:if>

	<liferay-portlet:renderURL var="reviewURL">
		<portlet:param name="mvcRenderCommandName" value="/change_lists/view_changes" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
		<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="review-changes"
		url="<%= reviewURL %>"
	/>
</liferay-ui:icon-menu>