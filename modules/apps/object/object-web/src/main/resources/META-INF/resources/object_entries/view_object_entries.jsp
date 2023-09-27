<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			propsTransformer="js/components/FDSPropsTransformer/MultiselectPicklistFDSPropsTransformer"
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