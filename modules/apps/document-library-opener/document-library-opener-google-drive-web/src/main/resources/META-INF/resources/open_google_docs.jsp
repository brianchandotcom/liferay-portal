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

<%@ include file="/init.jsp" %>

<%
DLOpenerGoogleDriveFileReference dlOpenerGoogleDriveFileReference = (DLOpenerGoogleDriveFileReference)request.getAttribute(DLOpenerGoogleDriveWebKeys.DL_OPENER_GOOGLE_DRIVE_FILE_REFERENCE);

long cssLastModifiedTime = PortalWebResourcesUtil.getLastModified(PortalWebResourceConstants.RESOURCE_TYPE_CSS);

String googleDocsEditURL = ParamUtil.getString(request, "googleDocsEditURL");
String googleDocsRedirect = ParamUtil.getString(request, "googleDocsRedirect");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta content="initial-scale=1.0, width=device-width" name="viewport" />

		<link href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getCDNDynamicResourcesHost() + PortalWebResourcesUtil.getContextPath(PortalWebResourceConstants.RESOURCE_TYPE_CSS) + "/main.css", cssLastModifiedTime)) %>" id="liferayPortalCSS" rel="stylesheet" type="text/css" />
		<link class="lfr-css-file" href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, themeDisplay.getPathThemeCss() + "/clay.css")) %>" id="liferayAUICSS" rel="stylesheet" type="text/css" />
		<link href="<%= HtmlUtil.escapeAttribute(PortalUtil.getStaticResourceURL(request, StringBundler.concat(themeDisplay.getCDNBaseURL(), PortalUtil.getPathProxy(), application.getContextPath(), "/css/google_docs.css"))) %>" id="liferayGoogleDriveCSS" rel="stylesheet" type="text/css" />
	</head>

	<body>
		<div class="autofit-padded autofit-row autofit-row-center google-docs-toolbar">
			<div class="autofit-col autofit-col-expand">
				<div class="autofit-section">
					<portlet:actionURL name="/document_library/edit_in_google_docs" var="checkInURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECKIN %>" />
						<portlet:param name="redirect" value="<%= googleDocsRedirect %>" />
						<portlet:param name="fileEntryId" value="<%= String.valueOf(dlOpenerGoogleDriveFileReference.getFileEntryId()) %>" />
					</portlet:actionURL>

					<form action="<%= checkInURL %>" method="post">
						<clay:button
							icon="angle-left"
							id="closeAndCheckinBtn"
							label='<%= LanguageUtil.format(resourceBundle, "save-and-return-to-x", themeDisplay.getSiteGroupName()) %>'
							size="sm"
							type="submit"
						/>
					</form>
				</div>
			</div>

			<div class="autofit-col">
				<portlet:actionURL name="/document_library/edit_in_google_docs" var="cancelCheckoutURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CANCEL_CHECKOUT %>" />
					<portlet:param name="redirect" value="<%= googleDocsRedirect %>" />
					<portlet:param name="fileEntryId" value="<%= String.valueOf(dlOpenerGoogleDriveFileReference.getFileEntryId()) %>" />
				</portlet:actionURL>

				<form action="<%= cancelCheckoutURL %>" method="post">
					<clay:button
						id="discardChangesBtn"
						label='<%= LanguageUtil.get(resourceBundle, "discard-changes") %>'
						size="sm"
						type="submit"
					/>
				</form>
			</div>
		</div>

		<iframe class="google-docs-iframe" frameborder="0" id="<portlet:namespace />gDocsIFrame" src="<%= GoogleDriveBackgroundTaskConstants.DOCS_GOOGLE_COM_URL + HtmlUtil.escapeAttribute(googleDocsEditURL) %>"></iframe>
	</body>
</html>