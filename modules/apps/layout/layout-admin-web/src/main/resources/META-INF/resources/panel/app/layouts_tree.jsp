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
Group group = layoutsAdminDisplayContext.getGroup();
%>

<c:if test="<%= !group.isLayoutPrototype() %>">

	<%
	Group stagingGroup = layoutsAdminDisplayContext.getStagingGroup();
	%>

	<c:if test="<%= stagingGroup.isStaged() && (selGroup.getGroupId() == stagingGroup.getGroupId()) %>">

	<%
	long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

	if (layoutSetBranchId <= 0) {
		LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

		layoutSetBranchId = StagingUtil.getRecentLayoutSetBranchId(user, selLayoutSet.getLayoutSetId());
	}

	LayoutSetBranch layoutSetBranch = null;

	if (layoutSetBranchId > 0) {
		layoutSetBranch = LayoutSetBranchLocalServiceUtil.fetchLayoutSetBranch(layoutSetBranchId);
	}

	if (layoutSetBranch == null) {
		try {
			layoutSetBranch = LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(layoutsAdminDisplayContext.getStagingGroupId(), layoutsAdminDisplayContext.isPrivateLayout());
		}
		catch (NoSuchLayoutSetBranchException nslsbe) {
		}
	}

	List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(layoutsAdminDisplayContext.getStagingGroupId(), layoutsAdminDisplayContext.isPrivateLayout());
	%>

	<c:choose>
		<c:when test="<%= layoutSetBranches.size() > 1 %>">
			<ul class="nav nav-equal-height nav-nested">
				<li>
					<div class="nav-equal-height-heading">
						<span><%= HtmlUtil.escape(LanguageUtil.get(request, layoutSetBranch.getName())) %></span>

						<span class="nav-equal-height-heading-field">
							<liferay-ui:icon-menu direction="down" icon="../aui/cog" message="" showArrow="<%= false %>">

								<%
								Map<String, Object> data = new HashMap<String, Object>();

								data.put("navigation", Boolean.TRUE.toString());

								for (int i = 0; i < layoutSetBranches.size(); i++) {
									LayoutSetBranch curLayoutSetBranch = layoutSetBranches.get(i);

									boolean selected = (curLayoutSetBranch.getLayoutSetBranchId() == layoutSetBranch.getLayoutSetBranchId());

									PortletURL layoutSetBranchURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.RENDER_PHASE);

									layoutSetBranchURL.setParameter("mvcPath", "/view.jsp");
									layoutSetBranchURL.setParameter("redirect", String.valueOf(layoutsAdminDisplayContext.getRedirectURL()));
									layoutSetBranchURL.setParameter("groupId", String.valueOf(curLayoutSetBranch.getGroupId()));
									layoutSetBranchURL.setParameter("privateLayout", String.valueOf(layoutsAdminDisplayContext.isPrivateLayout()));
									layoutSetBranchURL.setParameter("layoutSetBranchId", String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()));
								%>

									<liferay-ui:icon
										cssClass='<%= selected ? "disabled" : StringPool.BLANK %>'
										data="<%= data %>"
										message="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>"
										url="<%= selected ? null : layoutSetBranchURL.toString() %>"
									/>

								<%
								}
								%>

							</liferay-ui:icon-menu>
						</span>
					</div>
				</li>
			</ul>
		</c:when>
	</c:choose>
</c:if>

	<%
	String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");

	Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();

	Group selGroup = layoutsAdminDisplayContext.getSelGroup();
	%>

	<c:if test="<%= !selGroup.isLayoutSetPrototype() %>">
		<liferay-portlet:renderURL varImpl="editPublicLayoutURL">
			<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
			<portlet:param name="redirect" value="<%= layoutsAdminDisplayContext.getRedirect() %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getLiveGroupId()) %>" />
			<portlet:param name="viewLayout" value="<%= Boolean.TRUE.toString() %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:layouts-tree
			groupId="<%= selGroup.getGroupId() %>"
			portletURL="<%= editPublicLayoutURL %>"
			privateLayout="<%= false %>"
			rootNodeName="<%= liveGroup.getLayoutRootNodeName(false, themeDisplay.getLocale()) %>"
			selectedLayoutIds="<%= selectedLayoutIds %>"
			selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>"
			treeId="publicLayoutsTree"
		/>
	</c:if>

	<liferay-portlet:renderURL varImpl="editPrivateLayoutURL">
		<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
		<portlet:param name="redirect" value="<%= layoutsAdminDisplayContext.getRedirect() %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(layoutsAdminDisplayContext.getLiveGroupId()) %>" />
		<portlet:param name="viewLayout" value="<%= Boolean.TRUE.toString() %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:layouts-tree
		groupId="<%= selGroup.getGroupId() %>"
		portletURL="<%= editPrivateLayoutURL %>"
		privateLayout="<%= true %>"
		rootNodeName="<%= liveGroup.getLayoutRootNodeName(true, themeDisplay.getLocale()) %>"
		selectedLayoutIds="<%= selectedLayoutIds %>"
		selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>"
		treeId="privateLayoutsTree"
	/>
</c:if>