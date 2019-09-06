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

<liferay-frontend:component
	componentId='<%= renderResponse.getNamespace() + "DocumentLibraryOpener" %>'
	module="js/DocumentLibraryOpener.es"
/>

<aui:script>
	window.<portlet:namespace />openCreateOfficeDocument = function(formSubmitURL, dialogTitle) {
		Liferay.componentReady('<portlet:namespace />DocumentLibraryOpener').then(
			function(openerOnedrive) {
				openerOnedrive.createWithName({
					dialogTitle: dialogTitle,
					formSubmitURL: formSubmitURL
				});
			}
		);
	}

	window.<portlet:namespace />editOfficeDocument = function(formSubmitURL, dialogTitle) {
		Liferay.componentReady('<portlet:namespace />DocumentLibraryOpener').then(
			function(openerOnedrive) {
				openerOnedrive.edit({
					formSubmitURL: formSubmitURL
				});
			}
		);
	}

	<%
	String oneDriveBackgroundTaskStatusURL = (String)request.getAttribute("oneDriveBackgroundTaskStatusURL");
	String dialogMessage = (String)request.getAttribute("dialogMessage");
	%>

	<c:if test="<%= oneDriveBackgroundTaskStatusURL != null %>">
		Liferay.componentReady('<portlet:namespace />DocumentLibraryOpener').then(
			function(openerOnedrive) {
				openerOnedrive.open({
					dialogMessage: '<%= dialogMessage %>',
					pollingURL: '<%= oneDriveBackgroundTaskStatusURL %>'
				});
			}
		);
	</c:if>
</aui:script>

<liferay-util:html-top>
	<link href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, StringBundler.concat(themeDisplay.getCDNBaseURL(), PortalUtil.getPathProxy(), application.getContextPath(), "/css/document_library.css"))) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>