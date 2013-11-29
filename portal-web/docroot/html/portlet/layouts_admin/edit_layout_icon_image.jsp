<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
long selPlid = ParamUtil.getLong(request, "plid");

Layout selLayout = LayoutLocalServiceUtil.getLayout(selPlid);

String iconImageURL = null;

if (selLayout.getIconImageId() == 0) {
	iconImageURL = themeDisplay.getPathThemeImages() + "/spacer.png";
}
else {
	iconImageURL = themeDisplay.getPathImage() + "/logo?img_id=" + selLayout.getIconImageId() + "&t=" + WebServerServletTokenUtil.getToken(selLayout.getIconImageId());
}
%>

<portlet:resourceURL var="previewURL">
	<portlet:param name="struts_action" value="/layouts_admin/edit_layout_icon_image" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.GET_TEMP %>" />
	<portlet:param name="plid" value="<%= String.valueOf(selLayout.getPlid()) %>" />
</portlet:resourceURL>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "imageUploaded") %>'>

		<%
		FileEntry fileEntry = (FileEntry)SessionMessages.get(renderRequest, "imageUploaded");
		%>

		<aui:script>
			Liferay.Util.getOpener().<portlet:namespace />changeLogo('<%= previewURL %>', '<%= fileEntry.getFileEntryId() %>');

			Liferay.Util.getWindow().hide();
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="editLayoutIconImageURL">
			<portlet:param name="struts_action" value="/layouts_admin/edit_layout_icon_image" />
		</portlet:actionURL>

		<aui:form action="<%= editLayoutIconImageURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="plid" type="hidden" value="<%= selLayout.getPlid() %>" />
			<aui:input name="cropRegion" type="hidden" />

			<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />
			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

			<aui:fieldset cssClass="lfr-portrait-editor">
				<aui:input autoFocus="<= windowState.equals(WindowState.MAXIMIZED) %>" name="fileName" size="50" type="file" />

				<div class="lfr-change-logo lfr-portrait-preview" id="<portlet:namespace />portraitPreview">
					<img class="lfr-portrait-preview-img" id="<portlet:namespace />portraitPreviewImg" src="<%= iconImageURL %>" />
				</div>

				<aui:button-row>
					<aui:button name="submitButton" type="submit" />

					<aui:button onClick="window.close();" type="cancel" value="close" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

		<aui:script use="liferay-logo-editor">
			var logoEditor = new Liferay.LogoEditor(
				{
					maxFileSize: '<%= PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE) / 1024 %>',
					namespace: '<portlet:namespace />',
					previewURL: '<%= previewURL %>',
					uploadURL: '<portlet:actionURL><portlet:param name="struts_action" value="/layouts_admin/edit_layout_icon_image" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="plid" value="<%= String.valueOf(selLayout.getPlid()) %>" /></portlet:actionURL>'
				}
			);

			if (Liferay.Util.getTop() !== A.config.win) {
				var dialog = Liferay.Util.getWindow();

				if (dialog) {
					dialog.on(['resize:end', 'resize:resize', 'resize:start'], logoEditor.resize, logoEditor);
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>