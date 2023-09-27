<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/step_tracker/init.jsp" %>

<div class="step-tracker-root" id="<%= stepTrackerId %>"></div>

<aui:script require="commerce-frontend-js/components/step_tracker/entry as stepTracker">
	stepTracker.default('<%= stepTrackerId %>', '<%= stepTrackerId %>', {
		portletId: '<%= portletDisplay.getRootPortletId() %>',
		spritemap: '<%= HtmlUtil.escapeJS(spritemap) %>',
		steps: <%= jsonSerializer.serializeDeep(steps) %>,
	});
</aui:script>