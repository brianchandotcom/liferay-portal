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

<%@ include file="/layout/edit/init.jsp" %>

<aui:input name="TypeSettingsProperties--groupId--" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.getGroupId() %>" />
<aui:input name="TypeSettingsProperties--privateLayout--" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.isPrivateLayout() %>" />

<div class="form-group">
	<aui:input label="link-to-layout" name="linkToLayoutName" type="resource" value="<%= linkToPageLayoutTypeControllerDisplayContext.getLinkToLayoutName() %>" />
	<aui:input name="linkToLayoutUuid" type="hidden" value="<%= linkToPageLayoutTypeControllerDisplayContext.getLinkToLayoutUuid() %>" />

	<aui:button name="selectLayoutButton" value="select" />

	<aui:script use="liferay-item-selector-dialog">
		var selectLayoutButton = document.getElementById('<portlet:namespace />selectLayoutButton');

		if (selectLayoutButton) {
			selectLayoutButton.addEventListener(
				'click',
				function(event) {
					event.preventDefault();

					var itemSelectorDialog = new A.LiferayItemSelectorDialog(
						{
							eventName: '<%= linkToPageLayoutTypeControllerDisplayContext.getEventName() %>',
							on: {
								selectedItemChange: function(event) {
									var selectedItem = event.newVal;

									var linkToLayoutName = document.getElementById('<portlet:namespace />linkToLayoutName');
									var linkToLayoutUuid = document.getElementById('<portlet:namespace />linkToLayoutUuid');

									if (selectedItem && linkToLayoutName && linkToLayoutUuid) {
										linkToLayoutName.value = selectedItem.name;
										linkToLayoutUuid.value = selectedItem.id;
									}
								}
							},
							'strings.add': '<liferay-ui:message key="done" />',
							title: '<liferay-ui:message key="select-layout" />',
							url: '<%= linkToPageLayoutTypeControllerDisplayContext.getItemSelectorURL() %>'
						}
					);

					itemSelectorDialog.open();
				}
			);
		}
	</aui:script>
</div>