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

	<c:if test="<%= stagingGroup.isStaged() && (themeDisplay.getScopeGroupId() == stagingGroup.getGroupId()) %>">

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

		<%
		PortletURL editPublicLayoutURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, 0, PortletRequest.RENDER_PHASE);

		editPublicLayoutURL.setParameter("privateLayout", Boolean.FALSE.toString());
		editPublicLayoutURL.setParameter("redirect", layoutsAdminDisplayContext.getRedirect());
		editPublicLayoutURL.setParameter("groupId", String.valueOf(layoutsAdminDisplayContext.getLiveGroupId()));
		editPublicLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());
		%>

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

	<%
	PortletURL editPrivateLayoutURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, 0, PortletRequest.RENDER_PHASE);

	editPrivateLayoutURL.setParameter("privateLayout", Boolean.TRUE.toString());
	editPrivateLayoutURL.setParameter("redirect", layoutsAdminDisplayContext.getRedirect());
	editPrivateLayoutURL.setParameter("groupId", String.valueOf(layoutsAdminDisplayContext.getLiveGroupId()));
	editPrivateLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());
	%>

	<liferay-ui:layouts-tree
		groupId="<%= selGroup.getGroupId() %>"
		portletURL="<%= editPrivateLayoutURL %>"
		privateLayout="<%= true %>"
		rootNodeName="<%= liveGroup.getLayoutRootNodeName(true, themeDisplay.getLocale()) %>"
		selectedLayoutIds="<%= selectedLayoutIds %>"
		selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>"
		treeId="privateLayoutsTree"
	/>

	<%
	Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

	boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();

	if (selGroup.isLayoutSetPrototype()) {
		privateLayout = true;
	}
	%>

	<c:if test="<%= ((selLayout == null) && GroupPermissionUtil.contains(permissionChecker, selGroup, ActionKeys.ADD_LAYOUT)) || ((selLayout != null) && LayoutPermissionUtil.contains(permissionChecker, selLayout, ActionKeys.ADD_LAYOUT)) %>">

		<%
		PortletURL addPagesURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.GROUP_PAGES, 0, PortletRequest.RENDER_PHASE);

		addPagesURL.setParameter("mvcPath", "/add_layout.jsp");
		addPagesURL.setParameter("groupId", String.valueOf(selGroup.getGroupId()));
		addPagesURL.setParameter("selPlid", (selLayout != null) ? String.valueOf(selLayout.getPlid()) : StringPool.BLANK);
		addPagesURL.setParameter("privateLayout", String.valueOf(privateLayout));
		%>

		<aui:button-row>
			<aui:button cssClass="btn-block btn-primary" href="<%= addPagesURL.toString() %>" value="add-page" />
		</aui:button-row>
	</c:if>
</c:if>