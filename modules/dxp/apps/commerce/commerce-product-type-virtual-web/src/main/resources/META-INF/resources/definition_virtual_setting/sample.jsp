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
CPDefinitionVirtualSetting cpDefinitionVirtualSetting = (CPDefinitionVirtualSetting)request.getAttribute(CPDefinitionVirtualSettingWebKeys.COMMERCE_PRODUCT_DEFINITION_VIRTUAL_SETTING);

CPDefinitionVirtualSettingItemSelectorHelper cpDefinitionVirtualSettingItemSelectorHelper = (CPDefinitionVirtualSettingItemSelectorHelper)request.getAttribute(CPWebKeys.DEFINITION_VIRTUAL_SETTING_ITEM_SELECTOR_HELPER);
%>

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<div class="lfr-definition-virtual-setting-sample-file-selector">
	<aui:fieldset>
		<aui:input checked="<%= true %>" cssClass="lfr-definition-virtual-setting-sample-type" label="use-sample-file" name="useSampleFileEntry" type="radio" />

		<aui:button cssClass="lfr-definition-virtual-setting-sample-value" name="selectSampleFile" value="select-file" />
	</aui:fieldset>

	<div class="lfr-sample-file-entry-title">
		<liferay-ui:message key="select-file" />
	</div>

	<aui:fieldset>
		<aui:input cssClass="lfr-definition-virtual-setting-sample-type" label="use-url" name="useSampleUrl" type="radio" />

		<aui:input cssClass="lfr-definition-virtual-setting-sample-value" disabled="<%= true %>" name="sampleUrl" />
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

								$('div.lfr-sample-file-entry-title').replaceWith('<div class="lfr-sample-file-entry-title">Title: ' + value.title + '</div>');
							}
						}
					},
					title: '<liferay-ui:message key="select-file" />',
					url: '<%= cpDefinitionVirtualSettingItemSelectorHelper.getItemSelectorURL(renderRequest) %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script use="aui-toggler">
	var container = A.one('#<portlet:namespace />fileEntryContainer');

	var sampleTypes = container.all('.lfr-definition-virtual-setting-sample-type');
	var sampleValues = container.all('.lfr-definition-virtual-setting-sample-value');

	var selectSampleFileType = function(index) {
		sampleTypes.attr('checked', false);

		sampleTypes.item(index).attr('checked', true);

		sampleValues.attr('disabled', true);

		sampleValues.item(index).attr('disabled', false);

		sampleTypes.each(
			function(index) {
				if (sampleTypes.item(index).get('checked')) {
					sampleValues.item(index).attr('disabled', true);
				}
			}
		);
	};

	container.delegate(
		'change',
		function(event) {
			var index = sampleTypes.indexOf(event.currentTarget);

			selectSampleFileType(index);
		},
		'.lfr-definition-virtual-setting-sample-type'
	);
</aui:script>