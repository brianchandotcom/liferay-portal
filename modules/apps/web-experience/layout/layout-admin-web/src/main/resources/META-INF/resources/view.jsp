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
ViewLayoutsDisplayContext viewLayoutsDisplayContext = new ViewLayoutsDisplayContext(liferayPortletRequest, liferayPortletResponse);

renderResponse.setTitle(LanguageUtil.get(request, "pages"));
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="pages" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= false %>"
	includeCheckBox="<%= true %>"
	searchContainerId="pages"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= viewLayoutsDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="<%= viewLayoutsDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys="<%= viewLayoutsDisplayContext.getNavigationKeys() %>"
			portletURL="<%= viewLayoutsDisplayContext.getPortletURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= viewLayoutsDisplayContext.getOrderByCol() %>"
			orderByType="<%= viewLayoutsDisplayContext.getOrderByType() %>"
			orderColumns="<%= viewLayoutsDisplayContext.getOrderColumns() %>"
			portletURL="<%= viewLayoutsDisplayContext.getPortletURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedPages" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="/layout/delete_layout" var="deleteLayoutURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="pages"
		searchContainer="<%= viewLayoutsDisplayContext.getLayoutsSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Layout"
			keyProperty="plid"
			modelVar="curLayout"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="layout"
				value="<%= HtmlUtil.escape(curLayout.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="path"
			>
				<%= HtmlUtil.escape(viewLayoutsDisplayContext.getPath(curLayout, locale)) %> <strong><%= HtmlUtil.escape(curLayout.getName(locale)) %></strong>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-date
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-jsp
				path="/layout_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= viewLayoutsDisplayContext.getDisplayStyle() %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= viewLayoutsDisplayContext.isShowAddRootLayoutButton() %>">

	<%
	PortletURL addLayoutURL = viewLayoutsDisplayContext.getAddLayoutURL();

	addLayoutURL.setParameter("redirect", currentURL);
	addLayoutURL.setParameter("backURL", currentURL);
	%>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "page") %>' url="<%= addLayoutURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedPages').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>