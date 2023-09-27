<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutBranch layoutBranch = (LayoutBranch)request.getAttribute(StagingProcessesWebKeys.LAYOUT_BRANCH);

LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute(WebKeys.LAYOUT_REVISION);

List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
%>

<div class="control-menu-label staging-variation-label">
	<liferay-util:buffer
		var="pageVariationsHelpIcon"
	>
		<liferay-ui:icon-help message="page-variations-help" />
	</liferay-util:buffer>

	<liferay-ui:message arguments="<%= pageVariationsHelpIcon %>" key="page-variations-x" />
</div>

<div class="dropdown">
	<button aria-haspopup="true" class="dropdown-toggle form-control form-control-select form-control-sm layout-branch-selector staging-variation-selector" data-toggle="liferay-dropdown">
		<span class="c-inner" tabindex="-1">
			<liferay-ui:message key="<%= HtmlUtil.escape(layoutBranchDisplayContext.getLayoutBranchDisplayName(layoutBranch)) %>" localizeKey="<%= false %>" />
		</span>
	</button>

	<ul class="dropdown-menu">

		<%
		for (LayoutRevision rootLayoutRevision : layoutRevisions) {
			LayoutBranch curLayoutBranch = rootLayoutRevision.getLayoutBranch();

			boolean selected = curLayoutBranch.getLayoutBranchId() == layoutRevision.getLayoutBranchId();
		%>

			<portlet:actionURL name="/staging_bar/select_layout_branch" var="curLayoutBranchURL">
				<portlet:param name="redirect" value="<%= (String)request.getAttribute(StagingProcessesWebKeys.STAGING_URL) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
				<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
			</portlet:actionURL>

			<li>
				<a class="<%= selected ? "disabled" : StringPool.BLANK %> dropdown-item" href="<%= selected ? "javascript:void(0);" : curLayoutBranchURL %>">
					<liferay-ui:message key="<%= HtmlUtil.escape(layoutBranchDisplayContext.getLayoutBranchDisplayName(curLayoutBranch)) %>" localizeKey="<%= false %>" />
				</a>
			</li>

		<%
		}
		%>

	</ul>
</div>