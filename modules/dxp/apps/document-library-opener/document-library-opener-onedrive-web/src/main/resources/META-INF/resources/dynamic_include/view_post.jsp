<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/dynamic_include/init.jsp" %>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "DocumentLibraryOpener" %>'
	module="js/DocumentLibraryOpener.es"
/>

<aui:script>
	window.<portlet:namespace />openCreateOfficeDocument = function (
		formSubmitURL,
		dialogTitle
	) {
		Liferay.componentReady('<portlet:namespace />DocumentLibraryOpener').then(
			(openerOnedrive) => {
				openerOnedrive.createWithName({
					dialogTitle: dialogTitle,
					formSubmitURL: formSubmitURL,
				});
			}
		);
	};

	window.<portlet:namespace />editOfficeDocument = function (formSubmitURL) {
		Liferay.componentReady('<portlet:namespace />DocumentLibraryOpener').then(
			(openerOnedrive) => {
				openerOnedrive.edit({
					formSubmitURL: formSubmitURL,
				});
			}
		);
	};

	<%
	String oneDriveBackgroundTaskStatusURL = (String)request.getAttribute("oneDriveBackgroundTaskStatusURL");
	%>

	<c:if test="<%= oneDriveBackgroundTaskStatusURL != null %>">
		Liferay.componentReady('<portlet:namespace />DocumentLibraryOpener').then(
			(openerOnedrive) => {
				openerOnedrive.open({
					dialogMessage:
						'<%= (String)request.getAttribute("dialogMessage") %>',
					statusURL: '<%= oneDriveBackgroundTaskStatusURL %>',
				});
			}
		);
	</c:if>
</aui:script>

<liferay-util:html-top>
	<link href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, StringBundler.concat(themeDisplay.getCDNBaseURL(), PortalUtil.getPathProxy(), application.getContextPath(), "/css/document_library.css"))) %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>