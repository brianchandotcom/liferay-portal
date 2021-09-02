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
AssetListEntry assetListEntry = assetPublisherDisplayContext.fetchAssetListEntry();
%>

<aui:input id="assetListEntryId" name="preferences--assetListEntryId--" type="hidden" value="<%= (assetListEntry != null) ? assetListEntry.getAssetListEntryId() : StringPool.BLANK %>" />
<aui:input id="infoListProviderKey" name="preferences--infoListProviderKey--" type="hidden" value="<%= assetPublisherDisplayContext.getInfoListProviderKey() %>" />

<div class="form-group input-text-wrapper text-default" id="<portlet:namespace />title">
	<c:choose>
		<c:when test="<%= assetListEntry != null %>">
			<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
		</c:when>
		<c:when test="<%= Validator.isNotNull(assetPublisherDisplayContext.getInfoListProviderKey()) %>">
			<%= assetPublisherDisplayContext.getInfoListProviderLabel() %>
		</c:when>
		<c:otherwise>
			<span class="text-muted"><liferay-ui:message key="none" /></span>
		</c:otherwise>
	</c:choose>
</div>

<div class="button-row">
	<aui:button cssClass="mr-2" name="selectAssetListButton" value="select" />

	<aui:button name="clearAssetListButton" value="clear" />
</div>

<aui:script sandbox="<%= true %>">
	var assetListEntryId = document.getElementById(
		'<portlet:namespace />assetListEntryId'
	);
	var infoListProviderKey = document.getElementById(
		'<portlet:namespace />infoListProviderKey'
	);
	var title = document.getElementById('<portlet:namespace />title');

	var selectAssetListButton = document.getElementById(
		'<portlet:namespace />selectAssetListButton'
	);

	if (selectAssetListButton) {
		selectAssetListButton.addEventListener('click', () => {
			Liferay.Util.openSelectionModal({
				onSelect: function (selectedItem) {
					if (selectedItem) {
						if (
							selectedItem.returnType ===
							'<%= InfoListItemSelectorReturnType.class.getName() %>'
						) {
							var itemValue = JSON.parse(selectedItem.value);

							assetListEntryId.value = itemValue.classPK;

							title.innerHTML = itemValue.title;

							infoListProviderKey.value = '';
						}
						else if (
							selectedItem.returnType ===
							'<%= InfoListProviderItemSelectorReturnType.class.getName() %>'
						) {
							var itemValue = JSON.parse(selectedItem.value);

							title.innerHTML = itemValue.title;

							infoListProviderKey.value = itemValue.key;

							assetListEntryId.value = '';
						}
					}
				},
				selectEventName:
					'<%= assetPublisherDisplayContext.getSelectAssetListEventName() %>',
				title: '<liferay-ui:message key="select-collection" />',
				url:
					'<%= assetPublisherDisplayContext.getAssetListSelectorURL() %>',
			});
		});
	}

	var clearAssetListButton = document.getElementById(
		'<portlet:namespace />clearAssetListButton'
	);

	if (clearAssetListButton) {
		clearAssetListButton.addEventListener('click', (event) => {
			title.innerHTML = '<liferay-ui:message key="none" />';

			assetListEntryId.value = '';
			infoListProviderKey.value = '';
		});
	}
</aui:script>