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

	<c:if test="<%= lte.getType() == LayoutTypeException.NOT_INSTANCEABLE %>">
		<liferay-ui:message arguments="<%= type %>" key="pages-of-type-x-cannot-be-selected" />
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
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

boolean showHeader = ParamUtil.getBoolean(request, "showHeader");
%>

<c:if test="<%= !selGroup.isLayoutSetPrototype() || showHeader %>">

	<%
	Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();
	%>

	<c:if test="<%= showHeader %>">
		<liferay-ui:header
			escapeXml="<%= false %>"
			localizeTitle="<%= false %>"
			title="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>"
		/>
	</c:if>

	<liferay-ui:tabs
		names="<%= layoutsAdminDisplayContext.getTabs1Names() %>"
		param="tabs1"
		url="<%= String.valueOf(layoutsAdminDisplayContext.getRedirectURL()) %>"
		value="<%= layoutsAdminDisplayContext.getTabs1() %>"
	/>
</c:if>

<div class="container-fluid">
	<div class="lfr-app-column-view manage-view row">
		<c:if test="<%= !group.isLayoutPrototype() %>">
			<div class="col-md-3">

				<%
				Group stagingGroup = layoutsAdminDisplayContext.getStagingGroup();
				%>

				<c:if test="<%= stagingGroup.isStaged() %>">

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
							<aui:nav-bar>
								<aui:nav cssClass="navbar-nav">
									<aui:nav-item dropdown="<%= true %>" label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>">

										<%
										for (int i = 0; i < layoutSetBranches.size(); i++) {
											LayoutSetBranch curLayoutSetBranch = layoutSetBranches.get(i);

											boolean selected = (curLayoutSetBranch.getLayoutSetBranchId() == layoutSetBranch.getLayoutSetBranchId());
										%>

											<portlet:renderURL var="layoutSetBranchURL">
												<portlet:param name="mvcPath" value="/view.jsp" />
												<portlet:param name="redirect" value="<%= String.valueOf(layoutsAdminDisplayContext.getRedirectURL()) %>" />
												<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
												<portlet:param name="privateLayout" value="<%= String.valueOf(layoutsAdminDisplayContext.isPrivateLayout()) %>" />
												<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(curLayoutSetBranch.getLayoutSetBranchId()) %>" />
											</portlet:renderURL>

											<aui:nav-item cssClass='<%= selected ? "disabled" : StringPool.BLANK %>' href="<%= selected ? null : layoutSetBranchURL %>" label="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>" />

										<%
										}
										%>

									</aui:nav-item>
								</aui:nav>
							</aui:nav-bar>
						</c:when>
					</c:choose>

					<%
					request.setAttribute(WebKeys.PRIVATE_LAYOUT, layoutsAdminDisplayContext.isPrivateLayout());
					%>

					<liferay-staging:menu cssClass="manage-pages-branch-menu" extended="<%= true %>" icon="/common/tool.png" message="" selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>" showManageBranches="<%= true %>"  />
				</c:if>

				<%
				String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");
				%>

				<liferay-ui:layouts-tree
					groupId="<%= selGroup.getGroupId() %>"
					portletURL="<%= layoutsAdminDisplayContext.getEditLayoutURL() %>"
					privateLayout="<%= layoutsAdminDisplayContext.isPrivateLayout() %>"
					rootNodeName="<%= layoutsAdminDisplayContext.getRootNodeName() %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					selPlid="<%= layoutsAdminDisplayContext.getSelPlid() %>"
					treeId="layoutsTree"
				/>
			</div>
		</c:if>

		<div class='<%= !group.isLayoutPrototype() ? "col-md-9" : "col-md-12" %>'>
			<c:choose>
				<c:when test="<%= layoutsAdminDisplayContext.getSelPlid() > 0 %>">
					<liferay-util:include page="/edit_layout.jsp" servletContext="<%= application %>" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/edit_layout_set.jsp" servletContext="<%= application %>" />
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>