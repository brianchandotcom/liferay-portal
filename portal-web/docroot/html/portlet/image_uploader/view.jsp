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

<%@ include file="/html/portlet/init.jsp" %>

<%
String currentImageURL = ParamUtil.getString(request, "currentLogoURL");
long maxFileSize = ParamUtil.getLong(request, "maxFileSize");
String tempImageFileName = ParamUtil.getString(request, "tempImageFileName");
String randomNamespace = ParamUtil.getString(request, "randomNamespace");
%>

<portlet:resourceURL var="previewURL">
	<portlet:param name="struts_action" value="/image_uploader/view" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.GET_TEMP %>" />
	<portlet:param name="tempImageFileName" value="<%= tempImageFileName %>" />
</portlet:resourceURL>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "imageUploaded") %>'>

		<%
		FileEntry fileEntry = (FileEntry)SessionMessages.get(renderRequest, "imageUploaded");
		%>

		<aui:script>
		    <c:if test="<%= fileEntry != null%>">
				Liferay.Util.getOpener().<%= HtmlUtil.escapeJS(randomNamespace) %>changeLogo('<%= previewURL %>', '<%= fileEntry.getFileEntryId() %>');
		    </c:if>

			Liferay.Util.getWindow().hide();
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="uploadImageURL">
			<portlet:param name="struts_action" value="/image_uploader/view" />
		</portlet:actionURL>

		<aui:form action="<%= uploadImageURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="cropRegion" type="hidden" />
			<aui:input name="currentLogoURL" type="hidden" value="<%= currentImageURL %>" />
			<aui:input name="maxFileSize" type="hidden" value="<%= String.valueOf(maxFileSize) %>" />
			<aui:input name="previewURL" type="hidden" value="<%= previewURL %>" />
			<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
			<aui:input name="tempImageFileName" type="hidden" value="<%= tempImageFileName %>" />
			<aui:input name="imageUploaded" type="hidden" value='<%= SessionMessages.contains(renderRequest, "imageUploaded") %>' />

			<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />
			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

			<liferay-ui:error exception="<%= FileSizeException.class %>">
				<liferay-ui:message arguments="<%= maxFileSize %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<aui:fieldset cssClass="lfr-portrait-editor">
				<aui:input autoFocus="<= windowState.equals(WindowState.MAXIMIZED) %>" label='<%= LanguageUtil.format(pageContext, "upload-images-no-larger-than-x-k", maxFileSize, false) %>' name="fileName" size="50" type="file" />

				<div class="lfr-change-logo lfr-portrait-preview" id="<portlet:namespace />portraitPreview">
					<img alt="image-preview" class="lfr-portrait-preview-img" id="<portlet:namespace />portraitPreviewImg" src="<%= HtmlUtil.escape(currentImageURL) %>" />
				</div>

				<aui:button-row>
					<aui:button name="submitButton" type="submit" />

					<aui:button onClick="window.close();" type="cancel" value="close" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>

		<aui:script use="liferay-logo-editor">
			<portlet:actionURL var="addTempImageURL">
				<portlet:param name="struts_action" value="/image_uploader/view" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" />
			</portlet:actionURL>

			var imageUploadedInput = A.one('#<portlet:namespace />imageUploaded');

			var logoEditor = new Liferay.LogoEditor(
				{
					maxFileSize: '<%= maxFileSize %>',
					namespace: '<portlet:namespace />',
					on: {
						uploadComplete: A.bind('val', imageUploadedInput, true)
					},
					previewURL: '<%= previewURL %>',
					uploadURL: '<%= addTempImageURL %>'
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