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

String headerTitle = portletName.equals(DLPortletKeys.MEDIA_GALLERY_DISPLAY) ? LanguageUtil.get(request, "add-multiple-media") : LanguageUtil.get(request, "add-multiple-documents");

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(redirect);

	renderResponse.setTitle(headerTitle);
}
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<c:if test="<%= !portletTitleBasedNavigation %>">
		<liferay-ui:header
			backURL="<%= redirect %>"
			localizeTitle="<%= false %>"
			title="<%= headerTitle %>"
		/>
	</c:if>

	<div class="card main-content-card">
		<div class="card-body">
			<c:choose>
				<c:when test="<%= DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_DOCUMENT) %>">
					<aui:row>
						<aui:col width="<%= 50 %>">
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
									maxFileSize: '<%= dlConfiguration.fileMaxSize() %> B',
									metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
									metadataExplanationContainer:
										'#<portlet:namespace />metadataExplanationContainer',
									namespace: '<portlet:namespace />',
									tempFileURL: {
										method: Liferay.Service.bind('/dlapp/get-temp-file-names'),
										params: {
											folderId: <%= folderId %>,
											folderName: '<%= EditFileEntryMVCActionCommand.TEMP_FOLDER_NAME %>',
											groupId: <%= scopeGroupId %>
										}
									},
									tempRandomSuffix: '<%= TempFileEntryUtil.TEMP_RANDOM_SUFFIX %>',
									uploadFile:
										'<liferay-portlet:actionURL name="/document_library/upload_multiple_file_entries"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" /></liferay-portlet:actionURL>'
								});
							</aui:script>
						</aui:col>

						<aui:col width="<%= 50 %>">
							<div class="common-file-metadata-container hide selected" id="<portlet:namespace />commonFileMetadataContainer">
								<liferay-util:include page="/document_library/upload_multiple_file_entries_resources.jsp" servletContext="<%= application %>" />
							</div>

							<%
							DLBreadcrumbUtil.addPortletBreadcrumbEntries(folderId, request, renderResponse);

							PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-multiple-file-entries"), currentURL);
							%>

							<aui:script use="aui-base,aui-loading-mask-deprecated,node-load">
								Liferay.on('tempFileRemoved', function() {
									Liferay.Util.openToast({
										message:
											'<%= LanguageUtil.get(request, "your-request-completed-successfully") %>',
										title: '<%= LanguageUtil.get(request, "success") %>'
									});
								});

								Liferay.provide(
									window,
									'<portlet:namespace />updateMultipleFiles',
									function() {
										var Lang = A.Lang;

										var commonFileMetadataContainer = A.one(
											'#<portlet:namespace />commonFileMetadataContainer'
										);
										var selectedFileNameContainer = A.one(
											'#<portlet:namespace />selectedFileNameContainer'
										);
										var ddmFormFieldNamespaces = A.all(
											'#<portlet:namespace />ddmFormFieldNamespace'
										).val();

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

										for (var i = 0; i < ddmFormFieldNamespaces.length; i++) {
											var ddmFormFieldNamespace = ddmFormFieldNamespaces[i];

											var ddmForm = Liferay.component(
												'<portlet:namespace />' + ddmFormFieldNamespace + 'ddmForm'
											);

											ddmForm.updateDDMFormInputValue();
										}

										Liferay.Util.fetch(document.<portlet:namespace />fm2.action, {
											body: new FormData(document.<portlet:namespace />fm2),
											method: 'POST'
										})
											.then(function(response) {
												return response.json();
											})
											.then(function(response) {
												var itemFailed = false;

												for (var i = 0; i < response.length; i++) {
													var item = response[i];

													var checkBox = A.one(
														'input[data-fileName="' + item.originalFileName + '"]'
													);

													var li = checkBox.ancestor();

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
																'<span class="card-bottom success-message"><%= UnicodeLanguageUtil.get(request, "successfully-saved") %></span>';
														}
														else {
															childHTML =
																'<span class="card-bottom success-message"><%= UnicodeLanguageUtil.get(request, "successfully-saved") %> (' +
																item.fileName +
																')</span>';
														}
													}
													else {
														cssClass = 'upload-error';

														childHTML =
															'<span class="card-bottom error-message">' +
															item.errorMessage +
															'</span>';

														itemFailed = true;
													}

													li.addClass(cssClass);
													li.append(childHTML);
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
														'<%= uploadMultipleFileEntries %>'
													);
												}

												Liferay.fire('filesSaved');

												commonFileMetadataContainer.unplug(A.LoadingMask);

												if (!itemFailed) {
													location.href = '<%= HtmlUtil.escapeJS(redirect) %>';
												}
											})
											.catch(function(error) {
												var selectedItems = A.all(
													'#<portlet:namespace />fileUpload li.selected'
												);

												selectedItems
													.removeClass('selectable')
													.removeClass('selected')
													.addClass('upload-error');

												selectedItems.append(
													'<span class="card-bottom error-message"><%= UnicodeLanguageUtil.get(request, "an-unexpected-error-occurred-while-deleting-the-file") %></span>'
												);

												selectedItems.all('input').remove(true);

												commonFileMetadataContainer.loadingmask.hide();
											});
									},
									['aui-base']
								);
							</aui:script>
						</aui:col>
					</aui:row>
				</c:when>
				<c:otherwise>
					<div class="alert alert-danger">
						<liferay-ui:message key="you-do-not-have-the-required-permissions-to-access-this-application" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>