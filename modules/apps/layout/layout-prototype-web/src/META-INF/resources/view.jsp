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
PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:error exception="<%= RequiredLayoutPrototypeException.class %>" message="you-cannot-delete-page-templates-that-are-used-by-a-page" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item cssClass="active" label="templates" />
	</aui:nav>
</aui:nav-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="get" name="fm">
	<liferay-portlet:renderURLParams varImpl="portletURL" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-container
		emptyResultsMessage="no-page-templates-were-found"
		headerNames="name"
		iteratorURL="<%= portletURL %>"
		total="<%= LayoutPrototypeLocalServiceUtil.searchCount(company.getCompanyId(), null) %>"
	>
		<aui:input name="deleteLayoutPrototypesIds" type="hidden" />

		<liferay-ui:search-container-results
			results="<%= LayoutPrototypeLocalServiceUtil.search(company.getCompanyId(), null, searchContainer.getStart(), searchContainer.getEnd(), null) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.LayoutPrototype"
			escapedModel="<%= true %>"
			keyProperty="layoutPrototypeId"
			modelVar="layoutPrototype"
		>
			<liferay-ui:search-container-column-text
				name="name"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcPath" value="/edit_layout_prototype.jsp" />
					<portlet:param name="layoutPrototypeId" value="<%= String.valueOf(layoutPrototype.getLayoutPrototypeId()) %>" />
				</liferay-portlet:renderURL>

				<aui:a href="<%= rowURL.toString() %>"><%= layoutPrototype.getName(locale) %></aui:a>

				<%
				int mergeFailCount = SitesUtil.getMergeFailCount(layoutPrototype);
				%>

				<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
					<liferay-ui:icon
						iconCssClass="icon-warning-sign"
						message='<%= LanguageUtil.format(request, "the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors", new Object[] {mergeFailCount, LanguageUtil.get(request, "page-template")}, false) %>'
					/>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= layoutPrototype.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="active"
			>
				<%= LanguageUtil.get(request, layoutPrototype.isActive()? "yes" : "no") %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/layout_prototype_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_PROTOTYPE) %>">
	<portlet:renderURL var="addLayoutPrototypeURL">
		<portlet:param name="mvcPath" value="/edit_layout_prototype.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addLayoutPrototypeURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>