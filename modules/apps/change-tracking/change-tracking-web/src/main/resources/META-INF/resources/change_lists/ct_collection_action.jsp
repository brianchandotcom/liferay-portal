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
	<c:if test="<%= ctCollection.getCtCollectionId() != changeListsDisplayContext.getCtCollectionId() %>">
		<liferay-portlet:actionURL name="/change_lists/checkout_ct_collection" var="checkoutURL">
			<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:actionURL>

		<liferay-ui:icon
			message="work-on-publication"
			url="<%= checkoutURL %>"
		/>
	</c:if>

	<liferay-portlet:renderURL var="editURL">
		<portlet:param name="mvcRenderCommandName" value="/change_lists/edit_ct_collection" />
		<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editURL %>"
	/>

	<liferay-portlet:renderURL var="reviewURL">
		<portlet:param name="mvcRenderCommandName" value="/change_lists/view_changes" />
		<portlet:param name="backURL" value="<%= currentURL %>" />
		<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="review-changes"
		url="<%= reviewURL %>"
	/>

	<c:if test="<%= changeListsDisplayContext.isPublishEnabled(ctCollection.getCtCollectionId()) %>">
		<li aria-hidden="true" class="dropdown-divider" role="presentation"></li>

		<liferay-portlet:renderURL var="conflictsURL">
			<portlet:param name="mvcRenderCommandName" value="/change_lists/view_conflicts" />
			<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="prepare-to-publish"
			url="<%= conflictsURL %>"
		/>
	</c:if>

	<li aria-hidden="true" class="dropdown-divider" role="presentation"></li>

	<liferay-portlet:actionURL name="/change_lists/delete_ct_collection" var="deleteURL">
		<portlet:param name="ctCollectionId" value="<%= String.valueOf(ctCollection.getCtCollectionId()) %>" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</liferay-portlet:actionURL>

	<liferay-ui:icon-delete
		confirmation="are-you-sure-you-want-to-delete-this-publication"
		message="delete"
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>