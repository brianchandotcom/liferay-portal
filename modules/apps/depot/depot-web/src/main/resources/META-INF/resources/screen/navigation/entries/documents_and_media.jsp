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

	<c:if test="<%= depotAdminDLDisplayContext.isShowFileSizePerMimeType() %>">
		<liferay-frontend:fieldset
			collapsible="<%= true %>"
			cssClass="mt-5"
			label='<%= LanguageUtil.get(request, "maximum-file-size-and-mimetypes") %>'
		>
			<div>
				<span aria-hidden="true" class="loading-animation"></span>

				<react:component
					module="js/FileSizePerMimeType"
					props="<%= depotAdminDLDisplayContext.getFileSizePerMimeTypeData() %>"
				/>
			</div>
		</liferay-frontend:fieldset>
	</c:if>
</liferay-frontend:fieldset>