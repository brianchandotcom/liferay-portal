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

<div class="form-group input-text-wrapper text-default" id="<portlet:namespace />assetListTitle">
	<c:choose>
		<c:when test="<%= assetListEntry != null %>">
			<%= HtmlUtil.escape(assetListEntry.getTitle()) %>
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

<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var assetListEntryId = document.getElementById(
		'<portlet:namespace />assetListEntryId'
	);
	var assetListTitle = document.getElementById(
		'<portlet:namespace />assetListTitle'
	);

	var selectAssetListButton = document.getElementById(
		'<portlet:namespace />selectAssetListButton'
	);

	if (selectAssetListButton) {
		selectAssetListButton.addEventListener('click', function(event) {
			var itemSelectorDialog = new ItemSelectorDialog.default({
				eventName:
					'<%= assetPublisherDisplayContext.getSelectAssetListEventName() %>',
				singleSelect: true,
				title: '<liferay-ui:message key="select-content-set" />',
				url: '<%= assetPublisherDisplayContext.getAssetListSelectorURL() %>'
			});

			itemSelectorDialog.on('selectedItemChange', function(event) {
				if (event.selectedItem) {
					var itemValue = JSON.parse(event.selectedItem.value);

					assetListEntryId.value = itemValue.assetListEntryId;

					assetListTitle.innerHTML = itemValue.assetListEntryTitle;
				}
			});

			itemSelectorDialog.open();
		});
	}

	var clearAssetListButton = document.getElementById(
		'<portlet:namespace />clearAssetListButton'
	);

	if (clearAssetListButton) {
		clearAssetListButton.addEventListener('click', function(event) {
			assetListTitle.innerHTML = '<liferay-ui:message key="none" />';

			assetListEntryId.value = '';
		});
	}
</aui:script>