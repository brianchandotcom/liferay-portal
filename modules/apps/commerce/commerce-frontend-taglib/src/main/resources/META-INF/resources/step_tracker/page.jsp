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

<%@ include file="/step_tracker/init.jsp" %>

<div class="step-tracker-root" id="<%= stepTrackerId %>"></div>

<aui:script require="commerce-frontend-js/components/step_tracker/entry as stepTracker">
	stepTracker.default('<%= stepTrackerId %>', '<%= stepTrackerId %>', {
		portletId: '<%= portletDisplay.getRootPortletId() %>',
		spritemap: '<%= HtmlUtil.escapeJS(spritemap) %>',
		steps: <%= jsonSerializer.serializeDeep(steps) %>,
	});
</aui:script>