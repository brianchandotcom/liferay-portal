<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

DispatchTrigger dispatchTrigger = (DispatchTrigger)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= DispatchTriggerPermission.contains(permissionChecker, dispatchTrigger, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/dispatch/edit_dispatch_trigger" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="dispatchTriggerId" value="<%= String.valueOf(dispatchTrigger.getDispatchTriggerId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= DispatchTriggerPermission.contains(permissionChecker, dispatchTrigger, ActionKeys.DELETE) && !dispatchTrigger.isSystem() %>">
		<portlet:actionURL name="/dispatch/edit_dispatch_trigger" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="dispatchTriggerId" value="<%= String.valueOf(dispatchTrigger.getDispatchTriggerId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>