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
long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutSetBranchId, LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));

long layoutRevisionId = StagingUtil.getRecentLayoutRevisionId(request, layoutSetBranchId, plid);

LayoutRevision currentLayoutRevision = null;

if (layoutRevisionId <= 0) {
	LayoutBranch layoutBranch = LayoutBranchLocalServiceUtil.getMasterLayoutBranch(layoutSetBranchId, plid);

	currentLayoutRevision = LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutSetBranchId, layoutBranch.getLayoutBranchId(), plid);

	layoutRevisionId = currentLayoutRevision.getLayoutRevisionId();
}
else {
	currentLayoutRevision = LayoutRevisionLocalServiceUtil.getLayoutRevision(layoutRevisionId);
}

request.setAttribute("view_layout_branches.jsp-currenttLayoutBranchId", String.valueOf(currentLayoutRevision.getLayoutBranchId()));
%>

<liferay-ui:success key="pageVariationAdded" message="page-variation-was-added" />
<liferay-ui:success key="pageVariationDeleted" message="page-variation-was-deleted" />
<liferay-ui:success key="pageVariationUpdated" message="page-variation-was-updated" />

<div class="alert alert-info">
	<liferay-ui:message key="page-variations-help" />
</div>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, stagingGroup, ActionKeys.ADD_LAYOUT_BRANCH) %>">
	<liferay-portlet:renderURL var="addLayoutBranchURL">
		<portlet:param name="mvcRenderCommandName" value="editLayoutBranch" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevisionId) %>" />
	</liferay-portlet:renderURL>

	<aui:button-row>
		<aui:button href="<%= addLayoutBranchURL %>" name="addRootLayoutBranch" value="add-page-variation" />
	</aui:button-row>
</c:if>

<div class="branch-results">
	<liferay-ui:search-container>
		<liferay-ui:search-container-results
			results="<%= layoutRevisions %>"
			total="<%= layoutRevisions.size() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.LayoutRevision"
			escapedModel="<%= true %>"
			keyProperty="layoutRevisionId"
			modelVar="layoutRevision"
		>

			<%
			LayoutBranch layoutBranch = layoutRevision.getLayoutBranch();
			%>

			<liferay-ui:search-container-column-text
				name="name"
			>
				<c:if test="<%= layoutRevision.getLayoutBranchId() == currentLayoutRevision.getLayoutBranchId() %>">
					<strong>
				</c:if>

				<liferay-ui:message key="<%= HtmlUtil.escape(layoutBranch.getName()) %>" />

				<c:if test="<%= layoutBranch.isMaster() %>">
					<i class="icon-asterisk"></i>
				</c:if>

				<c:if test="<%= layoutRevision.getLayoutBranchId() == currentLayoutRevision.getLayoutBranchId() %>">
					</strong>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(layoutBranch.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/layout_branch_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>

<aui:script position="inline" use="liferay-staging-branch">
	Liferay.StagingBar.init(
		{
			namespace: '<portlet:namespace />',
			portletId: '<%= portletDisplay.getId() %>'
		}
	);
</aui:script>