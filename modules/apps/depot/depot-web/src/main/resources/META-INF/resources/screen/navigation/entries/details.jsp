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
DepotEntry depotEntry = (DepotEntry)request.getAttribute(DepotAdminWebKeys.DEPOT_ENTRY);

Group group = GroupServiceUtil.getGroup(depotEntry.getGroupId());
%>

<liferay-frontend:fieldset
	collapsible="false"
	label='<%= LanguageUtil.get(request, "details") %>'
>
	<aui:model-context bean="<%= group %>" model="<%= Group.class %>" />

	<aui:input name="repositoryId" type="resource" value="<%= String.valueOf(depotEntry.getDepotEntryId()) %>" />

	<aui:input name="name" placeholder="name" required="<%= true %>" value="<%= String.valueOf(group.getName(locale)) %>" />

	<aui:input name="description" placeholder="description" />
</liferay-frontend:fieldset>