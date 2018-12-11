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

AssetTag tag = (AssetTag)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= assetTagsDisplayContext.isShowTagsActions() %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_tag.jsp" />
			<portlet:param name="tagId" value="<%= String.valueOf(tag.getTagId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			label="<%= true %>"
			message="edit"
			url="<%= editURL %>"
		/>

		<portlet:renderURL var="mergeURL">
			<portlet:param name="mvcPath" value="/merge_tag.jsp" />
			<portlet:param name="mergeTagIds" value="<%= String.valueOf(tag.getTagId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			label="<%= true %>"
			message="merge"
			url="<%= mergeURL %>"
		/>
	</c:if>

	<portlet:actionURL name="deleteTag" var="deleteURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="tagId" value="<%= String.valueOf(tag.getTagId()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		url="<%= deleteURL %>"
	/>
</liferay-ui:icon-menu>