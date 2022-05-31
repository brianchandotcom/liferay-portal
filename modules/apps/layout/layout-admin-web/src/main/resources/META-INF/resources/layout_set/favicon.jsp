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
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();
%>

<div class="form-group">
	<img alt="<%= HtmlUtil.escape(layoutsAdminDisplayContext.getFaviconTitle()) %>" class="mb-2" height="16" id="<portlet:namespace />faviconFileEntryImage" src="<%= layoutsAdminDisplayContext.getFaviconImage() %>" width="16" />

	<p>
		<b><liferay-ui:message key="favicon-name" />:</b> <span id="<portlet:namespace />faviconFileEntryTitle"><%= layoutsAdminDisplayContext.getFaviconTitle() %></span>
	</p>

	<aui:input name="faviconClientExtensionEntryId" type="hidden" />
	<aui:input name="faviconClientExtensionEntryType" type="hidden" />
	<aui:input name="faviconFileEntryId" type="hidden" value="<%= selLayoutSet.getFaviconFileEntryId() %>" />

	<aui:button name="selectFaviconButton" value="change-favicon" />

	<aui:button disabled="<%= !layoutsAdminDisplayContext.isClearFaviconButtonEnabled() %>" name="clearFaviconButton" value="clear" />

	<aui:script sandbox="<%= true %>">
		const selectLayoutButton = document.getElementById(
			'<portlet:namespace />selectFaviconButton'
		);

		selectLayoutButton.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				onSelect: function (selectedItem) {
					const faviconClientExtensionEntryId = document.getElementById(
						'<portlet:namespace />faviconClientExtensionEntryId'
					);

					const faviconClientExtensionEntryType = document.getElementById(
						'<portlet:namespace />faviconClientExtensionEntryType'
					);

					const faviconFileEntryId = document.getElementById(
						'<portlet:namespace />faviconFileEntryId'
					);
					const faviconFileEntryImage = document.getElementById(
						'<portlet:namespace />faviconFileEntryImage'
					);
					const faviconFileEntryTitle = document.getElementById(
						'<portlet:namespace />faviconFileEntryTitle'
					);

					if (
						faviconClientExtensionEntryId &&
						faviconClientExtensionEntryType &&
						faviconFileEntryId &&
						faviconFileEntryImage &&
						faviconFileEntryTitle &&
						selectedItem &&
						selectedItem.value
					) {
						const itemValue = JSON.parse(selectedItem.value);

						if (
							selectedItem.returnType ===
							'<%= ClientExtensionItemSelectorReturnType.class.getName() %>'
						) {
							faviconClientExtensionEntryId.value =
								itemValue.clientExtensionEntryId;
							faviconClientExtensionEntryType.value = itemValue.type;
						}
						else {
							faviconFileEntryId.value = itemValue.fileEntryId;

							if (itemValue.url) {
								faviconFileEntryImage.src = itemValue.url;
							}
							else {
								faviconFileEntryImage.classList.add('d-none');
							}
						}

						faviconFileEntryTitle.innerHTML =
							itemValue.title || itemValue.name;
					}
				},
				selectEventName:
					'<%= layoutsAdminDisplayContext.getSelectFaviconEventName() %>',
				title: '<liferay-ui:message key="change-favicon" />',
				url: '<%= layoutsAdminDisplayContext.getFileEntryItemSelectorURL() %>',
			});
		});

		const clearFaviconButton = document.getElementById(
			'<portlet:namespace />clearFaviconButton'
		);
		const faviconFileEntryId = document.getElementById(
			'<portlet:namespace />faviconFileEntryId'
		);
		const faviconFileEntryImage = document.getElementById(
			'<portlet:namespace />faviconFileEntryImage'
		);
		const faviconFileEntryTitle = document.getElementById(
			'<portlet:namespace />faviconFileEntryTitle'
		);

		if (
			clearFaviconButton &&
			faviconFileEntryId &&
			faviconFileEntryImage &&
			faviconFileEntryTitle
		) {
			clearFaviconButton.addEventListener('click', (event) => {
				faviconFileEntryId.value = '0';
				faviconFileEntryImage.classList.add('d-none');
				faviconFileEntryTitle.innerHTML =
					'<liferay-ui:message key="favicon-from-theme" />';
			});
		}
	</aui:script>
</div>