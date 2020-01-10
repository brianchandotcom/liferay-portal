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
LayoutBranch layoutBranch = (LayoutBranch)request.getAttribute(StagingProcessesWebKeys.LAYOUT_BRANCH);
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute(WebKeys.LAYOUT_REVISION);
String stagingURL = (String)request.getAttribute(StagingProcessesWebKeys.STAGING_URL);
%>

<%
List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
%>

<div class="control-menu-label staging-variation-label">
	<liferay-ui:message key="page-variations" />
</div>

<div class="dropdown">
	<a class="dropdown-toggle layout-branch-selector staging-variation-selector" data-toggle="liferay-dropdown" href="#1">
		<liferay-ui:message key="<%= HtmlUtil.escape(layoutBranchDisplayContext.getLayoutBranchDisplayName(layoutBranch)) %>" localizeKey="<%= false %>" />

		<aui:icon image="caret-double-l" markupView="lexicon" />
	</a>

	<ul class="dropdown-menu">

		<%
		for (LayoutRevision rootLayoutRevision : layoutRevisions) {
			LayoutBranch curLayoutBranch = rootLayoutRevision.getLayoutBranch();

			boolean selected = curLayoutBranch.getLayoutBranchId() == layoutRevision.getLayoutBranchId();
		%>

			<portlet:actionURL name="selectLayoutBranch" var="curLayoutBranchURL">
				<portlet:param name="redirect" value="<%= stagingURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
				<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
			</portlet:actionURL>

			<li>
				<a class="<%= selected ? "disabled" : StringPool.BLANK %> dropdown-item" href="<%= selected ? "javascript:;" : curLayoutBranchURL %>">
					<liferay-ui:message key="<%= HtmlUtil.escape(layoutBranchDisplayContext.getLayoutBranchDisplayName(curLayoutBranch)) %>" localizeKey="<%= false %>" />
				</a>
			</li>

		<%
		}
		%>

	</ul>
</div>