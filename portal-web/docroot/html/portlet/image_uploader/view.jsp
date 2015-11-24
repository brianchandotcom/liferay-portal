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

<%@ include file="/html/portlet/init.jsp" %>

<%
String currentImageURL = ParamUtil.getString(request, "currentLogoURL");
long maxFileSize = ParamUtil.getLong(request, "maxFileSize");
String tempImageFileName = ParamUtil.getString(request, "tempImageFileName");
String randomNamespace = ParamUtil.getString(request, "randomNamespace");
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="previewURL">
	<portlet:param name="struts_action" value="/image_uploader/view" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.GET_TEMP %>" />
</liferay-portlet:resourceURL>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "imageUploaded") %>'>

		<%
		FileEntry fileEntry = (FileEntry)SessionMessages.get(renderRequest, "imageUploaded");

		previewURL = HttpUtil.addParameter(previewURL, renderResponse.getNamespace() + "tempImageFileName", tempImageFileName);
		%>

		<aui:script>
			<c:if test="<%= fileEntry != null %>">
				Liferay.Util.getOpener().<%= HtmlUtil.escapeJS(randomNamespace) %>changeLogo('<%= previewURL %>', '<%= fileEntry.getFileEntryId() %>');
			</c:if>

			Liferay.Util.getWindow().hide();
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL var="uploadImageURL">
			<portlet:param name="struts_action" value="/image_uploader/view" />
			<portlet:param name="maxFileSize" value="<%= String.valueOf(maxFileSize) %>" />
		</portlet:actionURL>

		<aui:form action="<%= uploadImageURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="cropRegion" type="hidden" />
			<aui:input name="currentLogoURL" type="hidden" value="<%= currentImageURL %>" />
			<aui:input name="previewURL" type="hidden" value="<%= previewURL %>" />
			<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
			<aui:input name="tempImageFileName" type="hidden" value="<%= tempImageFileName %>" />
			<aui:input name="imageUploaded" type="hidden" value='<%= SessionMessages.contains(renderRequest, "imageUploaded") %>' />

			<liferay-ui:error exception="<%= FileExtensionException.class %>">
				<liferay-ui:message arguments="<%= StringUtil.merge(PropsValues.DL_FILE_EXTENSIONS, StringPool.COMMA) %>" key="please-enter-a-file-with-a-valid-extension-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= FileSizeException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(maxFileSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
				<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(maxFileSize, locale) %>" key="upload-request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
			</liferay-ui:error>

			<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />
			<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

			<aui:fieldset cssClass="lfr-portrait-editor">
				<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" label='<%= LanguageUtil.format(request, "upload-images-no-larger-than-x", TextFormatter.formatStorageSize(maxFileSize, locale), false) %>' name="fileName" size="50" type="file">
					<aui:validator name="acceptFiles">
						'<%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA)) %>'
					</aui:validator>
				</aui:input>

				<div class="lfr-change-logo lfr-portrait-preview" id="<portlet:namespace />portraitPreview">
					<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="image-preview" />" class="lfr-portrait-preview-img" id="<portlet:namespace />portraitPreviewImg" src="<%= HtmlUtil.escape(currentImageURL) %>" />
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
				<portlet:param name="maxFileSize" value="<%= String.valueOf(maxFileSize) %>" />
			</portlet:actionURL>

			var imageUploadedInput = A.one('#<portlet:namespace />imageUploaded');

			var logoEditor = new Liferay.LogoEditor(
				{

					<%
					DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
					%>

					decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',

					maxFileSize: <%= maxFileSize %>,
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