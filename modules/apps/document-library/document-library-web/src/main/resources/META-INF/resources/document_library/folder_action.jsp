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
FolderActionDisplayContext folderActionDisplayContext = new FolderActionDisplayContext(dlTrashHelper, request);
%>

<c:if test="<%= folderActionDisplayContext.isShowActions() %>">
	<clay:dropdown-actions
		aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
		dropdownItems="<%= folderActionDisplayContext.getActionDropdownItems() %>"
		propsTransformer="document_library/js/DLFolderDropdownPropsTransformer"
	/>

	<aui:script use="uploader">
		if (!A.UA.ios && A.Uploader.TYPE != 'none') {
			var uploadMultipleDocumentsIcon = A.all(
				'.upload-multiple-documents:hidden'
			);

			uploadMultipleDocumentsIcon.show();
		}
	</aui:script>
</c:if>