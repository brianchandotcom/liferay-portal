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
%>

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<div class="lfr-definition-virtual-setting-file-selector">
	<aui:fieldset>
		<aui:input checked="<%= true %>" cssClass="lfr-definition-virtual-setting-type" label="use-file" name="useFileEntry" type="radio" />

		<aui:button cssClass="lfr-definition-virtual-setting-value" name="selectFile" value="select-file" />
	</aui:fieldset>

	<div class="lfr-file-entry-title">
		<liferay-ui:message key="select-file" />
	</div>

	<aui:fieldset>
		<aui:input cssClass="lfr-definition-virtual-setting-type" label="use-url" name="useUrl" type="radio" />

		<aui:input cssClass="lfr-definition-virtual-setting-value" disabled="<%= true %>" name="url" />
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

								$('div.lfr-file-entry-title').replaceWith('<div class="lfr-file-entry-title">Title: ' + value.title + '</div>');
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

	var types = container.all('.lfr-definition-virtual-setting-type');
	var values = container.all('.lfr-definition-virtual-setting-value');

	var selectFileType = function(index) {
		types.attr('checked', false);

		types.item(index).attr('checked', true);

		values.attr('disabled', true);

		values.item(index).attr('disabled', false);

		types.each(
			function(index) {
				if (types.item(index).get('checked')) {
					values.item(index).attr('disabled', true);
				}
			}
		);
	};

	container.delegate(
		'change',
		function(event) {
			var index = types.indexOf(event.currentTarget);

			selectFileType(index);
		},
		'.lfr-definition-virtual-setting-type'
	);
</aui:script>