<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<%
DLOpenerOneDriveFileReference dlOpenerOneDriveFileReference = (DLOpenerOneDriveFileReference)request.getAttribute(DLOpenerOneDriveWebKeys.DL_OPENER_ONE_DRIVE_FILE_REFERENCE);
%>

<c:if test="<%= dlOpenerOneDriveFileReference != null %>">
	<portlet:resourceURL id="/document_library/onedrive_background_task_status" var="oneDriveBackgroundTaskStatusURL">
		<portlet:param name="backgroundTaskId" value="<%= String.valueOf(dlOpenerOneDriveFileReference.getBackgroundTaskId()) %>" />
		<portlet:param name="fileEntryId" value="<%= String.valueOf(dlOpenerOneDriveFileReference.getFileEntryId()) %>" />
	</portlet:resourceURL>

	<aui:script>
		(function() {

			var TIME_POLLING = 500;
			var TIME_SHOW_MSG = 2000;

			var defaultError = '<liferay-ui:message key="an-unexpected-error-occurred" />';
			var dialogId = '<portlet:namespace />OneDriveLoadingDialog';

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
				fetch(
					'<%= oneDriveBackgroundTaskStatusURL %>',
					{
						credentials: 'include',
						method: 'POST'
					}
				)
				.then(
					function(response) {

						if (!response.ok) {
							throw defaultError;
						}

						return response.json();
					}
				)
				.then(
					function(response) {
						if (response.complete) {

							url = response.office365EditURL;

							navigate();
						}
						else if (response.error) {
							throw defaultError;
						}
						else {
							setTimeout(polling, TIME_POLLING);
						}
					}
				)
				.catch(
					function(error) {
						showError(error);

						Liferay.Util.getWindow(dialogId).hide();
					}
				);
			}

			function showError(message) {
				showStatusMessage(
					{
						message: message,
						title: '<liferay-ui:message key="error" />:',
						type: 'danger'
					}
				);
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
						bodyContent: '<p><liferay-ui:message key="<%= messageKey %>" /></p><div aria-hidden="true" class="loading-animation"></div>',
						cssClass: 'office-365-redirect-modal',
						height: 172,
						modal: true,
						resizable: false,
						title: '',
						width: 320
					}
				},
				function() {
					setTimeout(polling, TIME_POLLING);

					setTimeout(
						function() {
							isTimeConsumed = true;

							navigate();
						},
						TIME_SHOW_MSG
					);
				}
			);

		})();
	</aui:script>
</c:if>

<liferay-util:html-top>
	<link href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, StringBundler.concat(themeDisplay.getCDNBaseURL(), PortalUtil.getPathProxy(), application.getContextPath(), "/css/document_library.css"))) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>