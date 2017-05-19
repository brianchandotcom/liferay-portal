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

CPDefinitionVirtualSettingDisplayContext cpDefinitionVirtualSettingDisplayContext = (CPDefinitionVirtualSettingDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<FileEntry> fileEntrySearchContainer = cpDefinitionVirtualSettingDisplayContext.getSearchContainer();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-util:buffer var="removeFileEntryIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<div class="lfr-definition-virtual-setting-file-selector">
	<aui:fieldset>

		<%
		boolean useFileEntry = true;

		if ((cpDefinitionVirtualSetting != null) && Validator.isNull(cpDefinitionVirtualSetting.getFileEntryId())) {
			useFileEntry = false;
		}
		%>

		<aui:input checked="<%= useFileEntry %>" cssClass="lfr-definition-virtual-setting-type" label="use-file" name="useFileEntry" type="radio" />

		<%
		String fileEntryContainerCssClass = "lfr-definition-virtual-setting-value ";

		if ((cpDefinitionVirtualSetting != null) && (cpDefinitionVirtualSetting.getFileEntryId() <= 0)) {
			fileEntryContainerCssClass += "hidden";
		}
		%>

		<div class="<%= fileEntryContainerCssClass %>">
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

			<%
			String cssClass = "modify-file-entry-link ";

			if (fileEntrySearchContainer.hasResults()) {
				cssClass += "hidden";
			}
			%>

			<aui:button cssClass="<%= cssClass %>" name="selectFile" value="select-file" />
		</div>
	</aui:fieldset>

	<aui:fieldset>

		<%
		boolean useUrl = false;

		String urlContainerCssClass = "hidden lfr-definition-virtual-setting-value";

		if ((cpDefinitionVirtualSetting != null) && Validator.isNotNull(cpDefinitionVirtualSetting.getUrl())) {
			useUrl = true;

			urlContainerCssClass = "lfr-definition-virtual-setting-value";
		}
		%>

		<aui:input checked="<%= useUrl %>" cssClass="lfr-definition-virtual-setting-type" label="use-url" name="useUrl" type="radio" />

		<div class="<%= urlContainerCssClass %>">
			<aui:input name="url" />
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
					url: '<%= cpDefinitionVirtualSettingDisplayContext.getItemSelectorURL() %>'
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

		values.addClass('hidden');

		values.item(index).removeClass('hidden');

		types.each(
			function(index) {
				if (types.item(index).get('checked')) {
					values.item(index).addClass('hidden');
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