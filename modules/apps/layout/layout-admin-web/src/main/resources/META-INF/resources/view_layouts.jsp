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
LayoutsAdminManagementToolbarDisplayContext layoutsManagementToolbarDisplayContext = new LayoutsAdminManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, layoutsAdminDisplayContext);
%>

<liferay-ui:success key='<%= portletDisplay.getPortletName() + "layoutUpdated" %>' message='<%= LanguageUtil.get(resourceBundle, "the-page-was-updated-succesfully") %>' />

<liferay-ui:success key="layoutPublished" message="the-page-was-published-succesfully" />

<liferay-ui:error embed="<%= false %>" exception="<%= GroupInheritContentException.class %>" message="this-page-cannot-be-deleted-and-cannot-have-child-pages-because-it-is-associated-with-a-site-template" />

<clay:management-toolbar
	displayContext="<%= layoutsManagementToolbarDisplayContext %>"
/>

<liferay-ui:error exception="<%= LayoutTypeException.class %>">

	<%
	LayoutTypeException lte = (LayoutTypeException)errorException;
	%>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
		<liferay-ui:message arguments='<%= "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= RequiredSegmentsExperienceException.MustNotDeleteSegmentsExperienceReferencedBySegmentsExperiments.class %>" message="this-page-cannot-be-deleted-because-it-has-ab-tests-in-progress" />

<aui:form cssClass="container-fluid-1280" name="fm">
	<c:choose>
		<c:when test="<%= layoutsAdminDisplayContext.hasLayouts() %>">
			<c:choose>
				<c:when test="<%= layoutsAdminDisplayContext.isSearch() %>">
					<liferay-util:include page="/flattened_view.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>

					<%
					Map<String, Object> context = new HashMap<>();

					context.put("breadcrumbEntries", layoutsAdminDisplayContext.getBreadcrumbEntriesJSONArray());
					context.put("getItemChildrenURL", layoutsAdminDisplayContext.getLayoutChildrenURL());
					context.put("layoutColumns", layoutsAdminDisplayContext.getLayoutColumnsJSONArray());
					context.put("moveLayoutColumnItemURL", layoutsAdminDisplayContext.getMoveLayoutColumnItemURL());
					context.put("pathThemeImages", themeDisplay.getPathThemeImages());
					context.put("portletNamespace", renderResponse.getNamespace());
					context.put("searchContainerId", "pages");
					context.put("siteNavigationMenuNames", layoutsAdminDisplayContext.getAutoSiteNavigationMenuNames());
					%>

					<soy:component-renderer
						context="<%= context %>"
						module="js/miller_columns/Layout.es"
						templateNamespace="com.liferay.layout.admin.web.Layout.render"
					/>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<liferay-frontend:empty-result-message
				actionDropdownItems="<%= layoutsAdminDisplayContext.isShowAddRootLayoutButton() ? layoutsAdminDisplayContext.getAddLayoutDropdownItems() : null %>"
				description='<%= LanguageUtil.get(request, "fortunately-it-is-very-easy-to-add-new-ones") %>'
				elementType='<%= LanguageUtil.get(request, "pages") %>'
			/>
		</c:otherwise>
	</c:choose>
</aui:form>

<liferay-frontend:component
	componentId="<%= layoutsManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/LayoutsManagementToolbarDefaultEventHandler.es"
/>