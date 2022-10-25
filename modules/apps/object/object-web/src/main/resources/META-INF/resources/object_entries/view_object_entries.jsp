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
ViewObjectEntriesDisplayContext viewObjectEntriesDisplayContext = (ViewObjectEntriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

ObjectDefinition objectDefinition = viewObjectEntriesDisplayContext.getObjectDefinition();
%>

<c:choose>
	<c:when test="<%= objectDefinition.isPortlet() || Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTROL_PANEL) %>">
		<frontend-data-set:headless-display
			apiURL="<%= viewObjectEntriesDisplayContext.getAPIURL() %>"
			creationMenu="<%= viewObjectEntriesDisplayContext.getCreationMenu() %>"
			fdsActionDropdownItems="<%= viewObjectEntriesDisplayContext.getFDSActionDropdownItems() %>"
			fdsFilters="<%= viewObjectEntriesDisplayContext.getFDSFilters() %>"
			fdsSortItemList="<%= viewObjectEntriesDisplayContext.getFDSSortItemList() %>"
			formName="fm"
			id="<%= viewObjectEntriesDisplayContext.getFDSId() %>"
			itemsPerPage="<%= 20 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= liferayPortletResponse.createRenderURL() %>"
			propsTransformer="js/MultiselectPicklistFDSPropsTransformer"
			style="fluid"
		/>
	</c:when>
	<c:otherwise>
		<clay:alert
			displayType="warning"
			message="this-object-is-not-available"
			title="Warning"
		/>
	</c:otherwise>
</c:choose>