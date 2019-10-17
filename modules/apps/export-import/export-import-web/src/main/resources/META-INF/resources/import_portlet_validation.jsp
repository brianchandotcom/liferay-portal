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
String redirect = ParamUtil.getString(request, "redirect");
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="exportImport" var="importPortletURL">
	<portlet:param name="p_p_isolated" value="<%= Boolean.TRUE.toString() %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
	<portlet:param name="portletResource" value="<%= portletResource %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
	<portlet:param name="validate" value="<%= String.valueOf(Boolean.FALSE) %>" />
</liferay-portlet:resourceURL>

<aui:form action="<%= importPortletURL %>" cssClass="lfr-export-dialog" method="post" name="fm1">

	<%
	FileEntry fileEntry = ExportImportHelperUtil.getTempFileEntry(scopeGroupId, themeDisplay.getUserId(), ExportImportHelper.TEMP_FOLDER_NAME + selPortlet.getPortletId());
	%>

	<div class="lfr-dynamic-uploader <%= (fileEntry == null) ? "hide-dialog-footer" : StringPool.BLANK %>">
		<div class="container-fluid-1280">
			<div class="lfr-upload-container" id="<portlet:namespace />fileUpload"></div>
		</div>
	</div>

	<aui:button-row>
		<aui:button name="continueButton" type="submit" value="continue" />
	</aui:button-row>

	<%
	Date expirationDate = new Date(System.currentTimeMillis() + PropsValues.SESSION_TIMEOUT * Time.MINUTE);

	Ticket ticket = TicketLocalServiceUtil.addTicket(user.getCompanyId(), User.class.getName(), user.getUserId(), TicketConstants.TYPE_IMPERSONATE, null, expirationDate, new ServiceContext());
	%>

	<aui:script use="liferay-upload">
		var liferayUpload = new Liferay.Upload({
			boundingBox: '#<portlet:namespace />fileUpload',

			<%
			DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale);
			%>

			decimalSeparator: '<%= decimalFormatSymbols.getDecimalSeparator() %>',

			deleteFile:
				'<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>" name="exportImport"><portlet:param name="mvcRenderCommandName" value="exportImport" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE_TEMP %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="portletResource" value="<%= portletResource %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= Group.class.getName() %>" />',

			<%
			DLConfiguration dlConfiguration = ConfigurationProviderUtil.getSystemConfiguration(DLConfiguration.class);
			%>

			fileDescription:
				'<%= StringUtil.merge(dlConfiguration.fileExtensions()) %>',

			maxFileSize:
				'<%= UploadServletRequestConfigurationHelperUtil.getMaxSize() %> B',
			metadataContainer: '#<portlet:namespace />commonFileMetadataContainer',
			metadataExplanationContainer:
				'#<portlet:namespace />metadataExplanationContainer',
			multipleFiles: false,
			namespace: '<portlet:namespace />',
			'strings.dropFileText':
				'<liferay-ui:message key="drop-a-lar-file-here-to-import" />',
			'strings.fileCannotBeSavedText':
				'<liferay-ui:message key="the-file-x-cannot-be-imported" />',
			'strings.pendingFileText':
				'<liferay-ui:message key="this-file-was-previously-uploaded-but-not-actually-imported" />',
			'strings.uploadsCompleteText':
				'<liferay-ui:message key="the-file-is-ready-to-be-imported" />',
			tempFileURL: {
				method: Liferay.Service.bind('/layout/get-temp-file-names'),
				params: {
					folderName:
						'<%= HtmlUtil.escapeJS(ExportImportHelper.TEMP_FOLDER_NAME + selPortlet.getPortletId()) %>',
					groupId: <%= scopeGroupId %>
				}
			},
			uploadFile:
				'<liferay-portlet:actionURL doAsUserId="<%= user.getUserId() %>" name="exportImport"><portlet:param name="mvcRenderCommandName" value="exportImport" /><portlet:param name="<%= Constants.CMD %>" value="<%= Constants.ADD_TEMP %>" /><portlet:param name="redirect" value="<%= redirect %>" /><portlet:param name="plid" value="<%= String.valueOf(plid) %>" /> <portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getScopeGroupId()) %>" /><portlet:param name="portletResource" value="<%= portletResource %>" /></liferay-portlet:actionURL>&ticketKey=<%= ticket.getKey() %><liferay-ui:input-permissions-params modelName="<%= Group.class.getName() %>" />'
		});

		liferayUpload._uploader.on('alluploadscomplete', function(event) {
			toggleContinueButton();
		});

		Liferay.on('tempFileRemoved', function(event) {
			toggleContinueButton();
		});

		function toggleContinueButton() {
			var lfrDynamicUploader = liferayUpload
				.get('boundingBox')
				.ancestor('.lfr-dynamic-uploader');
			var uploadedFiles = liferayUpload._fileListContent.all(
				'.upload-file.upload-complete'
			);

			if (uploadedFiles.size() == 1) {
				lfrDynamicUploader.removeClass('hide-dialog-footer');
			} else {
				lfrDynamicUploader.addClass('hide-dialog-footer');
			}
		}

		<c:if test="<%= StagingUtil.isChangeTrackingEnabled(company.getCompanyId()) %>">
			var form = document.<portlet:namespace />fm1;

			var formElements = form.elements;

			for (var i = 0; i < formElements.length; ++i) {
				formElements[i].disabled = true;
			}
		</c:if>
	</aui:script>
</aui:form>

<aui:script require="metal-dom/src/all/dom as dom">
	var continueButton = document.getElementById(
		'<portlet:namespace />continueButton'
	);
	var exportImportOptions = document.getElementById(
		'<portlet:namespace />exportImportOptions'
	);

	if (continueButton && exportImportOptions) {
		var form = document.<portlet:namespace />fm1;

		continueButton.addEventListener('click', function(event) {
			event.preventDefault();

			Liferay.Util.fetch(form.action)
				.then(function(response) {
					return response.text();
				})
				.then(function(response) {
					exportImportOptions.innerHTML = response;

					dom.globalEval.runScriptsInElement(exportImportOptions);
				});
		});
	}
</aui:script>