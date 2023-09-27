<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SiteAdminDisplayContext siteAdminDisplayContext = new SiteAdminDisplayContext(request, liferayPortletRequest, liferayPortletResponse);

Group group = siteAdminDisplayContext.getGroup();

if (group != null) {
	portletDisplay.setShowBackIcon(true);

	portletDisplay.setURLBack(
		PortletURLBuilder.createRenderURL(
			renderResponse
		).setMVCPath(
			"/view.jsp"
		).setParameter(
			"groupId", group.getParentGroupId()
		).buildString());

	renderResponse.setTitle(group.getDescriptiveName(locale));
}

SiteAdminManagementToolbarDisplayContext siteAdminManagementToolbarDisplayContext = new SiteAdminManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, siteAdminDisplayContext);
%>

<clay:management-toolbar
	managementToolbarDisplayContext="<%= siteAdminManagementToolbarDisplayContext %>"
	propsTransformer="js/SiteManagementToolbarPropsTransformer"
/>

<div class="closed sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/site_admin/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="sites"
		title='<%= LanguageUtil.get(request, "sites") %>'
	>
		<liferay-util:include page="/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<clay:container-fluid
		cssClass="sidenav-content"
	>
		<portlet:actionURL name="/site_admin/delete_groups" var="deleteGroupsURL" />

		<aui:form action="<%= deleteGroupsURL %>" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<liferay-site-navigation:breadcrumb
				breadcrumbEntries="<%= siteAdminDisplayContext.getBreadcrumbEntries() %>"
			/>

			<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

				<%
				Group curGroup = GroupLocalServiceUtil.fetchGroup(scopeGroupId);

				NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

				String message = nslse.getMessage();

				int index = message.indexOf("{");

				if (index > 0) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(message.substring(index));

					curGroup = GroupLocalServiceUtil.fetchGroup(jsonObject.getLong("groupId"));
				}
				%>

				<c:if test="<%= curGroup != null %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(curGroup.getDescriptiveName(locale)) %>" key="site-x-does-not-have-any-private-pages" translateArguments="<%= false %>" />
				</c:if>
			</liferay-ui:error>

			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteCurrentGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-you-are-accessing-the-site" />
			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteGroupThatHasChild.class %>" message="you-cannot-delete-sites-that-have-subsites" />
			<liferay-ui:error exception="<%= RequiredGroupException.MustNotDeleteSystemGroup.class %>" message="the-site-cannot-be-deleted-or-deactivated-because-it-is-a-required-system-site" />

			<%
			request.setAttribute(SiteAdminDisplayContext.class.getName(), siteAdminDisplayContext);
			request.setAttribute(SiteAdminManagementToolbarDisplayContext.class.getName(), siteAdminManagementToolbarDisplayContext);
			%>

			<liferay-util:include page="/view_entries.jsp" servletContext="<%= application %>" />
		</aui:form>
	</clay:container-fluid>
</div>