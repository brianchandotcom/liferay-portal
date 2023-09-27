<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					<use xlink:href="<%= themeDisplay.getPathThemeSpritemap() %>#info-circle" />
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