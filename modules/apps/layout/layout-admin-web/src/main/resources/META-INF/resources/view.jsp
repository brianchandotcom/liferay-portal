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
Group selGroup = layoutsAdminDisplayContext.getSelGroup();

boolean showHeader = ParamUtil.getBoolean(request, "showHeader");
%>

<c:if test="<%= showHeader %>">

	<%
	Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();
	%>

	<liferay-ui:header
		escapeXml="<%= false %>"
		localizeTitle="<%= false %>"
		title="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>"
	/>
</c:if>

<div class="container-fluid-1280">
	<div class="lfr-app-column-view manage-view row">
		<c:if test="<%= !group.isLayoutPrototype() %>">
			<div class="col-md-3">

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