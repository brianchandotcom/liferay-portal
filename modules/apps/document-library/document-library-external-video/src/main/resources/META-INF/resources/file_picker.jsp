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
DLExternalVideo dlExternalVideo = (DLExternalVideo)request.getAttribute(DLExternalVideo.class.getName());
String getDLExternalVideoFieldsURL = (String)request.getAttribute(DLExternalVideoWebKeys.GET_EXTERNAL_VIDEO_FIELDS_URL);
String namespace = (String)request.getAttribute(DLExternalVideoWebKeys.NAMESPACE);
String onFilePickCallback = (String)request.getAttribute(DLExternalVideoWebKeys.ON_FILE_PICK_CALLBACK);
%>

<aui:input helpMessage="video-url-help" label="video-url" name="externalVideoURL" value="<%= (dlExternalVideo != null) ? dlExternalVideo.getURL() : null %>" />

<div id="<portlet:namespace />externalVideoPreview">
	<c:if test="<%= dlExternalVideo != null %>">
		<%= dlExternalVideo.getEmbeddableHTML() %>
	</c:if>
</div>

<aui:script sandbox="<%= true %>">
	var externalVideoURLInput = document.getElementById(
		'<portlet:namespace />externalVideoURL'
	);
	var externalVideoPreview = document.getElementById(
		'<portlet:namespace />externalVideoPreview'
	);

	if (externalVideoURLInput) {
		externalVideoURLInput.addEventListener('input', function () {
			var url = externalVideoURLInput.value;

			if (url) {
				fetch(
					'<%= getDLExternalVideoFieldsURL %>&<%= namespace %>dlExternalVideoURL=' +
						url
				)
					.then(function (res) {
						return res.json();
					})
					.then(function (fields) {
						externalVideoPreview.innerHTML = fields['HTML'];
						<%= onFilePickCallback %>(fields);
					});
			}
		});
	}
</aui:script>