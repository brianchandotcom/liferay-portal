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
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view.jsp-layoutRevision");
LayoutSetBranch layoutSetBranch = (LayoutSetBranch)request.getAttribute("view.jsp-layoutSetBranch");
List<LayoutSetBranch> layoutSetBranches = (List<LayoutSetBranch>)request.getAttribute("view.jsp-layoutSetBranches");
String stagingFriendlyURL = (String)request.getAttribute("view.jsp-stagingFriendlyURL");
%>

<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() >= 1) %>">
	<li class="control-menu-nav-item">
		<a class="staging-variation-label" href="javascript:;" id="manageLayoutSetRevisions" onclick='<%= renderResponse.getNamespace() + "openSitePagesVariationsDialog();" %>'>
			<liferay-ui:message key="site-pages-variation" />
		</a>

		<div class="dropdown">
			<a class="dropdown-toggle layout-set-branch-selector staging-variation-selector" data-toggle="dropdown" href="#1">
				<liferay-ui:message key="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" />

				<aui:icon image="caret-double-l" markupView="lexicon" />
			</a>

			<ul class="dropdown-menu">

				<%
				for (LayoutSetBranch curLayoutSetBranch : layoutSetBranches) {
					boolean selected = (group.isStagingGroup() || group.isStagedRemotely()) && (curLayoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());
				%>

				<portlet:actionURL name="selectLayoutSetBranch" var="curLayoutSetBranchURL">
					<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
					<portlet:param name="privateLayout" value="<%= String.valueOf(layout.isPrivateLayout()) %>" />
					<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
				</portlet:actionURL>

				<li>
					<a class="<%= selected ? "disabled" : StringPool.BLANK %>" href='<%= selected ? "javascript:;" : curLayoutSetBranchURL %>'>
						<liferay-ui:message key="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>" />
					</a>
				</li>

				<%
				}
				%>

			</ul>
		</div>
	</li>

	<aui:script>
		function <portlet:namespace />openSitePagesVariationsDialog() {
			var sitePagesVariationDialog = Liferay.Util.openWindow(
				{
					dialog: {
						destroyOnHide: true
					},
					id: 'sitePagesVariationDialog',
					title: '<liferay-ui:message key="site-pages-variation" />',

					<liferay-portlet:renderURL var="layoutSetBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="mvcRenderCommandName" value="viewLayoutSetBranches" />
					</liferay-portlet:renderURL>

					uri: '<%= HtmlUtil.escapeJS(layoutSetBranchesURL) %>'
				}
			);
		}

		$('.layout-set-branch-selector').on(
			'mouseenter',
			function(event) {
				Liferay.Portal.ToolTip.show(event.currentTarget, '<liferay-ui:message key="site-pages-variation" />');
			}
		);
	</aui:script>
</c:if>