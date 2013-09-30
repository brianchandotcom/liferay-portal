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

<c:if test="<%= (layoutSetBranches != null) && (layoutSetBranches.size() >= 1) %>">
	<div class="site-pages-variation-options span6">
		<h5>
			<span class="site-pages-variation-label"><liferay-ui:message key="site-variations-for" /></span>

			<span class="site-name"><%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %></span>
		</h5>

		<aui:select cssClass="variation-options" label="" name="sitePageVariations">

			<%
			for (LayoutSetBranch curLayoutSetBranch : layoutSetBranches) {
				boolean selected = (group.isStagingGroup() || group.isStagedRemotely()) && (curLayoutSetBranch.getLayoutSetBranchId() == layoutRevision.getLayoutSetBranchId());

				String sitePagesVariationLabel = "staging";

				if (layoutSetBranches.size() != 1) {
					sitePagesVariationLabel = HtmlUtil.escape(curLayoutSetBranch.getName());
				}
			%>

				<portlet:actionURL var="layoutSetBranchURL">
					<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
					<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
					<portlet:param name="redirect" value="<%= stagingFriendlyURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
					<portlet:param name="privateLayout" value="<%= String.valueOf(layout.isPrivateLayout()) %>" />
					<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
				</portlet:actionURL>

				<aui:option selected="<%= selected %>" value="<%= layoutSetBranchURL %>">
					<liferay-ui:message key="<%= sitePagesVariationLabel %>" />

					<c:if test="<%= selected %>">
						(<liferay-ui:message arguments="<%= layouts.size() %>" key='<%= (layouts.size() == 1) ? "1-page" : "x-pages" %>' />)
					</c:if>
				</aui:option>

			<%
			}
			%>

		</aui:select>

		<i class="icon-angle-right"></i>

		<portlet:renderURL var="layoutSetBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/staging_bar/view_layout_set_branches" />
		</portlet:renderURL>

		<div class="manage-layout-set-branches page-variations">
			<aui:icon
				cssClass="manage-layout-set-branches-link"
				id="manageLayoutSetBranches"
				image="cog"
				label="manage-site-pages-variations"
				url="<%= layoutSetBranchesURL %>"
			/>
		</div>
	</div>

	<aui:script use="aui-base">
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