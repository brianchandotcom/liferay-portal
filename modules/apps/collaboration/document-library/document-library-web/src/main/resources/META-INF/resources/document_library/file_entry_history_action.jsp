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
String redirect = ParamUtil.getString(request, "redirect");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileVersion fileVersion = null;

if (row != null) {
	fileVersion = (FileVersion)row.getObject();
}
else {
	fileVersion = (FileVersion)request.getAttribute("info_panel.jsp-fileVersion");
}

FileEntry fileEntry = fileVersion.getFileEntry();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<liferay-ui:icon
		message="download"
		method="get"
		url="<%= DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, StringPool.BLANK) %>"
	/>

	<portlet:renderURL var="viewFileVersionURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view_file_entry" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
		<portlet:param name="version" value="<%= fileVersion.getVersion() %>" />
	</portlet:renderURL>

	<liferay-ui:icon
		message="view[action]"
		url="<%= viewFileVersionURL %>"
	/>

	<portlet:renderURL var="viewFileEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view_file_entry" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
	</portlet:renderURL>

	<c:if test="<%= (fileVersion.getStatus() != WorkflowConstants.STATUS_IN_TRASH) && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.UPDATE) && (fileVersion.getStatus() == WorkflowConstants.STATUS_APPROVED) && !fileEntry.getLatestFileVersion().getVersion().equals(fileVersion.getVersion()) %>">
		<portlet:actionURL name="/document_library/edit_file_entry" var="revertURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REVERT %>" />
			<portlet:param name="redirect" value="<%= viewFileEntryURL %>" />
			<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
			<portlet:param name="version" value="<%= String.valueOf(fileVersion.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="revert"
			url="<%= revertURL %>"
		/>
	</c:if>

	<c:if test="<%= (fileVersion.getStatus() != WorkflowConstants.STATUS_IN_TRASH) && DLFileEntryPermission.contains(permissionChecker, fileEntry, ActionKeys.DELETE) && (fileVersion.getStatus() == WorkflowConstants.STATUS_APPROVED) && (fileEntry.getModel() instanceof DLFileEntry) && (((DLFileEntry)fileEntry.getModel()).getFileVersionsCount(WorkflowConstants.STATUS_APPROVED) > 1) %>">
		<portlet:actionURL name="/document_library/edit_file_entry" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= viewFileEntryURL %>" />
			<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
			<portlet:param name="version" value="<%= String.valueOf(fileVersion.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			message="delete-version"
			url="<%= deleteURL %>"
		/>
	</c:if>

	<%
	boolean comparableFileEntry = DocumentConversionUtil.isComparableVersion(fileVersion.getExtension());
	%>

	<c:if test="<%= comparableFileEntry %>">
		<portlet:renderURL var="selectFileVersionURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="/document_library/select_file_version" />
			<portlet:param name="redirect" value="<%= viewFileEntryURL %>" />
			<portlet:param name="fileEntryId" value="<%= String.valueOf(fileEntry.getFileEntryId()) %>" />
			<portlet:param name="version" value="<%= String.valueOf(fileVersion.getVersion()) %>" />
		</portlet:renderURL>

		<%
		Map<String, Object> data = new HashMap<String, Object>();

		data.put("uri", selectFileVersionURL);
		%>

		<liferay-ui:icon
			cssClass="compare-to-link"
			data="<%= data %>"
			label="<%= true %>"
			message="compare-to"
			url="javascript:;"
		/>

		<aui:script sandbox="<%= true %>">
			$('body').on(
				'click',
				'.compare-to-link a',
				function(event) {
					var currentTarget = $(event.currentTarget);

					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								destroyOnHide: true,
								modal: true
							},
							eventName: '<portlet:namespace />selectFileVersionFm',
							id: '<portlet:namespace />compareFileVersions' + currentTarget.attr('id'),
							title: '<liferay-ui:message key="compare-versions" />',
							uri: currentTarget.data('uri')
						},
						function(event) {
							<portlet:renderURL var="compareVersionURL">
								<portlet:param name="mvcRenderCommandName" value="/document_library/compare_versions" />
								<portlet:param name="backURL" value="<%= currentURL %>" />
							</portlet:renderURL>

							var uri = '<%= compareVersionURL %>';

							uri = Liferay.Util.addParams('<portlet:namespace />sourceFileVersionId=' + event.sourceversion, uri);
							uri = Liferay.Util.addParams('<portlet:namespace />targetFileVersionId=' + event.targetversion, uri);

							location.href = uri;
						}
					);
				}
			);
		</aui:script>
	</c:if>
</liferay-ui:icon-menu>