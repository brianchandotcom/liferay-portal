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
List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);

LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(themeDisplay.getUserId(), stagingGroup.getGroupId(), privateLayout, 0);
%>

<liferay-ui:error key="<%= LayoutSetBranchNameException.class.getName() + LayoutSetBranchNameException.DUPLICATE %>" message="a-branch-with-that-name-already-exists" />
<liferay-ui:error key="<%= LayoutSetBranchNameException.class.getName() + LayoutSetBranchNameException.TOO_LONG %>" message='<%= LanguageUtil.format(pageContext, "please-enter-a-value-between-x-and-x-characters-long", new Object[] {4, 100}) %>' />
<liferay-ui:error key="<%= LayoutSetBranchNameException.class.getName() + LayoutSetBranchNameException.TOO_SHORT %>" message='<%= LanguageUtil.format(pageContext, "please-enter-a-value-between-x-and-x-characters-long", new Object[] {4, 100}) %>' />

<div class="portlet-msg-info">
	<liferay-ui:message key="pages-variations-help" />
</div>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, stagingGroup.getGroupId(), ActionKeys.ADD_LAYOUT_SET_BRANCH) %>">
	<liferay-util:html-top>
		<liferay-util:include page="/html/portlet/staging_bar/edit_layout_set_branch.jsp">
			<liferay-util:param name="redirect" value="<%= currentURL %>" />
		</liferay-util:include>
	</liferay-util:html-top>

	<%
	String taglibOnClick = "javascript:Liferay.Staging.Branching.addBranch('" + LanguageUtil.get(pageContext, (privateLayout ? "add-private-pages-variation" : "add-public-pages-variation")) + "');";
	%>

	<aui:button-row>
		<aui:button name="addBranchButton" onClick="<%= taglibOnClick %>" value='<%= privateLayout ? "add-private-pages-variation" : "add-public-pages-variation" %>' />
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
				buffer="buffer"
				name="name"
			>

				<%
				if (layoutSetBranch.equals(curLayoutSetBranch)) {
					buffer.append("<strong>");
				}

				buffer.append(LanguageUtil.get(pageContext, curLayoutSetBranch.getName()));

				if (layoutSetBranch.equals(curLayoutSetBranch)) {
					buffer.append(" (*)</strong>");
				}
				%>

			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				property="description"
			/>

			<liferay-ui:search-container-column-jsp
				path="/html/portlet/staging_bar/layout_set_branch_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
	</liferay-ui:search-container>
</div>

<aui:script position="inline" use="liferay-staging">
	Liferay.Staging.Branching.init(
		{
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>