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
	textCssClass += "hide";

	useSampleFileEntry = true;
}
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="sample"
/>

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleException.class %>" message="please-enter-a-valid-sample-url-or-select-an-existing-sample-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleFileEntryIdException.class %>" message="please-select-an-existing-sample-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleUrlException.class %>" message="please-enter-a-valid-sample-url" />

<aui:fieldset>
	<aui:input label="enable-sample" name="useSample" />
</aui:fieldset>

<div class="col-md-3">
	<h4 class="text-default"><liferay-ui:message key="select-existing-content-or-add-the-url-below" /></h4>
</div>

<div class="col-md-9">
	<aui:fieldset>
		<p class="text-default">
			<span class="<%= (sampleFileEntryId > 0) ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />sampleFileEntryRemove" role="button">
				<aui:icon cssClass="icon-monospaced" image="times" markupView="lexicon" />
			</span>
			<span id="<portlet:namespace />sampleFileEntryNameInput">
				<c:choose>
					<c:when test="<%= (sampleFileEntry != null) %>">
						<a href="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadSampleFileEntryURL() %>">
							<%= sampleFileEntry.getFileName() %>
						</a>
					</c:when>
					<c:otherwise>
						<span class="text-muted"><liferay-ui:message key="none" /></span>
					</c:otherwise>
				</c:choose>
			</span>
		</p>

		<aui:button name="selectSampleFile" value="select" />

		<h4 class="<%= textCssClass %>" id="lfr-definition-virtual-sample-button-row-message"><liferay-ui:message key="or" /></h4>

		<aui:input disabled="<%= useSampleFileEntry %>" label="sample-file-url" name="sampleUrl" />
	</aui:fieldset>
</div>

<aui:script use="liferay-item-selector-dialog">
	var sampleFileEntryRemove = $('#<portlet:namespace />sampleFileEntryRemove');
	var sampleFileEntryNameInput = $('#<portlet:namespace />sampleFileEntryNameInput');

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

								$('#<portlet:namespace />sampleUrl').attr('disabled', true);

								$('#lfr-definition-virtual-sample-button-row-message').addClass('hide');

								sampleFileEntryRemove.removeClass('hide');

								sampleFileEntryNameInput.html('<a>' + value.title + '</a>');
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

	$('#<portlet:namespace />sampleFileEntryRemove').on(
		'click',
		function(event) {
			event.preventDefault();

			var useSampleCheckbox = A.one('#<portlet:namespace />useSample');

			if (useSampleCheckbox.attr('checked')) {
				$('#<portlet:namespace />sampleUrl').attr('disabled', false);
			}

			$('#<portlet:namespace />sampleFileEntryId').val(0);

			$('#lfr-definition-virtual-sample-button-row-message').removeClass('hide');

			sampleFileEntryNameInput.html('<liferay-ui:message key="none" />');

			sampleFileEntryRemove.addClass('hide');
		}
	);
</aui:script>

<aui:script>
	AUI().ready(
		'node', 'event',
		function(A) {
			selectSampleFileType(A);

			A.one('#<portlet:namespace />useSample').on(
				'click',
				function(b) {
					selectSampleFileType(A);
				}
			);
		}
	);

	function selectSampleFileType(A) {
		var useSampleCheckbox = A.one('#<portlet:namespace />useSample');

		var isSampleFileSelected = A.one('#lfr-definition-virtual-sample-button-row-message').hasClass('hide');

		if (useSampleCheckbox.attr('checked')) {
			A.one('#<portlet:namespace />sampleUrl').attr('disabled', isSampleFileSelected);
			A.one('#<portlet:namespace />selectSampleFile').attr('disabled', false);
		}
		else {
			A.one('#<portlet:namespace />sampleUrl').attr('disabled', true);
			A.one('#<portlet:namespace />selectSampleFile').attr('disabled', true);
		}
	}
</aui:script>