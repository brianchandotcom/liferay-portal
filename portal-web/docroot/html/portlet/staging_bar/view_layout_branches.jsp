<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

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

<liferay-ui:error exception="<%= LayoutBranchNameException.class %>">

	<%
	LayoutBranchNameException lbne = (LayoutBranchNameException)errorException;
	%>

	<c:if test="<%= lbne.getType() == LayoutBranchNameException.DUPLICATE %>">
		<liferay-ui:message key="a-page-variation-with-that-name-already-exists" />
	</c:if>

	<c:if test="<%= lbne.getType() == LayoutBranchNameException.TOO_LONG %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 100} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>

	<c:if test="<%= lbne.getType() == LayoutBranchNameException.TOO_SHORT %>">
		<liferay-ui:message arguments="<%= new Object[] {4, 100} %>" key="please-enter-a-value-between-x-and-x-characters-long" translateArguments="<%= false %>" />
	</c:if>
</liferay-ui:error>

<div class="alert alert-info">
	<liferay-ui:message key="page-variations-help" />
</div>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, stagingGroup, ActionKeys.ADD_LAYOUT_BRANCH) %>">
	<liferay-util:html-top>
		<liferay-util:include page="/html/portlet/staging_bar/edit_layout_branch.jsp">
			<liferay-util:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevisionId) %>" />
			<liferay-util:param name="redirect" value="<%= currentURL %>" />
		</liferay-util:include>
	</liferay-util:html-top>

	<%
	String taglibOnClick = "javascript:Liferay.StagingBar.addBranch('" + LanguageUtil.get(pageContext, "add-page-variation") + "');";
	%>

	<aui:button-row>
		<aui:button name="addRootLayoutBranch" onClick="<%= taglibOnClick %>" value="add-page-variation" />
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
				buffer="buffer"
				name="name"
			>

				<%
				String layoutBranchName = layoutBranch.getName();

				if (layoutRevision.getLayoutBranchId() == currentLayoutRevision.getLayoutBranchId()) {
					buffer.append("<strong>");
				}

				buffer.append(LanguageUtil.get(pageContext, HtmlUtil.escape(layoutBranchName)));

				if (layoutBranch.isMaster()) {
					buffer.append(" (*)");
				}

				if (layoutRevision.getLayoutBranchId() == currentLayoutRevision.getLayoutBranchId()) {
					buffer.append("</strong>");
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="description"
				value="<%= HtmlUtil.escape(layoutBranch.getDescription()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				path="/html/portlet/staging_bar/layout_branch_action.jsp"
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