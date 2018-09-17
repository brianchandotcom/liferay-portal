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

<%@ include file="/dynamic_include/init.jsp" %>

<%
DLOpenerGoogleDriveFileReference dlOpenerGoogleDriveFileReference = (DLOpenerGoogleDriveFileReference)request.getAttribute(DLOpenerGoogleDriveWebKeys.DL_OPENER_GOOGLE_DRIVE_FILE_REFERENCE);
%>

<c:if test="<%= dlOpenerGoogleDriveFileReference != null %>">
	<portlet:resourceURL id="/document_library/google_drive_background_task_status" var="googleDriveBackgroundTaskStatusURL">
		<portlet:param name="backgroundTaskId" value="<%= String.valueOf(dlOpenerGoogleDriveFileReference.getBackgroundTaskId()) %>" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(dlOpenerGoogleDriveFileReference.getFileEntryId()) %>" />
	</portlet:resourceURL>

	<portlet:renderURL var="openGoogleDocsURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="/document_library/open_google_docs" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(dlOpenerGoogleDriveFileReference.getFileEntryId()) %>" />
	</portlet:renderURL>

	<aui:script>
		window.open('<%= openGoogleDocsURL %>');
	</aui:script>
</c:if>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, StringBundler.concat(themeDisplay.getCDNBaseURL(), PortalUtil.getPathProxy(), application.getContextPath(), "/css/document_library.css")) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<script>
	window.<portlet:namespace />redirectNotification = function(url) {
		var TIME_SHOW_MSG = 2000;

		Liferay.Util.openWindow(
			{
				dialog:
				{
					bodyContent: '<p><liferay-ui:message key="you-are-being-redirected-to-an-external-editor-to-edit-this-document" /></p><div aria-hidden="true" class="loading-animation"></div>',
					cssClass: 'google-docs-redirect-modal',
					height: 172,
					modal: true,
					resizable: false,
					title: '',
					width: 320
				}
			}
		);

		setTimeout(
			function() {
				var form = document.createElement('form');

				form.method = 'POST';
				form.action = url;

				document.body.appendChild(form);

				form.submit();
			},
			TIME_SHOW_MSG);
	}
</script>