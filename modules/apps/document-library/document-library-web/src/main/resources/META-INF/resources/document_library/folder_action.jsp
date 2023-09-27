<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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