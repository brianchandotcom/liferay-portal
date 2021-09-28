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

<aui:input id="pagesContainerInput" name="layoutUuid" type="hidden" value="<%= selectAssetDisplayPageDisplayContext.getLayoutUuid() %>" />

<aui:input id="assetDisplayPageIdInput" name="assetDisplayPageId" type="hidden" value="<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageId() %>" />

<aui:select label="display-page-template" name="displayPageType" title="display-page-template-type" value="<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageType() %>">
	<aui:option label="default" value="<%= AssetDisplayPageConstants.TYPE_DEFAULT %>" />
	<aui:option label="specific" value="<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>" />

	<c:if test="<%= selectAssetDisplayPageDisplayContext.inheritableDisplayPageTemplate() %>">
		<aui:option label="inherited" value="<%= AssetDisplayPageConstants.TYPE_INHERITED %>" />
	</c:if>

	<aui:option label="none" value="<%= AssetDisplayPageConstants.TYPE_NONE %>" />
</aui:select>

<div class="input-group <%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeDefault() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />defaultDisplayPageNameContainer">

	<%
	String defaultAssetDisplayPageName = selectAssetDisplayPageDisplayContext.getDefaultAssetDisplayPageName();
	%>

	<div class="input-group-item">
		<input class="field form-control lfr-input-text" id="<portlet:namespace />defaultDisplayPageNameInput" readonly="readonly" title="<%= LanguageUtil.get(resourceBundle, "default-display-page-template") %>" type="text" value="<%= Validator.isNotNull(defaultAssetDisplayPageName) ? defaultAssetDisplayPageName : LanguageUtil.get(resourceBundle, "no-default-display-page-template") %>" />
	</div>

	<c:if test="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeDefault() && selectAssetDisplayPageDisplayContext.isShowViewInContextLink() && selectAssetDisplayPageDisplayContext.isURLViewInContext() %>">
		<div class="input-group-item input-group-item-shrink">
			<clay:button
				displayType="secondary"
				icon="view"
				id='<%= liferayPortletResponse.getNamespace() + "previewDefaultDisplayPageButton" %>'
				type="button"
			/>
		</div>
	</c:if>
</div>

<div class="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeSpecific() ? StringPool.BLANK : "hide" %>" id="<portlet:namespace />specificDisplayPageNameContainer">
	<div class="input-group mb-2">

		<%
		String specificAssetDisplayPageName = selectAssetDisplayPageDisplayContext.getSpecificAssetDisplayPageName();
		%>

		<div class="input-group-item">
			<input class="field form-control lfr-input-text" id="<portlet:namespace />specificDisplayPageNameInput" readonly="readonly" title="<%= LanguageUtil.get(resourceBundle, "specific-display-page-template") %>" type="text" value="<%= Validator.isNotNull(specificAssetDisplayPageName) ? specificAssetDisplayPageName : LanguageUtil.get(resourceBundle, "no-display-page-template-selected") %>" />
		</div>

		<c:if test="<%= selectAssetDisplayPageDisplayContext.isAssetDisplayPageTypeSpecific() && selectAssetDisplayPageDisplayContext.isShowViewInContextLink() && selectAssetDisplayPageDisplayContext.isURLViewInContext() %>">
			<div class="input-group-item input-group-item-shrink">
				<clay:button
					displayType="secondary"
					icon="view"
					id='<%= liferayPortletResponse.getNamespace() + "previewSpecificDisplayPageButton" %>'
					type="button"
				/>
			</div>
		</c:if>
	</div>

	<div class="btn-group">
		<aui:button name="chooseSpecificDisplayPage" value="select" />
	</div>
</div>

<aui:script sandbox="<%= true %>">
	var assetDisplayPageIdInput = document.getElementById(
		'<portlet:namespace />assetDisplayPageIdInput'
	);
	var chooseSpecificDisplayPage = document.getElementById(
		'<portlet:namespace />chooseSpecificDisplayPage'
	);
	var pagesContainerInput = document.getElementById(
		'<portlet:namespace />pagesContainerInput'
	);
	var previewSpecificDisplayPageButton = document.getElementById(
		'<portlet:namespace />previewSpecificDisplayPageButton'
	);
	var specificDisplayPageNameInput = document.getElementById(
		'<portlet:namespace />specificDisplayPageNameInput'
	);

	chooseSpecificDisplayPage.addEventListener('click', (event) => {
		Liferay.Util.openSelectionModal({
			onSelect: function (selectedItem) {
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

					if (previewSpecificDisplayPageButton) {
						previewSpecificDisplayPageButton.parentNode.remove();
					}
				}
			},
			selectEventName:
				'<%= selectAssetDisplayPageDisplayContext.getEventName() %>',
			title: '<liferay-ui:message key="select-page" />',
			url:
				'<%= selectAssetDisplayPageDisplayContext.getAssetDisplayPageItemSelectorURL() %>',
		});
	});

	var previewDefaultDisplayPageButton = document.getElementById(
		'<portlet:namespace />previewDefaultDisplayPageButton'
	);

	if (previewDefaultDisplayPageButton) {
		previewDefaultDisplayPageButton.addEventListener('click', (event) => {
			Liferay.Util.openModal({
				title: '<liferay-ui:message key="preview" />',
				url:
					'<%= selectAssetDisplayPageDisplayContext.getURLViewInContext() %>',
			});
		});
	}

	if (previewSpecificDisplayPageButton) {
		previewSpecificDisplayPageButton.addEventListener('click', (event) => {
			Liferay.Util.openModal({
				title: '<liferay-ui:message key="preview" />',
				url:
					'<%= selectAssetDisplayPageDisplayContext.getURLViewInContext() %>',
			});
		});
	}

	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />displayPageType',
		'<%= AssetDisplayPageConstants.TYPE_DEFAULT %>',
		'<portlet:namespace />defaultDisplayPageNameContainer'
	);
	Liferay.Util.toggleSelectBox(
		'<portlet:namespace />displayPageType',
		'<%= AssetDisplayPageConstants.TYPE_SPECIFIC %>',
		'<portlet:namespace />specificDisplayPageNameContainer'
	);
</aui:script>