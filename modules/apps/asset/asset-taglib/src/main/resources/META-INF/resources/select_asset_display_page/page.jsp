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

<%@ include file="/select_asset_display_page/init.jsp" %>

<aui:input id="pagesContainerInput" ignoreRequestValue="<%= true %>" name="layoutUuid" type="hidden" value="<%= selectAssetDisplayPageDisplayContext.getLayoutUuid() %>" />

<aui:input id="assetDisplayPageIdInput" ignoreRequestValue="<%= true %>" name="assetDisplayPageId" type="hidden" value="<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageId() %>" />

<aui:select label="" name="displayPageType" value="<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageType() %>">
	<aui:option label="default-display-page" value="<%= AssetDisplayPageConstants.TYPE_DEFAULT %>" />
	<aui:option label="specific-display-page" value="<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>" />
	<aui:option label="no-display-page" value="<%= AssetDisplayPageConstants.TYPE_NONE %>" />
</aui:select>

<div class="input-group <%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeDefault() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />defaultDisplayPageNameContainer">

	<%
	String defaultAssetDisplayPageName = selectAssetDisplayPageDisplayContext.getDefaultAssetDisplayPageName();
	%>

	<aui:input disabled="<%= true %>" label="" name="defaultDisplayPageNameInput" value='<%= Validator.isNotNull(defaultAssetDisplayPageName) ? defaultAssetDisplayPageName : LanguageUtil.get(resourceBundle, "no-default-display-page") %>' wrapperCssClass="input-group-item mb-0" />

	<c:if test="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeDefault() && selectAssetDisplayPageDisplayContext.isShowViewInContextLink() && selectAssetDisplayPageDisplayContext.isURLViewInContext() %>">
		<clay:button
			elementClasses="ml-1"
			icon="view"
			id='<%= liferayPortletResponse.getNamespace() + "previewDefaultDisplayPageButton" %>'
			size="sm"
			style="secondary"
		/>
	</c:if>
</div>

<div class="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeSpecific() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />specificDisplayPageNameContainer">
	<div class="input-group">

		<%
		String specificAssetDisplayPageName = selectAssetDisplayPageDisplayContext.getSpecificAssetDisplayPageName();
		%>

		<aui:input disabled="<%= true %>" label="" name="specificDisplayPageNameInput" value='<%= Validator.isNotNull(specificAssetDisplayPageName) ? specificAssetDisplayPageName : LanguageUtil.get(resourceBundle, "no-display-page-selected") %>' wrapperCssClass="input-group-item mb-0" />

		<c:if test="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeSpecific() && selectAssetDisplayPageDisplayContext.isShowViewInContextLink() && selectAssetDisplayPageDisplayContext.isURLViewInContext() %>">
			<clay:button
				elementClasses="ml-1"
				icon="view"
				id='<%= liferayPortletResponse.getNamespace() + "previewSpecificDisplayPageButton" %>'
				size="sm"
				style="secondary"
			/>
		</c:if>
	</div>

	<div class="button-holder">
		<aui:button name="chooseSpecificDisplayPage" value="choose" />
	</div>
</div>

<aui:script use="liferay-item-selector-dialog">
	var assetDisplayPageIdInput = document.getElementById('<portlet:namespace />assetDisplayPageIdInput');
	var chooseSpecificDisplayPage = document.getElementById('<portlet:namespace />chooseSpecificDisplayPage');
	var pagesContainerInput = document.getElementById('<portlet:namespace />pagesContainerInput');
	var specificDisplayPageNameInput = document.getElementById('<portlet:namespace />specificDisplayPageNameInput');

	chooseSpecificDisplayPage.addEventListener(
		'click',
		function(event) {
			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: '<%= selectAssetDisplayPageDisplayContext.getEventName() %>',
					on: {
						selectedItemChange: function(event) {
							var selectedItem = event.newVal;

							assetDisplayPageIdInput.value = '';

							pagesContainerInput.value = '';

							if (selectedItem) {
								if (selectedItem.type === 'asset-display-page') {
									assetDisplayPageIdInput.value = selectedItem.id;
								}
								else {
									pagesContainerInput.value = selectedItem.id;
								}

								specificDisplayPageNameInput.value = selectedItem.name;
							}
						}
					},
					'strings.add': '<liferay-ui:message key="done" />',
					title: '<liferay-ui:message key="select-page" />',
					url: '<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageItemSelectorURL() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);

	var previewDefaultDisplayPageButton = document.getElementById('<portlet:namespace />previewDefaultDisplayPageButton');

	if (previewDefaultDisplayPageButton) {
		previewDefaultDisplayPageButton.addEventListener(
			'click',
			function(event) {
				Liferay.Util.openWindow(
					{
						dialog: {
							destroyOnHide: true
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						},
						title: '<liferay-ui:message key="preview" />',
						uri: '<%= selectAssetDisplayPageDisplayContext.getURLViewInContext() %>'
					}
				);
			}
		);
	}

	var previewSpecificDisplayPageButton = document.getElementById('<portlet:namespace />previewSpecificDisplayPageButton');

	if (previewSpecificDisplayPageButton) {
		previewSpecificDisplayPageButton.addEventListener(
			'click',
			function(event) {
				Liferay.Util.openWindow(
					{
						dialog: {
							destroyOnHide: true
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						},
						title: '<liferay-ui:message key="preview" />',
						uri: '<%= selectAssetDisplayPageDisplayContext.getURLViewInContext() %>'
					}
				);
			}
		);
	}

	Liferay.Util.toggleSelectBox('<portlet:namespace />displayPageType', '<%= AssetDisplayPageConstants.TYPE_DEFAULT %>', '<portlet:namespace />defaultDisplayPageNameContainer');
	Liferay.Util.toggleSelectBox('<portlet:namespace />displayPageType', '<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>', '<portlet:namespace />specificDisplayPageNameContainer');
</aui:script>