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
String navigation = ParamUtil.getString(request, "navigation", "all");

String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String orderByCol = ParamUtil.getString(request, "orderByCol", "create-date");
String orderByType = ParamUtil.getString(request, "orderByType", "asc");

Boolean active = null;

if (navigation.equals("active")) {
	active = true;
}
else if (navigation.equals("inactive")) {
	active = false;
}

int layoutSetPrototypesCount = LayoutSetPrototypeLocalServiceUtil.searchCount(company.getCompanyId(), active);

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:error exception="<%= RequiredLayoutSetPrototypeException.class %>" message="you-cannot-delete-site-templates-that-are-used-by-a-site" />

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="templates" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<c:if test='<%= (layoutSetPrototypesCount > 0) || !navigation.equals("all") %>'>
	<liferay-frontend:management-bar
		includeCheckBox="<%= true %>"
		searchContainerId="layoutSetPrototype"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
				portletURL="<%= renderResponse.createRenderURL() %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= orderByCol %>"
				orderByType="<%= orderByType %>"
				orderColumns='<%= new String[] {"create-date"} %>'
				portletURL="<%= renderResponse.createRenderURL() %>"
			/>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= portletURL %>"
				selectedDisplayStyle="<%= displayStyle %>"
			/>
		</liferay-frontend:management-bar-buttons>

		<liferay-frontend:management-bar-action-buttons>
			<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedLayoutSetPrototypes" label="delete" />
		</liferay-frontend:management-bar-action-buttons>
	</liferay-frontend:management-bar>
</c:if>

<portlet:actionURL name="deleteLayoutSetPrototypes" var="deleteLayoutSetPrototypesURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteLayoutSetPrototypesURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-site-templates.-you-can-add-a-site-template-by-clicking-the-plus-button-on-the-bottom-right-corner"
		headerNames="name"
		id="layoutSetPrototype"
		iteratorURL="<%= portletURL %>"
		rowChecker="<%= new EmptyOnClickRowChecker(renderResponse) %>"
		total="<%= layoutSetPrototypesCount %>"
	>

		<%
		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<LayoutSetPrototype> orderByComparator = new LayoutSetPrototypeCreateDateComparator(orderByAsc);

		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(orderByType);
		%>

		<liferay-ui:search-container-results
			results="<%= LayoutSetPrototypeLocalServiceUtil.search(company.getCompanyId(), active, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.LayoutSetPrototype"
			cssClass="selectable"
			escapedModel="<%= true %>"
			keyProperty="layoutSetPrototypeId"
			modelVar="layoutSetPrototype"
		>

			<%
			String rowURL = null;

			Group group = layoutSetPrototype.getGroup();

			if (LayoutSetPrototypePermissionUtil.contains(permissionChecker, layoutSetPrototype.getLayoutSetPrototypeId(), ActionKeys.UPDATE) && (group.getPrivateLayoutsPageCount() > 0)) {
				rowURL = group.getDisplayURL(themeDisplay, true);
			}
			%>

			<liferay-ui:search-container-column-text
				name="name"
			>

				<aui:a href="<%= rowURL %>" target="_blank"><%= layoutSetPrototype.getName(locale) %></aui:a>

				<%
				int mergeFailCount = SitesUtil.getMergeFailCount(layoutSetPrototype);
				%>

				<c:if test="<%= mergeFailCount > PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD %>">
					<liferay-ui:message arguments='<%= new Object[] {mergeFailCount, LanguageUtil.get(request, "site-template")} %>' key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" translateArguments="<%= false %>" />
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= layoutSetPrototype.getDescription(locale) %>"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				property="createDate"
			/>

			<liferay-ui:search-container-column-text
				cssClass="list-group-item-field"
				name="active"
			>
				<%= LanguageUtil.get(request, layoutSetPrototype.isActive()? "yes" : "no") %>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				cssClass="list-group-item-field"
				href="<%= rowURL %>"
				path="/layout_set_prototype_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="<%= displayStyle %>" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_LAYOUT_SET_PROTOTYPE) %>">
	<portlet:renderURL var="addLayoutSetPrototypeURL">
		<portlet:param name="mvcPath" value="/edit_layout_set_prototype.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addLayoutSetPrototypeURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	$('#<portlet:namespace />deleteSelectedLayoutSetPrototypes').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm($(document.<portlet:namespace />fm));
			}
		}
	);
</aui:script>