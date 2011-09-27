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

LayoutSetBranch currentLayoutSetBranch = LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(themeDisplay.getUserId(), stagingGroup.getGroupId(), privateLayout, 0);

request.setAttribute("view_layout_set_branches.jsp-currentLayoutSetBranchId", String.valueOf(currentLayoutSetBranch.getLayoutSetBranchId()));
%>

<liferay-ui:success key="sitePageVariationAdded" message="site-page-variation-was-added" />
<liferay-ui:success key="sitePageVariationDeleted" message="site-page-variation-was-deleted" />
<liferay-ui:success key="sitePageVariationMerged" message="site-page-variation-was-merged" />
<liferay-ui:success key="sitePageVariationUpdated" message="site-page-variation-was-updated" />

<liferay-ui:error key="<%= LayoutSetBranchNameException.class.getName() + LayoutSetBranchNameException.DUPLICATE %>" message="a-site-pages-variation-with-that-name-already-exists" />
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
	String taglibOnClick = "javascript:Liferay.StagingBar.addBranch('" + LanguageUtil.get(pageContext, "add-site-pages-variation") + "');";
	%>

	<aui:button-row>
		<aui:button name="addBranchButton" onClick="<%= taglibOnClick %>" value="add-site-pages-variation" />
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
				if (currentLayoutSetBranch.equals(curLayoutSetBranch)) {
					buffer.append("<strong>");
				}

				buffer.append(LanguageUtil.get(pageContext, curLayoutSetBranch.getName()));

				if (curLayoutSetBranch.isMaster()) {
					buffer.append(" (*)");
				}

				if (currentLayoutSetBranch.equals(curLayoutSetBranch)) {
					buffer.append("</strong>");
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

<aui:script position="inline" use="liferay-staging-branch">
	Liferay.StagingBar.init(
		{
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>

<c:if test='<%= themeDisplay.isStatePopUp() && SessionMessages.contains(renderRequest, portletName + ".doConfigure") %>'>
	<aui:script use="aui-base">
		if (window.parent) {
			var data;

			<c:if test='<%= SessionMessages.contains(renderRequest, portletName + ".notAjaxable") %>'>
				data = {
					portletAjaxable: false
				};
			</c:if>

			Liferay.Util.getOpener().Liferay.Portlet.refresh('#p_p_id_<%= PortletKeys.STAGING_BAR %>_', data);
		}
	</aui:script>
</c:if>