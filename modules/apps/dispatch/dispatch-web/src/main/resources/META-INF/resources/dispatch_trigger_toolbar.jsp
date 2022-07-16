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