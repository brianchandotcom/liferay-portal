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

SearchContainer<FileEntry> sampleFileEntrySearchContainer = cpDefinitionVirtualSettingDisplayContext.getSampleFileEntrySearchContainer();

boolean useSample = BeanParamUtil.getBoolean(cpDefinitionVirtualSetting, request, "useSample");

String sampleButtonCssClass = "modify-sample-file-entry-link ";

boolean useSampleUrl = ParamUtil.getBoolean(request, "useSampleUrl", false);

if ((cpDefinitionVirtualSetting != null) && Validator.isNotNull(cpDefinitionVirtualSetting.getSampleUrl())) {
	useSampleUrl = true;
}

if (sampleFileEntrySearchContainer.hasResults()) {
	sampleButtonCssClass += "hidden";
}
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="sample" />

<aui:model-context bean="<%= cpDefinitionVirtualSetting %>" model="<%= CPDefinitionVirtualSetting.class %>" />

<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleFileEntryIdException.class %>" message="please-select-an-existing-sample-file" />
<liferay-ui:error exception="<%= CPDefinitionVirtualSettingSampleUrlException.class %>" message="please-enter-a-valid-sample-url" />

<liferay-util:buffer var="removeSampleFileEntryIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<div class="lfr-definition-virtual-setting-use-sample-header">
	<aui:fieldset>
		<aui:input name="useSample" />
	</aui:fieldset>
</div>

<div class="lfr-definition-virtual-setting-use-sample-content">
	<aui:fieldset>
		<aui:input checked="<%= useSampleUrl %>" cssClass="lfr-definition-virtual-setting-sample-type" label="use-sample-url" name="useSampleUrl" type="checkbox" />

		<div class="lfr-definition-virtual-setting-sample-file-selector">
			<liferay-ui:search-container
				cssClass="lfr-definition-virtual-setting-sample-file-entry"
				curParam="curSampleFileEntry"
				headerNames="title,null"
				id="sampleFileEntrySearchContainer"
				iteratorURL="<%= currentURLObj %>"
				searchContainer="<%= sampleFileEntrySearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.repository.model.FileEntry"
					keyProperty="fileEntryId"
					modelVar="sampleFileEntry"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="title"
					>
						<liferay-ui:icon
							iconCssClass="icon-ok-sign"
							label="<%= true %>"
							message="<%= HtmlUtil.escape(sampleFileEntry.getTitle()) %>"
							url="<%= cpDefinitionVirtualSettingDisplayContext.getDownloadSampleFileEntryURL() %>"
						/>
					</liferay-ui:search-container-column-text>

					<c:if test="<%= Validator.isNotNull(cpDefinitionVirtualSetting) %>">
						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="size"
							value="<%= TextFormatter.formatStorageSize(sampleFileEntry.getSize(), locale) %>"
						/>
					</c:if>

					<liferay-ui:search-container-column-text>
						<a class="modify-sample-file-entry-link" data-rowId="<%= sampleFileEntry.getFileEntryId() %>" href="javascript:;"><%= removeSampleFileEntryIcon %></a>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator markupView="lexicon" searchContainer="<%= sampleFileEntrySearchContainer %>" />
			</liferay-ui:search-container>

			<aui:button cssClass="<%= sampleButtonCssClass %>" name="selectSampleFile" value="select-file" />
		</div>

		<div class="hidden lfr-definition-virtual-sample-url">
			<aui:input name="sampleUrl" />
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

								var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />sampleFileEntrySearchContainer');

								var rowColumns = [];

								rowColumns.push(value.title);

								rowColumns.push('<a class="modify-sample-file-entry-link" data-rowId="' + value.fileEntryId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeSampleFileEntryIcon) %></a>');

								searchContainer.addRow(rowColumns, value.fileEntryId);

								searchContainer.updateDataStore();

								$('#<portlet:namespace />selectSampleFile').addClass('hidden');
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

<aui:script use="aui-toggler">
	new A.Toggler(
		{
			animated: true,
			content: '#<portlet:namespace />fileEntryContainer .lfr-definition-virtual-setting-use-sample-content',
			expanded: <%= useSample %>,
			header: '#<portlet:namespace />fileEntryContainer .lfr-definition-virtual-setting-use-sample-header',
			on: {
				animatingChange: function(event) {
					var instance = this;

					var expanded = !instance.get('expanded');

					A.one('#<portlet:namespace />useSample').attr('checked', expanded);
				}
			}
		}
	);
</aui:script>

<aui:script>
	AUI().ready('node', 'event', function(A) {
		selectSampleFileType(A);

		A.one('#<portlet:namespace/>useSampleUrl').on('click',function(b) {
			selectSampleFileType(A);
		})
	});

	function selectSampleFileType(A) {
		var sampleUrlCheckbox = A.one('#<portlet:namespace/>useSampleUrl');

		if (sampleUrlCheckbox.attr('checked')) {
			A.one('.lfr-definition-virtual-setting-sample-file-selector').addClass('hidden');
			A.one('.lfr-definition-virtual-sample-url').removeClass('hidden');
		}
		else {
			A.one('.lfr-definition-virtual-setting-sample-file-selector').removeClass('hidden');
			A.one('.lfr-definition-virtual-sample-url').addClass('hidden');
		}
	}
</aui:script>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />sampleFileEntrySearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			searchContainer.updateDataStore();

			$('#<portlet:namespace />selectSampleFile').removeClass('hidden');
		},
		'.modify-sample-file-entry-link'
	);
</aui:script>