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
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");
LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");
List<LayoutSetBranch> layoutSetBranches = (List<LayoutSetBranch>)request.getAttribute("view.jsp-layoutSetBranches");
String stagingFriendlyURL = (String)request.getAttribute("view.jsp-stagingFriendlyURL");
%>

<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() >= 1) %>">
	<div class="site-pages-variation-options span5">
		<div class="variations-options">
			<liferay-util:buffer var="taglibMessage">
				<liferay-ui:message key="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" />

				<small>(<liferay-ui:message arguments="<%= layouts.size() %>" key='<%= (layouts.size() == 1) ? "1-page" : "x-pages" %>' />)</small>
			</liferay-util:buffer>

			<c:choose>
				<c:when test="<%= layoutSetBranches.size() == 1 %>">
					<span class="layout-set-branch-selector staging-variation-selector"><i class="icon-globe"></i> <%= taglibMessage %></span>
				</c:when>
				<c:otherwise>
					<liferay-ui:icon-menu cssClass="layout-set-branch-selector staging-variation-selector" direction="down" extended="<%= false %>" icon="../aui/globe" message="<%= taglibMessage %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

						<%
						for (LayoutSetBranch curLayoutSetBranch : layoutSetBranches) {
							boolean selected = (group.isStagingGroup() || group.isStagedRemotely()) && (curLayoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());
						%>

							<portlet:actionURL var="layoutSetBranchURL">
								<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
								<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
								<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
								<portlet:param name="privateLayout" value="<%= String.valueOf(layout.isPrivateLayout()) %>" />
								<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								cssClass='<%= selected ? "disabled" : StringPool.BLANK %>'
								message="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>"
								url='<%= selected ? "javascript:;" : layoutSetBranchURL %>'
							/>

						<%
						}
						%>

					</liferay-ui:icon-menu>
				</c:otherwise>
			</c:choose>

			<portlet:renderURL var="layoutSetBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/staging_bar/view_layout_set_branches" />
			</portlet:renderURL>

			<div class="manage-layout-set-branches page-variations">
				<liferay-ui:icon
					id="manageLayoutSetBranches"
					image="../aui/cog"
					message="manage-site-pages-variations"
					url="<%= layoutSetBranchesURL %>"
				/>
			</div>
		</div>
	</div>

	<aui:script use="aui-base">
		var layoutSetBranchSelector = A.one('.layout-set-branch-selector');

		if (layoutSetBranchSelector) {
			layoutSetBranchSelector.on(
				'mouseenter',
				function(event) {
					Liferay.Portal.ToolTip.show(layoutSetBranchSelector, '<liferay-ui:message key="site-pages-variation" />')
				}
			);
		}

		var layoutSetBranchesLink = A.one('#<portlet:namespace />manageLayoutSetBranches');

		if (layoutSetBranchesLink) {
			layoutSetBranchesLink.detach('click');

			layoutSetBranchesLink.on(
				'click',
				function(event) {
					event.preventDefault();

					Liferay.Util.openWindow(
						{
							id: '<portlet:namespace />layoutSetBranches',
							title: '<%= UnicodeLanguageUtil.get(pageContext, "manage-site-pages-variations") %>',
							uri: event.currentTarget.attr('href')
						}
					);
				}
			);
		}
	</aui:script>
</c:if>