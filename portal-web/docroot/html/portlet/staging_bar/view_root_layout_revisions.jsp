<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
%>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, stagingGroup.getGroupId(), ActionKeys.ADD_LAYOUT_BRANCH) %>">
	<liferay-util:html-top>
		<liferay-util:include page="/html/portlet/staging_bar/add_root_layout_revision.jsp">
			<liferay-util:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevisionId) %>" />
		</liferay-util:include>
	</liferay-util:html-top>

	<%
	String taglibOnClick = "javascript:Liferay.Staging.Branching.addRootLayoutRevision('" + renderResponse.getNamespace() + "');";
	%>

	<aui:button-row>
		<aui:button name="addRootLayoutRevision" onClick="<%= taglibOnClick %>" value="add-page-variation" />
	</aui:button-row>
</c:if>

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

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="name"
		>

			<%
			LayoutBranch layoutBranch = layoutRevision.getLayoutBranch();

			String layoutBranchName = layoutBranch.getName();

			if (layoutRevision.isHead()) {
				buffer.append("<strong>");
			}

			buffer.append(layoutBranchName);

			if (layoutRevision.isHead()) {
				buffer.append(" (*)</strong>");
			}
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-jsp
			path="/html/portlet/staging_bar/root_layout_revision_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:script position="inline" use="liferay-staging">
	Liferay.Staging.Branching.init(
		{
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>