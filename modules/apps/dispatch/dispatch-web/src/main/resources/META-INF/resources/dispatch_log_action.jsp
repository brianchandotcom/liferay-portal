<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			<portlet:param name="dispatchTriggerId" value="<%= String.valueOf(dispatchLog.getDispatchTriggerId()) %>" />
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