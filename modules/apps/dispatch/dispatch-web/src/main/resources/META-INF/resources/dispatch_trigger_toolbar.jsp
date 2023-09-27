<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DispatchTriggerDisplayContext dispatchTriggerDisplayContext = (DispatchTriggerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/dispatch/edit_dispatch_trigger" var="deleteEntriesURL" />

<clay:management-toolbar
	actionDropdownItems="<%= dispatchTriggerDisplayContext.getActionDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteEntriesURL", deleteEntriesURL.toString()
		).put(
			"inputId", Constants.CMD
		).put(
			"inputValue", Constants.DELETE
		).build()
	%>'
	creationMenu="<%= dispatchTriggerDisplayContext.getCreationMenu() %>"
	propsTransformer="js/DispatchTriggerManagementToolbarPropsTransformer"
	searchContainerId='<%= ParamUtil.getString(request, "searchContainerId", "dispatchTrigger") %>'
	showSearch="<%= false %>"
	viewTypeItems="<%= dispatchTriggerDisplayContext.getViewTypeItems() %>"
/>