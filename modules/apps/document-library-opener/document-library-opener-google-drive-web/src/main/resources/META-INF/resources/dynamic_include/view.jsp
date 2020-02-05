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
		<portlet:param name="googleDocsRedirect" value='<%= ParamUtil.getString(request, "googleDocsRedirect") %>' />
	</portlet:resourceURL>

	<aui:script>
		(function() {
			var TIME_POLLING = 500;
			var TIME_SHOW_MSG = 2000;

			var defaultError =
				'<liferay-ui:message key="an-unexpected-error-occurred" />';
			var dialogId = '<portlet:namespace />LoadingDialog';

			var isTimeConsumed = false;

			var url;

			showStatusMessage = Liferay.lazyLoad(
				'frontend-js-web/liferay/toast/commands/OpenToast.es',
				function(toastCommands, data) {
					toastCommands.openToast(data);
				}
			);

			function navigate() {
				if (url && isTimeConsumed) {
					window.location.href = url;
				}
			}

			function polling() {
				Liferay.Util.fetch('<%= googleDriveBackgroundTaskStatusURL %>', {
					method: 'POST'
				})
					.then(function(response) {
						if (!response.ok) {
							throw defaultError;
						}

						return response.json();
					})
					.then(function(response) {
						if (response.complete) {
							url = response.googleDocsEditURL;

							navigate();
						}
						else if (response.error) {
							throw defaultError;
						}
						else {
							setTimeout(polling, TIME_POLLING);
						}
					})
					.catch(function(error) {
						showError(error);

						Liferay.Util.getWindow(dialogId).hide();
					});
			}

			function showError(message) {
				showStatusMessage({
					message: message,
					title: '<liferay-ui:message key="error" />:',
					type: 'danger'
				});
			}

			<%
			String messageKey = "you-are-being-redirected-to-an-external-editor-to-edit-this-document";

			String cmd = ParamUtil.getString(request, Constants.CMD);

			if (cmd.equals(Constants.ADD)) {
				messageKey = "you-are-being-redirected-to-an-external-editor-to-create-this-document";
			}
			%>

			Liferay.Util.openWindow(
				{
					id: dialogId,
					dialog: {
						bodyContent:
							'<p><liferay-ui:message key="<%= messageKey %>" /></p><div aria-hidden="true" class="loading-animation"></div>',
						cssClass: 'google-docs-redirect-modal',
						height: 172,
						modal: true,
						resizable: false,
						title: '',
						width: 320
					}
				},
				function() {
					setTimeout(polling, TIME_POLLING);

					setTimeout(function() {
						isTimeConsumed = true;

						navigate();
					}, TIME_SHOW_MSG);
				}
			);
		})();
	</aui:script>
</c:if>

<liferay-util:html-top>
	<link href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, StringBundler.concat(themeDisplay.getCDNBaseURL(), PortalUtil.getPathProxy(), application.getContextPath(), "/css/document_library.css"))) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>