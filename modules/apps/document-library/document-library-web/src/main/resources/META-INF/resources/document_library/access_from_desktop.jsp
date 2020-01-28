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
String randomNamespace = null;

if (portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY) || portletName.equals(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN)) {
	randomNamespace = PortalUtil.generateRandomKey(request, "portlet_document_library_folder_action") + StringPool.UNDERLINE;
}
else {
	randomNamespace = PortalUtil.generateRandomKey(request, "portlet_image_gallery_display_folder_action") + StringPool.UNDERLINE;
}

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = null;

if ((row != null) && (row instanceof Folder)) {
	folder = (Folder)row.getObject();
}
else {
	folder = (Folder)request.getAttribute("info_panel.jsp-folder");

	if (folder == null) {
		folder = ActionUtil.getFolder(liferayPortletRequest);
	}
}
%>

<liferay-ui:icon
	cssClass='<%= randomNamespace + "-webdav-action" %>'
	message="access-from-desktop"
	url="javascript:;"
/>

<div id="<%= randomNamespace %>webDav" style="display: none;">
	<div class="portlet-document-library">

		<%
		String webDavHelpMessage = null;

		if (BrowserSnifferUtil.isWindows(request)) {
			webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"https://support.microsoft.com/en-us/kb/892211", "https://dev.liferay.com/discover/portal/-/knowledge_base/7-0/publishing-files#desktop-access-to-documents-and-media"}, false);
		}
		else {
			webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "https://dev.liferay.com/discover/portal/-/knowledge_base/7-0/publishing-files#desktop-access-to-documents-and-media", false);
		}
		%>

		<liferay-ui:message key="<%= webDavHelpMessage %>" />

		<br /><br />

		<aui:input cssClass="webdav-url-resource" name="webDavURL" type="resource" value="<%= DLURLHelperUtil.getWebDavURL(themeDisplay, folder, null) %>" />
	</div>
</div>

<aui:script use="liferay-util-window">
	var webdavAction = A.one('.<%= randomNamespace %>-webdav-action');

	if (webdavAction) {
		webdavAction.on('click', function(event) {
			event.preventDefault();

			var webdavDialog = Liferay.Util.Window.getWindow({
				dialog: {
					bodyContent: A.one('#<%= randomNamespace %>webDav').html(),
					destroyOnHide: true
				},
				title:
					'<%= UnicodeLanguageUtil.get(request, "access-from-desktop") %>'
			});

			webdavDialog.after('render', function(event) {
				var webdavURLInput = webdavDialog
					.get('boundingBox')
					.one('#<portlet:namespace />webDavURL');

				webdavURLInput.focus();
			});

			webdavDialog.render();
		});
	}
</aui:script>