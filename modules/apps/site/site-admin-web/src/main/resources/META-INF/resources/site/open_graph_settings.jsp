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

<div class="form-group" id="<portlet:namespace />idOptions">
	<aui:input id="openGraphEnabled" label="enable-open-graph" name="openGraphEnabled" type="checkbox" value="<%= siteAdminDisplayContext.isOpenGraphEnabled() %>" />
</div>

<p class="text-muted">
	<liferay-ui:message key="enable-open-graph-description" />
</p>

<h4 class="sheet-tertiary-title">
	<liferay-ui:message key="open-graph-image" />
</h4>

<p class="text-muted">
	<liferay-ui:message key="open-graph-image-description" />
</p>

<div>
	<label class="control-label"><liferay-ui:message key="image" /></label>

	<div class="input-group">
		<div class="input-group-item">
			<aui:input disabled="<%= true %>" label="<%= StringPool.BLANK %>" name="openGraphImageURL" placeholder="image" type="text" value="<%= siteAdminDisplayContext.getOpenGraphImageURL() %>" wrapperCssClass="w-100" />
		</div>

		<div class="input-group-item input-group-item-shrink">
			<aui:button name="openGraphImageButton" value="select" />
		</div>
	</div>
</div>

<div id="<portlet:namespace />openGraphSettings">
	<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" />
</div>

<div class="form-group">
	<label><liferay-ui:message key="preview" /></label>
</div>

<portlet:actionURL name="/site/upload_open_graph_image" var="uploadOpenGraphImageURL" />

<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var openGraphImageButton = document.getElementById(
		'<portlet:namespace />openGraphImageButton'
	);

	if (openGraphImageButton) {
		var itemSelectorDialog = new ItemSelectorDialog.default({
			buttonAddLabel: '<liferay-ui:message key="done" />',
			eventName: '<portlet:namespace />openGraphImageSelectedItem',
			title: '<liferay-ui:message key="open-graph-image" />',
			url: '<%= siteAdminDisplayContext.getItemSelectorURL() %>'
		});

		itemSelectorDialog.on('selectedItemChange', function(event) {
			var selectedItem = event.selectedItem;

			if (selectedItem) {
				var itemValue = JSON.parse(selectedItem.value);

				var openGraphImageFileEntryId = document.getElementById(
					'<portlet:namespace />openGraphImageFileEntryId'
				);

				if (openGraphImageFileEntryId) {
					openGraphImageFileEntryId.value = itemValue.fileEntryId;
				}

				var openGraphImageURL = document.getElementById(
					'<portlet:namespace />openGraphImageURL'
				);

				if (openGraphImageURL) {
					openGraphImageURL.value = itemValue.url;
				}
			}
		});

		openGraphImageButton.addEventListener('click', function(event) {
			event.preventDefault();
			itemSelectorDialog.open();
		});
	}
	var openGraphEnabledCheck = document.getElementById(
		'<portlet:namespace />openGraphEnabled'
	);

	if (openGraphEnabledCheck && openGraphImageButton) {
		openGraphEnabledCheck.addEventListener('click', function(event) {
			Liferay.Util.toggleDisabled(
				openGraphImageButton,
				!event.target.checked
			);
		});
	}
</aui:script>