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
								maxFileSize: '<%= DLValidatorUtil.getMaxAllowableSize(null) %> B',
								metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
								metadataExplanationContainer:
									'#<portlet:namespace />metadataExplanationContainer',
								namespace: '<portlet:namespace />',
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
						<div class="common-file-metadata-container hide selected" id="<portlet:namespace />commonFileMetadataContainer">
							<liferay-util:include page="/document_library/upload_multiple_file_entries_resources.jsp" servletContext="<%= application %>" />
						</div>

						<%
						DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

						PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-multiple-file-entries"), currentURL);
						%>

						<aui:script require="frontend-js-web/liferay/util/run_scripts_in_element.es as runScriptsInElement">
							AUI().use('aui-base', 'aui-loading-mask-deprecated', 'node-load', (A) => {
								Liferay.on('tempFileRemoved', () => {
									Liferay.Util.openToast({
										message:
											'<%= HtmlUtil.escapeJS(LanguageUtil.get(request, "your-request-completed-successfully")) %>',
									});
								});

								function submit() {
									var Lang = A.Lang;

									var commonFileMetadataContainer = A.one(
										'#<portlet:namespace />commonFileMetadataContainer'
									);
									var selectedFileNameContainer = A.one(
										'#<portlet:namespace />selectedFileNameContainer'
									);

									var inputTpl =
										'<input id="<portlet:namespace />selectedFileName{0}" name="<portlet:namespace />selectedFileName" type="hidden" value="{1}" />';

									var values = A.all(
										'input[name=<portlet:namespace />selectUploadedFile]:checked'
									).val();

									var buffer = [];
									var dataBuffer = [];
									var length = values.length;

									for (var i = 0; i < length; i++) {
										dataBuffer[0] = i;
										dataBuffer[1] = values[i];

										buffer[i] = Lang.sub(inputTpl, dataBuffer);
									}

									selectedFileNameContainer.html(buffer.join(''));

									commonFileMetadataContainer.plug(A.LoadingMask);

									commonFileMetadataContainer.loadingmask.show();

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

												var checkBox = A.one(
													'input[data-fileName="' + item.originalFileName + '"]'
												);

												var li = checkBox.ancestor('li');

												checkBox.remove(true);

												li.removeClass('selectable').removeClass('selected');

												var cssClass = null;
												var childHTML = null;

												if (item.added) {
													cssClass = 'file-saved';

													var originalFileName = item.originalFileName;

													var pos = originalFileName.indexOf(
														'<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>'
													);

													if (pos != -1) {
														originalFileName = originalFileName.substr(0, pos);
													}

													if (originalFileName === item.fileName) {
														childHTML =
															'<div class="card-footer small text-success"><%= UnicodeLanguageUtil.get(request, "successfully-saved") %></div>';
													}
													else {
														childHTML =
															'<div class="card-footer small text-success"><%= UnicodeLanguageUtil.get(request, "successfully-saved") %> (' +
															item.fileName +
															')</div>';
													}
												}
												else {
													cssClass = 'upload-error';

													childHTML =
														'<div class="card-footer small text-danger">' +
														item.errorMessage +
														'</div>';

													itemFailed = true;
												}

												li.addClass(cssClass);
												li.one('.card').append(childHTML);
											}

											<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/document_library/upload_multiple_file_entries" var="uploadMultipleFileEntries">
												<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
												<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
											</liferay-portlet:resourceURL>

											if (commonFileMetadataContainer.io) {
												commonFileMetadataContainer.io.start();
											}
											else {
												commonFileMetadataContainer.load(
													'<%= uploadMultipleFileEntries %>',
													undefined,
													() => {
														runScriptsInElement.default(
															document.getElementById(
																'<portlet:namespace />commonFileMetadataContainer'
															)
														);
													}
												);
											}

											Liferay.fire('filesSaved');

											commonFileMetadataContainer.unplug(A.LoadingMask);

											if (!itemFailed) {
												Liferay.Util.navigate('<%= HtmlUtil.escapeJS(redirect) %>');
											}
										})
										.catch((error) => {
											var selectedItems = A.all(
												'#<portlet:namespace />fileUpload li.selected'
											);

											selectedItems
												.removeClass('selectable')
												.removeClass('selected')
												.addClass('upload-error');

											selectedItems.append(
												'<span class="card-bottom error-message"><%= UnicodeLanguageUtil.get(request, "an-unexpected-error-occurred-while-uploading-your-file") %></span>'
											);

											selectedItems.all('input').remove(true);

											commonFileMetadataContainer.loadingmask.hide();
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
							});
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