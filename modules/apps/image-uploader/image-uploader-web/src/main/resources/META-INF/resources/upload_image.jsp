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
int aspectRatio = ParamUtil.getInteger(request, "aspectRatio");
String currentImageURL = ParamUtil.getString(request, "currentLogoURL");
long maxFileSize = ParamUtil.getLong(request, "maxFileSize");
boolean preserveRatio = ParamUtil.getBoolean(request, "preserveRatio");
String randomNamespace = ParamUtil.getString(request, "randomNamespace");
String tempImageFileName = ParamUtil.getString(request, "tempImageFileName");
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/image_uploader/upload_image" var="previewURL">
	<portlet:param name="mvcRenderCommandName" value="/image_uploader/upload_image" />
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.GET_TEMP %>" />
</liferay-portlet:resourceURL>

<c:choose>
	<c:when test='<%= SessionMessages.contains(renderRequest, "imageUploaded") %>'>

		<%
		FileEntry fileEntry = (FileEntry)SessionMessages.get(renderRequest, "imageUploaded");

		previewURL = HttpUtil.addParameter(previewURL, liferayPortletResponse.getNamespace() + "tempImageFileName", tempImageFileName);
		%>

		<aui:script>
			<c:if test="<%= fileEntry != null %>">
				Liferay.Util.getOpener().<%= HtmlUtil.escapeJS(randomNamespace) %>changeLogo(
					'<%= previewURL %>',
					'<%= fileEntry.getFileEntryId() %>'
				);
			</c:if>

			Liferay.Util.getWindow().hide();
		</aui:script>
	</c:when>
	<c:otherwise>
		<portlet:actionURL name="/image_uploader/upload_image" var="uploadImageURL">
			<portlet:param name="mvcRenderCommandName" value="/image_uploader/upload_image" />
			<portlet:param name="maxFileSize" value="<%= String.valueOf(maxFileSize) %>" />
		</portlet:actionURL>

		<aui:form action="<%= uploadImageURL %>" enctype="multipart/form-data" method="post" name="fm">
			<aui:input name="cropRegion" type="hidden" />
			<aui:input name="currentLogoURL" type="hidden" value="<%= currentImageURL %>" />
			<aui:input name="preserveRatio" type="hidden" value="<%= String.valueOf(preserveRatio) %>" />
			<aui:input name="previewURL" type="hidden" value="<%= previewURL %>" />
			<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
			<aui:input name="tempImageFileName" type="hidden" value="<%= tempImageFileName %>" />
			<aui:input name="imageUploaded" type="hidden" value='<%= SessionMessages.contains(renderRequest, "imageUploaded") %>' />

			<div class="dialog-body">
				<clay:container-fluid>

					<%
					DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
					%>

					<liferay-ui:error exception="<%= FileExtensionException.class %>">
						<liferay-ui:message arguments="<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>" key="please-enter-a-file-with-a-valid-extension-x" translateArguments="<%= false %>" />
					</liferay-ui:error>

					<liferay-ui:error exception="<%= FileSizeException.class %>">
						<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(maxFileSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
					</liferay-ui:error>

					<liferay-ui:error exception="<%= NoSuchFileException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />
					<liferay-ui:error exception="<%= UploadException.class %>" message="an-unexpected-error-occurred-while-uploading-your-file" />

					<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
						<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(maxFileSize, locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
					</liferay-ui:error>

					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset cssClass="lfr-portrait-editor">
							<h4 class="text-default">
								<liferay-ui:message arguments="<%= LanguageUtil.formatStorageSize(maxFileSize, locale) %>" key="upload-images-no-larger-than-x" />
							</h4>

							<div class="lfr-change-logo lfr-portrait-preview" id="<portlet:namespace />portraitPreview">
								<img alt="<liferay-ui:message escapeAttribute="<%= true %>" key="image-preview" />" class="img-fluid lfr-portrait-preview-img" id="<portlet:namespace />portraitPreviewImg" src="<%= HtmlUtil.escape(currentImageURL) %>" />
							</div>

							<c:if test='<%= Validator.isNull(currentImageURL) || currentImageURL.contains("/spacer.png") %>'>
								<p class="text-muted" id="<portlet:namespace />emptyResultMessage">
									<%= StringUtil.toLowerCase(LanguageUtil.get(request, "none")) %>
								</p>
							</c:if>

							<div class="button-holder">
								<label class="btn btn-secondary mt-2" for="<portlet:namespace />fileName" id="<portlet:namespace />uploadImage" tabindex="0"><liferay-ui:message key="select" /></label>

								<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" cssClass="hide" label="" name="fileName" type="file">
									<aui:validator name="acceptFiles">
										'<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>'
									</aui:validator>

									<aui:validator name="maxFileSize">
										'<%= String.valueOf(maxFileSize) %>'
									</aui:validator>
								</aui:input>
							</div>
						</aui:fieldset>
					</aui:fieldset-group>
				</clay:container-fluid>
			</div>

			<aui:button-row>
				<aui:button name="submitButton" type="submit" value="done" />

				<aui:button onClick="window.close();" type="cancel" value="cancel" />
			</aui:button-row>
		</aui:form>

		<script>
			(function () {
				var uploadImageButton = document.getElementById(
					'<portlet:namespace />uploadImage'
				);

				if (uploadImageButton) {
					uploadImageButton.addEventListener('keydown', (event) => {
						event.preventDefault();

						if (event.key == 'Enter' || event.key == ' ') {
							uploadImageButton.click();
						}
					});
				}
			})();
		</script>

		<aui:script use="liferay-logo-editor">
			<portlet:actionURL name="/image_uploader/upload_image" var="addTempImageURL">
				<portlet:param name="mvcRenderCommandName" value="/image_uploader/upload_image" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" />
				<portlet:param name="aspectRatio" value="<%= String.valueOf(aspectRatio) %>" />
				<portlet:param name="maxFileSize" value="<%= String.valueOf(maxFileSize) %>" />
				<portlet:param name="preserveRatio" value="<%= String.valueOf(preserveRatio) %>" />
			</portlet:actionURL>

			var imageUploadedInput = A.one('#<portlet:namespace />imageUploaded');

			var logoEditor = new Liferay.LogoEditor({
				aspectRatio: <%= aspectRatio %>,

				<%
				DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
				%>

				decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',

				maxFileSize: <%= maxFileSize %>,
				namespace: '<portlet:namespace />',
				on: {
					uploadComplete: A.bind('val', imageUploadedInput, true),
				},
				preserveRatio: <%= preserveRatio %>,
				previewURL: '<%= previewURL %>',
				uploadURL: '<%= addTempImageURL %>',
			});

			if (Liferay.Util.getTop() !== A.config.win) {
				var dialog = Liferay.Util.getWindow();

				if (dialog) {
					dialog.on(
						['resize:end', 'resize:resize', 'resize:start'],
						logoEditor.resize,
						logoEditor
					);
				}
			}
		</aui:script>
	</c:otherwise>
</c:choose>