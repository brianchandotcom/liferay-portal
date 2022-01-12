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
DispatchLogDisplayContext dispatchLogDisplayContext = (DispatchLogDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DispatchLog dispatchLog = (DispatchLog)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DispatchTriggerPermission.contains(permissionChecker, dispatchLogDisplayContext.getDispatchTrigger(), ActionKeys.UPDATE) %>">
		<portlet:renderURL var="viewURL">
			<portlet:param name="mvcRenderCommandName" value="/dispatch/view_dispatch_log" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="dispatchLogId" value="<%= String.valueOf(dispatchLog.getDispatchLogId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="view"
			url="<%= viewURL %>"
		/>

		<c:if test="<%= DispatchTaskStatus.valueOf(dispatchLog.getStatus()) != DispatchTaskStatus.IN_PROGRESS %>">
			<portlet:actionURL name="/dispatch/edit_dispatch_log" var="deleteURL">
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="dispatchLogId" value="<%= String.valueOf(dispatchLog.getDispatchLogId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete
				url="<%= deleteURL %>"
			/>
		</c:if>
	</c:if>
</liferay-ui:icon-menu>