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
%>

<liferay-ui:icon
	cssClass='<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() + "-webdav-action" %>'
	message="access-from-desktop"
	url="javascript:;"
/>

<div id="<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() %>webDav" style="display: none;">
	<div class="portlet-document-library">
		<liferay-ui:message key="<%= dlAccessFromDesktopDisplayContext.getWebDAVHelpMessage() %>" />

		<br /><br />

		<aui:input cssClass="webdav-url-resource" id='<%= dlAccessFromDesktopDisplayContext.getRandomNamespace() + "webDavURL" %>' name="webDavURL" type="resource" value="<%= dlAccessFromDesktopDisplayContext.getWebDAVURL() %>" />
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