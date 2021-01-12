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
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute(WebKeys.LAYOUT_REVISION);

if ((layoutRevision == null) && (layout != null)) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);
}

Long liveLayoutRevisionId = null;

if (layoutRevision.isApproved()) {
	liveLayoutRevisionId = _getLastImportLayoutRevisionId(group, layout, themeDisplay.getUser());
}
%>

<span class="staging-bar-workflow-text text-center">
	<div class="alert alert-fluid alert-info custom-info-alert" role="alert">
		<div class="staging-alert-container">
			<span class="alert-indicator">
				<svg aria-hidden="true" class="lexicon-icon lexicon-icon-info-circle">
					<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg#info-circle" />
				</svg>
			</span>

			<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

			<%
			int status = layoutRevision.getStatus();

			if (layout.isTypeContent()) {
				status = WorkflowConstants.STATUS_APPROVED;
			}
			%>

			<aui:workflow-status markupView="lexicon" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= status %>" statusMessage="<%= _getStatusMessage(layoutRevision, GetterUtil.getLong(liveLayoutRevisionId)) %>" />
		</div>
	</div>
</span>