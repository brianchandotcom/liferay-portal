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
DLAccessFromDesktopDisplayContext dlAccessFromDesktopDisplayContext = new DLAccessFromDesktopDisplayContext(request);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Folder folder = null;

if ((row != null) && (row.getObject() instanceof Folder)) {
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
	cssClass='<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() + "-webdav-action" %>'
	message="access-from-desktop"
	url="javascript:;"
/>

<div id="<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() %>webDav" style="display: none;">
	<div class="portlet-document-library">

		<%
		String webDavHelpMessage = null;

		if (BrowserSnifferUtil.isWindows(request)) {
			webDavHelpMessage = LanguageUtil.format(request, "webdav-windows-help", new Object[] {"https://support.microsoft.com/en-us/kb/892211", "https://help.liferay.com/hc/en-us/articles/360028720352-Desktop-Access-to-Documents-and-Media"}, false);
		}
		else {
			webDavHelpMessage = LanguageUtil.format(request, "webdav-help", "https://help.liferay.com/hc/en-us/articles/360028720352-Desktop-Access-to-Documents-and-Media", false);
		}
		%>

		<liferay-ui:message key="<%= webDavHelpMessage %>" />

		<br /><br />

		<aui:input cssClass="webdav-url-resource" id='<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() + "webDavURL" %>' name="webDavURL" type="resource" value="<%= DLURLHelperUtil.getWebDavURL(themeDisplay, folder, null) %>" />
	</div>
</div>

<aui:script>
	(function () {
		var webdavContentContainer = document.getElementById(
			'<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() %>webDav'
		);

		var html = '';

		if (webdavContentContainer) {
			html = webdavContentContainer.innerHTML;

			webdavContentContainer.remove();
		}

		var webdavActionLink = document.querySelector(
			'.<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() %>-webdav-action'
		);

		if (webdavActionLink) {
			webdavActionLink.addEventListener('click', function (event) {
				event.preventDefault();

				if (webdavContentContainer) {
					Liferay.Util.openModal({
						bodyHTML: html,
						onOpen: function (event) {
							var webdavURLInput = document.getElementById(
								'<portlet:namespace /><%= dlAccessFromDesktopDisplayContext.getRandomNamespace() %>webDavURL'
							);

							if (webdavURLInput) {
								webdavURLInput.focus();
							}
						},
						title:
							'<%= UnicodeLanguageUtil.get(request, "access-from-desktop") %>',
					});
				}
			});
		}
	})();
</aui:script>