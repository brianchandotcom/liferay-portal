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

<liferay-util:buffer
	var="linkTemplate"
>
	<button class="{cssClass} btn btn-unstyled" data-label="{label}" data-url="{url}" id="{id}" title="{title}">
		{label}
	</button>
</liferay-util:buffer>

<%
Group group = StagingUtil.getStagingGroup(themeDisplay.getSiteGroupId());
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
%>

<liferay-layout:layouts-tree
	draggableTree="<%= false %>"
	groupId="<%= group.getGroupId() %>"
	linkTemplate="<%= linkTemplate %>"
	privateLayout="<%= privateLayout %>"
	rootLinkTemplate='<span class="{cssClass}" id="{id}" title="{title}">{label}</span>'
	rootNodeName="<%= group.getLayoutRootNodeName(privateLayout, locale) %>"
	treeId="layoutsTree"
/>