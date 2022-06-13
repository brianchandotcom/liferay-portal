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
	<img alt="<%= HtmlUtil.escape(layoutsAdminDisplayContext.getFaviconTitle()) %>" class="mb-2" height="16" id="<portlet:namespace />faviconImage" src="<%= layoutsAdminDisplayContext.getFaviconImage() %>" width="16" />

	<p>
		<b><liferay-ui:message key="favicon-name" />:</b> <span id="<portlet:namespace />faviconTitle"><%= layoutsAdminDisplayContext.getFaviconTitle() %></span>
	</p>

	<aui:input name="faviconCETExternalReferenceCode" type="hidden" />
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
					const faviconCETExternalReferenceCode = document.getElementById(
						'<portlet:namespace />faviconCETExternalReferenceCode'
					);

					const faviconFileEntryId = document.getElementById(
						'<portlet:namespace />faviconFileEntryId'
					);
					const faviconImage = document.getElementById(
						'<portlet:namespace />faviconImage'
					);
					const faviconTitle = document.getElementById(
						'<portlet:namespace />faviconTitle'
					);

					if (
						faviconCETExternalReferenceCode &&
						faviconFileEntryId &&
						faviconImage &&
						faviconTitle &&
						selectedItem &&
						selectedItem.value
					) {
						const itemValue = JSON.parse(selectedItem.value);

						if (
							selectedItem.returnType ===
							'<%= CETItemSelectorReturnType.class.getName() %>'
						) {
							faviconCETExternalReferenceCode.value =
								itemValue.cetExternalReferenceCode;
						}
						else {
							faviconFileEntryId.value = itemValue.fileEntryId;
						}

						if (itemValue.url) {
							faviconImage.src = itemValue.url;
						}
						else {
							faviconImage.classList.add('d-none');
						}

						faviconTitle.innerHTML = itemValue.title || itemValue.name;
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
		const faviconImage = document.getElementById(
			'<portlet:namespace />faviconImage'
		);
		const faviconTitle = document.getElementById(
			'<portlet:namespace />faviconTitle'
		);

		if (clearFaviconButton && faviconFileEntryId && faviconImage && faviconTitle) {
			clearFaviconButton.addEventListener('click', (event) => {
				faviconFileEntryId.value = '0';
				faviconImage.classList.add('d-none');
				faviconTitle.innerHTML =
					'<liferay-ui:message key="favicon-from-theme" />';
			});
		}
	</aui:script>
</div>