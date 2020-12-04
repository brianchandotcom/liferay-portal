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
DLVideoExternalShortcut dlVideoExternalShortcut = (DLVideoExternalShortcut)request.getAttribute(DLVideoExternalShortcut.class.getName());
String onFilePickCallback = (String)request.getAttribute(DLVideoWebKeys.ON_FILE_PICK_CALLBACK);
%>

<liferay-util:html-top
	outputKey="document_library_video_css"
>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<aui:input name="contentType" type="hidden" value="<%= DLContentTypes.VIDEO_EXTERNAL_SHORTCUT %>" />

<div class="form-group">
	<aui:input disabled="<%= true %>" label="video-url" name="dlVideoExternalShortcutURL" value="<%= (dlVideoExternalShortcut != null) ? dlVideoExternalShortcut.getURL() : null %>" wrapperCssClass="mb-0" />

	<p class="form-text"><liferay-ui:message key="video-url-help" /></p>

	<div class="mt-4 video-preview video-preview-framed video-preview-sm">
		<div class="video-preview-aspect-ratio">
			<c:choose>
				<c:when test="<%= dlVideoExternalShortcut != null %>">
					<%= dlVideoExternalShortcut.getEmbeddableHTML() %>
				</c:when>
				<c:otherwise>
					<div class="video-preview-placeholder">
						<clay:icon
							symbol="video"
						/>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<liferay-portlet:resourceURL id="/document_library_video/get_dl_video_external_shortcut_fields" portletName="<%= DLVideoPortletKeys.DL_VIDEO %>" var="getDLVideoExternalShortcutFieldsURL" />

	<react:component
		module="js/DLVideoExternalShortcutDLFilePicker"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"dlVideoExternalShortcutHTML", (dlVideoExternalShortcut != null) ? dlVideoExternalShortcut.getEmbeddableHTML() : ""
			).put(
				"dlVideoExternalShortcutURL", (dlVideoExternalShortcut != null) ? dlVideoExternalShortcut.getURL() : ""
			).put(
				"getDLVideoExternalShortcutFieldsURL", getDLVideoExternalShortcutFieldsURL
			).put(
				"namespace", PortalUtil.getPortletNamespace(DLVideoPortletKeys.DL_VIDEO)
			).put(
				"onFilePickCallback", onFilePickCallback
			).build()
		%>'
	/>
</div>