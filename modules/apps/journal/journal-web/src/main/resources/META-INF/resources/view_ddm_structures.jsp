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
JournalDDMStructuresDisplayContext journalDDMStructuresDisplayContext = new JournalDDMStructuresDisplayContext(renderRequest, renderResponse);

JournalDDMStructuresManagementToolbarDisplayContext journalDDMStructuresManagementToolbarDisplayContext = new JournalDDMStructuresManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, journalDDMStructuresDisplayContext);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationBarItems("structures") %>'
/>

<clay:management-toolbar
	displayContext="<%= journalDDMStructuresManagementToolbarDisplayContext %>"
/>

<portlet:actionURL name="/journal/delete_ddm_structure" var="deleteDDMStructureURL">
	<portlet:param name="mvcPath" value="/view_ddm_structures.jsp" />
</portlet:actionURL>

<aui:form action="<%= deleteDDMStructureURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByStructureLinks.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-structure-links" />
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-templates" />
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureThatHasChild.class %>" message="the-structure-cannot-be-deleted-because-it-has-one-or-more-substructures" />

	<liferay-ui:search-container
		id="ddmStructures"
		searchContainer="<%= journalDDMStructuresDisplayContext.getDDMStructureSearch() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			keyProperty="structureId"
			modelVar="ddmStructure"
		>
			<liferay-ui:search-container-column-text
				name="id"
				property="structureId"
			/>

			<%
			String rowHREF = StringPool.BLANK;

			if (DDMStructurePermission.contains(permissionChecker, ddmStructure, ActionKeys.UPDATE)) {
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcPath", "/edit_ddm_structure.jsp");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("ddmStructureId", String.valueOf(ddmStructure.getStructureId()));

				rowHREF = rowURL.toString();
			}

			Map<String, Object> rowData = new HashMap<>();

			rowData.put("actions", journalDDMStructuresManagementToolbarDisplayContext.getAvailableActions(ddmStructure));

			row.setData(rowData);
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				href="<%= rowHREF %>"
				name="name"
				value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="description"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(ddmStructure.getDescription(locale)) %>"
			/>

			<%
			Group group = GroupLocalServiceUtil.getGroup(ddmStructure.getGroupId());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-minw-150"
				name="scope"
				value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-ws-nowrap"
				name="modified-date"
				value="<%= ddmStructure.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/ddm_structure_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= journalDDMStructuresManagementToolbarDisplayContext.getDefaultEventHandler() %>"
	module="js/DDMStructuresManagementToolbarDefaultEventHandler.es"
/>