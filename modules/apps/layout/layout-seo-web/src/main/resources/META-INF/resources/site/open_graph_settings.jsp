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

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="form-group" id="<portlet:namespace />idOptions">
	<aui:input id="openGraphEnabled" label="enable-open-graph" name="openGraphEnabled" type="checkbox" value="<%= openGraphSettingsDisplayContext.isOpenGraphEnabled() %>" />
</div>

<p class="text-muted">
	<liferay-ui:message key="enable-open-graph-description" />
</p>

<div class="open-graph-settings <%= openGraphSettingsDisplayContext.isOpenGraphEnabled() ? "" : "disabled" %>" id="<portlet:namespace />openGraphSettings">
	<h4 class="sheet-tertiary-title">
		<liferay-ui:message key="open-graph-image" />
	</h4>

	<p class="text-muted">
		<liferay-ui:message key="open-graph-image-description" />
	</p>

	<div class="form-group">
		<label class="control-label"><liferay-ui:message key="image" /></label>

		<aui:input disabled="<%= !openGraphSettingsDisplayContext.isOpenGraphEnabled() %>" label="<%= StringPool.BLANK %>" name="openGraphImageTitle" placeholder="image" readonly="<%= true %>" type="text" value="<%= openGraphSettingsDisplayContext.getOpenGraphImageTitle() %>" wrapperCssClass="mb-3" />

		<aui:button-row cssClass="mt-0">
			<aui:button name="openGraphImageButton" value="select" />
			<aui:button name="openGraphClearImageButton" value="clear" />
		</aui:button-row>

		<label class="control-label"><liferay-ui:message key="open-graph-image-alt-description" /></label>

		<%
		LayoutSEOSite layoutSEOSite = openGraphSettingsDisplayContext.getLayoutSEOSite();
		%>

		<c:if test="<%= layoutSEOSite != null %>">
			<aui:model-context bean="<%= layoutSEOSite %>" model="<%= LayoutSEOSite.class %>" />
		</c:if>

		<aui:input disabled="<%= !openGraphSettingsDisplayContext.isOpenGraphEnabled() || Validator.isNull(openGraphSettingsDisplayContext.getOpenGraphImageURL()) %>" id="openGraphImageAlt" label="<%= StringPool.BLANK %>" localized="<%= true %>" name="openGraphImageAlt" placeholder="open-graph-alt-description" type="text" />
	</div>

	<aui:input id="openGraphImageFileEntryId" name="openGraphImageFileEntryId" type="hidden" value="<%= openGraphSettingsDisplayContext.getOpenGraphImageFileEntryId() %>" />

	<div class="form-group">
		<label><liferay-ui:message key="preview" /></label>

		<div class="preview-seo preview-seo-og" dir="ltr">
			<div class="aspect-ratio aspect-ratio-191-to-100 bg-light preview-seo-image">
				<div class="preview-seo-placeholder" id="<portlet:namespace />openGraphPreviewPlaceholder">
					<liferay-ui:icon
						icon="picture"
						iconCssClass="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-fluid preview-seo-placeholder-icon"
						markupView="lexicon"
					/>

					<div class="preview-seo-placeholder-text"><liferay-ui:message key="open-graph-image-placeholder" /></div>
				</div>

				<img alt="" class="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-flush <%= Validator.isNull(openGraphSettingsDisplayContext.getOpenGraphImageURL()) ? "hide" : "" %>" id="<portlet:namespace />openGraphPreviewImage" src="<%= openGraphSettingsDisplayContext.getOpenGraphImageURL() %>" />
			</div>
		</div>
	</div>
</div>

<portlet:actionURL name="/site/upload_open_graph_image" var="uploadOpenGraphImageURL" />

<aui:script require="frontend-js-web/liferay/ItemSelectorDialog.es as ItemSelectorDialog">
	var openGraphImageButton = document.getElementById(
		'<portlet:namespace />openGraphImageButton'
	);

	var itemSelectorDialog = new ItemSelectorDialog.default({
		buttonAddLabel: '<liferay-ui:message key="done" />',
		eventName: '<portlet:namespace />openGraphImageSelectedItem',
		singleSelect: true,
		title: '<liferay-ui:message key="open-graph-image" />',
		url: '<%= openGraphSettingsDisplayContext.getItemSelectorURL() %>'
	});

	var openGraphImageFileEntryId = document.getElementById(
		'<portlet:namespace />openGraphImageFileEntryId'
	);
	var openGraphImageTitle = document.getElementById(
		'<portlet:namespace />openGraphImageTitle'
	);
	var openGraphPreviewImage = document.getElementById(
		'<portlet:namespace />openGraphPreviewImage'
	);

	var openGraphImageAltField = document.getElementById(
		'<portlet:namespace />openGraphImageAlt'
	);
	var openGraphImageAltFieldDefaultLocale = document.getElementById(
		'<portlet:namespace />openGraphImageAlt_<%= themeDisplay.getLanguageId() %>'
	);

	itemSelectorDialog.on('selectedItemChange', function(event) {
		var selectedItem = event.selectedItem;

		if (selectedItem) {
			var itemValue = JSON.parse(selectedItem.value);

			openGraphImageFileEntryId.value = itemValue.fileEntryId;
			openGraphImageTitle.value = itemValue.title;
			openGraphPreviewImage.src = itemValue.url;

			Liferay.Util.toggleDisabled(openGraphImageAltField, false);
			Liferay.Util.toggleDisabled(openGraphImageAltFieldDefaultLocale, false);

			openGraphPreviewImage.classList.remove('hide');
		}
	});

	openGraphImageButton.addEventListener('click', function(event) {
		event.preventDefault();
		itemSelectorDialog.open();
	});

	var openGraphClearImageButton = document.getElementById(
		'<portlet:namespace />openGraphClearImageButton'
	);

	openGraphClearImageButton.addEventListener('click', function() {
		openGraphImageFileEntryId.value = '';
		openGraphImageTitle.value = '';
		openGraphPreviewImage.src = '';

		Liferay.Util.toggleDisabled(openGraphImageAltField, true);
		Liferay.Util.toggleDisabled(openGraphImageAltFieldDefaultLocale, true);

		openGraphPreviewImage.classList.add('hide');
	});

	var openGraphEnabledCheck = document.getElementById(
		'<portlet:namespace />openGraphEnabled'
	);
	var openGraphSettings = document.getElementById(
		'<portlet:namespace />openGraphSettings'
	);

	openGraphEnabledCheck.addEventListener('click', function(event) {
		var disabled = !event.target.checked;

		Liferay.Util.toggleDisabled(openGraphImageTitle, disabled);
		Liferay.Util.toggleDisabled(openGraphImageButton, disabled);
		Liferay.Util.toggleDisabled(openGraphClearImageButton, disabled);

		Liferay.Util.toggleDisabled(openGraphImageAltField, disabled);
		Liferay.Util.toggleDisabled(openGraphImageAltFieldDefaultLocale, disabled);

		openGraphSettings.classList.toggle('disabled');
	});
</aui:script>