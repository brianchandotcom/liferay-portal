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
long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

PortletURL checkinURL = PortalUtil.getControlPanelPortletURL(request, DLPortletKeys.DOCUMENT_LIBRARY_ADMIN, PortletRequest.ACTION_PHASE);

checkinURL.setParameter("javax.portlet.action", "/document_library/edit_file_entry");
checkinURL.setParameter(Constants.CMD, Constants.CHECKIN);
checkinURL.setParameter("redirect", currentURL);
checkinURL.setParameter("fileEntryId", String.valueOf(fileEntryId));

String taglibOnClick = renderResponse.getNamespace() + "showVersionDetailsDialog('" + checkinURL.toString() + "')";
%>

<liferay-ui:icon
	message="checkin"
	onClick="<%= taglibOnClick %>"
	url="javascript:;"
/>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />showVersionDetailsDialog',
		function(form) {
			Liferay.Portlet.DocumentLibrary.Checkin.showDialog(
				'<portlet:namespace />versionDetails',
				'<%= UnicodeLanguageUtil.get(request, "describe-your-changes") %>',
				function(event) {
					var $ = AUI.$;

					var majorVersionNode = $("input:radio[name='<portlet:namespace />versionDetailsMajorVersion']:checked");

					form.fm('majorVersion').val(majorVersionNode.val());

					var changeLogNode = $('#<portlet:namespace />versionDetailsChangeLog');

					form.fm('changeLog').val(changeLogNode.val());

					submitForm(form);
				}
			);
		},
		['document-library-checkin']
	);
</aui:script>