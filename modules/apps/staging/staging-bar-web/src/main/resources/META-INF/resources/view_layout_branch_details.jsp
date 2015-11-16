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
LayoutBranch layoutBranch = (LayoutBranch)request.getAttribute("view.jsp-layoutBranch");
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");
LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");
String stagingFriendlyURL = (String)request.getAttribute("view.jsp-stagingFriendlyURL");
%>

<%
List<LayoutRevision> layoutRevisions = LayoutRevisionLocalServiceUtil.getChildLayoutRevisions(layoutRevision.getLayoutSetBranchId(), LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new LayoutRevisionCreateDateComparator(true));
%>

<li class="control-menu-nav-item">
	<label>
		<liferay-ui:message key="page-variations" />
	</label>

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

					<portlet:actionURL name="selectLayoutBranch" var="curLayoutBranchURL">
						<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(curLayoutBranch.getGroupId()) %>" />
						<portlet:param name="layoutBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutBranchId()) %>" />
						<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutBranch.getLayoutSetBranchId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						cssClass='<%= selected ? "disabled" : StringPool.BLANK %>'
						message="<%= HtmlUtil.escape(curLayoutBranch.getName()) %>"
						url='<%= selected ? "javascript:;" : curLayoutBranchURL %>'
					/>

				<%
				}
				%>

			</liferay-ui:icon-menu>
		</c:otherwise>
	</c:choose>

	<portlet:renderURL var="layoutBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="viewLayoutBranches" />
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
</li>

<aui:script sandbox="<%= true %>">
	$('.layout-branch-selector').on(
		'mouseenter',
		function(event) {
			Liferay.Portal.ToolTip.show(event.currentTarget, '<liferay-ui:message key="page-variation" />');
		}
	);

	var layoutRevisionsLink = $('#<portlet:namespace />manageLayoutRevisions');

	layoutRevisionsLink.on(
		'click',
		function(event) {
			event.preventDefault();

			Liferay.Util.openWindow(
				{
					id: '<portlet:namespace />layoutRevisions',
					title: '<%= UnicodeLanguageUtil.get(request, "manage-page-variations") %>',
					uri: layoutRevisionsLink.attr('href')
				}
			);
		}
	);

</aui:script>