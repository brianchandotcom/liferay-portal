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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group group = layoutsAdminDisplayContext.getGroup();

SitesUtil.addPortletBreadcrumbEntries(group, layoutsAdminDisplayContext.getPagesName(), layoutsAdminDisplayContext.getRedirectURL(), request, renderResponse);
%>

<liferay-ui:error exception="<%= LayoutTypeException.class %>">

	<%
	LayoutTypeException lte = (LayoutTypeException)errorException;

	String type = BeanParamUtil.getString(layoutsAdminDisplayContext.getSelLayout(), request, "type");
	%>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
		<liferay-ui:message arguments='<%= Validator.isNull(lte.getLayoutType()) ? type : "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
	</c:if>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT_PERMISSION %>">
		<liferay-ui:message key="you-cannot-delete-this-page-because-the-next-page-is-not-vieweable-by-unathenticated-users-and-so-cannot-be-the-first-page" />
	</c:if>

	<c:if test="<%= lte.getType() == LayoutTypeException.NOT_PARENTABLE %>">
		<liferay-ui:message arguments="<%= type %>" key="pages-of-type-x-cannot-have-child-pages" />
	</c:if>
</liferay-ui:error>

<liferay-ui:error exception="<%= LayoutNameException.class %>" message="please-enter-a-valid-name" />

<liferay-ui:error exception="<%= RequiredLayoutException.class %>">

	<%
	RequiredLayoutException rle = (RequiredLayoutException)errorException;
	%>

	<c:if test="<%= rle.getType() == RequiredLayoutException.AT_LEAST_ONE %>">
		<liferay-ui:message key="you-must-have-at-least-one-page" />
	</c:if>
</liferay-ui:error>

<%
Group selGroup = layoutsAdminDisplayContext.getSelGroup();
%>

<c:choose>
	<c:when test="<%= !selGroup.isLayoutSetPrototype() && (portletName.equals(PortletKeys.MY_SITES) || portletName.equals(PortletKeys.GROUP_PAGES) || portletName.equals(PortletKeys.MY_PAGES) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USER_GROUPS_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN)) %>">

		<%
		Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();
		%>

		<c:if test="<%= portletName.equals(PortletKeys.MY_SITES) || (portletName.equals(PortletKeys.GROUP_PAGES) && !layout.isTypeControlPanel()) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USER_GROUPS_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN) %>">
			<liferay-ui:header
				backURL="<%= layoutsAdminDisplayContext.getBackURL() %>"
				escapeXml="<%= false %>"
				localizeTitle="<%= false %>"
				title="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>"
			/>
		</c:if>

		<%
		String tabs1URL = String.valueOf(layoutsAdminDisplayContext.getRedirectURL());

		if (liveGroup.isUser()) {
			PortletURL userTabs1URL = renderResponse.createRenderURL();

			userTabs1URL.setParameter("struts_action", "/my_pages/edit_layouts");
			userTabs1URL.setParameter("tabs1", layoutsAdminDisplayContext.getTabs1());
			userTabs1URL.setParameter("backURL", layoutsAdminDisplayContext.getBackURL());
			userTabs1URL.setParameter("groupId", String.valueOf(layoutsAdminDisplayContext.getLiveGroupId()));

			tabs1URL = userTabs1URL.toString();
		}
		%>

		<liferay-ui:tabs
			names="<%= layoutsAdminDisplayContext.getTabs1Names() %>"
			param="tabs1"
			url="<%= tabs1URL %>"
			value="<%= layoutsAdminDisplayContext.getTabs1() %>"
		/>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(layoutsAdminDisplayContext.getTabs1(), TextFormatter.O)), String.valueOf(layoutsAdminDisplayContext.getRedirectURL()));
		%>

	</c:when>
	<c:otherwise>
		<liferay-ui:breadcrumb displayStyle="horizontal" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</c:otherwise>
</c:choose>

<div class="container-fluid">
	<div class="lfr-app-column-view manage-view row">
		<c:if test="<%= !group.isLayoutPrototype() %>">
			<div class="col-md-3">

				<%
				Group stagingGroup = layoutsAdminDisplayContext.getStagingGroup();
				%>

				<c:if test="<%= stagingGroup.isStagingGroup() %>">

					<%
					long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

					if (layoutSetBranchId <= 0) {
						LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

						layoutSetBranchId = StagingUtil.getRecentLayoutSetBranchId(user, selLayoutSet.getLayoutSetId());
					}

					LayoutSetBranch layoutSetBranch = null;

					if (layoutSetBranchId > 0) {
						try {
							layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);
						}
						catch (NoSuchLayoutSetBranchException nslsbe) {
						}
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
							<aui:nav-bar>
								<aui:nav cssClass="navbar-nav">
									<aui:nav-item dropdown="<%= true %>" label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>">

										<%
										for (int i = 0; i < layoutSetBranches.size(); i++) {
											LayoutSetBranch curLayoutSetBranch = layoutSetBranches.get(i);

											boolean selected = (curLayoutSetBranch.getLayoutSetBranchId() == layoutSetBranch.getLayoutSetBranchId());
										%>

											<portlet:actionURL var="layoutSetBranchURL">
												<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
												<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
												<portlet:param name="redirect" value="<%= String.valueOf(layoutsAdminDisplayContext.getRedirectURL()) %>" />
												<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
												<portlet:param name="privateLayout" value="<%= String.valueOf(layoutsAdminDisplayContext.isPrivateLayout()) %>" />
												<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
											</portlet:actionURL>

											<aui:nav-item cssClass='<%= selected ? "disabled" : StringPool.BLANK %>' href="<%= selected ? null : layoutSetBranchURL %>" label="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>" />

										<%
										}
										%>

									</aui:nav-item>
								</aui:nav>
							</aui:nav-bar>
						</c:when>
					</c:choose>

					<liferay-staging:menu cssClass="manage-pages-branch-menu" extended="<%= true %>" icon="/common/tool.png" message="" selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>" showManageBranches="<%= true %>"  />
				</c:if>

				<%
				String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");
				%>

				<liferay-ui:layouts-tree
					groupId="<%= layoutsAdminDisplayContext.getGroupId() %>"
					portletURL="<%= layoutsAdminDisplayContext.getEditLayoutURL() %>"
					privateLayout="<%= layoutsAdminDisplayContext.isPrivateLayout() %>"
					rootNodeName="<%= layoutsAdminDisplayContext.getRootNodeName() %>"
					selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					treeId="layoutsTree"
				/>
			</div>
		</c:if>

		<div class='<%= !group.isLayoutPrototype() ? "col-md-9" : "col-md-12" %>'>
			<c:choose>
				<c:when test="<%= layoutsAdminDisplayContext.getSelPlid() > 0 %>">
					<liferay-util:include page="/html/portlet/layouts_admin/edit_layout.jsp" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/html/portlet/layouts_admin/edit_layout_set.jsp" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>