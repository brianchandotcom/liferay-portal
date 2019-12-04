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
OpenGraphSettingsDisplayContext openGraphSettingsDisplayContext = (OpenGraphSettingsDisplayContext)request.getAttribute(OpenGraphSettingsDisplayContext.class.getName());
%>

<div class="form-group" id="<portlet:namespace />idOptions">
	<aui:input id="openGraphEnabled" label="enable-open-graph" name="openGraphEnabled" type="checkbox" value="<%= openGraphSettingsDisplayContext.isOpenGraphEnabled() %>" />
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

<div class="form-group">
	<label class="control-label"><liferay-ui:message key="image" /></label>

	<aui:input label="<%= StringPool.BLANK %>" name="openGraphImageURL" placeholder="image" readonly="<%= true %>" type="text" value="<%= openGraphSettingsDisplayContext.getOpenGraphImageURL() %>" wrapperCssClass="mb-3" />

	<aui:button-row cssClass="mt-0">
		<aui:button name="openGraphImageButton" value="select" />
		<aui:button name="openGraphClearImageButton" value="clear" />
	</aui:button-row>
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
	var openGraphImageFileEntryId = document.getElementById(
		'<portlet:namespace />openGraphImageFileEntryId'
	);
	var openGraphImageURL = document.getElementById(
		'<portlet:namespace />openGraphImageURL'
	);

	if (openGraphImageButton) {
		var itemSelectorDialog = new ItemSelectorDialog.default({
			buttonAddLabel: '<liferay-ui:message key="done" />',
			eventName: '<portlet:namespace />openGraphImageSelectedItem',
			singleSelect: true,
			title: '<liferay-ui:message key="open-graph-image" />',
			url: '<%= openGraphSettingsDisplayContext.getItemSelectorURL() %>'
		});

		itemSelectorDialog.on('selectedItemChange', function(event) {
			var selectedItem = event.selectedItem;

			if (selectedItem) {
				var itemValue = JSON.parse(selectedItem.value);

				if (openGraphImageFileEntryId) {
					openGraphImageFileEntryId.value = itemValue.fileEntryId;
				}

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

	var openGraphClearImageButton = document.getElementById(
		'<portlet:namespace />openGraphClearImageButton'
	);

	openGraphClearImageButton.addEventListener('click', function() {
		openGraphImageFileEntryId.value = '';
		openGraphImageURL.value = '';
	});

	var openGraphEnabledCheck = document.getElementById(
		'<portlet:namespace />openGraphEnabled'
	);

	if (openGraphEnabledCheck && openGraphImageButton) {
		openGraphEnabledCheck.addEventListener('click', function(event) {
			var disabled = !event.target.checked;

			Liferay.Util.toggleDisabled(
				openGraphImageURL,
				disabled
			);
			Liferay.Util.toggleDisabled(
				openGraphImageButton,
				disabled
			);
			Liferay.Util.toggleDisabled(
				openGraphClearImageButton,
				disabled
			);
		});
	}
</aui:script>