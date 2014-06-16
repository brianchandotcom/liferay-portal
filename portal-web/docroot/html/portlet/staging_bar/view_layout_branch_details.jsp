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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<%
LayoutBranch layoutBranch = (LayoutBranch)request.getAttribute("view.jsp-layoutBranch");
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");
LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");
String stagingFriendlyURL = (String)request.getAttribute("view.jsp-stagingFriendlyURL");
%>

<div class="col-md-5 page-variations-options">

	<%
	List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
	%>

	<div class="layout-info">
		<div class="variations-options">
			<liferay-util:buffer var="taglibMessage">
				<liferay-ui:message key="<%= HtmlUtil.escape(layoutBranch.getName()) %>" />
			</liferay-util:buffer>

			<c:choose>
				<c:when test="<%= layoutRevisions.size() == 1 %>">
					<span class="layout-branch-selector staging-variation-selector"><i class="icon-file"></i> <%= taglibMessage %></span>
				</c:when>
				<c:otherwise>
					<liferay-ui:icon-menu cssClass="layout-branch-selector staging-variation-selector" direction="down" extended="<%= false %>" icon="../aui/file" message="<%= taglibMessage %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

						<%
						for (LayoutRevision rootLayoutRevision : layoutRevisions) {
							LayoutBranch curLayoutBranch = rootLayoutRevision.getLayoutBranch();

							boolean selected = (curLayoutBranch.getLayoutBranchId() == layoutRevision.getLayoutBranchId());
						%>

							<portlet:actionURL var="layoutBranchURL">
								<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
								<portlet:param name="<%= Constants.CMD %>" value="select_layout_branch" />
								<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
								<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
								<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								cssClass='<%= selected ? "disabled" : StringPool.BLANK %>'
								message="<%= HtmlUtil.escape(curLayoutBranch.getName()) %>"
								url='<%= selected ? "javascript:;" : layoutBranchURL %>'
							/>

						<%
						}
						%>

					</liferay-ui:icon-menu>
				</c:otherwise>
			</c:choose>

			<portlet:renderURL var="layoutBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/staging_bar/view_layout_branches" />
				<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranch.getLayoutSetBranchId()) %>" />
			</portlet:renderURL>

			<div class="manage-page-variations page-variations">
				<liferay-ui:icon
					iconCssClass="icon-cog"
					id="manageLayoutRevisions"
					message="manage-page-variations"
					url="<%= layoutBranchesURL %>"
				/>
			</div>
		</div>
	</div>
</div>

<aui:script use="aui-base,event-mouseenter">
	var layoutBranchSelector = A.one('.layout-branch-selector');

	if (layoutBranchSelector) {
		layoutBranchSelector.on(
			'mouseenter',
			function(event) {
				Liferay.Portal.ToolTip.show(layoutBranchSelector, '<liferay-ui:message key="page-variation" />')
			}
		);
	}

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