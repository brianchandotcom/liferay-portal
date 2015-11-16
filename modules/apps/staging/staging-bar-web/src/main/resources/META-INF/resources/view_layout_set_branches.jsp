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
List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);

LayoutSetBranch currentLayoutSetBranch = LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(themeDisplay.getUserId(), stagingGroup.getGroupId(), privateLayout, 0, 0);

request.setAttribute("view_layout_set_branches.jsp-currentLayoutSetBranchId", String.valueOf(currentLayoutSetBranch.getLayoutSetBranchId()));
%>

<liferay-ui:success key="sitePageVariationAdded" message="site-page-variation-was-added" />
<liferay-ui:success key="sitePageVariationDeleted" message="site-page-variation-was-deleted" />
<liferay-ui:success key="sitePageVariationMerged" message="site-page-variation-was-merged" />
<liferay-ui:success key="sitePageVariationUpdated" message="site-page-variation-was-updated" />

<div class="alert alert-info">
	<liferay-ui:message key="pages-variations-help" />
</div>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, stagingGroup, ActionKeys.ADD_LAYOUT_SET_BRANCH) %>">
	<liferay-portlet:renderURL var="addLayoutSetBranchURL">
		<portlet:param name="mvcRenderCommandName" value="editLayoutSetBranch" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</liferay-portlet:renderURL>

	<aui:button-row>
		<aui:button href="<%= addLayoutSetBranchURL %>" name="addBranchButton" value="add-site-pages-variation" />
	</aui:button-row>
</c:if>

<div class="branch-results">
	<liferay-ui:search-container>
		<liferay-ui:search-container-results
			results="<%= layoutSetBranches %>"
			total="<%= layoutSetBranches.size() %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.LayoutSetBranch"
			escapedModel="<%= true %>"
			keyProperty="layoutSetBranchId"
			modelVar="curLayoutSetBranch"
		>

			<liferay-ui:search-container-column-text
				name="name"
			>
				<c:if test="<%= currentLayoutSetBranch.equals(curLayoutSetBranch) %>">
					<strong>
				</c:if>

				<liferay-ui:message key="<%= curLayoutSetBranch.getName() %>" />

				<c:if test="<%= curLayoutSetBranch.isMaster() %>">
					<i class="icon-asterisk"></i>
				</c:if>

				<c:if test="<%= currentLayoutSetBranch.equals(curLayoutSetBranch) %>">
					</strong>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				property="description"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/layout_set_branch_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-staging-branch">
	Liferay.StagingBar.init(
		{
			namespace: '<portlet:namespace />',
			portletId: '<%= portletDisplay.getId() %>'
		}
	);
</aui:script>