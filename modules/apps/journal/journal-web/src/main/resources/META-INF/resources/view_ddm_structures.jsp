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
%>

<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByStructureLinks.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-structure-links" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-templates" />
<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureThatHasChild.class %>" message="the-structure-cannot-be-deleted-because-it-has-one-or-more-substructures" />

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationBarItems("structures") %>'
/>

<clay:management-toolbar
	actionDropdownItems="<%= journalDDMStructuresDisplayContext.getActionItemsDropdownItems() %>"
	clearResultsURL="<%= journalDDMStructuresDisplayContext.getClearResultsURL() %>"
	componentId="ddmStructureManagementToolbar"
	creationMenu="<%= journalDDMStructuresDisplayContext.isShowAddButton() ? journalDDMStructuresDisplayContext.getCreationMenu() : null %>"
	disabled="<%= journalDDMStructuresDisplayContext.isDisabledManagementBar() %>"
	filterDropdownItems="<%= journalDDMStructuresDisplayContext.getFilterItemsDropdownItems() %>"
	itemsTotal="<%= journalDDMStructuresDisplayContext.getTotalItems() %>"
	searchActionURL="<%= journalDDMStructuresDisplayContext.getSearchActionURL() %>"
	searchContainerId="ddmStructures"
	sortingOrder="<%= journalDDMStructuresDisplayContext.getOrderByType() %>"
	sortingURL="<%= journalDDMStructuresDisplayContext.getSortingURL() %>"
/>

<portlet:actionURL name="/journal/delete_ddm_structure" var="deleteDDMStructureURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteDDMStructureURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

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
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowHREF %>"
				name="name"
				value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="description"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(ddmStructure.getDescription(locale)) %>"
			/>

			<%
			Group group = GroupLocalServiceUtil.getGroup(ddmStructure.getGroupId());
			%>

			<liferay-ui:search-container-column-text
				name="scope"
				value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
			/>

			<liferay-ui:search-container-column-date
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

<aui:script sandbox="<%= true %>">
	var deleteDDMStructures = function() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
			submitForm(document.querySelector('#<portlet:namespace />fm'));
		}
	}

	var ACTIONS = {
		'deleteDDMStructures': deleteDDMStructures
	};

	Liferay.componentReady('ddmStructureManagementToolbar').then(
		function(managementToolbar) {
			managementToolbar.on(
				'actionItemClicked',
				function(event) {
					var itemData = event.data.item.data;

					if (itemData && itemData.action && ACTIONS[itemData.action]) {
						ACTIONS[itemData.action]();
					}
				}
			);
		}
	);
</aui:script>