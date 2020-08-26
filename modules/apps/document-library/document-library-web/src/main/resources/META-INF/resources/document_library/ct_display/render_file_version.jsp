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

<%@ include file="/document_library/ct_display/init.jsp" %>

<%
DisplayContext<?> displayContext = (DisplayContext<?>)request.getAttribute(DLWebKeys.CHANGE_TRACKING_DISPLAY_CONTEXT);

DLFileVersion dlFileVersion = (DLFileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);
%>

<div class="table-responsive">
	<table class="table">
		<tr>
			<td><liferay-ui:message key="title" /></td>
			<td><%= HtmlUtil.escape(dlFileVersion.getTitle()) %></td>
		</tr>
		<tr>
			<td><liferay-ui:message key="description" /></td>
			<td><%= HtmlUtil.escape(dlFileVersion.getDescription()) %></td>
		</tr>
		<tr>
			<td><liferay-ui:message key="file-name" /></td>
			<td><%= HtmlUtil.escape(dlFileVersion.getFileName()) %></td>
		</tr>
		<tr>
			<td><liferay-ui:message key="extension" /></td>
			<td><%= HtmlUtil.escape(dlFileVersion.getExtension()) %></td>
		</tr>
		<tr>
			<td><liferay-ui:message key="mime-type" /></td>
			<td><%= HtmlUtil.escape(dlFileVersion.getMimeType()) %></td>
		</tr>
		<tr>
			<td><liferay-ui:message key="version" /></td>
			<td><%= HtmlUtil.escape(dlFileVersion.getVersion()) %></td>
		</tr>
		<tr>
			<td><liferay-ui:message key="size" /></td>
			<td><%= dlFileVersion.getSize() %></td>
		</tr>
		<tr>
			<td>
				<clay:link
					displayType="primary"
					href="<%= displayContext.getDownloadURL(dlFileVersion.getVersion(), dlFileVersion.getSize(), dlFileVersion.getFileName()) %>"
					icon="download"
					label="download"
					small="<%= true %>"
					title='<%= LanguageUtil.format(resourceBundle, "file-size-x", LanguageUtil.formatStorageSize(dlFileVersion.getSize(), locale), false) %>'
					type="button"
				/>
			</td>
		</tr>
	</table>
</div>

<div class="container-fluid">

</div>