<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/compare_checkbox/init.jsp" %>

<div class="compare-checkbox-root" id="<%= rootId %>"></div>

<aui:script require="commerce-frontend-js/components/compare_checkbox/entry as compareCheckbox">
	compareCheckbox.default('<%= rootId %>', '<%= rootId %>', {
		commerceChannelGroupId: <%= commerceChannelGroupId %>,
		disabled: <%= disabled %>,
		inCompare: <%= inCompare %>,
		itemId: '<%= cpCatalogEntry.getCPDefinitionId() %>',
		label: '<%= label %>',
		pictureUrl: '<%= pictureUrl %>',
		refreshOnRemove: <%= refreshOnRemove %>,
	});
</aui:script>