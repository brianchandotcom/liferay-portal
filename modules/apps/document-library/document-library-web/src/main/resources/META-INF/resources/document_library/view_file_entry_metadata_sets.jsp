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

<%@ include file="/document_library/init.jsp" %>

<%
DLViewFileEntryMetadataSetsDisplayContext dLViewFileEntryMetadataSetsDisplayContext = (DLViewFileEntryMetadataSetsDisplayContext)request.getAttribute(DLWebKeys.DOCUMENT_LIBRARY_VIEW_FILE_ENTRY_METADATA_SETS_DISPLAY_CONTEXT);
%>

<liferay-util:include page="/document_library/navigation.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	displayContext="<%= new DLViewFileEntryMetadataSetsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, dLViewFileEntryMetadataSetsDisplayContext) %>"
/>

<div class="container-fluid container-fluid-max-xl main-content-body">
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByStructureLinks.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-structure-links" />
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureReferencedByTemplates.class %>" message="the-structure-cannot-be-deleted-because-it-is-required-by-one-or-more-templates" />
	<liferay-ui:error exception="<%= RequiredStructureException.MustNotDeleteStructureThatHasChild.class %>" message="the-structure-cannot-be-deleted-because-it-has-one-or-more-substructures" />

	<liferay-ui:search-container
		id="ddmStructures"
		rowChecker="<%= new DDMStructureRowChecker(renderResponse) %>"
		searchContainer="<%= dLViewFileEntryMetadataSetsDisplayContext.getStructureSearch() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.dynamic.data.mapping.model.DDMStructure"
			keyProperty="structureId"
			modelVar="ddmStructure"
		>

			<%
			String rowHREF = StringPool.BLANK;

			if (DDMStructurePermission.contains(permissionChecker, ddmStructure, ActionKeys.UPDATE)) {
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "/document_library/ddm/edit_ddm_structure");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("ddmStructureId", String.valueOf(ddmStructure.getStructureId()));

				rowHREF = rowURL.toString();
			}
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowHREF %>"
				name="id"
				orderable="<%= true %>"
				orderableProperty="id"
				property="structureId"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200 table-title"
				href="<%= rowHREF %>"
				name="name"
				value="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand table-cell-minw-200"
				href="<%= rowHREF %>"
				name="description"
				value="<%= HtmlUtil.escape(ddmStructure.getDescription(locale)) %>"
			/>

			<%
			Group group = GroupLocalServiceUtil.getGroup(ddmStructure.getGroupId());
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand-smallest table-cell-minw-150"
				name="scope"
				value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
			/>

			<liferay-ui:search-container-column-date
				cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
				href="<%= rowHREF %>"
				name="modified-date"
				orderable="<%= true %>"
				orderableProperty="modified-date"
				value="<%= ddmStructure.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/document_library/ddm/ddm_structure_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>