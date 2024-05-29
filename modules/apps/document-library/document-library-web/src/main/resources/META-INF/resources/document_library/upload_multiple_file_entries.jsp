<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long repositoryId = ParamUtil.getLong(request, "repositoryId");

if (repositoryId <= 0) {

	// <liferay-ui:asset_add_button /> only passes in groupId

	repositoryId = ParamUtil.getLong(request, "groupId");
}

long folderId = ParamUtil.getLong(request, "folderId");

String headerTitle = Objects.equals(dlRequestHelper.getResourcePortletName(), DLPortletKeys.MEDIA_GALLERY_DISPLAY) ? LanguageUtil.get(request, "add-multiple-media") : LanguageUtil.get(request, "add-multiple-documents");

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid container-fluid-max-xl container-form-lg\"" : StringPool.BLANK %>>
	<c:if test="<%= !portletTitleBasedNavigation %>">
		<liferay-ui:header
			backURL="<%= redirect %>"
			localizeTitle="<%= false %>"
			title="<%= headerTitle %>"
		/>
	</c:if>

	<div class="sheet">
		<c:choose>
			<c:when test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">
				<clay:row>
					<clay:col
						md="6"
					>
						<aui:form name="fm1">
							<div class="lfr-dynamic-uploader">
								<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
							</div>
						</aui:form>

						<aui:script use="liferay-upload">
							new Liferay.Upload({
								boundingBox: '#<portlet:namespace />fileUpload',

								<%
								DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
								%>

								decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',

								deleteFile:
									'<liferay-portlet:actionURL name="/document_library/upload_multiple_file_entries"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL>',
								fileDescription:
									'<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',
								maxFileSize:
									'<%= DLValidatorUtil.getMaxAllowableSize(themeDisplay.getScopeGroupId(), null) %> B',
								metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
								metadataExplanationContainer:
									'#<portlet:namespace />metadataExplanationContainer',
								namespace: '<portlet:namespace />',
								simultaneousUploads: 1,
								tempFileURL: {
									method: Liferay.Service.bind('/dlapp/get-temp-file-names'),
									params: {
										folderId: <%= folderId %>,
										folderName: '<%= EditFileEntryMVCActionCommand.TEMP_FOLDER_NAME %>',
										groupId: <%= scopeGroupId %>,
									},
								},
								tempRandomSuffix: '<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>',
								uploadFile:
									'<liferay-portlet:actionURL name="/document_library/upload_multiple_file_entries"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL>',
							});
						</aui:script>
					</clay:col>

					<clay:col
						md="6"
					>
						<div>
							<div class="common-file-metadata-container hide selected" id="<portlet:namespace />commonFileMetadataContainer">
								<liferay-util:include page="/document_library/upload_multiple_file_entries_resources.jsp" servletContext="<%= application %>" />
							</div>

							<span aria-hidden="true" class="hide loading-animation loading-animation-secondary loading-animation-sm" id="<portlet:namespace />loading"></span>
						</div>

						<%
						DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

						PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-multiple-file-entries"), currentURL);
						%>

						<aui:script sandbox="<%= true %>">
							Liferay.on('tempFileRemoved', () => {
								Liferay.Util.openToast({
									message:
										'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "your-request-completed-successfully")) %>',
								});
							});

							function submit() {
								var commonFileMetadataContainer = document.getElementById(
									'<portlet:namespace />commonFileMetadataContainer'
								);
								var selectedFileNameContainer = document.getElementById(
									'<portlet:namespace />selectedFileNameContainer'
								);

								var inputTpl =
									'<input id="<portlet:namespace />selectedFileName{0}" name="<portlet:namespace />selectedFileName" type="hidden" value="{1}" />';

								var values = Array.from(
									document.querySelectorAll(
										'input[name=<portlet:namespace />selectUploadedFile]:checked'
									)
								).map((input) => {
									return input.value;
								});

								var buffer = [];
								var dataBuffer = [];
								var length = values.length;

								for (var i = 0; i < length; i++) {
									dataBuffer[0] = i;
									dataBuffer[1] = values[i];

									buffer[i] = Liferay.Util.sub(inputTpl, dataBuffer);
								}

								selectedFileNameContainer.innerHTML = buffer.join('');

								var loading = document.getElementById('<portlet:namespace />loading');

								loading.classList.remove('hide');
								commonFileMetadataContainer.classList.add('hide');

								Liferay.Util.fetch(document.<portlet:namespace />fm2.action, {
									body: new FormData(document.<portlet:namespace />fm2),
									method: 'POST',
								})
									.then((response) => {
										return response.json();
									})
									.then((response) => {
										var itemFailed = false;

										for (var i = 0; i < response.length; i++) {
											var item = response[i];

											var checkBox = document.querySelector(
												'input[data-fileName="' + item.originalFileName + '"]'
											);

											var li = checkBox.closest('li');

											checkBox.remove();

											li.classList.remove('selectable', 'selected');

											var cssClass = null;
											const childHTML = document.createElement('div');
											childHTML.classList.add('card-footer', 'small');

											if (item.added) {
												cssClass = 'file-saved';

												var originalFileName = item.originalFileName;

												var pos = originalFileName.indexOf(
													'<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>'
												);

												if (pos != -1) {
													originalFileName = originalFileName.substring(0, pos);
												}

												if (originalFileName === item.fileName) {
													childHTML.classList.add('text-success');
													childHTML.innerText =
														'<%= UnicodeLanguageUtil.get(request, "successfully-saved") %>';
												}
												else {
													childHTML.classList.add('text-success');
													childHTML.innerText =
														'<%= UnicodeLanguageUtil.get(request, "successfully-saved") %>' +
														'(' +
														item.fileName +
														')';
												}
											}
											else {
												cssClass = 'upload-error';
												childHTML.classList.add('text-danger');
												childHTML.innerText = item.errorMessage;

												itemFailed = true;
											}

											li.classList.add(cssClass);
											li.querySelector('.card').appendChild(childHTML);
										}

										<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/document_library/upload_multiple_file_entries" var="uploadMultipleFileEntries">
											<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
											<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
										</liferay-portlet:resourceURL>

										Liferay.Util.fetch('<%= uploadMultipleFileEntries %>')
											.then((response) => response.text())
											.then((response) => {
												commonFileMetadataContainer.innerHTML = response;

												Liferay.Util.runScriptsInElement(
													commonFileMetadataContainer
												);
											});

										Liferay.fire('filesSaved');

										loading.classList.add('hide');

										if (!itemFailed) {
											Liferay.Util.navigate('<%= HtmlUtil.escapeJS(redirect) %>');
										}
									})
									.catch((error) => {
										var selectedItems = document.querySelectorAll(
											'#<portlet:namespace />fileUpload li.selected'
										);
										const errorHTML = document.createElement('span');
										errorHTML.className = 'card-bottom error-message';
										errorHTML.innerText =
											'<%= UnicodeLanguageUtil.get(request, "an-unexpected-error-occurred-while-uploading-your-file") %>';

										selectedItems.forEach((selectedItem) => {
											selectedItem.classList.remove('selectable');
											selectedItem.classList.remove('selected');
											selectedItem.classList.add('upload-error');
											selectedItem.appendChild(errorHTML);
											if (selectedItem.hasAttribute('input')) {
												selectedItem.remove();
											}
										});

										commonFileMetadataContainer.classList.remove('hide');
										loading.classList.add('hide');
									});
							}

							function ddmFormValid(event) {
								if (event.formWrapperId === document.<portlet:namespace />fm2.id) {
									submit();
								}
							}

							function ddmFormError(event) {
								if (event.formWrapperId === document.<portlet:namespace />fm2.id) {
									Liferay.CollapseProvider.show({
										panel: document.querySelector('.document-type .panel-collapse'),
									});
								}
							}

							Liferay.on('ddmFormValid', ddmFormValid);

							Liferay.on('ddmFormError', ddmFormError);

							window['<portlet:namespace />updateMultipleFiles'] = function () {
								var isDataEngineControlled = Boolean(
									document.querySelector('[data-ddm-fieldset]')
								);

								if (!isDataEngineControlled) {
									submit();
								}
							};

							function cleanUp() {
								Liferay.detach('ddmFormValid', ddmFormValid);
								Liferay.detach('ddmFormError', ddmFormError);
								Liferay.detach('destroyPortlet', cleanUp);
							}

							Liferay.on('destroyPortlet', cleanUp);
						</aui:script>
					</clay:col>
				</clay:row>
			</c:when>
			<c:otherwise>
				<div class="alert alert-danger">
					<liferay-ui:message key="you-do-not-have-the-required-permissions-to-access-this-application" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>