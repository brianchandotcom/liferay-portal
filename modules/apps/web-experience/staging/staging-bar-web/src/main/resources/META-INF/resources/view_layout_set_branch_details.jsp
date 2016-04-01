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
		<div class="control-menu-label staging-variation-label">
			<liferay-ui:message key="site-pages-variation" />
		</div>

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
						<a class="<%= selected ? "disabled" : StringPool.BLANK %>" href="<%= selected ? "javascript:;" : curLayoutSetBranchURL %>">
							<liferay-ui:message key="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>" />
						</a>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</li>
</c:if>