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
DLFileEntryType fileEntryType = (DLFileEntryType)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE);

com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure = (com.liferay.dynamic.data.mapping.model.DDMStructure)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE);
%>

<aui:model-context bean="<%= fileEntryType %>" model="<%= DLFileEntryType.class %>" />

<aui:field-wrapper>
	<c:if test="<%= (ddmStructure != null) && (ddmStructure.getGroupId() != scopeGroupId) %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="this-document-type-does-not-belong-to-this-site.-you-may-affect-other-sites-if-you-edit-this-document-type" />
		</div>
	</c:if>
</aui:field-wrapper>

<aui:input name="name" />

<aui:input name="description" />