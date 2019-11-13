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

<%@ include file="/sharing/init.jsp" %>

<%
Map<String, Object> data = new HashMap<>();

data.put("classNameId", request.getAttribute("classNameId"));
data.put("classPK", request.getAttribute("classPK"));
data.put("dialogId", request.getAttribute("dialogId"));
data.put("portletNamespace", request.getAttribute("portletNamespace"));
data.put("shareActionURL", request.getAttribute("shareActionURL"));
data.put("sharingEntryPermissionDisplays", request.getAttribute("sharingEntryPermissionDisplays"));
data.put("sharingEntryPermissionDisplayActionId", request.getAttribute("sharingEntryPermissionDisplayActionId"));
data.put("sharingUserAutocompleteURL", request.getAttribute("sharingUserAutocompleteURL"));
data.put("sharingVerifyEmailAddressURL", request.getAttribute("sharingVerifyEmailAddressURL"));
%>

<react:component
	data="<%= data %>"
	module="sharing/js/Sharing.es"
/>