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

SearchContainer<FileEntry> fileEntrySearchContainer = cpDefinitionVirtualSettingDisplayContext.getFileEntrySearchContainer();

String buttonCssClass = "modify-file-entry-link ";

boolean useUrl = ParamUtil.getBoolean(request, "useUrl", false);

if ((cpDefinitionVirtualSetting != null) && Validator.isNotNull(cpDefinitionVirtualSetting.getUrl())) {
	useUrl = true;
}

if (fileEntrySearchContainer.hasResults()) {
	buttonCssClass += "hidden";
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-ui:error exception="<%= CPDefinitionVirtualSettingFileEntryIdException.class %>" message="please-select-an-existing-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingUrlException.class %>" message="please-enter-a-valid-url" />

<liferay-util:buffer var="removeFileEntryIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:fieldset>
	<aui:input checked="<%= useUrl %>" cssClass="lfr-definition-virtual-setting-type" label="use-url" name="useUrl" type="checkbox" />

	<div class="lfr-definition-virtual-setting-file-selector">
		<liferay-ui:search-container
			cssClass="lfr-definition-virtual-setting-file-entry"
			curParam="curFileEntry"
			headerNames="title,null"
			id="fileEntrySearchContainer"
			iteratorURL="<%= currentURLObj %>"
			searchContainer="<%= fileEntrySearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.FileEntry"
				keyProperty="fileEntryId"
				modelVar="fileEntry"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="title"
				>
					<liferay-ui:icon
						iconCssClass="icon-ok-sign"
						label="<%= true %>"
						message="<%= HtmlUtil.escape(fileEntry.getTitle()) %>"
						url="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadFileEntryURL() %>"
					/>
				</liferay-ui:search-container-column-text>

				<c:if test="<%= Validator.isNotNull(cpDefinitionVirtualSetting) %>">
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="size"
						value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-text>
					<a class="modify-file-entry-link" data-rowId="<%= fileEntry.getFileEntryId() %>" href="javascript:;"><%= removeFileEntryIcon %></a>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= fileEntrySearchContainer %>" />
		</liferay-ui:search-container>

		<aui:button cssClass="<%= buttonCssClass %>" name="selectFile" value="select-file" />
	</div>

	<div class="hidden lfr-definition-virtual-url">
		<aui:input name="url" />
	</div>
</aui:fieldset>

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

								var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />fileEntrySearchContainer');

								var rowColumns = [];

								rowColumns.push(value.title);

								rowColumns.push('<a class="modify-file-entry-link" data-rowId="' + value.fileEntryId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeFileEntryIcon) %></a>');

								searchContainer.addRow(rowColumns, value.fileEntryId);

								searchContainer.updateDataStore();

								$('#<portlet:namespace />selectFile').addClass('hidden');
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
		selectFileType(A);

		A.one('#<portlet:namespace/>useUrl').on('click',function(b) {
			selectFileType(A);
		})
	});

	function selectFileType(A) {
		var urlCheckbox = A.one('#<portlet:namespace/>useUrl');

		if (urlCheckbox.attr('checked')) {
			A.one('.lfr-definition-virtual-setting-file-selector').addClass('hidden');
			A.one('.lfr-definition-virtual-url').removeClass('hidden');
		}
		else {
			A.one('.lfr-definition-virtual-setting-file-selector').removeClass('hidden');
			A.one('.lfr-definition-virtual-url').addClass('hidden');
		}
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />fileEntrySearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			searchContainer.updateDataStore();

			$('#<portlet:namespace />selectFile').removeClass('hidden');
		},
		'.modify-file-entry-link'
	);
</aui:script>