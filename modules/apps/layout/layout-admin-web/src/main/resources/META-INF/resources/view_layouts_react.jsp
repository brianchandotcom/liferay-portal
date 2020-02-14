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
LayoutsAdminReactDisplayContext layoutsAdminReactDisplayContext = (LayoutsAdminReactDisplayContext)request.getAttribute(LayoutAdminWebKeys.LAYOUT_PAGE_LAYOUT_ADMIN_REACT_DISPLAY_CONTEXT);

LayoutsAdminManagementToolbarDisplayContext layoutsManagementToolbarDisplayContext = new LayoutsAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, layoutsAdminReactDisplayContext);
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
		<c:when test="<%= layoutsAdminReactDisplayContext.hasLayouts() %>">
			<c:choose>
				<c:when test="<%= layoutsAdminReactDisplayContext.isSearch() %>">
					<liferay-util:include page="/flattened_view.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>

					<%
					Map<String, Object> layoutData = new HashMap<>();

					layoutData.put("context", Collections.singletonMap("namespace", renderResponse.getNamespace()));

					Map<String, Object> layoutProps = new HashMap<>();

					layoutProps.put("breadcrumbEntries", layoutsAdminReactDisplayContext.getBreadcrumbEntriesJSONArray());
					layoutProps.put("layoutColumns", layoutsAdminReactDisplayContext.getLayoutColumnsJSONArray());
					layoutProps.put("searchContainerId", "pages");

					layoutData.put("props", layoutProps);
					%>

					<div>
						<react:component
							data="<%= layoutData %>"
							module="js/Layout.es"
						/>
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<liferay-frontend:empty-result-message
				actionDropdownItems="<%= layoutsAdminReactDisplayContext.isShowAddRootLayoutButton() ? layoutsAdminReactDisplayContext.getAddLayoutDropdownItems() : null %>"
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