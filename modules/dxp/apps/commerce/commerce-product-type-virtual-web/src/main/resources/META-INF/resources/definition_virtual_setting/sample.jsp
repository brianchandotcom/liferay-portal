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

FileEntry sampleFileEntry = cpDefinitionVirtualSettingDisplayContext.getSampleFileEntry();

long sampleFileEntryId = BeanParamUtil.getLong(cpDefinitionVirtualSetting, request, "sampleFileEntryId");

String textCssClass = "text-default ";

boolean useSampleFileEntry = false;

if (sampleFileEntryId > 0) {
	textCssClass += "hidden";

	useSampleFileEntry = true;
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="sample" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleException.class %>" message="please-enter-a-valid-sample-url-or-select-an-existing-sample-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleFileEntryIdException.class %>" message="please-select-an-existing-sample-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleUrlException.class %>" message="please-enter-a-valid-sample-url" />

<aui:fieldset>
	<aui:input name="useSample" />
</aui:fieldset>

<div class="col-md-3">
	<h4 class="text-default"><liferay-ui:message key="insert-the-url-or-select-a-file-of-your-sample" /></h4>
</div>

<div class="col-md-9">
	<aui:fieldset>
		<aui:input disabled="<%= useSampleFileEntry %>" name="sampleUrl" />

		<div class="lfr-definition-virtual-setting-sample-file-selector">
			<div id="lfr-definition-virtual-setting-sample-file-entry">
				<c:if test="<%= sampleFileEntry != null %>">
					<a href="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadSampleFileEntryURL() %>">
						<%= sampleFileEntry.getFileName() %>
					</a>
				</c:if>
			</div>

			<h4 class="<%= textCssClass %>" id="lfr-definition-virtual-sample-button-row-message"><liferay-ui:message key="or" /></h4>

			<aui:button name="selectSampleFile" value="select-file" />

			<aui:button name="deleteSampleFile" value="delete" />
		</div>
	</aui:fieldset>
</div>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectSampleFile').on(
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

								$('#<portlet:namespace />sampleFileEntryId').val(value.fileEntryId);

								$('#lfr-definition-virtual-setting-sample-file-entry').html('');

								$('#lfr-definition-virtual-setting-sample-file-entry').append('<a>' + value.title + '</a>');

								$('#lfr-definition-virtual-sample-button-row-message').addClass('hidden');

								$('#<portlet:namespace />sampleUrl').attr('disabled', true);
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
	AUI().ready('node', 'event', function(A) {
		selectSampleFileType(A);

		A.one('#<portlet:namespace/>useSample').on('click',function(b) {
			selectSampleFileType(A);
		})
	});

	function selectSampleFileType(A) {
		var useSampleCheckbox = A.one('#<portlet:namespace/>useSample');

		var isSampleFileSelected = A.one('#lfr-definition-virtual-sample-button-row-message').hasClass('hidden');

		if (useSampleCheckbox.attr('checked')) {
			A.one('#<portlet:namespace />deleteSampleFile').attr('disabled', false);
			A.one('#<portlet:namespace />sampleUrl').attr('disabled', isSampleFileSelected);
			A.one('#<portlet:namespace />selectSampleFile').attr('disabled', false);
		}
		else {
			A.one('#<portlet:namespace />deleteSampleFile').attr('disabled', true);
			A.one('#<portlet:namespace />sampleUrl').attr('disabled', true);
			A.one('#<portlet:namespace />selectSampleFile').attr('disabled', true);
		}
	}
</aui:script>

<aui:script>
	$('#<portlet:namespace />deleteSampleFile').on(
		'click',
		function(event) {
			event.preventDefault();

			$('#<portlet:namespace />sampleFileEntryId').val(0);

			$('#lfr-definition-virtual-setting-sample-file-entry').html('');

			$('#lfr-definition-virtual-sample-button-row').removeClass('hidden');

			$('#<portlet:namespace />sampleUrl').attr('disabled', false);
		}
	);
</aui:script>