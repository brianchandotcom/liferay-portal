<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/view_file_entry_type");
%>

<liferay-util:include page="/html/portlet/document_library/file_entry_type_toolbar.jsp" />

<liferay-ui:error exception="<%= RequiredStructureException.class %>" message="cannot-delete-a-document-type-that-is-presently-used-by-one-or-more-documents" />

<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
	<liferay-ui:search-form
		page="/html/portlet/document_library/file_entry_type_search.jsp"
	/>
</aui:form>

<div class="separator"></div>

<liferay-ui:search-container
	searchContainer="<%= new StructureSearch(renderRequest, portletURL) %>"
>
	<liferay-ui:search-container-results>
		<%@ include file="/html/portlet/document_library/file_entry_type_search_results.jspf" %>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.documentlibrary.model.DLFileEntryType"
		escapedModel="<%= true %>"
		keyProperty="fileEntryTypeId"
		modelVar="fileEntryType"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<%
		Group group = GroupLocalServiceUtil.getGroup(fileEntryType.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="scope"
			value="<%= LanguageUtil.get(pageContext, group.getScopeType(themeDisplay)) %>"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="modified-date"
		>

			<%
			buffer.append(dateFormatDateTime.format(fileEntryType.getModifiedDate()));
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/document_library/file_entry_type_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>