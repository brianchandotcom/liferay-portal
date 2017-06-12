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
CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinitionVirtualSetting cpDefinitionVirtualSetting = cpDefinitionVirtualSettingDisplayContext.getCPDefinitionVirtualSetting();

FileEntry fileEntry = cpDefinitionVirtualSettingDisplayContext.getFileEntry();

long fileEntryId = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "fileEntryId");

String textCssClass = "text-default ";

boolean useFileEntry = false;

if (fileEntryId > 0) {
	textCssClass += "hidden";

	useFileEntry = true;
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-ui:error exception="<%= CPDefinitionVirtualSettingException.class %>" message="please-enter-a-valid-url-or-select-an-existing-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingFileEntryIdException.class %>" message="please-select-an-existing-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingUrlException.class %>" message="please-enter-a-valid-url" />

<div class="col-md-3">
	<h4 class="text-default"><liferay-ui:message key="insert-the-url-or-select-a-file-of-your-virtual-product" /></h4>
</div>

<div class="col-md-9">
	<aui:fieldset>
		<aui:input disabled="<%= useFileEntry %>" name="url" />

		<div class="lfr-definition-virtual-setting-file-selector">
			<div id="lfr-definition-virtual-setting-file-entry">
				<c:if test="<%= fileEntry != null %>">
					<a href="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadFileEntryURL() %>">
						<%= fileEntry.getFileName() %>
					</a>
				</c:if>
			</div>

			<h4 class="<%= textCssClass %>" id="lfr-definition-virtual-button-row-message"><liferay-ui:message key="or" /></h4>

			<aui:button name="selectFile" value="select-file" />

			<aui:button name="deleteFile" value="delete" />
		</div>
	</aui:fieldset>
</div>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectFile').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'uploadCPDefinitionVirtualSetting',
					on: {
						selectedItemChange: function(event) {

							var selectedItem = event.newVal;

							if (selectedItem) {

								var value = JSON.parse(selectedItem.value);

								$('#<portlet:namespace />fileEntryId').val(value.fileEntryId);

								$('#lfr-definition-virtual-setting-file-entry').html('');

								$('#lfr-definition-virtual-setting-file-entry').append('<a>' + value.title + '</a>');

								$('#lfr-definition-virtual-button-row-message').addClass('hidden');

								$('#<portlet:namespace />url').attr('disabled', true);
							}
						}
					},
					title: '<liferay-ui:message key="select-file" />',
					url: '<%= cpDefinitionVirtualSettingDisplayContext.getFileEntryItemSelectorURL() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	$('#<portlet:namespace />deleteFile').on(
		'click',
		function(event) {
			event.preventDefault();

			$('#<portlet:namespace />fileEntryId').val(0);

			$('#lfr-definition-virtual-setting-file-entry').html('');

			$('#lfr-definition-virtual-button-row-message').removeClass('hidden');

			$('#<portlet:namespace />url').attr('disabled', false);
		}
	);
</aui:script>