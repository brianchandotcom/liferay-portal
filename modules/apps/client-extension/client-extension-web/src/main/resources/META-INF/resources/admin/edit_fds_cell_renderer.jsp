<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryDisplayContext<FDSCellRendererCET> editClientExtensionEntryDisplayContext = (EditClientExtensionEntryDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_DISPLAY_CONTEXT);

FDSCellRendererCET fdsCellRendererCET = editClientExtensionEntryDisplayContext.getCET();
%>

<aui:field-wrapper cssClass="form-group">
	<aui:input label="js-url" name="url" required="<%= true %>" type="text" value="<%= fdsCellRendererCET.getURL() %>" />

	<div class="form-text">
		<liferay-ui:message key="enter-the-url-of-the-javascript-file-to-customize-a-frontend-data-set-cell-renderer" />
	</div>
</aui:field-wrapper>