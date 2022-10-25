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

JournalDDMStructuresManagementToolbarDisplayContext journalDDMStructuresManagementToolbarDisplayContext = new JournalDDMStructuresManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, journalDDMStructuresDisplayContext);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%= journalDisplayContext.getNavigationItems("structures") %>'
/>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= journalDDMStructuresManagementToolbarDisplayContext %>"
	propsTransformer="js/DDMStructuresManagementToolbarPropsTransformer"
/>

<portlet:actionURL copyCurrentRenderParameters="<%= true %>" name="/journal/delete_data_definition" var="deleteDataDefinitionURL">
	<portlet:param name="mvcPath" value="/view_ddm_structures.jsp" />
</portlet:actionURL>

<div>
	<react:component
		module="js/modals/ImportAndOverrideDataDefinitionModal"
	/>
</div>

<aui:form action="<%= deleteDataDefinitionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

	<liferay-ui:success key="importDataDefinitionSuccessMessage" message="the-structure-was-successfully-imported" />

	<liferay-ui:error embed="<%= false %>" key="importDataDefinitionErrorMessage" message="the-structure-was-not-successfully-imported" />
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByStructureLinks.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-structure-links" />
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-templates" />
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureThatHasChild.class %>" message="the-structure-cannot-be-deleted-because-it-has-one-or-more-substructures" />

	<c:if test="<%= !journalDisplayContext.isNavigationMine() && !journalDisplayContext.isNavigationRecent() %>">
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= new ArrayList<>() %>"
		/>
	</c:if>

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
				rowHREF = PortletURLBuilder.createRenderURL(
					renderResponse
				).setMVCPath(
					"/edit_data_definition.jsp"
				).setRedirect(
					currentURL
				).setParameter(
					"ddmStructureId", ddmStructure.getStructureId()
				).buildString();
			}

			row.setData(
				HashMapBuilder.<String, Object>put(
					"actions", journalDDMStructuresManagementToolbarDisplayContext.getAvailableActions(ddmStructure)
				).build());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				href="<%= rowHREF %>"
				name="name"
				value="<%= HtmlUtil.escape(ddmStructure.getName(locale, true)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				name="description"
				truncate="<%= true %>"
				value="<%= HtmlUtil.escape(ddmStructure.getDescription(locale, true)) %>"
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

			<liferay-ui:search-container-column-text>

				<%
				DDMStructureActionDropdownItemsProvider ddmStructureActionDropdownItemsProvider = new DDMStructureActionDropdownItemsProvider(ddmStructure, liferayPortletRequest, liferayPortletResponse);
				%>

				<clay:dropdown-actions
					aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
					dropdownItems="<%= ddmStructureActionDropdownItemsProvider.getActionDropdownItems() %>"
					propsTransformer="js/DDMStructrureElementsDefaultPropsTransformer"
				/>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="list"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>