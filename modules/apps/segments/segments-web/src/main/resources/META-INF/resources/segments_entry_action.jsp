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
SegmentsDisplayContext segmentsDisplayContext = (SegmentsDisplayContext)request.getAttribute(SegmentsWebKeys.SEGMENTS_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

SegmentsEntry segmentsEntry = (SegmentsEntry)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message='<%= LanguageUtil.format(request, "show-more-options-for-x", HtmlUtil.escape(segmentsEntry.getName(locale))) %>'
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= segmentsDisplayContext.isShowUpdateAction(segmentsEntry) %>">
		<liferay-ui:icon
			message="edit"
			url="<%= segmentsDisplayContext.getEditURL(segmentsEntry) %>"
		/>
	</c:if>

	<c:if test="<%= segmentsDisplayContext.isShowViewAction(segmentsEntry) %>">
		<liferay-ui:icon
			message="view-members"
			url="<%= segmentsDisplayContext.getPreviewMembersURL(segmentsEntry) %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= segmentsDisplayContext.isShowAssignUserRolesAction(segmentsEntry) %>">
		<liferay-ui:icon
			data="<%= segmentsDisplayContext.getAssignUserRolesDataMap(segmentsEntry) %>"
			linkCssClass="<%= segmentsDisplayContext.getAssignUserRolesLinkCss(segmentsEntry) %>"
			message="assign-site-roles"
			url="javascript:void(0);"
		/>
	</c:if>

	<c:if test="<%= segmentsDisplayContext.isShowPermissionAction(segmentsEntry) %>">
		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= segmentsDisplayContext.getPermissionURL(segmentsEntry) %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= segmentsDisplayContext.isShowDeleteAction(segmentsEntry) %>">
		<liferay-ui:icon-delete
			url="<%= segmentsDisplayContext.getDeleteURL(segmentsEntry) %>"
		/>
	</c:if>
</liferay-ui:icon-menu>