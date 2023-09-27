<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/edit/init.jsp" %>

<aui:input name="TypeSettingsProperties--groupId--" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.getGroupId() %>" />
<aui:input name="TypeSettingsProperties--privateLayout--" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.isPrivateLayout() %>" />

<div class="form-group">
	<aui:input label="link-to-layout" name="linkToLayoutName" type="resource" value="<%= linkToPageLayoutTypeControllerDisplayContext.getLinkToLayoutName() %>" />
	<aui:input name="linkToLayoutUuid" type="hidden" value="<%= linkToPageLayoutTypeControllerDisplayContext.getLinkToLayoutUuid() %>" />

	<clay:button
		displayType="secondary"
		id='<%= liferayPortletResponse.getNamespace() + "selectLayoutButton" %>'
		label="select"
	/>

	<aui:script sandbox="<%= true %>">
		var selectLayoutButton = document.getElementById(
			'<portlet:namespace />selectLayoutButton'
		);

		selectLayoutButton.addEventListener('click', (event) => {
			event.preventDefault();

			Liferay.Util.openSelectionModal({
				onSelect: function (selectedItem) {
					var linkToLayoutName = document.getElementById(
						'<portlet:namespace />linkToLayoutName'
					);
					var linkToLayoutUuid = document.getElementById(
						'<portlet:namespace />linkToLayoutUuid'
					);

					if (selectedItem && linkToLayoutName && linkToLayoutUuid) {
						linkToLayoutName.value = selectedItem.name;
						linkToLayoutUuid.value = selectedItem.id;
					}
				},
				selectEventName:
					'<%= linkToPageLayoutTypeControllerDisplayContext.getEventName() %>',
				title: '<liferay-ui:message key="select-layout" />',
				url:
					'<%= linkToPageLayoutTypeControllerDisplayContext.getItemSelectorURL() %>',
			});
		});
	</aui:script>
</div>