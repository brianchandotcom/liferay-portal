<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotAdminDLDisplayContext depotAdminDLDisplayContext = (DepotAdminDLDisplayContext)request.getAttribute(DepotAdminDLDisplayContext.class.getName());
%>

<liferay-frontend:fieldset
	collapsible="<%= true %>"
	cssClass="panel-group-flush"
	label='<%= LanguageUtil.get(request, "documents-and-media") %>'
>
	<aui:input helpMessage='<%= LanguageUtil.format(request, "can-user-with-view-permission-browse-the-asset-library-document-library-files-and-folders", new Object[] {depotAdminDLDisplayContext.getGroupName(), depotAdminDLDisplayContext.getGroupDLFriendlyURL()}, false) %>' inlineLabel="right" label="enable-directory-indexing" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--directoryIndexingEnabled--" type="toggle-switch" value="<%= depotAdminDLDisplayContext.isDirectoryIndexingEnabled() %>" />

	<aui:input helpMessage='<%= LanguageUtil.format(request, "file-max-size-help", new Object[] {"overall-maximum-upload-request-size", "upload-servlet-request-configuration-name"}, true) %>' label="file-max-size" name="fileMaxSize" type="number" value="<%= depotAdminDLDisplayContext.getFileMaxSize() %>" />

	<liferay-frontend:fieldset
		collapsible="<%= true %>"
		cssClass="mt-5"
		label='<%= LanguageUtil.get(request, "mime-type-limit") %>'
	>
		<div>
			<span aria-hidden="true" class="loading-animation"></span>

			<react:component
				module="{FileSizeMimetypes} from document-library-web"
				props="<%= depotAdminDLDisplayContext.getFileSizePerMimeTypeData() %>"
			/>
		</div>
	</liferay-frontend:fieldset>
</liferay-frontend:fieldset>