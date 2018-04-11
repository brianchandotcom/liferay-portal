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
long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
long classPK = ParamUtil.getLong(request, "classPK");
String displayStyle = ParamUtil.getString(request, "displayStyle", "list");
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");
boolean searchRestriction = ParamUtil.getBoolean(request, "searchRestriction");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/select_structure.jsp");
portletURL.setParameter("classPK", String.valueOf(classPK));
portletURL.setParameter("eventName", eventName);

SearchContainer structureSearch = new StructureSearch(renderRequest, portletURL, WorkflowConstants.STATUS_APPROVED);

String orderByCol = ParamUtil.getString(request, "orderByCol", "modified-date");

structureSearch.setOrderByCol(orderByCol);

String orderByType = ParamUtil.getString(request, "orderByType", "asc");

structureSearch.setOrderByType(orderByType);

OrderByComparator<DDMStructure> orderByComparator = DDMUtil.getStructureOrderByComparator(orderByCol, orderByType);

structureSearch.setOrderByComparator(orderByComparator);

request.setAttribute(WebKeys.SEARCH_CONTAINER, structureSearch);
%>

<liferay-util:include page="/structure_navigation_bar.jsp" servletContext="<%= application %>" />

<liferay-frontend:management-bar>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= structureSearch.getOrderByCol() %>"
			orderByType="<%= structureSearch.getOrderByType() %>"
			orderColumns='<%= new String[] {"modified-date", "id"} %>'
			portletURL="<%= portletURL %>"
		/>

		<li>
			<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" varImpl="searchURL">
				<portlet:param name="mvcPath" value="/select_structure.jsp" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="eventName" value="<%= eventName %>" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= searchURL.toString() %>" method="post" name="searchForm">
				<liferay-util:include page="/structure_search.jsp" servletContext="<%= application %>" />
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<c:if test="<%= ddmDisplay.isShowAddStructureButton() && DDMStructurePermission.containsAddStructurePermission(permissionChecker, groupId, scopeClassNameId) && !searchRestriction %>">
			<portlet:renderURL var="viewStructureURL">
				<portlet:param name="mvcPath" value="/select_structure.jsp" />
				<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
				<portlet:param name="eventName" value="<%= eventName %>" />
			</portlet:renderURL>

			<portlet:renderURL var="addStructureURL">
				<portlet:param name="mvcPath" value="/edit_structure.jsp" />
				<portlet:param name="redirect" value="<%= viewStructureURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add") %>'
					url="<%= addStructureURL %>"
				/>
			</liferay-frontend:add-menu>
		</c:if>
	</liferay-frontend:management-bar-buttons>
</liferay-frontend:management-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectStructureFm">
	<liferay-ui:search-container
		searchContainer="<%= structureSearch %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/structure_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			keyProperty="structureId"
			modelVar="structure"
		>
			<liferay-ui:search-container-column-text
				name="id"
				value="<%= String.valueOf(structure.getStructureId()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
			>
				<c:choose>
					<c:when test="<%= ddmDisplay.isEnableSelectStructureLink(structure, classPK) %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						if (ddmDisplay.isShowConfirmSelectStructure()) {
							data.put("confirm-selection", Boolean.TRUE.toString());
							data.put("confirm-selection-message", ddmDisplay.getConfirmSelectStructureMessage(locale));
						}

						data.put("ddmstructureid", structure.getStructureId());
						data.put("ddmstructurekey", structure.getStructureKey());
						data.put("name", structure.getName(locale));
						%>

						<aui:a cssClass="selector-button" data="<%= data %>" href="javascript:;">
							<%= HtmlUtil.escape(structure.getUnambiguousName(structureSearch.getResults(), themeDisplay.getScopeGroupId(), locale)) %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(structure.getUnambiguousName(structureSearch.getResults(), themeDisplay.getScopeGroupId(), locale)) %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
				value="<%= HtmlUtil.escape(structure.getDescription(locale)) %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= structure.getModifiedDate() %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= displayStyle %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />searchForm.<portlet:namespace />keywords);

	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectStructureFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>