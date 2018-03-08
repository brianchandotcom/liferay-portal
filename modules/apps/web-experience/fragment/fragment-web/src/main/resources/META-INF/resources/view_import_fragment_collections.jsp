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
DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
%>

<liferay-ui:success key='<%= portletDisplay.getId() + "filesImported" %>' message='<%= LanguageUtil.get(resourceBundle, "the-files-were-imported-correctly") %>' />

<liferay-ui:error exception="<%= DuplicateFragmentCollectionKeyException.class %>">

	<%
	DuplicateFragmentCollectionKeyException dfcke = (DuplicateFragmentCollectionKeyException)errorException;
	%>

	<liferay-ui:message arguments="<%= dfcke.getMessage() %>" key="a-fragment-collection-with-the-key-x-already-exists" />
</liferay-ui:error>

<div class="lfr-dynamic-uploader" id="<portlet:namespace />uploaderContainer">
	<div class="container-fluid-1280">
		<aui:row>
			<aui:col width="<%= 50 %>">
				<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
			</aui:col>

			<aui:col width="<%= 50 %>">
				<div class="hide" id="<portlet:namespace />metadataExplanationContainer"></div>

				<div class="common-file-metadata-container hide selected" id="<portlet:namespace />commonFileMetadataContainer">
					<portlet:actionURL name="/fragment/import_fragment_collections" var="importFragmentCollectionsURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.IMPORT %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
					</portlet:actionURL>

					<aui:form action="<%= importFragmentCollectionsURL %>" method="post" name="fm2" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "importMultipleFiles();" %>'>
						<aui:fieldset-group markupView="lexicon">
							<h3 class="p-3"><liferay-ui:message key="import-selected-files" /></h3>

							<aui:fieldset>
								<aui:input checked="<%= true %>" label="overwrite-existing-entries" name="overwrite" type="checkbox" />
							</aui:fieldset>
						</aui:fieldset-group>

						<span id="<portlet:namespace />selectedFileNameContainer"></span>

						<aui:button-row>
							<aui:button type="submit" value="import" />
						</aui:button-row>
					</aui:form>
				</div>
			</aui:col>
		</aui:row>
	</div>
</div>

<aui:script use="liferay-portlet-url,liferay-upload">
	var uploader = new Liferay.Upload(
		{
			boundingBox: '#<portlet:namespace />fileUpload',

			<%
			DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
			%>

			decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',
			fallback: '#<portlet:namespace />fallback',
			fileDescription: '<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',
			maxFileSize: '<%= UploadServletRequestConfigurationHelperUtil.getMaxSize() %> B',
			metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
			metadataExplanationContainer: '#<portlet:namespace />metadataExplanationContainer',
			namespace: '<portlet:namespace />',
			rootElement: '#<portlet:namespace />uploaderContainer',
			tempFileURL: {
				method: Liferay.Service.bind('/fragment.fragmentcollection/get-temp-file-names'),
				params: {
					folderName: '<%= ExportImportConstants.FRAGMENT_COLLECTION_TEMP_FOLDER_NAME %>',
					groupId: <%= scopeGroupId %>
				}
			},
			'strings.uploadsCompleteText': '<liferay-ui:message key="fragment-collections-imported-successfully" />',
			uploadFile: '<portlet:actionURL name="/fragment/import_fragment_collections"><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /></portlet:actionURL>'
		}
	);
</aui:script>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />importMultipleFiles',
		function() {
			var A = AUI();
			var Lang = A.Lang;

			var selectedFileNameContainer = A.one('#<portlet:namespace />selectedFileNameContainer');

			var inputTpl = '<input id="<portlet:namespace />selectedFileName{0}" name="<portlet:namespace />selectedFileName" type="hidden" value="{1}" />';

			var values = A.all('input[name=<portlet:namespace />selectUploadedFile]:checked').val();

			var buffer = [];
			var dataBuffer = [];
			var length = values.length;

			for (var i = 0; i < length; i++) {
				dataBuffer[0] = i;
				dataBuffer[1] = values[i];

				buffer[i] = Lang.sub(inputTpl, dataBuffer);
			}

			selectedFileNameContainer.html(buffer.join(''));

			submitForm(document.<portlet:namespace />fm2);
		}
	);
</aui:script>