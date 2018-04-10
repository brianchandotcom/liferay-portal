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
DLViewFileEntryTypesDisplayContext dlViewFileEntryTypesDisplayContext = new DLViewFileEntryTypesDisplayContext(renderRequest, renderResponse, request);
%>

<liferay-util:include page="/document_library/navigation.jsp" servletContext="<%= application %>" />

<clay:management-toolbar
	clearResultsURL="<%= dlViewFileEntryTypesDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= dlViewFileEntryTypesDisplayContext.getCreationMenu() %>"
	disabled="<%= dlViewFileEntryTypesDisplayContext.getTotalItems() == 0 %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= dlViewFileEntryTypesDisplayContext.getSearchActionURL() %>"
	searchFormName="fm"
	selectable="<%= false %>"
	totalItems="<%= dlViewFileEntryTypesDisplayContext.getTotalItems() %>"
/>

<div class="container-fluid-1280 main-content-body">
	<liferay-ui:error exception="<%= RequiredFileEntryTypeException.class %>" message="cannot-delete-a-document-type-that-is-presently-used-by-one-or-more-documents" />

	<liferay-ui:search-container
		searchContainer="<%= dlViewFileEntryTypesDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.document.library.kernel.model.DLFileEntryType"
			escapedModel="<%= true %>"
			keyProperty="fileEntryTypeId"
			modelVar="fileEntryType"
		>

			<%
			PortletURL rowURL = liferayPortletResponse.createRenderURL();

			rowURL.setParameter("mvcRenderCommandName", "/document_library/edit_file_entry_type");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("fileEntryTypeId", String.valueOf(fileEntryType.getFileEntryTypeId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="text-strong"
				href="<%= rowURL %>"
				name="name"
				value="<%= fileEntryType.getName(locale) %>"
			/>

			<%
			Group group = GroupLocalServiceUtil.getGroup(fileEntryType.getGroupId());
			%>

			<liferay-ui:search-container-column-text
				name="scope"
				value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= fileEntryType.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-jsp
				align="right"
				cssClass="entry-action"
				path="/document_library/file_entry_type_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>