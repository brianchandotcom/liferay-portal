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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<%
SitesUtil.addPortletBreadcrumbEntries(group, pagesName, redirectURL, request, renderResponse);
%>

<liferay-ui:error exception="<%= LayoutTypeException.class %>">

	<%
	LayoutTypeException lte = (LayoutTypeException)errorException;

	String type = BeanParamUtil.getString(selLayout, request, "type");
	%>

	<c:if test="<%= lte.getType() == LayoutTypeException.FIRST_LAYOUT %>">
		<liferay-ui:message arguments='<%= Validator.isNull(lte.getLayoutType()) ? type : "layout.types." + lte.getLayoutType() %>' key="the-first-page-cannot-be-of-type-x" />
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

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.MY_SITES) || portletName.equals(PortletKeys.GROUP_PAGES) || portletName.equals(PortletKeys.MY_PAGES) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USER_GROUPS_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN) %>">
		<c:if test="<%= portletName.equals(PortletKeys.MY_SITES) || (portletName.equals(PortletKeys.GROUP_PAGES) && !layout.isTypeControlPanel()) || portletName.equals(PortletKeys.SITES_ADMIN) || portletName.equals(PortletKeys.USER_GROUPS_ADMIN) || portletName.equals(PortletKeys.USERS_ADMIN) %>">
			<liferay-ui:header
				backURL="<%= backURL %>"
				escapeXml="<%= false %>"
				localizeTitle="<%= false %>"
				title="<%= HtmlUtil.escape(liveGroup.getDescriptiveName(locale)) %>"
			/>
		</c:if>

		<%
		String tabs1URL = redirectURL.toString();

		if (liveGroup.isUser()) {
			PortletURL userTabs1URL = renderResponse.createRenderURL();

			userTabs1URL.setParameter("struts_action", "/my_pages/edit_layouts");
			userTabs1URL.setParameter("tabs1", tabs1);
			userTabs1URL.setParameter("backURL", backURL);
			userTabs1URL.setParameter("groupId", String.valueOf(liveGroupId));

			tabs1URL = userTabs1URL.toString();
		}
		%>

		<liferay-ui:tabs
			names="<%= tabs1Names %>"
			param="tabs1"
			url="<%= tabs1URL %>"
			value="<%= tabs1 %>"
		/>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), redirectURL.toString());
		%>

	</c:when>
	<c:otherwise>
		<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" showPortletBreadcrumb="<%= true %>" />
	</c:otherwise>
</c:choose>

<div class="container-fluid">
	<div class="lfr-app-column-view manage-view row-fluid">
		<c:if test="<%= !group.isLayoutPrototype() %>">
			<div class="span3">
				<c:if test="<%= stagingGroup != null %>">

					<%
					long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

					if (layoutSetBranchId <= 0) {
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
							layoutSetBranch = LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(stagingGroup.getGroupId(), privateLayout);
						}
						catch (NoSuchLayoutSetBranchException nslsbe) {
						}
					}

					List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);
					%>

					<c:choose>
						<c:when test="<%= layoutSetBranches.size() > 1 %>">
							<aui:nav-bar>
								<aui:nav>
									<aui:nav-item dropdown="<%= true %>" label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>">

										<%
										for (int i = 0; i < layoutSetBranches.size(); i++) {
											LayoutSetBranch curLayoutSetBranch = layoutSetBranches.get(i);

											boolean selected = (curLayoutSetBranch.getLayoutSetBranchId() == layoutSetBranch.getLayoutSetBranchId());
										%>

											<portlet:actionURL var="layoutSetBranchURL">
												<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
												<portlet:param name="<%= Constants.CMD %>" value="select_layout_set_branch" />
												<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
												<portlet:param name="groupId" value="<%= String.valueOf(curLayoutSetBranch.getGroupId()) %>" />
												<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
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

					<liferay-ui:staging cssClass="manage-pages-branch-menu" extended="<%= true %>" groupId="<%= groupId %>" icon="/common/tool.png" message="" privateLayout="<%= privateLayout %>" selPlid="<%= selPlid %>" showManageBranches="<%= true %>"  />
				</c:if>

				<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
					<liferay-util:param name="treeId" value="layoutsTree" />
				</liferay-util:include>
			</div>
		</c:if>

		<div class='<%= !group.isLayoutPrototype() ? "span9" : "span12" %>'>
			<div id="<portlet:namespace />layoutsContainer">
				<c:choose>
					<c:when test="<%= selPlid > 0 %>">
						<liferay-util:include page="/html/portlet/layouts_admin/edit_layout.jsp" />
					</c:when>
					<c:otherwise>
						<liferay-util:include page="/html/portlet/layouts_admin/edit_layout_set.jsp" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>

<c:if test="<%= !group.isLayoutPrototype() %>">
	<aui:script use="aui-io-plugin-deprecated">
		var layoutsContainer = A.one('#<portlet:namespace />layoutsContainer');

		layoutsContainer.plug(
			A.Plugin.IO,
			{
				autoLoad: false
			}
		);

		A.one('#<portlet:namespace />layoutsTreeOutput').delegate(
			'click',
			function(event) {
				event.preventDefault();

				var link = event.currentTarget.one('a');

				if (link && !event.target.hasClass('tree-hitarea')) {
					var href = link.attr('href');

					var hash = location.hash;

					var prefix = '#_LFR_FN_<portlet:namespace />';
					var historyKey = '';

					if (hash.indexOf(prefix) != -1) {
						historyKey = hash.replace(prefix, '');
					}

					var requestUri = A.Lang.sub(
						href,
						{
							historyKey: historyKey
						}
					);

					layoutsContainer.io.set('uri', requestUri);

					if (layoutsContainer.ParseContent) {
						layoutsContainer.ParseContent.get('queue').stop();
					}

					layoutsContainer.io.start();
				}
			},
			'.tree-node-content'
		);
	</aui:script>
</c:if>