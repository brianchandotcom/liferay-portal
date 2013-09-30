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
LayoutBranch layoutBranch = (LayoutBranch)request.getAttribute("view.jsp-layoutBranch");
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");
LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");
String stagingFriendlyURL = (String)request.getAttribute("view.jsp-stagingFriendlyURL");
%>

<div class="page-variations-options span6">
	<h5>
		<span class="page-variation-label"><liferay-ui:message key="page-variations-for" /></span>

		<span class="page-name"><%= HtmlUtil.escape(layout.getName(locale)) %></span>
	</h5>

	<%
	List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
	%>

	<div class="layout-info">
		<div class="variations-options">
			<aui:select cssClass="variation-options" label="" name="pageVariations">

			<%
			for (LayoutRevision rootLayoutRevision : layoutRevisions) {
				LayoutBranch curLayoutBranch = rootLayoutRevision.getLayoutBranch();
			%>

				<portlet:actionURL var="layoutBranchURL">
					<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
					<portlet:param name="<%= Constants.CMD %>" value="select_layout_branch" />
					<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
					<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
					<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
				</portlet:actionURL>

				<aui:option label="<%= HtmlUtil.escape(curLayoutBranch.getName()) %>" selected="<%= curLayoutBranch.getLayoutBranchId() == layoutRevision.getLayoutBranchId() %>" value="<%= layoutBranchURL %>" />

			<%
			}
			%>

			</aui:select>

			<portlet:renderURL var="layoutBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/staging_bar/view_layout_branches" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
			</portlet:renderURL>

			<div class="manage-page-variations page-variations">
				<aui:icon
					cssClass="manage-layout-set-branches-link"
					id="manageLayoutRevisions"
					image="cog"
					label="manage-page-variations"
					url="<%= layoutBranchesURL %>"
				/>
			</div>
		</div>

		<c:if test="<%= Validator.isNotNull(layoutBranch.getDescription()) %>">
			<div class="variations-content">
				<div class="layout-branch-description">
					<%= HtmlUtil.escape(layoutBranch.getDescription()) %>
				</div>
			</div>
		</c:if>
	</div>
</div>

<aui:script use="aui-base">
	var layoutRevisionsLink = A.one('#<portlet:namespace />manageLayoutRevisions');

	if (layoutRevisionsLink) {
		layoutRevisionsLink.detach('click');

		layoutRevisionsLink.on(
			'click',
			function(event) {
				event.preventDefault();

				Liferay.Util.openWindow(
					{
						id: '<portlet:namespace />layoutRevisions',
						title: '<%= UnicodeLanguageUtil.get(pageContext, "manage-page-variations") %>',
						uri: event.currentTarget.attr('href')
					}
				);
			}
		);
	}
</aui:script>